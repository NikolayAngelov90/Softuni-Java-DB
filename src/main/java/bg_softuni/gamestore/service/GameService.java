package bg_softuni.gamestore.service;

import bg_softuni.gamestore.service.dtos.AddGameDto;
import bg_softuni.gamestore.service.dtos.DeleteGameDto;
import bg_softuni.gamestore.service.dtos.TitleGameDto;
import bg_softuni.gamestore.service.dtos.EditGameDto;

public interface GameService {

    String addGame(AddGameDto addGameDto);

    String editGame(EditGameDto editGameDto);

    String deleteGame(DeleteGameDto deleteGameDto);

    void printAllGames();

    String printDetailsGame(TitleGameDto detailGameDto);
}
