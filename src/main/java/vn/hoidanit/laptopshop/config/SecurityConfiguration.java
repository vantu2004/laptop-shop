package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration 
@EnableMethodSecurity (securedEnabled = true)
public class SecurityConfiguration {
	
	// Spring security yêu cầu dùng phương pháp j để mã hóa mất khẩu -> override phương thức từ interface và chọn BCryptPasswordEncoder
	// @Bean giúp spring quản lý  phương thức để khi chương trình vừa chạy lên thì đã bị override
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
