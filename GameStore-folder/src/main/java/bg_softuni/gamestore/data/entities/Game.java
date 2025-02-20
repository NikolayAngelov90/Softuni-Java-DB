package bg_softuni.gamestore.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "games")
public class Game extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column
    private String trailer;

    @Column(name = "image_thumbnail")
    private String imageThumbnail;

    @Column
    private double size;

    @Column
    private BigDecimal price;

    @Column
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    public Game() {
    }

    @Override
    public String toString() {
        return String.format("Title: %s\nPrice: %.2f\nDescription: %s\nRelease date: %s",
                this.getTitle(), this.getPrice(), this.getDescription(), this.getReleaseDate());
    }
}
