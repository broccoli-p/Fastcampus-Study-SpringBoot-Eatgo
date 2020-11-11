package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String email;
    @NotEmpty
    private String name;
    @NotNull
    private Long level;

    private String password;

    private Long restaurantId;

    public boolean isAdmin() {
        return this.level >= 100;
    }

    public boolean isActive() {
        return this.level > 0;
    }

    public void deactivate() {
        this.level = 0L;
    }

    public boolean isRestaurantsOwner() {
        return 50 == this.level;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
