package ForMZ.Server.Core.Dto;

import lombok.Data;

@Data
public class JoinDto {
    private Long id;
    private String message;

    public JoinDto(Long user_id, String message) {
        this.id = user_id;
        this.message = message;
    }
}
