package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MenuItemRepositoryTests {
    @Mock
    private MenuItemRepository menuItemRepository;
    @BeforeEach // @Test 실행 전 실행, 아래 테스트는 스프링 테스트가 아니므로 의존성을 수동으로 주읩해줘야한다.
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMenuItemRepository();

    }

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

    @Test
    public void findAllByRestaurantId() {
        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(1004L);
        MenuItem menuItem = menuItems.get(0);
        assertThat(menuItem.getName(), is("KimChi"));
    }
}