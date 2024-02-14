package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.restaurant.domain.Category;
import eatku.eatkuserver.restaurant.domain.Hashtag;
import eatku.eatkuserver.restaurant.domain.Menu;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RestaurantRegisterRequestDto {
    private String name;
    private String location;
    private Long latitude;
    private Long longitude;
    private String endTime;
    private List<Menu> menuList;
    private List<Category> categoryList;
    private List<Hashtag> hashtagList;
}
