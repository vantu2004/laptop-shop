package vn.hoidanit.laptopshop.config;

import java.util.regex.Pattern;

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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import vn.hoidanit.laptopshop.service.CustomOAuth2UserService;
import vn.hoidanit.laptopshop.service.CustomUserDetailsService;
import vn.hoidanit.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService(UserService userService) {
		return new CustomUserDetailsService(userService);
	}

	@Bean
	public DaoAuthenticationProvider authProvider(PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		// authProvider.setHideUserNotFoundExceptions(false);
		return authProvider;
	}

	@Bean
	public AuthenticationSuccessHandler customSuccessHandler() {
		return new CustomSuccessHandler();
	}

	@Bean
	public SpringSessionRememberMeServices rememberMeServices() {
		SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
		// optionally customize
		rememberMeServices.setAlwaysRemember(true);

		return rememberMeServices;
	}

	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http, UserService userService) throws Exception {
	        http
	        	.csrf((csrf) -> csrf.disable())
	            .requiresChannel(channel -> channel
	                .anyRequest().requiresSecure())
	            .headers(headers -> headers
	                .httpStrictTransportSecurity(hsts -> hsts
	                    .includeSubDomains(true)
	                    .preload(true)
	                    .maxAgeInSeconds(31536000)))
	            .authorizeHttpRequests(authorize -> authorize
	                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE).permitAll()
	                .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
	                .anyRequest().authenticated())
//	            .csrf(csrf -> csrf
//	                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//	                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//	                .requireCsrfProtectionMatcher(new CsrfProtectionMatcher()))
	            .oauth2Login(oauth2 -> oauth2
	                .loginPage("/login")
	                .defaultSuccessUrl("/", true)
	                .failureUrl("/login?error")
	                .userInfoEndpoint(user -> user.userService(new CustomOAuth2UserService(userService))))
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
	                .invalidSessionUrl("/logout?expired")
	                .maximumSessions(1)
	                .maxSessionsPreventsLogin(false))
	            .logout(logout -> logout
	                .deleteCookies("JSESSIONID")
	                .invalidateHttpSession(true))
	            .rememberMe(r -> r
	                .rememberMeServices(rememberMeServices()))
	            .formLogin(form -> form
	                .loginPage("/login")
	                .failureUrl("/login?error")
	                .successHandler(customSuccessHandler())
	                .permitAll())
	            .exceptionHandling(ex -> ex
	                .accessDeniedPage("/access-deny"));

	        return http.build();
	    }

	private static class CsrfProtectionMatcher implements RequestMatcher {
		private static final Pattern CSRF_PATTERN = Pattern.compile("^[a-f0-9]{32}$");

		@Override
		public boolean matches(HttpServletRequest request) {
			String csrfToken = request.getParameter("_csrf");
			if (csrfToken == null) {
				csrfToken = request.getHeader("X-CSRF-TOKEN");
			}
			if (csrfToken != null && !CSRF_PATTERN.matcher(csrfToken).matches()) {
				throw new IllegalArgumentException("Invalid CSRF token format");
			}
			return request.getMethod().equals("POST") || request.getMethod().equals("PUT")
					|| request.getMethod().equals("DELETE");
		}
	}
}
