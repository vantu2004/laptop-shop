package vn.hoidanit.laptopshop.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@GetMapping("/admin/user")
	public String getUserPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
		// currentPage mặc định = 1, dùng optional để đề phòng người dùng sửa chỉ số
		// trang thành chuỗi bất kỳ
		int currentPage = 1;
		try {
			if (pageOptional.isPresent()) {
				currentPage = Integer.parseInt(pageOptional.get());
			}
		} catch (Exception ex) {

		}
		
		// trang bắt đầu khi dùng pageable là 0
		Pageable pageable = PageRequest.of(currentPage - 1, 1);

		// lấy tất cả product theo kiểu Pageable đã được phân trang, mỗi trang 3 sản
		// phẩm
		Page<User> pageUsers = this.userService.getAllUser(pageable);
		// convert Page sang List
		List<User> listUsers = pageUsers.getContent();

		// Số lượng trang hiển thị tối đa
		int maxDisplayPages = 5;
		// đảm bảo trang bắt đầu ko < 1
		int startPage = Math.max(1, currentPage - 2);
		// đảm bảo trang kết thúc luôn < totalPages
		int endPage = Math.min(startPage + maxDisplayPages - 1, pageUsers.getTotalPages());

		// Điều chỉnh lại chỉ số cho trang bắt đầu
		if (endPage - startPage + 1 < maxDisplayPages) {
			startPage = Math.max(1, endPage - maxDisplayPages + 1);
		}

		// Truyền startPage và endPage về view
		model.addAttribute("users", listUsers);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		
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
