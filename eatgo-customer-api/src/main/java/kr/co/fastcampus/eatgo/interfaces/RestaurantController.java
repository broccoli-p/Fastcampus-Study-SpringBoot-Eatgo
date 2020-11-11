package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller 은 URL 맵핑에 사용된다.

@CrossOrigin
@RestController
public class RestaurantController {

    // 아래 코드는 Controller이 repository를 생성, 관리 하므로 의존성이 생성된다.
    //private RestaurantRepository repository = new RestaurantRepository();

    // @Autowired를 이용하면 Spring이 객체를 생성하여 초기화 해준다.
    // 단, Tests에서는 사용할 수 없기 때문에 수동으로 의존성을 주입해줘야 한다.
    // Tests 의 @SpyBean 확인

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping("/restaurants")
    public List<Restaurant> list(
        @RequestParam("region") String region,
        @RequestParam("category") Long categoryId
    ) {
        //String region = "Seoul";
        List<Restaurant> restaurants = restaurantService.getRestaurants(region, categoryId);
        return restaurants;
    }

    @GetMapping("/restaurants/{id}")
    public Restaurant detail(@PathVariable("id") long id) {
        //try {
        Restaurant restaurant = restaurantService.getRestaurantById(id); // 아래 주석 부분이 들어가있는
        //} catch(RestaurantNotFoundException exception) {
        // TODO 처리할 수도 있고
        //}
        return restaurant;
    }
}
