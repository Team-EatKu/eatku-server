package eatku.eatkuserver.restaurant.domain;

import eatku.eatkuserver.like.domain.Like;
import eatku.eatkuserver.restaurant.dto.RestaurantDto;
import eatku.eatkuserver.review.domain.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phoneNumber;

    private String information;

    private String profileImageUrl;

    private double averageScope;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    private String startTime;

    private String endTime;



    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RestaurantCategory> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Review> reiviewList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RestaurantHashtag> hashtagList = new ArrayList<>();


}
