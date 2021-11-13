package nhattan.lnt.clothesshop.DTO;

public class TaiKhoanDTO {
    int MATK;
    String TENTK;
    String MATKHAU;
    int SDT;
    String EMAIL;
    String NGAYSINH;
    String DIACHI;
    int MAQUYEN;

    public TaiKhoanDTO(){

    }

    public TaiKhoanDTO(int MATK, String TENTK, String MATKHAU, int SDT, String EMAIL, String NGAYSINH,String DIACHI) {
        this.MATK = MATK;
        this.TENTK = TENTK;
        this.MATKHAU = MATKHAU;
        this.SDT = SDT;
        this.EMAIL = EMAIL;
        this.NGAYSINH = NGAYSINH;
        this.DIACHI = DIACHI;
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

    public String getNGAYSINH() {
        return NGAYSINH;
    }

    public void setNGAYSINH(String NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public int getMAQUYEN() {
        return MAQUYEN;
    }

    public void setMAQUYEN(int MAQUYEN) {
        this.MAQUYEN = MAQUYEN;
    }
}
