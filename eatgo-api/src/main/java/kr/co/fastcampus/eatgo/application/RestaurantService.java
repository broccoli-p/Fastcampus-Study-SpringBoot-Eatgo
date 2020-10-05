package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }


    public Restaurant getRestaurantById(long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        restaurant.setMenuItems(menuItems);
        return restaurant;
    }

    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    // 명시적으로 save를 해 주었으나 이번에는 아무것도 하지 않았다.
    //
    @Transactional // 적용하면 트랜젝션이 해당 범위를 벗어나면서 적용된다.
    public Restaurant updateRestaurant(long id, String name, String address) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
//        restaurant.setName(name);
//        restaurant.setAddress(address);
        restaurant.updateInformation(name, address);

        return restaurant;
    }
}
