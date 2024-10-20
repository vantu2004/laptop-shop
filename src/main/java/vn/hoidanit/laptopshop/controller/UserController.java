package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
	
	//	Dependency injection
	UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/")
	public String getHomePage(Model model) {
		String dt = this.userService.handleHello();
		model.addAttribute("hello", dt);
		return "HelloJSP";
	}

	@RequestMapping("/admin/user")
	public String getNewUser(Model model) {
		model.addAttribute("newUser", new User());
		return "/admin/user/CreateNewUser";
	}
	
	@RequestMapping(value="/admin/user/home", method=RequestMethod.POST)
	public String redirectHome(Model model, @ModelAttribute("newUser") User temp) {
		System.out.println("run here: " + temp);
		return "HelloJSP";
	}
}
