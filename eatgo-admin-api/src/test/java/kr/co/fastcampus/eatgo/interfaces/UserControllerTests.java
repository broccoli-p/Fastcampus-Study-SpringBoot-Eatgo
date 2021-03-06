package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.apache.catalina.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
            .email("tester@example.com")
            .name("tester")
            .level(1L)
            .build()
        );
        given(userService.getUsers()).willReturn(users);

        mvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("tester")));
    }

    @Test
    public void create() throws Exception {
        String email = "admin@example.com";
        String name = "Administrator";
        Long level = 1004L;
        String password = "test";
        User user = User.builder()
            .id(1004L)
            .email(email)
            .name(name)
            .level(level)
            .password(password)
            .build();

        given(userService.addUser(email, name, password, level)).willReturn(user);

        mvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"admin@example.com\", \"name\":\"Administrator\", \"password\":\"test\", \"level\":1004}"))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", "/users/1004"))
        ;

        verify(userService).addUser(email, name, password, level);

    }

    @Test
    public void update() throws Exception {
        mvc.perform(patch("/users/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"admin@exmaple.com\"," +
                "\"name\":\"Administrator\",\"level\":100}"))
            .andExpect(status().isOk());

        Long id = 1004L;
        String email = "admin@exmaple.com";
        String name = "Administrator";
        Long level = 100L;

        verify(userService).updateUser(eq(id), eq(email), eq(name), eq(level));

    }

    @Test
    public void deactivate() throws Exception {
        mvc.perform(delete("/users/1004"))
            .andExpect(status().isOk());

        verify(userService).deactiveUser(1004L);
    }
}