package vn.hoidanit.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import java.util.List;
import java.util.Optional;


@Repository 
public interface CartDetailRepository extends JpaRepository<CartDetail, Long>{
	CartDetail save (CartDetail cartdetail);
	
	boolean existsByCartAndProduct (Cart cart, Product product);
	
	CartDetail findByCartAndProduct (Cart cart, Product product);
	
	Optional<CartDetail> findById(long id);
	
	void deleteById(long id);
}
 