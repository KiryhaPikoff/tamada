package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import ul.ulstu.tamada.model.Animator;
import ul.ulstu.tamada.rest.dto.animator.AnimatorWithPhotoResponse;
import ul.ulstu.tamada.rest.dto.animator.AnimatorWithScheduleResponse;

@Component
public class AnimatorToAnimatorWithScheduleResponseConverter extends BaseConverter<Animator, AnimatorWithScheduleResponse> {

    @Override
    public AnimatorWithScheduleResponse convert(Animator animator) {
        var animatorWithSchedule = new AnimatorWithScheduleResponse();

        animatorWithSchedule.setId(animator.getId());
        animatorWithSchedule.setName(animator.getName());

        return animatorWithSchedule;
    }
}
