package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

class RestaurantServiceTests {

    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach // @Test 실행 전 실행, 아래 테스트는 스프링 테스트가 아니므로 의존성을 수동으로 주읩해줘야한다.
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockRestaurantRepository();
        mockMenuItemRepository();
        mockReviewRepository();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);

    }

    private void mockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder()
            .name("BeRyong")
            .score(1)
            .description("Bad")
            .build());

        given(reviewRepository.findAllByRestaurantId(1004L))
            .willReturn(reviews);
    }

    // 가짜 객체를 이용해서 테스트 진행
    private void mockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(
            MenuItem.builder()
            .name("KimChi")
            .restaurantId(1004L)
            .build()
        );
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void mockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
//        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        Restaurant restaurant = Restaurant.builder()
            .id(1004L)
            .name("Bob zip")
            .address("Seoul")
            .build();
        MenuItem menuItem = MenuItem.builder()
            .name("Kimchi")
            .build();
        restaurant.setMenuItems(Arrays.asList(menuItem));
        Review review = Review.builder()
            .name("JOKER")
            .score(5)
            .description("Great!")
            .build();
        restaurant.setReviews(Arrays.asList(review));

        restaurants.add(restaurant);

        given(restaurantRepository.findAll()).willReturn(restaurants);
        given(restaurantRepository.findById(1004L)).
            willReturn(Optional.of(restaurant));

    }

    @Test
    void getRestaurantWithExisted() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);

        assertThat(restaurant.getId(), is(1004L));

        MenuItem menuItem = restaurant.getMenuItems().get(0);

        assertThat(menuItem.getName(), is("Kimchi"));

        Review review = restaurant.getReviews().get(0);

        assertThat(review.getDescription(), is("Great!"));
    }

    @Test
    void getRestaurantWithNotExisted() {
        // 아래 코드는 Junit4에서는 @Test(expected = RestaurantNotFoundException.class) 로 사용 가능
        Assertions.assertThrows(RestaurantNotFoundException.class, () -> {
           restaurantService.getRestaurantById(404L);
        });
    }


    @Test
    void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }


    @Test
    void addRestaurant(){
//        Restaurant restaurant = new Restaurant("BeRyong", "Busan");
//        Restaurant saved = new Restaurant(1234L, "ByRyong", "Busan");

        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        Restaurant restaurant = Restaurant.builder()
            .id(1004L)
            .name("Bob zip")
            .address("Seoul")
            .build();
        Restaurant saved = Restaurant.builder()
            .id(1234L)
            .name("ByRyong")
            .address("Seoul")
            .build();
        //given(restaurantRepository.save(any())).willReturn(saved);


        Restaurant created = restaurantService.addRestaurant(restaurant);


        assertThat(created.getId(), is(1234L));
    }

    @Test
    void updateRestaurant() {
//        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        Restaurant restaurant = Restaurant.builder()
            .id(1004L)
            .name("Bob zip")
            .address("Seoul")
            .build();
        restaurantService.addRestaurant(restaurant);


        given(restaurantRepository.findById(1004L))
            .willReturn(Optional.of(restaurant));

        Restaurant updated = restaurantService.updateRestaurant(1004L, "Sul zip", "Busan");
        assertThat(updated.getName(), is("Sul zip"));
    }
}