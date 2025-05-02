package Manager.Restaurant.mai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

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

    public User(String userSlug, String userName, String userEmail, String userPassword, String userSalt, String userGender, String userPhone, String userAvatar, LocalDateTime userDob, Role role, String userStatus) {
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
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.getRoleName());
    }


    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return "ACTIVE".equalsIgnoreCase(userStatus); }
}
