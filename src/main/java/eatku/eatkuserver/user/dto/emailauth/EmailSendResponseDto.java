package eatku.eatkuserver.user.dto.emailauth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailSendResponseDto {
    String statusMessage;
}
