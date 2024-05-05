package ForMZ.Server.domain.file.entity;

import ForMZ.Server.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    @Column(nullable = false)
    private String fileUrl;
    @Column(nullable = false)
    private String fileOriginName;

    @Column(nullable = false)
    private String fileKey;  // s3 파일 저장시 발급되는 고유 키
    @Column
    private String fileSize;

    public void updateFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }
}
