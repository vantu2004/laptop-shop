package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;

@Repository
@Transactional
public interface OrderDetailRepository  extends JpaRepository<Order, Long>{
	OrderDetail save(OrderDetail orderDetail);
	
	void deleteById(long id);
	
//	@Modifying
//	@Query("DELETE FROM OrderDetail o WHERE o.id = :id")
//	void deleteOrderDetailById(@Param("id") long id);

}
