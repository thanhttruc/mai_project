package Manager.Restaurant.mai.service;

import Manager.Restaurant.mai.dto.UpdateProfileRequest;
import Manager.Restaurant.mai.entity.User;
import Manager.Restaurant.mai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User getProfileByEmail(String email) {
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User updateProfileByEmail(String email, UpdateProfileRequest request) {
        User user = getProfileByEmail(email); // tái sử dụng
        user.setUserName(request.getName());
        user.setUserPhone(request.getPhone());
        user.setUserGender(request.getGender());
        user.setUserAvatar(request.getAvatar());
        user.setUserDob(request.getDob());
        return userRepository.save(user);
    }
}
