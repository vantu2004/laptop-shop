package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

//	Đánh dấu class này là class cấu hình chức năng giống file XML
@Configuration
//	Đánh dấu class ở dạng Spring MVC
@EnableWebMvc
//	Triển khai interface WebMvcConfigure để tùy chỉnh cấu hình Spring MVC
public class WebMvcConfig implements WebMvcConfigurer {

//	Trả về ViewResolver xác định view được render từ tên trả về bởi controller
  @Bean
  public ViewResolver viewResolver() {
//	Tìm file JSP dựa trên đường dẫn cung cấp
    final InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setViewClass(JstlView.class);
    bean.setPrefix("/WEB-INF/view/");
    bean.setSuffix(".jsp");
    return bean;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
//	Kích hoạt viewResolver được cấu hình phía trên
    registry.viewResolver(viewResolver());
  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
      registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
      registry.addResourceHandler("/avatar/**").addResourceLocations("/resources/images/avatar/");
      registry.addResourceHandler("/productImage/**").addResourceLocations("/resources/images/productImage/");
      registry.addResourceHandler("/client/**").addResourceLocations("/resources/client/");
  }

}

