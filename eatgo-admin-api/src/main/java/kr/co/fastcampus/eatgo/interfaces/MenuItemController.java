package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MenuItemController {
    @Autowired
    MenuItemService menuItemService;
    @PatchMapping("/restaurants/{id}/menuitems")
    public String bulkUpdate(
        @PathVariable Long id,
        @RequestBody List<MenuItem> menuItems
    ) {
        //List<MenuItem> menuItems = new ArrayList<>();
        menuItemService.bulkUpdate(id, menuItems);
        return "";

    }
    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public List<MenuItem> list(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = menuItemService.getMenuItems(restaurantId);
        return menuItems;
    }

}
