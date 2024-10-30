package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class HomePageController {
	
	private final ProductService productservice;
	
	public HomePageController(ProductService productservice) {
		this.productservice = productservice;
	}

	@GetMapping("/")
	private String getHomePage(Model model) {
		List<Product> products = productservice.getAllProduct();
		model.addAttribute("products", products);
		
		return "client/homepage/HomePage";
	}
}
