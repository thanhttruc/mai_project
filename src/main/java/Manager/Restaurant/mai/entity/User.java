package Manager.Restaurant.mai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userSlug;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userSalt;
    private String userGender;
    private String userPhone;
    private String userAvatar;
    private LocalDateTime userDob;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private Role role;

    private String userStatus;

    @Column(nullable = false)
    private boolean isDeleted = false;

    // Constructor dùng khi khởi tạo User mới (không có userId)
    public User(String userSlug, String userName, String userEmail, String userPassword, String userSalt,
                String userGender, String userPhone, String userAvatar, LocalDateTime userDob,
                Role role, String userStatus) {
        this.userSlug = userSlug;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userSalt = userSalt;
        this.userGender = userGender;
        this.userPhone = userPhone;
        this.userAvatar = userAvatar;
        this.userDob = userDob;
        this.role = role;
        this.userStatus = userStatus;
        this.isDeleted = false;
    }
}
