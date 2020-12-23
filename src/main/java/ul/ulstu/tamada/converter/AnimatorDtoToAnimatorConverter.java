package ul.ulstu.tamada.converter;

import org.springframework.stereotype.Component;
import ul.ulstu.tamada.model.Animator;
import ul.ulstu.tamada.rest.dto.animator.AnimatorDto;

@Component
public class AnimatorDtoToAnimatorConverter extends BaseConverter<AnimatorDto, Animator> {

    @Override
    public Animator convert(AnimatorDto animatorDto) {
        var animator = new Animator();

        animator.setName(animatorDto.getName());
        animator.setAge(animatorDto.getAge());
        animator.setDescription(animatorDto.getDescription());
        animator.setMotto(animatorDto.getMotto());
        animator.setPrice(animatorDto.getPrice());

        return animator;
    }
}
