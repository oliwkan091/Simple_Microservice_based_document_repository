package com.Microservice_based_document_repository.Gateway.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Model of user saved in database
 */
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "createTime", nullable = false)
    private LocalDateTime createTime;

    @Transient
    private String token;

    public User(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.name = user.name;
        this.password = passwordEncoder.encode(user.getPassword());
        this.createTime = LocalDateTime.now();
        this.role = Role.USER;
    }
}
