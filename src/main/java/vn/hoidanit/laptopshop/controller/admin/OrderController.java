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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.UploadImageService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class OrderController {

	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/admin/order")
	public String getDashBoard(Model model, @RequestParam("page") Optional<String> pageOptional) {
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
		Page<Order> pageOrders = this.orderService.getAllOrder(pageable);
		// convert Page sang List
		List<Order> listOrders = pageOrders.getContent();

		// Số lượng trang hiển thị tối đa
		int maxDisplayPages = 5;
		// đảm bảo trang bắt đầu ko < 1
		int startPage = Math.max(1, currentPage - 2);
		// đảm bảo trang kết thúc luôn < totalPages
		int endPage = Math.min(startPage + maxDisplayPages - 1, pageOrders.getTotalPages());

		// Điều chỉnh lại chỉ số cho trang bắt đầu
		if (endPage - startPage + 1 < maxDisplayPages) {
			startPage = Math.max(1, endPage - maxDisplayPages + 1);
		}

		// Truyền startPage và endPage về view
		model.addAttribute("orders", listOrders);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("currentPage", currentPage);
		
		return "admin/order/ListOrder";
	}

	@GetMapping("/admin/order/{id}")
	public String getOrderDetailPage(Model model, @PathVariable long id) {
		Optional<Order> order = this.orderService.getOrder(id);
		if (order.isPresent()) {
			Order od = order.get();
			model.addAttribute("order", order);
			model.addAttribute("orderDetails", od.getOrderDetails());
		}
			
		return "admin/order/ShowOrder";
	}

	@GetMapping("/admin/order/delete/{id}")
	public String getDeleteUserPage(Model model, @PathVariable long id) {
		model.addAttribute("id", id);
		model.addAttribute("newOrder", new Order());

		return "admin/order/DeleteOrder";
	}

	@PostMapping("/admin/order/delete")
	public String postDeleteUser(@ModelAttribute("newOrder") Order order) {
		this.orderService.deleteOrderById(order.getId());
		
		return "redirect:/admin/order";
	}
	
    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable long id) {
        Optional<Order> currentOrder = this.orderService.getOrder(id);
        model.addAttribute("newOrder", currentOrder.get());
        
        return "admin/order/UpdateOrder";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.updateOrder(order);
        
        return "redirect:/admin/order";
    }
    
    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
		User currentUser = new User();
		HttpSession session = request.getSession(false);
		long id = (long) session.getAttribute("id");
		currentUser.setId(id);
		
		List<Order> orders = this.orderService.getOrderByUser(currentUser);
		model.addAttribute("orders", orders);
		
		return "client/cart/OrderHistory";
    }
}
