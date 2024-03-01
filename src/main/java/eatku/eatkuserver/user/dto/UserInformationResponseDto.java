package eatku.eatkuserver.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInformationResponseDto {
    private String nickName;
    private String email;
    private String lectureBuilding;
    private String profileImage;
}
