package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final CartDetailRepository cartDetailRepository;
	private final UserService userservice;
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;

	public ProductService(ProductRepository productRepository, CartRepository cartRepository,
			CartDetailRepository cartDetailRepository, UserService userservice, OrderRepository orderRepository,
			OrderDetailRepository orderDetailRepository) {
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.cartDetailRepository = cartDetailRepository;
		this.userservice = userservice;
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
	}

	public void handleSaveProduct(Product product) {
		this.productRepository.save(product);
	}

	public List<Product> getAllProduct() {
		return this.productRepository.findAll();
	}

	public Product getInfoProductById(long id) {
		return this.productRepository.findById(id);
	}

	public void deleteProductById(long id) {
		this.productRepository.deleteById(id);
	}

	public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {
		User user = this.userservice.getUserByEmail(email);

		if (user != null) {
			// check user đã có cart hay chưa, chưa thì tạo
			Cart cart = this.cartRepository.findByUser(user);

			if (cart == null) {
				// tạo mới cart
				Cart newCart = new Cart();
				newCart.setUser(user);
				newCart.setSum(0);

				cart = this.cartRepository.save(newCart);
			}

			Product p = this.productRepository.findById(productId);

			// check sản phẩm đã tồn tại trog giỏ hàng ngời dùng đó chưa
			CartDetail cd = this.cartDetailRepository.findByCartAndProduct(cart, p);

			if (cd == null) {
				cd = new CartDetail();
				cd.setCart(cart);
				cd.setProduct(p);
				cd.setPrice(p.getPrice());
				cd.setQuantity(quantity);

				int sum = cart.getSum() + 1;

				// set lại số lượng khi có 1 sản phẩm mới thêm vào giỏ
				// phân biệt giữa số lượng loại sản phẩm trong 1 giỏ và số lượng sản phẩm của 1
				// loại
				cart.setSum(sum);
				this.cartRepository.save(cart);

				session.setAttribute("sum", sum);
			} else {
				cd.setQuantity(cd.getQuantity() + quantity);
			}

			this.cartDetailRepository.save(cd);
		}
	}

	public Cart getCartByUser(User user) {
		return this.cartRepository.findByUser(user);
	}

	// nếu muốn dùng Optional thì bên repository cũng phải dùng Optional
	public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
		Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
		if (cartDetailOptional.isPresent()) {
			CartDetail cartDetail = cartDetailOptional.get();

			Cart cart = cartDetail.getCart();

			this.cartDetailRepository.deleteById(cartDetailId);

			// nếu sum trong cart > 1 thì -1, = 1 thì xóa cart và gán 0 cho sum trong
			// session
			if (cart.getSum() > 1) {
				int sum = cart.getSum() - 1;
				cart.setSum(sum);
				session.setAttribute("sum", sum);

				this.cartRepository.save(cart);
			} else {
				this.cartRepository.deleteById(cart.getId());
				session.setAttribute("sum", 0);
			}
		}
	}

	public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
		for (CartDetail cartDetail : cartDetails) {
			Optional<CartDetail> opCartDetail = this.cartDetailRepository.findById(cartDetail.getId());
			if (opCartDetail.isPresent()) {
				CartDetail currentCartDetail = opCartDetail.get();
				// cập nhật lại quantity
				currentCartDetail.setQuantity(cartDetail.getQuantity());

				this.cartDetailRepository.save(currentCartDetail);
			}
		}
	}

	public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
			String receiverPhone) {

		// lấy giỏ hàng từ người dùng
		Cart cart = this.cartRepository.findByUser(user);
		if (cart != null) {
			List<CartDetail> cartDetails = cart.getCartDetails();

			if (cartDetails != null) {
				// tạo order
				Order order = new Order();
				// làm theo hướng oop nên set user
				order.setUser(user);
				order.setReceiverName(receiverName);
				order.setReceiverAddress(receiverAddress);
				order.setReceiverPhone(receiverPhone);
				order.setStatus("PENDDING");
				
				double totalPrice = 0;
				for (CartDetail cd : cartDetails) {
					totalPrice += cd.getPrice() * cd.getQuantity();
				}
				order.setTotalPrice(totalPrice);
				
				// trước khi lưu thì ko có id, sau lưu thì có thể lấy đc
				order = this.orderRepository.save(order);
				
				for (CartDetail cd : cartDetails) {
					OrderDetail orderDetail = new OrderDetail();
					orderDetail.setOrder(order);
					orderDetail.setProduct(cd.getProduct());
					orderDetail.setQuantity(cd.getQuantity());
					orderDetail.setPrice(cd.getPrice());
					
					this.orderDetailRepository.save(orderDetail);
				}
				
				//	sau khi tạo xong orderDetail thì xóa cartDetail
				for (CartDetail cd : cartDetails) {
					this.cartDetailRepository.deleteById(cd.getId());
				}
				
				//	sau khi tạo đơn, xóa chi tiết giỏ hàng xong thì tiến hành xóa giỏ hàng
				this.cartRepository.deleteById(cart.getId());
				
				//	update session
				session.setAttribute("sum", 0);
			}
		}
	}
}
