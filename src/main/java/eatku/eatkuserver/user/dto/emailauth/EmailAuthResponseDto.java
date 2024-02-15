package eatku.eatkuserver.user.dto.emailauth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailAuthResponseDto {
    String statustMessage;
    String authNumber;
}
