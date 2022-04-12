package nhattan.lnt.clothesshop.DTO;

public class TaiKhoanDTO {
    int MATK,QUYEN,SDT,SLM;
    String TENTK,MATKHAU,EMAIL,DIACHI;
    byte[] HINHANH;

    public TaiKhoanDTO(){
        this.MATK = -1;
    }


    public TaiKhoanDTO(int MATK, String TENTK, String MATKHAU, byte[] HINHANH, int SDT, String EMAIL, String DIACHI, int QUYEN, int SLM) {
        this.MATK = MATK;
        this.TENTK = TENTK;
        this.MATKHAU = MATKHAU;
        this.HINHANH = HINHANH;
        this.SDT = SDT;
        this.EMAIL = EMAIL;
        this.DIACHI = DIACHI;
        this.QUYEN = QUYEN;
        this.SLM = SLM;
    }


    public int getMATK() {
        return MATK;
    }

    public void setMATK(int MATK) {
        this.MATK = MATK;
    }

    public String getTENTK() {
        return TENTK;
    }

    public void setTENTK(String TENTK) {
        this.TENTK = TENTK;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public int getQUYEN() {
        return QUYEN;
    }

    public void setQUYEN(int QUYEN) {
        this.QUYEN = QUYEN;
    }

    public byte[] getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(byte[] HINHANH) {
        this.HINHANH = HINHANH;
    }

    public int getSLM() {
        return SLM;
    }

    public void setSLM(int SLM) {
        this.SLM = SLM;
    }
}
