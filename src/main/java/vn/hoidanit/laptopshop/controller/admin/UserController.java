package vn.hoidanit.laptopshop.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadImageService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

	private final UserService userService;
	private final UploadImageService uploadImageService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, UploadImageService uploadImageService,
			PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.uploadImageService = uploadImageService;
		this.passwordEncoder = passwordEncoder;
	}

//	@GetMapping("/")
//	public String getHomePage(Model model) {
//		List<User> arrUsers = this.userService.getAllUserByEmail("1@gmail.com");
//		System.out.println(arrUsers);
//
//		return "HelloJSP";
//	}

	@GetMapping("/admin/user")
	public String getUserPage(Model model) {
		List<User> users = this.userService.getAllUser();
		model.addAttribute("users", users);
		return "admin/user/ListUser";
	}

	@GetMapping("/admin/user/{id}")
	public String getUserDetailPage(Model model, @PathVariable long id) {
		User user = this.userService.getInfoUserById(id);
		model.addAttribute("infoUser", user);

		return "admin/user/ShowUser";
	}

	@GetMapping("/admin/user/create") // GET
	public String getCreateUserPage(Model model) {
		model.addAttribute("newUser", new User());
		return "admin/user/CreateNewUser";
	}

	@PostMapping("/admin/user/create")
	public String createUserPage(Model model, @ModelAttribute("newUser") @Valid User user,
		BindingResult newUserBindingResult, 
		@RequestParam("imageFile") MultipartFile file) throws IOException {

		// Validate thông tin tạo
		List<FieldError> errors = newUserBindingResult.getFieldErrors();
		for (FieldError error : errors) {
			System.out.println(error.getField() + " - " + error.getDefaultMessage());
		}

		if (newUserBindingResult.hasErrors()) {
			return "admin/user/CreateNewUser";
		}
		
		// Đảm bảo file ko null để avatar hoặc là có trị hoặc là ko, giúp đảm bảo việc
		// xuất ảnh bên ShowUser.jsp
		String avatar = null;
		if (file != null && !file.isEmpty()) {
			avatar = this.uploadImageService.handleSaveUploadFile(file, "avatar");
		}

		// Hash pass
		String hashPassword = this.passwordEncoder.encode(user.getPassword());

		user.setAvatar(avatar);
		user.setPassword(hashPassword);
		// user.getRole().getName()) lấy đối tượng Role dựa vào name rối gán Role đó cho
		// Role của user, khi lưu vào database thì tự hiểu và lưu Id
		user.setRole(this.userService.getRoleByName(user.getRole().getName()));

		// save
		this.userService.handleSaveUser(user);

		return "redirect:/admin/user";
	}

	@GetMapping("/admin/user/update/{id}") // GET
	public String getUpdateUserPage(Model model, @PathVariable long id) {
		User currentUser = this.userService.getInfoUserById(id);

		model.addAttribute("newUser", currentUser);
		model.addAttribute("avatar", currentUser.getAvatar());

		return "admin/user/UpdateUser";
	}

	@PostMapping("/admin/user/update")
	public String postUpdateUser(Model model, @ModelAttribute("newUser") User user,
			@RequestParam("imageFile") MultipartFile file) throws IOException {
		User currentUser = this.userService.getInfoUserById(user.getId());

		// Đảm bảo file ko null để avatar hoặc là có trị hoặc là ko, giúp đảm bảo việc
		// xuất ảnh bên UpdateUser.jsp
		String avatar = null;
		if (file != null && !file.isEmpty()) {
			avatar = this.uploadImageService.handleSaveUploadFile(file, "avatar");
		}

		if (currentUser != null) {
			currentUser.setAddress(user.getAddress());
			currentUser.setFullName(user.getFullName());
			currentUser.setPhone(user.getPhone());
			if (avatar != null) {
				currentUser.setAvatar(avatar);
			}

			this.userService.handleSaveUser(currentUser);
		}

		return "redirect:/admin/user";
	}

	@GetMapping("/admin/user/delete/{id}")
	public String getDeleteUserPage(Model model, @PathVariable long id) {
		model.addAttribute("id", id);
		model.addAttribute("newUser", new User());

		return "admin/user/DeleteUser";
	}

	@PostMapping("/admin/user/delete")
	public String postDeleteUser(Model model, @ModelAttribute("newUser") User user) {
		this.userService.deleteUserById(user.getId());
		return "redirect:/admin/user";
	}
}
