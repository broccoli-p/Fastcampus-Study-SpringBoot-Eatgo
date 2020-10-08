package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTests {
    @Autowired
    private MockMvc mvc;

    // 테스트 코드에서는 아래와 같이 의존성을 주입해주어야 한다.
    // @SpyBean()
    // 아래를 이용해서 구현체를 호출 가능하다.
//    @SpyBean(RestaurantRepositoryImpl.class)
//    private RestaurantRepository restaurantRepository;
//
//    @SpyBean(MenuItemRepositoryImpl.class)
//    private MenuItemRepository menuItemRepository;
//
//    @SpyBean(RestaurantService.class)
//    private RestaurantService restaurantService;
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        //restaurants.add(new Restaurant(1004L, "Bob zip", "Seoul"));
        restaurants.add(Restaurant.builder()
            .id(1004L)
            .name("Bob zip")
            .address("Seoul")
            .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                containsString("\"id\":1004")
            ))
            .andExpect(content().string(
                containsString("\"name\":\"Bob zip\"")
            ));
    }

    @Test
    public void detail() throws Exception {

//        Restaurant restaurant1 = new Restaurant(1004L, "JOKER House", "Seoul");
        Restaurant restaurant1 = Restaurant.builder()
            .id(1004L)
            .name("JOKER House")
            .address("Seoul")
            .build();
        restaurant1.setMenuItems(
            Arrays.asList(
                MenuItem.builder()
                    .name("KimChi")
                    .build()
            )
        );

//        restaurant1.addMenuItem(
//                MenuItem.builder()
//                    .name("KimChi")
//                    .build()
//        );
        given(restaurantService.getRestaurantById(1004L)).willReturn(restaurant1);

//        Restaurant restaurant2 = new Restaurant(2020L, "Cyber Food", "Seoul");
        Restaurant restaurant2 = Restaurant.builder()
            .id(2020L)
            .name("Cyber Food")
            .address("Seoul")
            .build();
        given(restaurantService.getRestaurantById(2020L)).willReturn(restaurant2);


        mvc.perform(get("/restaurants/1004"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                containsString("\"id\":1004")
            ))
            .andExpect(content().string(
                containsString("\"name\":\"JOKER House\"")
            ))
            .andExpect(content().string(
                containsString("KimChi")
            ));
        mvc.perform(get("/restaurants/2020"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                containsString("\"id\":2020")
            ))
            .andExpect(content().string(
                containsString("\"name\":\"Cyber Food\"")
            ));
    }

    @Test
    public void detailWithExisted() throws Exception {

//        Restaurant restaurant1 = new Restaurant(1004L, "JOKER House", "Seoul");
        Restaurant restaurant1 = Restaurant.builder()
            .id(1004L)
            .name("JOKER House")
            .address("Seoul")
            .build();
        restaurant1.setMenuItems(
            Arrays.asList(
                MenuItem.builder()
                    .name("KimChi")
                    .build()
            )
        );

//        restaurant1.addMenuItem(
//                MenuItem.builder()
//                    .name("KimChi")
//                    .build()
//        );
        given(restaurantService.getRestaurantById(1004L)).willReturn(restaurant1);

//        Restaurant restaurant2 = new Restaurant(2020L, "Cyber Food", "Seoul");
        Restaurant restaurant2 = Restaurant.builder()
            .id(2020L)
            .name("Cyber Food")
            .address("Seoul")
            .build();
        given(restaurantService.getRestaurantById(2020L)).willReturn(restaurant2);


        mvc.perform(get("/restaurants/1004"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                containsString("\"id\":1004")
            ))
            .andExpect(content().string(
                containsString("\"name\":\"JOKER House\"")
            ))
            .andExpect(content().string(
                containsString("KimChi")
            ));
        mvc.perform(get("/restaurants/2020"))
            .andExpect(status().isOk())
            .andExpect(content().string(
                containsString("\"id\":2020")
            ))
            .andExpect(content().string(
                containsString("\"name\":\"Cyber Food\"")
            ));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurantById(404L))
            .willThrow(new RestaurantNotFoundException());
        mvc.perform(get("/restaurants/404"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void createWithValidData() throws Exception {
        //Restaurant restaurant = new Restaurant(1234L, "BeRyong", "Seoul");

        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                .address(restaurant.getAddress())
                .id(restaurant.getId())
                .name(restaurant.getName())
                .build();
        });
        mvc.perform(post("/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":1234, \"name\":\"BeRyong\", \"address\":\"Seoul\"}"))
            .andExpect(status().isCreated())
        ;

        // 실행 중심인 경우는 verify
        // verify(restaurantService).addRestaurantRepositoryImplTestsRestaurant(restaurant);
        // Mockito에서는 어느 데이터를 넣던 작동하는 any를 제공
        verify(restaurantService).addRestaurant(any());
    }

    @Test
    public void createWithInvalidData() throws Exception {
        given(restaurantService.addRestaurant(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            return Restaurant.builder()
                .address(restaurant.getAddress())
                .id(restaurant.getId())
                .name(restaurant.getName())
                .build();
        });
        mvc.perform(post("/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":1234, \"name\":\"\", \"address\":\"\"}"))
            .andExpect(status().isBadRequest())
        ;
        // 실행 중심인 경우는 verify
        // verify(restaurantService).addRestaurantRepositoryImplTestsRestaurant(restaurant);
        // Mockito에서는 어느 데이터를 넣던 작동하는 any를 제공
    }

    @Test
    public void updateWithValidData() throws Exception {

        mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"JOKER Bar\", \"address\":\"Busan\"}"))
            .andExpect(status().isOk());
        verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
    }

    @Test
    public void updateWithInvalidData() throws Exception {

        mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\", \"address\":\"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithName() throws Exception {

        mvc.perform(patch("/restaurants/1004")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\", \"address\":\"Busan\"}"))
            .andExpect(status().isBadRequest());
    }
}