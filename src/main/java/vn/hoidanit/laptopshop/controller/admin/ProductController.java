package vn.hoidanit.laptopshop.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadImageService;

@Controller
public class ProductController {

	private final ProductService productService;
	private final UploadImageService uploadImageService;

	public ProductController(ProductService productService, UploadImageService uploadImageService) {
		this.productService = productService;
		this.uploadImageService = uploadImageService;
	}

	@GetMapping("/admin/product")
	public String getProduct(Model model, @RequestParam("page") Optional<String> pageOptional) {
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
		Pageable pageable = PageRequest.of(currentPage - 1, 3);

		// lấy tất cả product theo kiểu Pageable đã được phân trang, mỗi trang 3 sản
		// phẩm
		Page<Product> pageProducts = this.productService.getAllProduct(pageable);
		// convert Page sang List
		List<Product> listProducts = pageProducts.getContent();

		// Số lượng trang hiển thị tối đa
		int maxDisplayPages = 5;
		// đảm bảo trang bắt đầu ko < 1
		int startPage = Math.max(1, currentPage - 2);
		// đảm bảo trang kết thúc luôn < totalPages
		int endPage = Math.min(startPage + maxDisplayPages - 1, pageProducts.getTotalPages());

		// Điều chỉnh lại chỉ số cho trang bắt đầu
		if (endPage - startPage + 1 < maxDisplayPages) {
			startPage = Math.max(1, endPage - maxDisplayPages + 1);
		}

		// Truyền startPage và endPage về view
		model.addAttribute("products", listProducts);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);

		return "admin/product/ListProduct";
	}

	@GetMapping("/admin/product/create") // GET
	public String getCreateProductPage(Model model) {
		model.addAttribute("newProduct", new Product());
		return "admin/product/CreateNewProduct";
	}

	@PostMapping("/admin/product/create")
	public String createProductPage(Model model, @ModelAttribute("newProduct") @Valid Product product,
			BindingResult newUserBindingResult, @RequestParam("imageFile") MultipartFile file) throws IOException {

		// Validate thông tin tạo
		List<FieldError> errors = newUserBindingResult.getFieldErrors();
		for (FieldError error : errors) {
			System.out.println(error.getField() + " - " + error.getDefaultMessage());
		}

		if (newUserBindingResult.hasErrors()) {
			return "admin/product/CreateNewProduct";
		}

		// Đảm bảo file ko null để product image hoặc là có trị hoặc là ko, giúp đảm bảo
		// việc
		// xuất ảnh bên ShowProduct.jsp
		String productImage = null;
		if (file != null && !file.isEmpty()) {
			productImage = this.uploadImageService.handleSaveUploadFile(file, "productImage");
		}

		product.setImage(productImage);

		// save
		this.productService.handleSaveProduct(product);

		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/{id}")
	public String getProductDetailPage(Model model, @PathVariable long id) {
		Product product = this.productService.getInfoProductById(id);
		model.addAttribute("infoProduct", product);

		return "admin/product/ShowProduct";
	}

	@GetMapping("/admin/product/update/{id}") // GET
	public String getUpdateProductPage(Model model, @PathVariable long id) {
		Product currentProduct = this.productService.getInfoProductById(id);

		model.addAttribute("newProduct", currentProduct);
		model.addAttribute("productImage", currentProduct.getImage());

		return "admin/product/UpdateProduct";
	}

	@PostMapping("/admin/product/update")
	public String postUpdateProduct(Model model, @ModelAttribute("newProduct") @Valid Product product,
			BindingResult newProductBindingResult, @RequestParam("imageFile") MultipartFile file) throws IOException {

		// Validate thông tin tạo
		List<FieldError> errors = newProductBindingResult.getFieldErrors();
		for (FieldError error : errors) {
			System.out.println(error.getField() + " - " + error.getDefaultMessage());
		}

		if (newProductBindingResult.hasErrors()) {
			return "admin/product/UpdateProduct";
		}

		Product currentProduct = this.productService.getInfoProductById(product.getId());

		System.out.println(product.getId());
		System.out.println(currentProduct);
		// Đảm bảo file ko null để product image hoặc là có trị hoặc là ko, giúp đảm bảo
		// việc
		// xuất ảnh bên UpdateUser.jsp
		String productImage = null;
		if (file != null && !file.isEmpty()) {
			productImage = this.uploadImageService.handleSaveUploadFile(file, "productImage");
		}

		if (currentProduct != null) {
			currentProduct.setName(product.getName());
			currentProduct.setPrice(product.getPrice());
			currentProduct.setShortDesc(product.getShortDesc());
			currentProduct.setDetailDesc(product.getDetailDesc());
			currentProduct.setFactory(product.getFactory());
			currentProduct.setTarget(product.getTarget());
			currentProduct.setQuantity(product.getQuantity());
			if (productImage != null) {
				currentProduct.setImage(productImage);
			}

			this.productService.handleSaveProduct(currentProduct);
		}

		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String getDeleteProductPage(Model model, @PathVariable long id) {
		model.addAttribute("id", id);
		model.addAttribute("newProduct", new Product());

		return "admin/product/DeleteProduct";
	}

	@PostMapping("/admin/product/delete")
	public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
		this.productService.deleteProductById(product.getId());
		return "redirect:/admin/product";
	}
}
