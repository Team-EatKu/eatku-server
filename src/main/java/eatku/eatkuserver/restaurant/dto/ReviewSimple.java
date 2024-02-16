package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.user.dto.UserSimple;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewSimple {

    private Long id;
    private UserSimple user;

    private String content;
    private int scope;
    private List<String> imageUrls;
}
