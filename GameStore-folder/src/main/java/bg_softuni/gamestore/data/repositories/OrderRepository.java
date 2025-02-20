package bg_softuni.gamestore.data.repositories;

import bg_softuni.gamestore.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Transactional
    void removeByBuyerId(int buyerId);
}
