package ul.ulstu.tamada.service.animator.schedule;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ul.ulstu.tamada.exception.AnimatorNotFoundException;
import ul.ulstu.tamada.model.Animator;
import ul.ulstu.tamada.model.enums.AnimatorStatus;
import ul.ulstu.tamada.repository.IAnimatorRepository;
import ul.ulstu.tamada.rest.dto.animator.AnimatorWithScheduleResponse;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithScheduleResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class ScheduleService implements IScheduleService {

    private final IAnimatorRepository animatorRepository;
    private final ConversionService conversionService;

    public ScheduleService(
            IAnimatorRepository animatorRepository,
            ConversionService conversionService
    ) {
        this.animatorRepository = animatorRepository;
        this.conversionService = conversionService;
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

        var dates = LongStream.range(1, 16)
                .filter(value -> value % 3 == 0)
                .map(operand -> LocalDateTime.now().plusDays(operand).toEpochSecond(ZoneOffset.UTC))
                .boxed()
                .collect(Collectors.toList());

        animatorSchedule.setDates(dates);

        return animatorSchedule;
    }
}
