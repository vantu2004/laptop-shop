package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.repository.UserRepositoryCustom;

@Service
public class UserService {

	// Dependency injection
	private final UserRepository userRepository;
	private final UserRepositoryCustom userRepositoryCustom;
	private final RoleRepository roleRepository;
	private final ProductRepository productRepository; 
	private final OrderRepository orderRepository; 

	public UserService(UserRepository userRepository, UserRepositoryCustom userRepositoryCustom, RoleRepository roleRepository, ProductRepository productRepository, OrderRepository orderRepository) {
		this.userRepository = userRepository;
		this.userRepositoryCustom = userRepositoryCustom;
		this.roleRepository = roleRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}

	public void handleSaveUser(User user) {
		this.userRepository.save(user);
	}

	public Page<User> getAllUser(Pageable pageable) {
		return this.userRepository.findAll(pageable);
	}

	public User getInfoUserById(long id) {
		return this.userRepository.findById(id);
	}

	public void deleteUserById(long id) {
		this.userRepository.deleteById(id);
	}

	public Role getRoleByName(String name) {
		return this.roleRepository.findByName(name);
	}

	// chuyển DTO thành user bằng cách code thủ công, hoặc có thể dùng công cụ
	// mapstruct
	public User registerDTOtoUser(RegisterDTO registerDTO) {
		User user = new User();

		user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(registerDTO.getPassword());

		return user;
	}

	public boolean checkEmailExist(String email) {
		return this.userRepository.existsByEmail(email);
	}
	
	public User getUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	 public User getUserByEmailInjection(String email) {
	        return userRepositoryCustom.findByEmailInjection(email);
	    }
	
	public long countUsers() {
		return this.userRepository.count();
	}
	
	public long countOrders() {
		return this.orderRepository.count();
	}
	
	public long countProducts() {
		return this.productRepository.count();
	}
}
