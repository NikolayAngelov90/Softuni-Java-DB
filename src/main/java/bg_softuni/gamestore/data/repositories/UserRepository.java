package bg_softuni.gamestore.data.repositories;

import bg_softuni.gamestore.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);


    User findByGamesTitle(String title);
}
