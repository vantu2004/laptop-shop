package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.hoidanit.laptopshop.service.CustomUserDetailsService;
import vn.hoidanit.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

	// Spring security yêu cầu dùng phương pháp j để mã hóa mật khẩu -> override
	// phương thức từ interface và chọn BCryptPasswordEncoder (báo cho security dùng
	// thuật toán BCryptPasswordEncoder để hash pass)
	// @Bean giúp spring quản lý phương thức để khi chương trình vừa chạy lên thì đã
	// bị override
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// báo cho security biết interface UserDetailsService đã được triển khai bởi
	// CustomUserDetailsService và hàm loadByUsername đã bị ghi đè
	// mặc định dữ liệu được load lên từ memory (RAM) nhưng vì loadByUsernaem đã bị
	// ghi đè nên dữ liệu đc load lên phụ thuộc vào hàm ghi đè
	// trong hàm dùng biến userService để inject dependency cho userService trong
	// constructor bên CustomUserDetailsService
	@Bean
	public UserDetailsService userDetailsService(UserService userService) {
		return new CustomUserDetailsService(userService);
	}

	/*
	 * DaoAuthenticationProvider là một triển khai của AuthenticationProvider dùng
	 * để xác thực người dùng dựa trên dữ liệu từ một UserDetailsService và mã hóa
	 * mật khẩu từ PasswordEncoder. Bean DaoAuthenticationProvider xác thực người
	 * dùng bằng cách: Tải thông tin người dùng từ UserDetailsService. Mã hóa và
	 * kiểm tra mật khẩu với PasswordEncoder. Ẩn hoặc hiển thị lỗi chi tiết khi tên
	 * người dùng không tồn tại.
	 */
	@Bean
	public DaoAuthenticationProvider authProvider(PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		// ẩn đi thông báo mặc định, hiển thị thông báo người dùng custom, ví dụ nhập
		// sai
		// email thì bên CustomUserDetailsService sẽ báo "Không tìm thấy người dùng"
		// (hạn chế)
		// authProvider.setHideUserNotFoundExceptions(false);

		return authProvider;
	}

	// tùy chỉnh hành động sau khi xác thực thành công (chuyển hướng user về home,
	// admin về manager)
	// AuthenticationSuccessHandler là một interface định nghĩa các phương thức cần
	// có để xử lý sau khi xác thực thành công
	// CustomSuccessHandler triển khai AuthenticationSuccessHandler nên override các
	// phương thức bên trong -> tính đa hình -> trả về kiểu của CustomSuccessHandler
	// vẫn được
	@Bean
	public AuthenticationSuccessHandler customSuccessHandler() {
		return new CustomSuccessHandler();
	}

	// Cấu hình bao gồm các quy tắc cho phép và yêu cầu xác thực đối với các URL cụ
	// thể, cũng như cấu hình trang đăng nhập
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// v6. lamda
		http.authorizeHttpRequests(authorize -> authorize
				.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE).permitAll()

				.requestMatchers("/", "/login", "/product/**", "/client/**", "/css/**", "/js/**", "/productImage/**")
				.permitAll()

				// hàm hasRole cần bỏ tiền tố ROLE_ và chỉ lấy đúng tên role
				.requestMatchers("/admin/**").hasRole("ADMIN")

				.anyRequest().authenticated())

				.sessionManagement((sessionManagement) -> sessionManagement
						//	luôn tạo session mới cho người dùng mới
						.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
						//	hết hạn session thì logout
						.invalidSessionUrl("/logout?expired")
						//	tại 1 thời điểm chỉ cho giới hạn thiết bị đăng nhập
						.maximumSessions(1)
						//	nếu set là false thì khi người 2 đăng nhập sẽ đã người 1, set là true thì người 2 đăng nhập nhưng phải đợi người 1 hết session
						.maxSessionsPreventsLogin(false))

				//	mỗi lần đăng xuất thì tự xóa session
				.logout(logout->logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))

				// khi gọi cơ chế này thì thời hạn session tự động gia hạn bằng vs rememberme
				.rememberMe(r -> r.rememberMeServices(rememberMeServices()))

				.formLogin(formLogin -> formLogin.loginPage("/login").failureUrl("/login?error")
						// chuyển hướng người dùng sau khi xác thực thành công
						.successHandler(customSuccessHandler()).permitAll())

				// xử lý lỗi khi role chưa đc cấp quyền truy cập
				.exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

		return http.build();
	}

	@Bean
	public SpringSessionRememberMeServices rememberMeServices() {
		SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
		// set thời hạn cho cơ chế rememberme
		rememberMeServices.setAlwaysRemember(true);
		return rememberMeServices;
	}

}
