package eatku.eatkuserver.user.dto.join;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String nickName;
    private String lectureBuilding;
    private String authNumber;
}
