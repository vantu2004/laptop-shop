package vn.hoidanit.laptopshop.controller.client;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.VNPayService;

@Controller
public class ItemController {

	private ProductService productService;
	private VNPayService vNPayService;

	public ItemController(ProductService productService, VNPayService vNPayService) {
		this.productService = productService;
		this.vNPayService = vNPayService;
	}

//	@GetMapping("/product/{id}")
//	public String getHomePage(Model model, @PathVariable long id) {
//		Product product = this.productService.getInfoProductById(id);
//		model.addAttribute("product", product);
//		model.addAttribute("id", id);
//
//		System.out.println("PRODUCT ID: " + id);
//
//		return "client/product/Detail";
//	}
	@GetMapping("/product/{id}")
	public String getHomePage(Model model, @PathVariable String id) {
	    try {
	        long parsedId = Long.parseLong(id);
	        Product product = this.productService.getInfoProductById(parsedId);
	        model.addAttribute("product", product);
	        model.addAttribute("id", parsedId);

	        System.out.println("PRODUCT ID: " + parsedId);

	        return "client/product/Detail";

	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("ID không hợp lệ: " + id);
	    }
	}

	@PostMapping("/add-product-to-cart/{id}")
	public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
		// kiểm tra đã có session nào chưa, nếu chưa thì thay vì tạo mới thì nó truyền
		// vào false và trả về null
		HttpSession session = request.getSession(false);

		long productId = id;
		// mặc định đang là object nên phải ép
		String email = (String) session.getAttribute("email");
		this.productService.handleAddProductToCart(email, productId, session, 1);

		return "redirect:/";
	}

	// xử lý ở home thì khi thêm product chỉ cần 1 là đc, nhưng trong detail product
	// có thể thêm nhiều -> truyền động
	@PostMapping("/add-product-from-view-detail")
	public String handleAddProductFromViewDetail(@RequestParam("id") long id, @RequestParam("quantity") long quantity,
			HttpServletRequest request) {

		// bug xảy ra vì code bên js chỉ bắt sự kiện cập nhật quantity chỉ khi click,
		// nếu ko click thì sẽ null -> lỗi

		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute("email");
		this.productService.handleAddProductToCart(email, id, session, quantity);

		return "redirect:/product/" + id;
	}

	@GetMapping("/cart")
	public String getCartPage(Model model, HttpServletRequest request) {
		User currentUser = new User();

		HttpSession session = request.getSession(false);
		long id = (long) session.getAttribute("id");
		currentUser.setId(id);

		// phải lấy id từ session rồi gán cho currentUser để có thể join bảng
		// mục đích truyền currentUser là để getCartByUser lấy currentUser join vs Cart
		// và trả về cart
		Cart cart = this.productService.getCartByUser(currentUser);
		// khi gọi getCartDetail thì cart tự join với cartDetail theo id
		// phải check điều kiện vì khi cart rỗng thì ko thể getCartDetail -> lỗi
		List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

		double totalPrice = 0;
		for (var cartDetail : cartDetails) {
			totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
		}

		model.addAttribute("cartDetails", cartDetails);
		model.addAttribute("totalPrice", totalPrice);

		// thuộc tính path cần xuất phát từ 1 đối tượng gốc nên phải truyền cart, nếu
		// dùng list cartDetails thì Cart.jsp ko hiểu
		/*
		 * Khi bạn sử dụng path="cartDetails[${status.index}].id", Spring cần biết
		 * cartDetails thuộc đối tượng nào. Do đó, bạn phải cung cấp đối tượng bao
		 * (trong trường hợp này là Cart) thông qua modelAttribute.
		 */
		model.addAttribute("cart", cart);

		return "client/cart/Cart";
	}

	@PostMapping("/delete-cart-product/{id}")
	public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		long cartDetailId = id;
		this.productService.handleRemoveCartDetail(cartDetailId, session);

		return "redirect:/cart";
	}

	@GetMapping("/checkout")
	public String getCheckoutPage(Model model, HttpServletRequest request) {
		User currentUser = new User();

		HttpSession session = request.getSession(false);
		long id = (long) session.getAttribute("id");
		currentUser.setId(id);

		// phải lấy id từ session rồi gán cho currentUser để có thể join bảng
		// mục đích truyền currentUser là để getCartByUser lấy currentUser join vs Cart
		// và trả về cart
		Cart cart = this.productService.getCartByUser(currentUser);
		// khi gọi getCartDetail thì cart tự join với cartDetail theo id
		// phải check điều kiện vì khi cart rỗng thì ko thể getCartDetail -> lỗi
		List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

		double totalPrice = 0;
		for (var cartDetail : cartDetails) {
			totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
		}

		model.addAttribute("cartDetails", cartDetails);
		model.addAttribute("totalPrice", totalPrice);

		model.addAttribute("cart", cart);

		return "client/cart/Checkout";
	}

	@PostMapping("/confirm-checkout")
	public String getCheckoutPage(@ModelAttribute("cart") Cart cart) {
		// sau khi nhấn Xác nhận thanh toán thì chuyển hướng đến đây với biến cart có
		// cartDetail đã được set lại quantity
		// xử lý lưu vào database để cập nhật lại quantity
		// trường hợp cart null thì ko xử lý gì
		List<CartDetail> cartDetails = cart == null ? new ArrayList() : cart.getCartDetails();
		this.productService.handleUpdateCartBeforeCheckout(cartDetails);

		return "redirect:/checkout";
	}

	@PostMapping("/place-order")
	public String handlePlaceOrder(HttpServletRequest request, @RequestParam("receiverName") String receiverName,
			@RequestParam("receiverAddress") String receiverAddress,
			@RequestParam("receiverPhone") String receiverPhone, @RequestParam("paymentMethod") String paymentMethod,
			@RequestParam("totalPrice") String totalPrice) throws NumberFormatException, UnsupportedEncodingException {

		User currentUser = new User();

		HttpSession session = request.getSession(false);
		long id = (long) session.getAttribute("id");
		currentUser.setId(id);

		// guid dùng bên .NET, uuid dùng bên java
		// tạo uuid bên controller vì phải lưu vào db và kết hợp để tạo URL thanh toán
		final String uuid = UUID.randomUUID().toString().replace("-", "");

		this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone,
				paymentMethod, uuid);

		if (!paymentMethod.equals("COD")) {
			// todo: redirect to VNPAY
			String ip = this.vNPayService.getIpAddress(request);
			String vnpUrl = this.vNPayService.generateVNPayURL(Double.parseDouble(totalPrice), uuid, ip);

			return "redirect:" + vnpUrl;
		}

		return "redirect:/thanks";
	}

	@GetMapping("/thanks")
	public String getThanksPage(@RequestParam("vnp_ResponseCode") Optional<String> vnpayResponseCode,
			@RequestParam("vnp_TxnRef") Optional<String> vnpayTxnRef) {
		
		if (vnpayResponseCode.isPresent() && vnpayTxnRef.isPresent()) {
			String paymentStatus = vnpayResponseCode.get().equals("00") ? "PAYMENT_SUCCEED" : "PAYMENT_FAILED"; 
			this.productService.updatePaymentStatus(paymentStatus, vnpayTxnRef.get());
		}
		return "client/cart/Thanks";
	}

	// Spring MVC cung cấp cơ chế tự ánh xạ value trong queryString vào DTO
	@GetMapping("/products")
	public String getProductPage(Model model, ProductCriteriaDTO productCriteriaDTO, HttpServletRequest request) {

		int currentPage = 1;
		try {
			if (productCriteriaDTO.getPage().isPresent()) {
				currentPage = Integer.parseInt(productCriteriaDTO.getPage().get());
			}
		} catch (Exception ex) {

		}

		// trang bắt đầu khi dùng pageable là 0
		Pageable pageable = PageRequest.of(currentPage - 1, 6);
		if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
			String sort = productCriteriaDTO.getSort().get();
			if (sort.equals("gia-tang-dan")) {
				pageable = PageRequest.of(currentPage - 1, 6, Sort.by(Product_.PRICE).ascending());
			} else if (sort.equals("gia-giam-dan")) {
				pageable = PageRequest.of(currentPage - 1, 6, Sort.by(Product_.PRICE).descending());
			}
		}

		// lấy tất cả product theo kiểu Pageable đã được phân trang, mỗi trang 6 sản
		// phẩm
		Page<Product> pageProducts = this.productService.getAllProduct(pageable, productCriteriaDTO);

		// convert Page sang List
		List<Product> listProducts = pageProducts.getContent().size() > 0 ? pageProducts.getContent()
				: new ArrayList<>();

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

		String queryString = request.getQueryString();
		if (queryString != null && !queryString.isBlank()) {
			// set page=??? rỗng để khi truyền qua view thì được thay thế bởi EL xuất
			// page=??? bên đó + queryString đã bị làm rỗng page=???
			queryString = queryString.replace("page=" + currentPage, "");
		}

		// Truyền startPage và endPage về view
		model.addAttribute("products", listProducts);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("queryString", queryString);
		model.addAttribute("currentPage", currentPage);

		return "client/product/ListProduct";
	}

//	private int parseIntOrDefault(String value) {
//	    try {
//	        return Integer.parseInt(value);
//	    } catch (NumberFormatException e) {
//	        return 1;
//	    }
//	}
//
//	private double parseDoubleOrDefault(String value) {
//	    try {
//	        return Double.parseDouble(value);
//	    } catch (NumberFormatException e) {
//	        return 0.0;
//	    }
//	}
}
