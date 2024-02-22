package eatku.eatkuserver.restaurant.dto;

import eatku.eatkuserver.restaurant.domain.Category;
import eatku.eatkuserver.restaurant.domain.Hashtag;
import eatku.eatkuserver.restaurant.domain.Menu;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class RestaurantRegisterRequestDto {
    private String name;
    private String address;
    private String location;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private String information;
    private String startTime;
    private String endTime;
    private List<Menu> menuList;
    private List<Category> categoryList;
    private List<Hashtag> hashtagList;
}
