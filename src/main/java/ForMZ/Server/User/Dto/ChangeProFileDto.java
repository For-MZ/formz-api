package ForMZ.Server.User.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeProFileDto {
    private String email;
    private String nickName;
    private String password;
    private String profileImage;
}
