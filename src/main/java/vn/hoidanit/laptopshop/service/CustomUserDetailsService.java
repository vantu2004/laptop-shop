package vn.hoidanit.laptopshop.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
//	ko gợi ý nên tự thêm
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//	ở mặc định thì dữ liệu người dùng đc lưu tại memory (RAM) nên phải override lại vì dữ liệu hiện đang lưu trong db
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService userService;

	public CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// trùng kiểu User trong security nên khai báo tường minh, lấy thông tin người dùng bằng email (username)
		vn.hoidanit.laptopshop.domain.User user = this.userService.getUserByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("Không tìm thấy người dùng");
		}
		
		// trả về kiểu User của security
		// lớp User của security đang triển khai interface Userdetails
		// theo tính đa hình thì lớp con ghi đè lớp cha nên trả về kiểu User thì vẫn đc tự động ép về UserDetails
		// trong lớp User của security có constructor nhận 3 tham số bên dưới
		// trong lớp User tự định nghĩa thì role là 1 đối tượng có thuộc tính name nên phải gọi getName()
		// khi dùng SimpleGrantedAuthority thì phải truyền vào biến có tiền tố ROLE_ để báo cho spring nhận diện đc role
		// khi qua bên SecurityConfiguration có dùng hàm hasRole thì hàm này tự động bỏ tiền tố ROLE_ và chỉ lấy đúng tên role
		return new User(user.getEmail(), user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())));

	}

}
