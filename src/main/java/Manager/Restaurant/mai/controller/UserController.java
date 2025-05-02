package Manager.Restaurant.mai.controller;

import Manager.Restaurant.mai.dto.AuthResponse;
import Manager.Restaurant.mai.dto.UpdateProfileRequest;
import Manager.Restaurant.mai.entity.User;
import Manager.Restaurant.mai.service.JwtService;
import Manager.Restaurant.mai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromAuthHeader(authHeader);
        return ResponseEntity.ok(userService.getProfileByEmail(email));
    }

    @PutMapping("/profile")
    public ResponseEntity<AuthResponse> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UpdateProfileRequest request
    ) {
        String email = extractEmailFromAuthHeader(authHeader);
        User updated = userService.updateProfileByEmail(email, request);
        return ResponseEntity.ok(new AuthResponse("Profile updated", updated));
    }

    private String extractEmailFromAuthHeader(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtService.extractEmail(token);
    }
}
