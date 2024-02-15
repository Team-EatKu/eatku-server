package eatku.eatkuserver.user.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
