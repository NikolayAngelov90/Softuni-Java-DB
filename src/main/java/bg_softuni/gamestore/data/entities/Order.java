package bg_softuni.gamestore.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    private User buyer;

    @OneToMany
    private Set<Game> games;

    public Order() {
        this.games = new HashSet<>();
    }

}
