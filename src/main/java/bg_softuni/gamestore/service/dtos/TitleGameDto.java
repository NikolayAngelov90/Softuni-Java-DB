package bg_softuni.gamestore.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TitleGameDto {

    private String title;

    public TitleGameDto() {
    }

    public TitleGameDto(String title) {
        this.title = title;
    }
}


