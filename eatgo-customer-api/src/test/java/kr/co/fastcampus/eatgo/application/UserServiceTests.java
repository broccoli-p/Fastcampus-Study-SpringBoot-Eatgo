package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";
        Long level = 1L;
        userService.registerUser(email, name, password, level);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedEmail() {

        String email = "tester@example.com";
        String name = "Tester";
        String password = "test";
        Long level = 1L;
        User mockUser = User.builder()
            .email(email)
            .password(password)
            .level(level)
            .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        Assertions.assertThrows(EmailExistedException.class,
            ()-> {
                userService.registerUser(email, name, password, level);
            }
        );
        verify(userRepository, never()).save(any());
    }

    @Test
    public void authenticateWithValidAttribute() {
        String email = "tester@example.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        Assertions.assertThrows(EmailNotExistedException.class,
            ()-> {
                userService.authenticate(email, password);
            }
        );
    }

    @Test
    public void authenticateWithWrongPassword() {
        String email = "tester@example.com";
        String name = "Tester";
        String password = "x";
        Long level = 1L;

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());
        User mockUser = User.builder()
            .email(email)
            .password(password)
            .level(level)
            .build();

        given(userRepository.findByEmail(email))
            .willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(false);

        Assertions.assertThrows(PasswordWrongException.class,
            ()-> {
                userService.authenticate(email, password);
            }
        );
    }
}