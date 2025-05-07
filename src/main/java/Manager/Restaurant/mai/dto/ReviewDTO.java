package Manager.Restaurant.mai.dto;

import Manager.Restaurant.mai.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewDTO {

    private Long reviewId;
    private String comment;
    private LocalDateTime createdAt;
    private String userName;
    private String restaurantName;

    public static ReviewDTO fromEntity(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUser().getUserName(),
                review.getRestaurant().getName()
        );
    }
}
