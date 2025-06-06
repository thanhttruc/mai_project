package Manager.Restaurant.mai.repository;

import Manager.Restaurant.mai.entity.Cart;
import Manager.Restaurant.mai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser_UserId(Long userId);
}

