package vn.hoidanit.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
	public static Specification<Product> nameLike(String name) {
		// phải thêm %temp% giống truy vấn SQL để tìm chuỗi có chứa abc
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
	}

	// le là <=, ge là >=, lt là <, gt là >
	public static Specification<Product> minPriceLike(double minPice) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), minPice);
	}

	public static Specification<Product> maxPriceLike(double maxPice) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), maxPice);
	}

	public static Specification<Product> factoryLike(String factory) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Product_.FACTORY), factory);
	}

	public static Specification<Product> listTargetLike(List<String> listTarget) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.TARGET)).value(listTarget);
	}
	
	public static Specification<Product> listFactoryLike(List<String> listFactory) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(listFactory);
	}

	// sử dụng kết hợp "đồng thời" 2 predicate để trả về cho 1 predicate bọc ngoài
	// khác với việc criteriaBuilder.or(...), or giúp check điều kiện của 1 hoặc
	// nhiều biểu thức chứ ko bắt buộc đồng thời thỏa tất cả điều kiện như .and
	public static Specification<Product> priceLike(double minPrice, double maxPrice) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.and(
				criteriaBuilder.ge(root.get(Product_.PRICE), minPrice),
				criteriaBuilder.le(root.get(Product_.PRICE), maxPrice));
	}

	// dùng phương thức sẵn có, ưu tiên
	public static Specification<Product> listPriceLike(double minPrice, double maxPrice) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), minPrice, maxPrice);
	}
}
