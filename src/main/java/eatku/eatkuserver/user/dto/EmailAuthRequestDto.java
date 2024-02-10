package eatku.eatkuserver.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailAuthRequestDto {
    String email;
    String authNumber;
}
