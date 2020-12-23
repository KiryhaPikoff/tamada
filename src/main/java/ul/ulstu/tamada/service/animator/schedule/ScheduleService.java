package ul.ulstu.tamada.service.animator.schedule;

import org.springframework.stereotype.Service;
import ul.ulstu.tamada.rest.dto.animator.AnimatorWithScheduleResponse;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithScheduleResponse;

@Service
public class ScheduleService implements IScheduleService {

    @Override
    public AnimatorWithScheduleResponse createAnimatorSchedule(Long animatorId) {
        return null;
    }

    @Override
    public AnimatorsWithScheduleResponse createAnimatorsSchedule() {
        return null;
    }
}
