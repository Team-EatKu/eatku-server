package eatku.eatkuserver.review.domain;

import eatku.eatkuserver.restaurant.domain.BaseTimeEntity;
import eatku.eatkuserver.restaurant.domain.Menu;
import eatku.eatkuserver.restaurant.domain.Restaurant;
import eatku.eatkuserver.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false, length = 200)
    private String content;

    @Column(nullable = false)
    private int scope;

    @Column(name = "review_image_url")
    @ElementCollection
    private List<String> imageUrls = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;



}
