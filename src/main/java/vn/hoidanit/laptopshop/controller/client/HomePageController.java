package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class HomePageController {

	private final ProductService productservice;
	private UserService userservice;
	private final PasswordEncoder passwordEncoder;

	public HomePageController(ProductService productservice, UserService userservice, PasswordEncoder passwordEncoder) {
		this.productservice = productservice;
		this.userservice = userservice;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/")
	private String getHomePage(Model model, HttpServletRequest request) {
		Pageable pageable = PageRequest.of(0, 1000);
		Page<Product> pageProducts = this.productservice.getAllProduct(pageable);
		//	convert Page sang List
		List<Product> listProducts = pageProducts.getContent();
		
		model.addAttribute("products", listProducts);

		return "client/homepage/HomePage";
	}

	@GetMapping("/register")
	private String getRegisterPage(Model model, @ModelAttribute("registerUser") RegisterDTO registerDTO) {

		return "client/auth/Register";
	}

	@PostMapping("/register")
	private String handleRegister(Model model, @ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
			BindingResult bidingresult) {

		// Validate thông tin tạo
		List<FieldError> errors = bidingresult.getFieldErrors();
		for (FieldError error : errors) {
			System.out.println(error.getField() + " - " + error.getDefaultMessage());
		}

		if (bidingresult.hasErrors()) {
			return "client/auth/Register";
		}

		User user = this.userservice.registerDTOtoUser(registerDTO);

		// Hash pass
		String hashPassword = this.passwordEncoder.encode(user.getPassword());

		user.setPassword(hashPassword);
		/*
		 * phải setRole vì role đang tham chiếu đến Role, và 1 người dùng thì phải có
		 * role, mặc dịnh người dùng là USER
		 */
		user.setRole(this.userservice.getRoleByName("USER"));

		this.userservice.handleSaveUser(user);

		return "redirect:/login";
	}

	@GetMapping("/login")
	private String getLoginPage(Model model, @ModelAttribute("registerUser") RegisterDTO registerDTO) {
		return "client/auth/Login";
	}
	
	@GetMapping("/access-deny")
	private String getDenyPage(Model model) {

		return "client/auth/Deny";
	}
}
