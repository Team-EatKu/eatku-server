package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.review.domain.Review;
import eatku.eatkuserver.user.dto.UserSimple;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ReviewSimple {

    private Long id;
    private UserSimple user;

    private String content;
    private int scope;
    private List<String> imageUrls;

}
