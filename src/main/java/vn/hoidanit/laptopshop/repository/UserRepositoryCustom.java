package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.Query;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.hoidanit.laptopshop.domain.User;

@Repository
public class UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // Sử dụng native sql -> dễ bị SQL Injection
    public User findByEmailInjection(String email) {
        String sql = "SELECT * FROM users WHERE email = '" + email + "'";
        //String sql = "SELECT * FROM users WHERE email = :email";

        Query query = entityManager.createNativeQuery(sql, User.class);
        //query.setParameter("email", email);
        List<User> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
    
   
    
    
}