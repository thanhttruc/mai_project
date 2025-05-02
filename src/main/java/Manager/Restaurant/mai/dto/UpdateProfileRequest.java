package Manager.Restaurant.mai.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String name;
    private String phone;
    private String gender;
    private String avatar;
    private LocalDateTime dob;
}
