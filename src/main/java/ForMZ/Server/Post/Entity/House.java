package ForMZ.Server.Post.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;

    private String hsmpSn;
    private String insttNm;
    private String brtcNm;
    private String signguNm;
    private String hsmpNm;
    private String rnAdres;
    private String competDe;
    private String hshldCo;
    private String suplyTyNm;
    private String styleNm;
    private String suplyPrvuseAr;
    private String suplyCmnuseAr;
    private String houseTyNm;
    private String heatMthdDetailNm;
    private String buldStleNm;
    private String elvtrInstlAtNm;
    private String parkngCo;
    private String bassRentGtn;
    private String bassMtRntchrg;
    private String bassCnvrsGtnLmt;

    public House() {
    }
    public House(String hsmpSn, String insttNm, String brtcNm, String signguNm, String hsmpNm, String rnAdres, String competDe, String hshldCo, String suplyTyNm, String styleNm, String suplyPrvuseAr, String suplyCmnuseAr, String houseTyNm, String heatMthdDetailNm, String buldStleNm, String elvtrInstlAtNm, String parkngCo, String bassRentGtn, String bassMtRntchrg, String bassCnvrsGtnLmt) {
        this.hsmpSn = hsmpSn;
        this.insttNm = insttNm;
        this.brtcNm = brtcNm;
        this.signguNm = signguNm;
        this.hsmpNm = hsmpNm;
        this.rnAdres = rnAdres;
        this.competDe = competDe;
        this.hshldCo = hshldCo;
        this.suplyTyNm = suplyTyNm;
        this.styleNm = styleNm;
        this.suplyPrvuseAr = suplyPrvuseAr;
        this.suplyCmnuseAr = suplyCmnuseAr;
        this.houseTyNm = houseTyNm;
        this.heatMthdDetailNm = heatMthdDetailNm;
        this.buldStleNm = buldStleNm;
        this.elvtrInstlAtNm = elvtrInstlAtNm;
        this.parkngCo = parkngCo;
        this.bassRentGtn = bassRentGtn;
        this.bassMtRntchrg = bassMtRntchrg;
        this.bassCnvrsGtnLmt = bassCnvrsGtnLmt;
    }
}
