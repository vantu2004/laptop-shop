package vn.hoidanit.laptopshop.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull(message = "Không được để trống")
    @Size(min = 2, max = 100, message = "Tên sản phẩm phải từ 2 đến 100 ký tự")
    private String name;

    @NotNull(message = "Không được để trống")
    //	value = 0 nhưng thêm inclusive = "false" để nhận cả trường hợp 0.1
    @DecimalMin(value = "0", inclusive = false, message = "Giá phải lớn hơn 0")
    private double price;

    private String image;

    //	Mặc dù có thể dùng max = 1500 để quy định số ký tự cho cột detailDesc vs varchar(1500) nhưng ko hiệu quả
    //	@Size(min = 100, max = 1500, message = "Không được nhỏ hơn 100 và vượt quá 1500 ký tự")
    @NotNull
    @NotEmpty(message = "Không được để trống")
    //	dùng mediumtext có thể lưu chuỗi nặng 4mb
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;

    //	@Size(min = 100, max = 500, message = "Không được nhỏ hơn 100 và vượt quá 500 ký tự")
    @NotNull
    @NotEmpty(message = "Không được để trống")
    //	dùng mediumtext có thể lưu chuỗi nặng 4mb
    @Column(columnDefinition = "MEDIUMTEXT")
    private String shortDesc;

    @NotNull(message = "Không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private long quantity;

    private long sold;
    private String factory;
    private String target;

    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getSold() {
        return sold;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", image=" + image + ", detailDesc="
                + detailDesc + ", shortDesc=" + shortDesc + ", quantity=" + quantity + ", sold=" + sold + ", factory="
                + factory + ", target=" + target + "]";
    }

}
