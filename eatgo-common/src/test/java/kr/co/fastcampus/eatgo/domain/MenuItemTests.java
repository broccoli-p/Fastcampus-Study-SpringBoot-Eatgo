package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuItemTests {
    @Test
    public void creation() {
        //Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        MenuItem menuItem = MenuItem.builder()
            .name("Kimchi")
            .restaurantId(1004L)
            .build();
        assertThat(menuItem.getName(), is("Kimchi"));
        assertThat(menuItem.getRestaurantId(), is(1004L));

    }
}