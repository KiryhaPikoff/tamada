package ul.ulstu.tamada.service.animator.schedule;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ul.ulstu.tamada.configuration.properties.AnimatorScheduleProperties;
import ul.ulstu.tamada.exception.AnimatorNotFoundException;
import ul.ulstu.tamada.model.Animator;
import ul.ulstu.tamada.model.Order;
import ul.ulstu.tamada.model.enums.AnimatorStatus;
import ul.ulstu.tamada.model.enums.OrderStatus;
import ul.ulstu.tamada.repository.IAnimatorRepository;
import ul.ulstu.tamada.rest.dto.animator.AnimatorWithScheduleResponse;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithScheduleResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScheduleService implements IScheduleService {

    private final IAnimatorRepository animatorRepository;
    private final ConversionService conversionService;
    private final AnimatorScheduleProperties animatorScheduleProperties;

    public ScheduleService(
            IAnimatorRepository animatorRepository,
            ConversionService conversionService,
            AnimatorScheduleProperties animatorScheduleProperties
    ) {
        this.animatorRepository = animatorRepository;
        this.conversionService = conversionService;
        this.animatorScheduleProperties = animatorScheduleProperties;
    }

    @Override
    public AnimatorWithScheduleResponse createAnimatorSchedule(Long animatorId) {
        var animator = animatorRepository.findById(animatorId)
                .orElseThrow(AnimatorNotFoundException::new);

        return createAnimatorSchedule(animator);
    }

    @Override
    public AnimatorsWithScheduleResponse createAnimatorsSchedule() {
        var inStatuses = List.of(AnimatorStatus.ACTIVE);
        var animatorsWithSchedule = animatorRepository.findAllByStatusIn(inStatuses).stream()
                .map(this::createAnimatorSchedule)
                .collect(Collectors.toList());

        var response = new AnimatorsWithScheduleResponse();
        response.setAnimators(animatorsWithSchedule);
        return response;
    }

    private AnimatorWithScheduleResponse createAnimatorSchedule(Animator animator) {
        var animatorSchedule = conversionService.convert(animator, AnimatorWithScheduleResponse.class);

        var busyDays = animator.getOrders()
                .stream()
                .filter(order -> order.getStatus() != OrderStatus.CANCELED)
                .map(Order::getDate)
                .map(this::convertToLocalDateViaInstant)
                .collect(Collectors.toList());

        var freeDays = LocalDate.now().datesUntil(LocalDate.now().plusDays(animatorScheduleProperties.getScheduleDaysAhead()))
                .collect(Collectors.toList())
                .stream()
                .filter(date -> !busyDays.contains(date))
                .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());

        animatorSchedule.setDates(freeDays);

        return animatorSchedule;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
