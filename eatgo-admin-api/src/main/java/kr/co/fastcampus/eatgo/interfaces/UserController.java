package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Transactional
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // 1. User list
    // 2. User create -> 회원가입
    // 3. User update
    // 4. User delete
    //  -> (1:Custoner, 2: Restaurant owner, 3:admin, 4:Not action
    @GetMapping("/users")
    public List<User> list() {
        List<User> users = userService.getUsers();
        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(
        @RequestBody User resource
    ) throws URISyntaxException {
        User user = userService.addUser(
            resource.getEmail(),
            resource.getName(),
            resource.getPassword(),
            resource.getLevel()
        );
        String url = "/users/" + user.getId();
        return ResponseEntity.created(new URI(url)).body("{}");

    }

    @PatchMapping("/users/{userId}")
    public String updateUser(
        @PathVariable("userId") Long id,
        @RequestBody User resource
    ) {
        userService.updateUser(
            id,
            resource.getEmail(),
            resource.getName(),
            resource.getLevel()
        );
        return "{}";
    }

    @DeleteMapping("/users/{userId}")
    public String deactivate(
        @PathVariable("userId") Long id
    ) {
        userService.deactiveUser(id);
        return "{}";
    }
}
