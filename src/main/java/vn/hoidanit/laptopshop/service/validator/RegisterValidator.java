package vn.hoidanit.laptopshop.service.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.UserService;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {

	public final UserService userservice;

	public RegisterValidator(UserService userservice) {
		this.userservice = userservice;
	}

	@Override
	public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
		boolean valid = true;

		// Check if password fields match
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			context.buildConstraintViolationWithTemplate("Confirm password không chính xác")
					.addPropertyNode("confirmPassword").addConstraintViolation().disableDefaultConstraintViolation();
			valid = false;
		}

		// Additional validations can be added here

		// Check email
		if (this.userservice.checkEmailExist(user.getEmail())) {
			context.buildConstraintViolationWithTemplate("Email đã tồn tại").addPropertyNode("email")
					.addConstraintViolation().disableDefaultConstraintViolation();
			valid = false;
		}
		return valid;
	}
}
