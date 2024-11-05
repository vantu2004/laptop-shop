package vn.hoidanit.laptopshop.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import vn.hoidanit.laptopshop.service.validator.RegisterChecked;

//	annotation tự định nghĩa
@RegisterChecked
public class RegisterDTO {

	@NotEmpty(message = "Không được để trống thông tin")
	@Size(min = 2, message = "FirstName phải nhiều hơn 2 ký tự")
	private String firstName;

	@NotEmpty(message = "Không được để trống thông tin")
	@Size(min = 2, message = "FirstName phải nhiều hơn 2 ký tự")
	private String lastName;

	@NotEmpty(message = "Không được để trống thông tin")
	private String email;

	@NotEmpty(message = "Không được để trống thông tin")
	private String password;

	@NotEmpty(message = "Không được để trống thông tin")
	private String confirmPassword;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "RegisterDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
				+ password + ", confirmPassword=" + confirmPassword + "]";
	}

}
