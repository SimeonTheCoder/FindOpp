package app.service.impl;

import app.model.dto.UserLoginBinding;
import app.model.dto.UserRegisterBinding;
import app.model.entities.User;
import app.repo.UserRepository;
import app.service.LoggedUser;
import app.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUser loggedUser;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggedUser = loggedUser;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean login(UserLoginBinding binding) {
        User user = userRepository.findByUsername(binding.getUsername());

        if(user == null) return false;
        if (!passwordEncoder.matches(binding.getPassword(), user.getPassword())) return false;

        loggedUser.login(user);

        return true;
    }

    @Override
    @Transactional
    public boolean register(UserRegisterBinding binding) {
        if(!binding.getPassword().equals(binding.getRepeat())) return false;
        if(userRepository.findByUsername(binding.getUsername()) != null) return false;

        if(binding.getUsername().length() < 3 || binding.getUsername().length() > 20) return false;
        if(binding.getPassword().length() < 3 || binding.getPassword().length() > 20) return false;

        User user = new User();

        user.setUsername(binding.getUsername());
        user.setPassword(passwordEncoder.encode(binding.getPassword()));

        user.setFirstName(binding.getFirstName());
        user.setLastName(binding.getLastName());

        user.setEmail(binding.getEmail());
        user.setRole(binding.getRole());

        userRepository.save(user);

        return true;
    }
}
