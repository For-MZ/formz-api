package ForMZ.Server.Post.Dto;

import lombok.Data;

@Data
public class BookMarkPostDto {
    String status;

    public BookMarkPostDto(String status) {
        this.status = status;
    }
}
