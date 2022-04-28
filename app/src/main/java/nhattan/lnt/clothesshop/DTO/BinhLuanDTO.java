package nhattan.lnt.clothesshop.DTO;

public class BinhLuanDTO {

    int IDTAIKHOAN;
    String TENTAIKHOAN;
    float Danhgia;
    byte[] Hinhdaidien;
    String NoidungBL;
    String ThoiGianBL;
    String Size;
    int IDSANPHAM;

    public BinhLuanDTO(int IDTAIKHOAN, String TENTAIKHOAN, float danhgia, byte[] hinhdaidien, String noidungBL, String thoiGianBL, String Size) {
        this.IDTAIKHOAN = IDTAIKHOAN;
        this.TENTAIKHOAN = TENTAIKHOAN;
        Danhgia = danhgia;
        Hinhdaidien = hinhdaidien;
        NoidungBL = noidungBL;
        ThoiGianBL = thoiGianBL;
        this.Size = Size;
    }

    public int getIDTAIKHOAN() {
        return IDTAIKHOAN;
    }

    public void setIDTAIKHOAN(int IDTAIKHOAN) {
        this.IDTAIKHOAN = IDTAIKHOAN;
    }

    public String getTENTAIKHOAN() {
        return TENTAIKHOAN;
    }

    public void setTENTAIKHOAN(String TENTAIKHOAN) {
        this.TENTAIKHOAN = TENTAIKHOAN;
    }

    public float getDanhgia() {
        return Danhgia;
    }

    public void setDanhgia(float danhgia) {
        Danhgia = danhgia;
    }

    public byte[] getHinhdaidien() {
        return Hinhdaidien;
    }

    public void setHinhdaidien(byte[] hinhdaidien) {
        Hinhdaidien = hinhdaidien;
    }

    public String getNoidungBL() {
        return NoidungBL;
    }

    public void setNoidungBL(String noidungBL) {
        NoidungBL = noidungBL;
    }

    public String getThoiGianBL() {
        return ThoiGianBL;
    }

    public void setThoiGianBL(String thoiGianBL) {
        ThoiGianBL = thoiGianBL;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getIDSANPHAM() {
        return IDSANPHAM;
    }

    public void setIDSANPHAM(int IDSANPHAM) {
        this.IDSANPHAM = IDSANPHAM;
    }

}
