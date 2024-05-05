package ForMZ.Server.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MailReq {
    @Email
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;
}
