package bg_softuni.gamestore.service.impls;

import bg_softuni.gamestore.data.entities.Game;
import bg_softuni.gamestore.data.repositories.GameRepository;
import bg_softuni.gamestore.service.GameService;
import bg_softuni.gamestore.service.dtos.AddGameDto;
import bg_softuni.gamestore.service.dtos.DeleteGameDto;
import bg_softuni.gamestore.service.dtos.TitleGameDto;
import bg_softuni.gamestore.service.dtos.EditGameDto;
import bg_softuni.gamestore.utils.ValidatorUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper,
                           ValidatorUtil validatorUtil) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String addGame(AddGameDto addGameDto) {
        StringBuilder sb = new StringBuilder();

        if (!validatorUtil.isValid(addGameDto)) {
            for (ConstraintViolation<AddGameDto> violation : validatorUtil.validate(addGameDto)) {
                System.out.println(violation.getMessage());
            }
        } else {
            String trailerUrl = addGameDto.getTrailer();
            if (trailerUrl.startsWith("https://www.youtube.com/")) {
                String youTubeId = trailerUrl.substring(trailerUrl.length() - 11);
                addGameDto.setTrailer(youTubeId);
            }

            Game game = this.modelMapper.map(addGameDto, Game.class);

            this.gameRepository.save(game);
            sb.append(String.format("Added %s", game.getTitle()));
        }
        return sb.toString();
    }

    @Override
    public String editGame(EditGameDto editGameDto) {

        if (!validatorUtil.isValid(editGameDto)) {
            for (ConstraintViolation<EditGameDto> violation : validatorUtil.validate(editGameDto)) {
                System.out.println(violation.getMessage());
            }
        }

        Optional<Game> optionalGame = this.gameRepository.findById(editGameDto.getId());
        if (optionalGame.isEmpty()) {
            return "No such game found.";
        }

        Game game = optionalGame.get();

        setsFields(game, editGameDto);

        gameRepository.saveAndFlush(game);

        return String.format("Edited %s", game.getTitle());
    }

    @Override
    public String deleteGame(DeleteGameDto deleteGameDto) {
        Optional<Game> optionalGame = gameRepository.findById(deleteGameDto.getId());

        if (optionalGame.isEmpty()) {
            return "No such game found.";
        }

        Game game = optionalGame.get();
        gameRepository.delete(game);

        return String.format("Deleted %s", game.getTitle());
    }

    @Override
    public void printAllGames() {
        List<Game> games = gameRepository.findAll();

        games.forEach(g -> System.out.println(g.getTitle() + " " + g.getPrice()));
    }

    @Override
    public String printDetailsGame(TitleGameDto detailGameDto) {

        Game game = this.gameRepository.findByTitle(detailGameDto.getTitle());

        return game.toString();
    }

    private void setsFields(Game game, EditGameDto editGameDto) {

        if (editGameDto.getTitle() != null) {
            game.setTitle(editGameDto.getTitle());
        }

        if (editGameDto.getPrice() != null) {
            game.setPrice(editGameDto.getPrice());
        }

        if (editGameDto.getSize() != null) {
            game.setSize(editGameDto.getSize());
        }

        if (editGameDto.getTrailer() != null) {
            game.setTrailer(editGameDto.getTrailer());
        }

        if (editGameDto.getImageThumbnail() != null) {
            game.setImageThumbnail(editGameDto.getImageThumbnail());
        }

        if (editGameDto.getDescription() != null) {
            game.setDescription(editGameDto.getDescription());
        }


        if (editGameDto.getReleaseDate() != null) {
            game.setReleaseDate(editGameDto.getReleaseDate());
        }
    }
}
