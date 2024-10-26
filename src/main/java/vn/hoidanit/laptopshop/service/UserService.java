package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {
	
	//	Dependency injection
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public void handleSaveUser(User user) {
		 this.userRepository.save(user);
	}
	
	public List<User> getAllUser(){
		return this.userRepository.findAll();
	}
	
	public List<User> getAllUserByEmail(String email){
		return this.userRepository.findByEmail(email);
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
}
