package Manager.Restaurant.mai.controller;

import Manager.Restaurant.mai.entity.User;
import Manager.Restaurant.mai.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;
    private final OrderRepository orderRepo;
    private final AddressRepository addressRepo;
    private final ReviewRepository reviewRepo;
    private final NotificationRepository notificationRepo;

    // ✅ Lấy thông tin người dùng theo ID (chỉ khi chưa bị xoá)
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam Long userId) {
        Optional<User> user = userRepo.findById(userId)
                .filter(u -> !Boolean.TRUE.equals(u.isDeleted()));

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Cập nhật thông tin người dùng (chỉ nếu chưa bị xoá)
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestParam Long userId, @RequestBody User updatedInfo) {
        Optional<User> userOpt = userRepo.findById(userId)
                .filter(u -> !Boolean.TRUE.equals(u.isDeleted()));

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();
        user.setUserName(updatedInfo.getUserName());
        user.setUserPhone(updatedInfo.getUserPhone());
        user.setUserGender(updatedInfo.getUserGender());
        user.setUserAvatar(updatedInfo.getUserAvatar());
        user.setUserDob(updatedInfo.getUserDob());

        userRepo.save(user);
        return ResponseEntity.ok("Cập nhật thông tin thành công.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();

        if (Boolean.TRUE.equals(user.isDeleted())) {
            return ResponseEntity.badRequest().body("Người dùng đã bị xoá trước đó.");
        }

        // Soft delete user
        user.setDeleted(true);
        userRepo.save(user);

        // Soft delete liên quan
        notificationRepo.findByUserUserIdAndIsDeletedFalse(userId).forEach(n -> {
            n.setDeleted(true);
            notificationRepo.save(n);
        });

        addressRepo.findByUserUserIdAndIsDeletedFalse(userId).forEach(a -> {
            a.setDeleted(true);
            addressRepo.save(a);
        });

        reviewRepo.findByUserUserIdAndIsDeletedFalse(userId).forEach(r -> {
            r.setDeleted(true);
            reviewRepo.save(r);
        });

        orderRepo.findByUserUserIdAndIsDeletedFalse(userId).forEach(o -> {
            o.setDeleted(true);
            orderRepo.save(o);
        });

        return ResponseEntity.ok("Đã xoá người dùng và các thông tin liên quan (soft delete).");
    }



    // ✅ Lấy danh sách tất cả người dùng chưa bị xoá
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> activeUsers = userRepo.findAll()
                .stream()
                .filter(u -> !Boolean.TRUE.equals(u.isDeleted()))
                .toList();

        return ResponseEntity.ok(activeUsers);
    }
}
