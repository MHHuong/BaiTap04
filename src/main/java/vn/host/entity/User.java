package vn.host.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String fullname;

    @Column(length = 15)
    private String phone;

    @Column(length = 255)
    private String avatarUrl;

    @Column(nullable = false)
    private Integer roleid;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Category> categories;
}
