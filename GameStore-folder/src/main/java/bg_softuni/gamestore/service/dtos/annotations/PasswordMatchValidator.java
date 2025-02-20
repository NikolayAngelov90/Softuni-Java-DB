package bg_softuni.gamestore.service.dtos.annotations;


import bg_softuni.gamestore.service.dtos.UserCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserCreateDto> {

    @Override
    public boolean isValid(UserCreateDto dto, ConstraintValidatorContext context) {
        return dto.getPassword() != null && dto.getPassword().equals(dto.getConfirmPassword());
    }
}
