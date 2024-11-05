package vn.hoidanit.laptopshop.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserService userService;
	
	// là 1 interface thực hiện chuyển hướng trong Spring Security
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	// chỉ định sau đăng nhập, user về home, admin về manager
	// authentication chứa thông tin xác thực của người dùng hiện tại, bao gồm các
	// vai trò của họ.
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// xác định URL cần điều hướng dựa trên vai trò của người dùng.
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			return;
		}

		// thực hiện chuyển hướng người dùng đến URL đã xác định.
		redirectStrategy.sendRedirect(request, response, targetUrl);
		clearAuthenticationAttributes(request, authentication);
	}

	protected String determineTargetUrl(final Authentication authentication) {
		// tạo 1 map lưu đường dẫn cho ROLE_USER và ROLE_ADMIN
		Map<String, String> roleTargetUrlMap = new HashMap<>();
		roleTargetUrlMap.put("ROLE_USER", "/");
		roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

		// lấy danh sách các vai trò của người dùng được nạp vào từ hàm loadUserByUsername bên CustomUserDetailsService
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		for (final GrantedAuthority grantedAuthority : authorities) {
			
			// lấy quyền trong biến authentication (chứa thông tin xác thực người dùng)
			String authorityName = grantedAuthority.getAuthority();
			if (roleTargetUrlMap.containsKey(authorityName)) {
				// lấy đường dẫn từ quyền trong biến authentication
				return roleTargetUrlMap.get(authorityName);
			}
		}

		throw new IllegalStateException();
	}

	/*
	 * xóa thuộc tính lỗi xác thực (AUTHENTICATION_EXCEPTION) khỏi HttpSession của
	 * người dùng. Đảm bảo rằng các thông báo lỗi từ các lần đăng nhập thất bại
	 * trước đó sẽ không hiển thị khi người dùng đăng nhập thành công
	 */
	protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		//	get email từ authentication, (getName ở đây thực chất là lấy username, mà username lại là email)
		String email = authentication.getName();
		
		User user = this.userService.getUserByEmail(email);
		if (user != null) {
			session.setAttribute("fullName", user.getFullName());
			session.setAttribute("avatar", user.getAvatar());
		}
	}
}
