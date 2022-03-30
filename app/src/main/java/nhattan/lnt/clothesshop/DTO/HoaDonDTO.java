package nhattan.lnt.clothesshop.DTO;

public class HoaDonDTO {
    int IDHOADON,TONGTIEN,IDCTHOADON,IDTAIKHOAN;
    String GHICHU,DIACHI,TENTAIKHOAN,NGAYDAT;

    public HoaDonDTO(int IDHOADON, int IDTAIKHOAN, String TENTAIKHOAN, int IDCTHOADON, int TONGTIEN, String NGAYDAT, String DIACHI, String GHICHI  ) {
        this.IDHOADON = IDHOADON;
        this.TONGTIEN = TONGTIEN;
        this.IDCTHOADON = IDCTHOADON;
        this.GHICHU = GHICHI;
        this.DIACHI = DIACHI;
        this.IDTAIKHOAN = IDTAIKHOAN;
        this.TENTAIKHOAN = TENTAIKHOAN;
        this.NGAYDAT = NGAYDAT;
    }

    public int getIDHOADON() {
        return IDHOADON;
    }

    public void setIDHOADON(int IDHOADON) {
        this.IDHOADON = IDHOADON;
    }

    public int getTONGTIEN() {
        return TONGTIEN;
    }

    public void setTONGTIEN(int TONGTIEN) {
        this.TONGTIEN = TONGTIEN;
    }

    public int getIDCTHOADON() {
        return IDCTHOADON;
    }

    public void setIDCTHOADON(int IDCTHOADON) {
        this.IDCTHOADON = IDCTHOADON;
    }

    public String getGHICHU() {
        return GHICHU;
    }

    public void setGHICHU(String GHICHU) {
        this.GHICHU = GHICHU;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
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

    public String getNGAYDAT() {
        return NGAYDAT;
    }

    public void setNGAYDAT(String NGAYDAT) {
        this.NGAYDAT = NGAYDAT;
    }
}
