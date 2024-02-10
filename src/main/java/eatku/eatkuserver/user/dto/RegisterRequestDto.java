package eatku.eatkuserver.user.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String authNumber;
}
