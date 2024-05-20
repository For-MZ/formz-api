package ForMZ.Server.User.Dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String email;
    private String nickName;
    private String profileImage;


}
