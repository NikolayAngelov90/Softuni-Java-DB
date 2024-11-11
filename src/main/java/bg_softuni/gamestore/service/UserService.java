package bg_softuni.gamestore.service;

import bg_softuni.gamestore.data.entities.User;
import bg_softuni.gamestore.service.dtos.UserCreateDto;
import bg_softuni.gamestore.service.dtos.UserLoginDto;

public interface UserService {

    String registerUser(UserCreateDto userCreateDto);

    String loginUser(UserLoginDto userLoginDto);

    String logoutUser();

    User getloginUser();
}
