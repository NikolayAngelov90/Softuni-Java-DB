package bg_softuni.gamestore.controller;

import bg_softuni.gamestore.service.GameService;
import bg_softuni.gamestore.service.OrderService;
import bg_softuni.gamestore.service.UserService;
import bg_softuni.gamestore.service.dtos.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
public class MainRunner implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    public MainRunner(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while (!(line = reader.readLine()).equals("exit")) {
            String[] tokens = line.split("\\|");

            String command = "";
            switch (tokens[0]) {
                case "RegisterUser":
                    command = this.userService.registerUser(
                            new UserCreateDto(tokens[1], tokens[2], tokens[3], tokens[4]));
                    break;
                case "LoginUser":
                    command = this.userService.loginUser(
                            new UserLoginDto(tokens[1], tokens[2]));
                    break;
                case "Logout":
                    command = this.userService.logoutUser();
                    break;
                case "AddGame":
                    command = this.gameService.addGame(
                            new AddGameDto(tokens[1], new BigDecimal(tokens[2]),
                                    Double.parseDouble(tokens[3]),
                                    tokens[4], tokens[5], tokens[6],
                                    LocalDate.parse(tokens[7], DateTimeFormatter.
                                            ofPattern("dd-MM-yyyy"))));
                    break;
                case "EditGame":
                    EditGameDto editGameDto = new EditGameDto();
                    editGameDto.setId(Integer.parseInt(tokens[1]));
                    Arrays.stream(tokens).skip(2).forEach(v -> {
                        String[] split = v.split("=");
                        String field = split[0];
                        switch (field) {
                            case "title":
                                editGameDto.setTitle(split[1]);
                                break;
                            case "price":
                                editGameDto.setPrice(new BigDecimal(split[1]));
                                break;
                            case "size":
                                editGameDto.setSize(Double.parseDouble(split[1]));
                                break;
                            case "trailer":
                                editGameDto.setTrailer(split[1]);
                                break;
                            case "imageThumbnail":
                                editGameDto.setImageThumbnail(split[1]);
                                break;
                            case "description":
                                editGameDto.setDescription(split[1]);
                                break;
                            case "releaseDate":
                                editGameDto.setReleaseDate(LocalDate.parse(tokens[1],
                                        DateTimeFormatter.
                                                ofPattern("dd-MM-yyyy")));
                                break;
                            default:
                                System.out.println("Invalid value");
                                break;
                        }
                    });
                    command = this.gameService.editGame(editGameDto);
                    break;
                case "DeleteGame":
                    command = this.gameService.deleteGame(
                            new DeleteGameDto(Integer.parseInt(tokens[1])));
                    break;
                case "AllGames":
                    this.gameService.printAllGames();
                    continue;
                case "DetailGame":
                    command = this.gameService.printDetailsGame(
                            new TitleGameDto(tokens[1]));
                    break;
                case "AddItem":
                    command = this.orderService.addItem(
                            new TitleGameDto(tokens[1]));
                    break;
                case "RemoveItem":
                    command = this.orderService.removeItem(
                            new TitleGameDto(tokens[1]));
                    break;
                case "BuyItem":
                    this.orderService.buyItems();
                    continue;
            }

            System.out.println(command);
        }
    }
}
