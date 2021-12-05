package nhattan.lnt.clothesshop.DTO;

public class GopYDTO {

    int IDGY;
    String TENTK;
    int SDT;
    String NOIDUNG;

    public GopYDTO(int IDGY, String TENTK, int SDT, String NOIDUNG) {
        this.IDGY = IDGY;
        this.TENTK = TENTK;
        this.SDT = SDT;
        this.NOIDUNG = NOIDUNG;
    }

    public int getIDGY() {
        return IDGY;
    }

    public void setIDGY(int IDGY) {
        this.IDGY = IDGY;
    }

    public String getTENTK() {
        return TENTK;
    }

    public void setTENTK(String TENTK) {
        this.TENTK = TENTK;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }

}
