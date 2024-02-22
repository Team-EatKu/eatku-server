package eatku.eatkuserver.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String nickName;
    private String profileImage;
}
