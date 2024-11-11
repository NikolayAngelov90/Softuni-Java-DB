package bg_softuni.gamestore.service;

import bg_softuni.gamestore.service.dtos.TitleGameDto;

public interface OrderService {

    String addItem(TitleGameDto titleGameDto);

    String removeItem(TitleGameDto titleGameDto);

    void buyItems();
}
