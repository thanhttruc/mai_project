package Manager.Restaurant.mai.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resId;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String status;
    private Boolean verify;

    @Column(columnDefinition = "json")
    private String roles;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MenuItem> menuItems;

    public Restaurant(String name, String email, String password, String phone, String status, Boolean verify, String roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.status = status;
        this.verify = verify;
        this.roles = roles;
    }
}
