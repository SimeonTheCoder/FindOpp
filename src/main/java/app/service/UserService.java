package app.service;

import app.model.dto.UserLoginBinding;
import app.model.dto.UserRegisterBinding;
import app.model.entities.User;

public interface UserService {

    User findByUsername(String username);

    boolean login(UserLoginBinding userLoginBindingModel);

    boolean register(UserRegisterBinding userRegisterBindingModel);
}
