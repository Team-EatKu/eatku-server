package eatku.eatkuserver.user.dto;

import eatku.eatkuserver.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String nickName;
    private String profileImage;

    public static UserDto from(User user) {
        return UserDto.builder()
                .nickName(user.getNickName())
                .profileImage(user.getProfileImageUrl())
                .build();
    }
}
