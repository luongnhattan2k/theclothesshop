package nhattan.lnt.clothesshop.DTO;

public class TinTucDTO {
    int IDTINTUC;
    String TIEUDE, NGAYDANG, NOIDUNG;
    byte[] HINHANH;

    public TinTucDTO(int IDTINTUC, String TIEUDE,  String NOIDUNG, byte[] HINHANH, String NGAYDANG) {
        this.IDTINTUC = IDTINTUC;
        this.TIEUDE = TIEUDE;
        this.NOIDUNG = NOIDUNG;
        this.NGAYDANG = NGAYDANG;
        this.HINHANH = HINHANH;
    }

    public int getIDTINTUC() {
        return IDTINTUC;
    }

    public void setIDTINTUC(int IDTINTUC) {
        this.IDTINTUC = IDTINTUC;
    }

    public String getTIEUDE() {
        return TIEUDE;
    }

    public void setTIEUDE(String TIEUDE) {
        this.TIEUDE = TIEUDE;
    }

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }

    public String getNGAYDANG() {
        return NGAYDANG;
    }

    public void setNGAYDANG(String NGAYDANG) {
        this.NGAYDANG = NGAYDANG;
    }

    public byte[] getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(byte[] HINHANH) {
        this.HINHANH = HINHANH;
    }
}
