package ul.ulstu.tamada.service.animator.schedule;

import ul.ulstu.tamada.rest.dto.animator.AnimatorWithScheduleResponse;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithScheduleResponse;

public interface IScheduleService {

    AnimatorWithScheduleResponse createAnimatorSchedule(Long animatorId);

    AnimatorsWithScheduleResponse createAnimatorsSchedule();
}
