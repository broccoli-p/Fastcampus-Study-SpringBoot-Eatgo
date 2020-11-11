package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, passwordEncoder);
        mockUserRepository();
    }

    public void mockUserRepository() {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
            .email("tester@example.com")
            .name("tester")
            .level(1L)
            .build());

        given(userRepository.findAll()).willReturn(users);
    }

    @Test
    public void getUsers() {
        List<User> users = userService.getUsers();
        User user = users.get(0);
        assertThat(user.getName(), is("tester"));
    }

    @Test
    public void addUser() {
        String email = "admin@example.com";
        String name = "Administrator";
        String password = "test";
        Long level = 1004L;
        User mockUser = User.builder()
            .email(email)
            .name(name)
            .level(level)
            .password(password)
            .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name, password, level);

        assertThat(user.getName(), is(name));
    }

    @Test
    public void updateUser() {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Superman";
        Long level = 100L;
        User mockUser = User.builder()
            .id(id)
            .email(email)
            .name(name)
            .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));
        assertThat(user.getName(), is("Superman"));
    }

    @Test
    public void deactivateUser() {
        Long id = 1004L;

        User mockUser = User.builder()
            .id(id)
            .email("admin@example.com")
            .name("Administrator")
            .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));
        User user = userService.deactiveUser(1004L);
        verify(userRepository).findById(1004L);
        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }
}