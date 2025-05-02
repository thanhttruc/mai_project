package Manager.Restaurant.mai.service;

import Manager.Restaurant.mai.dto.*;
import Manager.Restaurant.mai.entity.*;
import Manager.Restaurant.mai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        Role role = roleRepository.findByRoleSlug("user")
                .orElseThrow(() -> new RuntimeException("Role user not found"));

        User user = new User();
        user.setUserSlug(UUID.randomUUID().toString());
        user.setUserName(request.getName());
        user.setUserEmail(request.getEmail());
        user.setUserPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserSalt("salt");
        user.setUserGender(request.getGender());
        user.setUserPhone(request.getPhone());
        user.setUserAvatar(request.getAvatar());
        user.setUserDob(request.getDob() != null ? request.getDob() : LocalDateTime.now());
        user.setRole(role);
        user.setUserStatus("ACTIVE");

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse("Register successful", token);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        User user = userRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        return new AuthResponse("Login successful", token);
    }
}
