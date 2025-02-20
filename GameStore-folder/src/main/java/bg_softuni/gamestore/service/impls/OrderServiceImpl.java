package bg_softuni.gamestore.service.impls;

import bg_softuni.gamestore.data.entities.Game;
import bg_softuni.gamestore.data.entities.Order;
import bg_softuni.gamestore.data.entities.User;
import bg_softuni.gamestore.data.repositories.GameRepository;
import bg_softuni.gamestore.data.repositories.OrderRepository;
import bg_softuni.gamestore.data.repositories.UserRepository;
import bg_softuni.gamestore.service.OrderService;
import bg_softuni.gamestore.service.UserService;
import bg_softuni.gamestore.service.dtos.TitleGameDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final GameRepository gameRepository;
    private final UserService userService;

    private final Set<Game> shoppingCart;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            GameRepository gameRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.shoppingCart = new HashSet<>();
    }

    @Override
    public String addItem(TitleGameDto titleGameDto) {
        Game game = this.gameRepository.findByTitle(titleGameDto.getTitle());
        if (game == null) {
            return "Not found game";
        }

        if (this.userService.getloginUser() != null &&
                this.userRepository.findByGamesTitle(titleGameDto.getTitle()) == null) {
            Order order = new Order();
            order.setBuyer(this.userService.getloginUser());
            Set<Game> games = new HashSet<>();
            games.add(game);
            order.setGames(games);

            this.orderRepository.save(order);
            this.shoppingCart.add(game);
        }

        return String.format("%s added to cart.", game.getTitle());
    }

    @Override
    public String removeItem(TitleGameDto titleGameDto) {
        Game game = this.gameRepository.findByTitle(titleGameDto.getTitle());
        if (!this.shoppingCart.contains(game)) {
            return String.format("Not found game %s in shopping cart.", titleGameDto.getTitle());
        }
        this.shoppingCart.remove(game);

        return String.format("%s removed from cart.", titleGameDto.getTitle());
    }

    @Override
    public void buyItems() {

        if (this.shoppingCart.isEmpty()) {
            System.out.println("No games in shopping cart.");
        }

        User user = this.userService.getloginUser();

        Set<Game> userGames = user.getGames();
        userGames.addAll(shoppingCart);

        user.setGames(userGames);
        this.userRepository.save(user);
        Integer userId = user.getId();

        this.orderRepository.removeByBuyerId(userId);

        System.out.println("Successfully bought games:");
        this.shoppingCart.forEach(g -> System.out.printf(" -%s%n", g.getTitle()));
    }
}
