package vn.hoidanit.laptopshop.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//	// Xử lý NullPointerException
//	@ExceptionHandler(NullPointerException.class)
//	public String handleNullPointerException(NullPointerException ex, HttpServletRequest request, Model model) {
//		System.err.println("NullPointerException tại URL: " + request.getRequestURL());
//		ex.printStackTrace();
//		model.addAttribute("errorMessage", "Dữ liệu không hợp lệ hoặc không tìm thấy. Vui lòng kiểm tra lại.");
//		return "error/default"; // JSP: /WEB-INF/view/error/customError.jsp
//	}
//
//	// Xử lý IllegalArgumentException
//	@ExceptionHandler(IllegalArgumentException.class)
//	public String handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request, Model model) {
//		System.err.println("IllegalArgumentException tại URL: " + request.getRequestURL());
//		ex.printStackTrace();
//		model.addAttribute("errorMessage", "Tham số không hợp lệ. Vui lòng kiểm tra lại dữ liệu đầu vào.");
//		return "error/default";
//	}
//
//	// Xử lý HttpMessageNotReadableException (lỗi JSON không hợp lệ)
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request,
//			Model model) {
//		System.err.println("HttpMessageNotReadableException tại URL: " + request.getRequestURL());
//		ex.printStackTrace();
//		model.addAttribute("errorMessage", "Dữ liệu đầu vào không đúng định dạng. Vui lòng kiểm tra lại.");
//		return "error/default";
//	}
//
//	// Xử lý lỗi DuplicateKeyException (lỗi trùng khóa, như trong session)
//	@ExceptionHandler(DuplicateKeyException.class)
//	public String handleDuplicateKeyException(DuplicateKeyException ex, HttpServletRequest request, Model model) {
//		System.err.println("Lỗi Duplicate Key tại URL: " + request.getRequestURL());
//		ex.printStackTrace();
//		model.addAttribute("errorMessage", "Dữ liệu đã tồn tại hoặc trùng lặp. Vui lòng thử lại.");
//		return "error/duplicate-key"; // Bạn nên tạo file duplicate-key.jsp hoặc .html
//	}


}