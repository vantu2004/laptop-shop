package vn.hoidanit.laptopshop.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

@RestController
public class SqlInjectionTestController {

    @Autowired
    private UserService userService;

    @GetMapping("/injection-test")
    public User testSqlInjection(@RequestParam String email) {
    	System.out.println(email);
        return userService.getUserByEmailInjection(email);
    }
    
    @PostMapping("/injection-test")
    public User testSqlInjectionPost(@RequestParam String email) {
    	System.out.println(email);
        return userService.getUserByEmailInjection(email);
    }
}
