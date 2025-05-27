package vn.hoidanit.laptopshop.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, HttpServletRequest request, Model model) {
        // Ghi log (chỉ ghi vào console, không trả ra client)
        System.err.println("Lỗi tại URL: " + request.getRequestURL());
        ex.printStackTrace();

        // Trả về trang lỗi thân thiện (không lộ stack trace)
        model.addAttribute("errorMessage", "Hệ thống đang gặp sự cố. Vui lòng thử lại sau.");
        return "error/customError"; // JSP: /WEB-INF/view/error/customError.jsp
    }
}
