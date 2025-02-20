package bg_softuni.gamestore.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteGameDto {

    private Integer id;

    public DeleteGameDto() {
    }

    public DeleteGameDto(Integer id) {
        this.id = id;
    }
}
