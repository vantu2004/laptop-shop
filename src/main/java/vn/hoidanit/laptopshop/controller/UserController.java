package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

	// Dependency injection
	final UserService userService;

	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public String getHomePage(Model model) {
		List<User> listUsers = this.userService.getAllUser();
		System.out.println(listUsers);

		List<User> allUserEmail = this.userService.getAllUserByEmail("tulevan526@gmail.com");
		System.out.println(allUserEmail);

		return "HelloJSP";
	}

	// Truy cập vào đường dẫn dưới sẽ trả về view CreateNewUser.jsp
	@RequestMapping("/admin/user/create")
	public String getCreateUserPage(Model model) {
		model.addAttribute("newUser", new User());

		return "/admin/user/CreateNewUser";
	}

	// Truy cập vào đường dẫn dưới sẽ trả về view ListUser.jsp
	@RequestMapping("/admin/user")
	public String getUserPage(Model model) {
		List<User> users = this.userService.getAllUser();
		model.addAttribute("users", users);

		return "/admin/user/ListUser";
	}

	@RequestMapping("/admin/user/{id}")
	public String getUserDetailPage(Model model, @PathVariable long id) {
		User infoUser = this.userService.getInfoUserById(id);
		model.addAttribute("infoUser", infoUser);

		return "/admin/user/ShowUser";
	}

	@GetMapping("/admin/user/update/{id}")
	public String getUpdateUser(Model model, @PathVariable long id) {
		User currentInfoUser = this.userService.getInfoUserById(id);
		model.addAttribute("newUser", currentInfoUser);

		return "/admin/user/UpdateUser";
	}

	@PostMapping("/admin/user/update")
	public String postUpdateUser(Model model, @ModelAttribute("newUser") User user) {
		User currentUser = this.userService.getInfoUserById(user.getId());
		System.out.println("old: " + currentUser);
		if (currentUser != null) {
			currentUser.setAddress(user.getAddress());
			currentUser.setFullName(user.getFullName());
			currentUser.setPhone(user.getPhone());

			System.out.println("new: " + currentUser);
			this.userService.handleSaveUser(currentUser);
		}

		// Nếu ko dùng redirect thì chỉ trả về view và ko gọi controller để xử lý logic
		// bên dưới, còn redirect giúp chuyển hướng đến URL mới
		return "redirect:/admin/user";
	}

	// Khi user gửi form tạo user từ view CreateNewUser.jsp thì hàm dưới xử lý thông
	// tin
	@RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
	public String createUserPage(Model model, @ModelAttribute("newUser") User user) {
		this.userService.handleSaveUser(user);

		// Nếu ko dùng redirect thì chỉ trả về view và ko gọi controller để xử lý logic
		// bên dưới, còn redirect giúp chuyển hướng đến URL mới
		return "redirect:/admin/user";
	}

	//	chuyển hướng page xác nhận xóa, gửi kèm id, object User mới
	//	mục tiêu là lấy giá trị id gán sẵn cho thẻ input, khi nhấn submit sẽ tạo 1 object User mới chỉ có đúng thuộc tính id
	@GetMapping("/admin/user/delete/{id}")
	public String getDeleteUser(Model model, @PathVariable long id) {
		model.addAttribute("id", id); 
		model.addAttribute("newUser", new User());

		return "/admin/user/DeleteUser";
	}
	
	//	xử lý xóa dựa vào user vừa đc tạo (chỉ có đúng thuộc tính id)
	@PostMapping("/admin/user/delete") 
	public String postDeleteUser(Model model, @ModelAttribute("newUser") User user) {
		this.userService.deleteUserById(user.getId());
		
		return "redirect:/admin/user"; 
	}
}
