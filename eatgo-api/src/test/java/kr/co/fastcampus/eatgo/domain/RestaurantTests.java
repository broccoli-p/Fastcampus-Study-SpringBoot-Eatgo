package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests {
    @Test
    public void creation() {
        //Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        Restaurant restaurant = Restaurant.builder()
            .id(1004L)
            .name("Bob zip")
            .address("Seoul")
            .build();
        assertThat(restaurant.getName(), is("Bob zip"));
        assertThat(restaurant.getAddress(), is("Seoul"));
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void information() {
        //Restaurant restaurant = new Restaurant("Bob zip", "Seoul");
        Restaurant restaurant = Restaurant.builder()
            .id(1004L)
            .name("Bob zip")
            .address("Seoul")
            .build();
        restaurant.setName("Sul zip");

        assertThat(restaurant.getInformation(), is("Sul zip in Seoul"));

    }
}