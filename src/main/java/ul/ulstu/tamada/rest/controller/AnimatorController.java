package ul.ulstu.tamada.rest.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ul.ulstu.tamada.authorization.AllowedUserRoles;
import ul.ulstu.tamada.configuration.enums.UserRole;
import ul.ulstu.tamada.rest.dto.animator.AnimatorDto;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithPhotoResponse;
import ul.ulstu.tamada.rest.dto.animator.AnimatorsWithScheduleResponse;
import ul.ulstu.tamada.service.animator.IAnimatorFacade;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("${base_uri}/animators/v1")
public class AnimatorController {

    private final IAnimatorFacade animatorFacade;

    public AnimatorController(
            IAnimatorFacade animatorFacade
    ) {
        this.animatorFacade = animatorFacade;
    }

    @GetMapping
    @ApiOperation("Получение списка аниматоров с фотографиями")
    public ResponseEntity<AnimatorsWithPhotoResponse> getAnimatorsWithPhoto() {
        var response = animatorFacade.getAnimatorsWithPhoto();
        return ResponseEntity.ok(response);
    }

    @AllowedUserRoles(roles = {UserRole.CUSTOMER, UserRole.ADMIN})
    @GetMapping("/schedules")
    @ApiOperation("Получение списка аниматоров с расписанием")
    public ResponseEntity<AnimatorsWithScheduleResponse> getAnimatorsWithSchedule() {
        var response = animatorFacade.getAnimatorsWithSchedule();
        return ResponseEntity.ok(response);
    }

    @AllowedUserRoles(roles = {UserRole.ADMIN})
    @PostMapping
    @ApiOperation("Добавление нового аниматора в систему")
    public ResponseEntity<Void> createAnimator(
            @RequestBody AnimatorDto animator
    ) {
        animatorFacade.createOrUpdate(
                Optional.empty(),
                animator
        );
        return ResponseEntity.ok().build();
    }

    @AllowedUserRoles(roles = {UserRole.ADMIN})
    @PutMapping("/{animatorId}")
    @ApiOperation("Изменение аниматора в системе")
    public ResponseEntity<Void> updateAnimator(
            @PathVariable Long animatorId,
            @RequestBody @Valid AnimatorDto animator
    ) {
        animatorFacade.createOrUpdate(
                Optional.ofNullable(animatorId),
                animator
        );
        return ResponseEntity.ok().build();
    }

    @AllowedUserRoles(roles = {UserRole.ADMIN})
    @DeleteMapping("/{animatorId}")
    @ApiOperation("Перевод аниматора в статус 'в архиве'")
    public ResponseEntity<Void> updateAnimator(
            @PathVariable Long animatorId
    ) {
        animatorFacade.archiveAnimator(animatorId);
        return ResponseEntity.ok().build();
    }
}