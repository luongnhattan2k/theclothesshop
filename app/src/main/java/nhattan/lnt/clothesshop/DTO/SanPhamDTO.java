package nhattan.lnt.clothesshop.DTO;

public class SanPhamDTO {

    int MaSP;
    byte[] ImageSP;
    String TenSP;
    int GiaSP;
    int Sl_SP;
    String MotaSP;
    int ID_DANHMUC;
    int SP_New;

    public SanPhamDTO() {

    }

    public SanPhamDTO(int maSP, byte[] imageSP, String tenSP, int giaSP, int sl_SP, String motaSP, int ID_DANHMUC, int SP_New) {
        this.MaSP = maSP;
        this.TenSP = tenSP;
        this.GiaSP = giaSP;
        this.ImageSP = imageSP;
        this.MotaSP = motaSP;
        this.Sl_SP = sl_SP;
        this.ID_DANHMUC = ID_DANHMUC;
        this.SP_New = SP_New;
    }


    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public byte[] getImageSP() {
        return ImageSP;
    }

    public void setImageSP(byte[] imageSP) {
        ImageSP = imageSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(int giaSP) {
        GiaSP = giaSP;
    }

    public int getSl_SP() {
        return Sl_SP;
    }

    public void setSl_SP(int sl_SP) {
        Sl_SP = sl_SP;
    }

    public String getMotaSP() {
        return MotaSP;
    }

    public void setMotaSP(String motaSP) {
        MotaSP = motaSP;
    }

    public int getID_DANHMUC() {
        return ID_DANHMUC;
    }

    public void setID_DANHMUC(int ID_DANHMUC) {
        this.ID_DANHMUC = ID_DANHMUC;
    }

    public int getSP_New() {
        return SP_New;
    }

    public void setSP_New(int SP_New) {
        this.SP_New = SP_New;
    }

}
