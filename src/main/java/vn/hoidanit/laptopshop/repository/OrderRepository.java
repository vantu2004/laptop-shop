package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.User;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long>{
	Order save(Order order);
	
	Order findById (Order order);
	
	void deleteById (long id);
	
	List<Order> findByUser (User user);
	
	Page<Order> findAll (Pageable page);
}
