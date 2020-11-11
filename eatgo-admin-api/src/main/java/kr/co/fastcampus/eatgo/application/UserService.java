package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.EmailExistedException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(String email, String name, String password, Long level) {
        Optional<User> existedUser = userRepository.findByEmail(email);
        if (existedUser.isPresent()) {
            throw new EmailExistedException(email);
        }

        String encodedPassword = passwordEncoder.encode(password);

        return userRepository.save(
            User.builder()
                .level(level)
                .email(email)
                .name(name)
                .password(encodedPassword)
                .build()
        );
    }

    public User updateUser(Long id, String email, String name, Long level) {
        // TODO: restaurantService의 예외 처리 참고
        User user =  userRepository.findById(id).orElse(null);

        user.setName(name);
        user.setEmail(email);
        user.setLevel(level);

        return user;
    }

    public User deactiveUser(long id) {
        // TODO: 실제 작업 필요
        User user =  userRepository.findById(id).orElse(null);
        user.deactivate();
        return user;
    }
}
