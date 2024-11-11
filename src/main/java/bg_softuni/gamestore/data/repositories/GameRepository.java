package bg_softuni.gamestore.data.repositories;

import bg_softuni.gamestore.data.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {


    Game findByTitle(String title);
}
