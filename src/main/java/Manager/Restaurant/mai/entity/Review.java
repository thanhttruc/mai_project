package Manager.Restaurant.mai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private MenuItem item;

    private String comment;
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Review(Long reviewId, User user, MenuItem item, Restaurant restaurant, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.user = user;
        this.item = item;
        this.restaurant = restaurant;
        this.comment = comment;
        this.createdAt = createdAt;
        this.isDeleted = false;
    }
}
