package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import ul.ulstu.tamada.model.Animator;
import ul.ulstu.tamada.rest.dto.animator.AnimatorWithPhotoResponse;

@Component
public class AnimatorToAnimatorWithPhotoResponseConverter extends BaseConverter<Animator, AnimatorWithPhotoResponse> {

    @Override
    public AnimatorWithPhotoResponse convert(Animator animator) {
        var animatorWithPhoto = new AnimatorWithPhotoResponse();

        animatorWithPhoto.setId(animator.getId());
        animatorWithPhoto.setAge(animator.getAge());
        animatorWithPhoto.setDescription(animator.getDescription());
        animatorWithPhoto.setMotto(animator.getMotto());
        animatorWithPhoto.setName(animator.getName());
        animatorWithPhoto.setPrice(animator.getPrice());

        return animatorWithPhoto;
    }
}
