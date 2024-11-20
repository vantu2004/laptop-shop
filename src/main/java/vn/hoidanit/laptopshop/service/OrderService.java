package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	
	public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
	}
	
	public Optional<Order> getOrder(long id){
		return this.orderRepository.findById(id);
	}
	
	public Page<Order> getAllOrder(Pageable pageable) {
		return this.orderRepository.findAll(pageable);
	}

	public void deleteOrderById(long id) {
        // delete order detail
        Optional<Order> orderOptional = this.getOrder(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                this.orderDetailRepository.deleteById(orderDetail.getId());
            }
        }

        //	phải xóa orderDetail trước rồi mới được xóa order
        this.orderRepository.deleteById(id);
	}
	
    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.getOrder(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            
            this.orderRepository.save(currentOrder);
        }
    }
    
    public List<Order> getOrderByUser(User user){
    	return this.orderRepository.findByUser(user);
    }
}
