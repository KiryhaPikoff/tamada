package ul.ulstu.tamada.service.animator;

import com.nimbusds.jose.util.Base64;
import org.apache.tika.Tika;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ul.ulstu.tamada.exception.AnimatorNotFoundException;
import ul.ulstu.tamada.model.Animator;
import ul.ulstu.tamada.model.File;
import ul.ulstu.tamada.model.enums.AnimatorStatus;
import ul.ulstu.tamada.model.enums.FileType;
import ul.ulstu.tamada.repository.IAnimatorRepository;
import ul.ulstu.tamada.rest.dto.animator.*;
import ul.ulstu.tamada.service.animator.schedule.IScheduleService;
import ul.ulstu.tamada.service.file.IFileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimatorFacade implements IAnimatorFacade {

    private final IScheduleService scheduleService;
    private final ConversionService conversionService;
    private final IAnimatorRepository animatorRepository;
    private final IFileService fileService;

    public AnimatorFacade(
            IScheduleService scheduleService,
            ConversionService conversionService,
            IAnimatorRepository animatorRepository,
            IFileService fileService
    ) {
        this.scheduleService = scheduleService;
        this.conversionService = conversionService;
        this.animatorRepository = animatorRepository;
        this.fileService = fileService;
    }

    @Override
    public AnimatorsWithPhotoResponse getAnimatorsWithPhoto() {
       var response = new AnimatorsWithPhotoResponse();

       var inStatuses = List.of(AnimatorStatus.ACTIVE);
       var animators = animatorRepository.findAllByStatusIn(inStatuses).stream()
               .map(animator -> conversionService.convert(animator, AnimatorWithPhotoResponse.class))
               .peek(animatorWithPhoto -> {
                   var image = fileService.findByName(animatorWithPhoto.getId().toString(), FileType.ANIMATOR_PHOTO);
                   var fileMimeType = new Tika().detect(image);
                   animatorWithPhoto.setImage("data:" + fileMimeType + ";base64,"+Base64.encode(image).toString());
               })
               .collect(Collectors.toList());

        response.setAnimators(animators);
        return response;
    }

    @Override
    public AnimatorsWithScheduleResponse getAnimatorsWithSchedule() {
        return scheduleService.createAnimatorsSchedule();
    }

    @Override
    @Transactional
    public void createOrUpdate(Optional<Long> animatorId, AnimatorDto animatorDto) {
        Animator animator;
        if (animatorId.isPresent()) {
            animator = animatorRepository.findById(animatorId.get())
                    .orElseThrow(AnimatorNotFoundException::new);
            animator.setName(animatorDto.getName());
            animator.setAge(animatorDto.getAge());
            animator.setDescription(animatorDto.getDescription());
            animator.setMotto(animatorDto.getMotto());
            animator.setPrice(animatorDto.getPrice());
        } else {
            animator = conversionService.convert(animatorDto, Animator.class);
        }

        animator.setStatus(AnimatorStatus.ACTIVE);

        animatorRepository.saveAndFlush(animator);

        var animatorPhoto = new File();
        animatorPhoto.setIdent(animator.getId().toString());
        animatorPhoto.setData(animatorDto.getImage());
        animatorPhoto.setType(FileType.ANIMATOR_PHOTO);

        fileService.save(animatorPhoto);
    }

    @Override
    public void archiveAnimator(Long animatorId) {
        var animator = animatorRepository.findById(animatorId)
                .orElseThrow(AnimatorNotFoundException::new);
        animator.setStatus(AnimatorStatus.ARCHIVED);
        animatorRepository.save(animator);
    }
}
