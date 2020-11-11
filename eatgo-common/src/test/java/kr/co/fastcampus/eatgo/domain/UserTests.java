package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {
    @Test
    public void creation(){
        User user = User.builder()
            .email("tester@example.com")
            .name("tester")
            .level(100L)
            .build();

        assertThat(user.getName(), is("tester"));
        assertThat(user.isAdmin(), is(true));
        assertThat(user.isActive(), is(true));

        user.deactivate();
        assertThat(user.isActive(), is(false));
    }

    @Test
    public void isRestaurantsOwner() {
        User user = User.builder().level(1L).build();

        assertThat(user.isRestaurantsOwner(), is(false));

        user.setRestaurantId(1004L);

        assertThat(user.isRestaurantsOwner(), is(true));
        assertThat(user.getRestaurantId(), is(1004L));

    }
}