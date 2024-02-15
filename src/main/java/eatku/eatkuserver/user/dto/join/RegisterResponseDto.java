package eatku.eatkuserver.user.dto.join;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {
    private String statusMessage;
}
