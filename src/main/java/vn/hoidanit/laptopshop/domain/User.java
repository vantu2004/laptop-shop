package vn.hoidanit.laptopshop.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import vn.hoidanit.laptopshop.service.validator.StrongPassword;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	//	@NotNull chỉ dành cho null, @NotEmpty gồm null và ""
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@NotEmpty(message = "Email cannot be empty")
	private String email;

	//	@Min(3), Min dùng cho int
	@NotEmpty
	@Size(min = 3, max = 100)
	//	annotation tự định nghĩa
	//	@StrongPassword (demo)
	private String password;

	@NotEmpty
	@Size(min = 3, max = 50)
	private String fullName;
	private String address;
	private String phone;
	private String avatar;

	// nhiều user thuộc 1 role, giữ khóa ngoại -> owner
	@ManyToOne
	// nếu ko có annotation này thì bên mysql mặc định tự tạo 1 bảng trung gian, có
	// annotation cho mysql biết 2 table liên kết theo role_id
	// role_id là tên tự đặt, thực tế nó liên kết tới thuộc tính đc đặt làm id (@Id)
	// bên role
	@JoinColumn(name = "role_id")
	private Role role;

	// 1 user có nhiều order, ko giữ khóa ngoại -> mapped by order
	@OneToMany(mappedBy = "user")
	List<Order> orders;

	public User() {

	}

	public User(long id, String email, String password, String fullName, String address, String phone) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.address = address;
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

//	@Override
//	public String toString() {
//		return "User [id=" + id + ", email=" + email + ", password=" + password + ", fullName=" + fullName
//				+ ", address=" + address + ", phone=" + phone + ", avatar=" + avatar + ", role=" + role.getName()
//				+ ", orders=" + orders + "]";
//	}

}
