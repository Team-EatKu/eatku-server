package eatku.eatkuserver.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
