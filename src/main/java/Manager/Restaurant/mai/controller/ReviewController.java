package Manager.Restaurant.mai.controller;

import Manager.Restaurant.mai.repository.*;
import Manager.Restaurant.mai.entity.*;
import Manager.Restaurant.mai.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;
    private final MenuItemRepository itemRepo;
    private final RestaurantRepository restaurantRepo;

    // POST /reviews/restaurant - Thêm review cho nhà hàng
    @PostMapping("/restaurant")
    public ResponseEntity<?> addRestaurantReview(@RequestBody Map<String, Object> data) {
        Long userId = Long.valueOf(data.get("userId").toString());
        Long resId = Long.valueOf(data.get("restaurantId").toString());
        String comment = data.get("comment").toString();

        // Kiểm tra xem người dùng và nhà hàng có tồn tại không
        User user = userRepo.findById(userId).orElse(null);
        Restaurant restaurant = restaurantRepo.findById(resId).orElse(null);

        if (user == null || restaurant == null) {
            return ResponseEntity.badRequest().body("Người dùng hoặc nhà hàng không tồn tại.");
        }

        // Thêm review cho nhà hàng
        Review review = new Review(null, user, null, restaurant, comment, LocalDateTime.now());
        reviewRepo.save(review);

        return ResponseEntity.ok(Map.of("message", "Review nhà hàng đã được thêm.", "reviewId", review.getReviewId()));
    }

    // GET /reviews/restaurant/{restaurantId} - Lấy review theo nhà hàng
    @GetMapping("/restaurant/{resId}")
    public ResponseEntity<?> getReviewsByRestaurant(@PathVariable Long resId) {
        // Tìm reviews cho nhà hàng
        List<Review> reviewsForRestaurant = reviewRepo.findByRestaurant_ResIdAndIsDeletedFalse(resId);
        List<ReviewDTO> reviewDTOs = reviewsForRestaurant.stream()
                .map(ReviewDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reviewDTOs);
    }

    // GET /reviews/item/{itemId} - Lấy danh sách review theo món ăn
    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getReviewsByItem(@PathVariable Long itemId) {
        // Tìm reviews cho món ăn
        List<Review> reviewsForItem = reviewRepo.findByItem_ItemIdAndIsDeletedFalse(itemId);
        List<ReviewDTO> reviewDTOs = reviewsForItem.stream()
                .map(ReviewDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reviewDTOs);
    }

    // DELETE /reviews/{id} - Xoá mềm review
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        return reviewRepo.findById(id)
                .map(review -> {
                    // Đánh dấu review là đã bị xóa
                    review.setDeleted(true);
                    reviewRepo.save(review);
                    return ResponseEntity.ok(Map.of("message", "Review đã được xoá."));
                }).orElse(ResponseEntity.notFound().build());
    }
}
