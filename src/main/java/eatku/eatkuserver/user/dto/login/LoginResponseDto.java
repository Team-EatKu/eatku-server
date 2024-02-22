package eatku.eatkuserver.user.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String email;
    private String nickName;
    private String token;
    private String lectureBuilding;
}
