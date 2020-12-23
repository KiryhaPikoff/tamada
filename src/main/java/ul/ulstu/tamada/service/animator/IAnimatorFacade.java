package ul.ulstu.tamada.service.animator;

import ul.ulstu.tamada.rest.dto.animator.AnimatorDto;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithPhotoResponse;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithScheduleResponse;

import java.util.Optional;

public interface IAnimatorFacade {

    AnimatorsWithPhotoResponse getAnimatorsWithPhoto();

    AnimatorsWithScheduleResponse getAnimatorsWithSchedule();

    void createOrUpdate(Optional<Long> animatorId, AnimatorDto animator);

    void archiveAnimator(Long animatorId);
}
