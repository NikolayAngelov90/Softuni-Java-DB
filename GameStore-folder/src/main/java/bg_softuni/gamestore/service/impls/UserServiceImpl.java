package bg_softuni.gamestore.service.impls;

import bg_softuni.gamestore.data.entities.User;
import bg_softuni.gamestore.data.repositories.UserRepository;
import bg_softuni.gamestore.service.UserService;
import bg_softuni.gamestore.service.dtos.UserCreateDto;
import bg_softuni.gamestore.service.dtos.UserLoginDto;
import bg_softuni.gamestore.utils.ValidatorUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    private User loginUser = null;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String registerUser(UserCreateDto userCreateDto) {
        StringBuilder sb = new StringBuilder();

        if (!userCreateDto.getPassword().equals(userCreateDto.getConfirmPassword())) {
            sb.append("The passwords don't match!\n");
        }

        if (!validatorUtil.isValid(userCreateDto)) {
            for (ConstraintViolation<UserCreateDto> violation : validatorUtil.validate(userCreateDto)) {
                System.out.print(violation.getMessage());
            }
        } else {
            User user = this.modelMapper.map(userCreateDto, User.class);
            setUserAdmin(user);

            this.userRepository.save(user);

            sb.append(String.format("%s was registered\n", user.getFullName()));
        }
        return sb.toString();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {

        User user = userRepository.findByEmail(userLoginDto.getEmail());
        if (user == null) {
            return "The email address does not exist!";
        } else if (!user.getPassword().equals(userLoginDto.getPassword())) {
            return "The password does not match!";
        }

        loginUser = user;
        return String.format("Successfully logged in %s", loginUser.getFullName());
    }

    @Override
    public String logoutUser() {
        if (loginUser != null) {
            return String.format("User %s successfully logged out", loginUser.getFullName());
        } else {
            return "Cannot log out. No user was logged in.";
        }
    }

    @Override
    public User getloginUser() {
        return this.loginUser;
    }

    private void setUserAdmin(User user) {
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }
    }
}
