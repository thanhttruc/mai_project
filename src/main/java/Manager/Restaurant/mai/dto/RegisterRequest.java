package Manager.Restaurant.mai.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String avatar;
    private LocalDateTime dob;
}
