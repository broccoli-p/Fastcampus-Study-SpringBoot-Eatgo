package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

// Restaurant 는 데이터의 속성을 표현하는데 사용된다.
@Entity
@Getter
@NoArgsConstructor // 속성이 없는거에 대해 자동으로 생성
// @Setter // 전체 적용
@AllArgsConstructor
@Builder // 빌드패턴 사용
public class Restaurant {
    @Id  // Entity의 ID 지정
    @GeneratedValue // 자동 생성
    @Setter
    private Long id;
    @Setter // 특정 속성 적용
    @NotEmpty // NotEmpty Validation
    private String name;
    @NotEmpty
    private String address;
//    private String regionName; // Korean
//    private String categoryName; // Korean
//    private String tagName; // #JMT
    // 디비 저장 처리를 하지 않음
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)// NULL이 아닌 경우에만 반환
    private List<MenuItem> menuItems;
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)// NULL이 아닌 경우에만 반환
    private List<Review> reviews;
//    public Restaurant() {
//    }
//    아래 내용을 적용하기 싫다면 @AllArgsConstructor
//    public Restaurant(String name, String location){
//        this.name = name;
//        this.address = location;
//    }
//
//    public Restaurant(long id, String name, String address) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }


    public String getInformation() {
        return this.name + " in " + this.address;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<MenuItem>();

        for (MenuItem menuItem:menuItems)
        {
            this.menuItems.add(menuItem);
        }
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getName(){
//        return this.name;
//    }
//    public void setAddress(String address) {
//        this.address = address;
//    }
//    public  String getAddress() {
//        return this.address;
//    }
//    public void setId(long id) {
//        this.id = id;
//    }
//    public Long getId() {
//        return this.id;
//    }
//    public List<MenuItem> getMenuItems() {
//        return this.menuItems;
//    }
}
