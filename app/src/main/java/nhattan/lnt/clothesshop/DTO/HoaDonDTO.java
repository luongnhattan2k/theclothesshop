package nhattan.lnt.clothesshop.DTO;

public class HoaDonDTO {
    int IDHOADON,TONGTIEN,IDCTHOADON;
    String GHICHU,DIACHI;
    int IDTAIKHOAN;

    public HoaDonDTO(int IDHOADON, int IDTAIKHOAN, int IDCTHOADON, int TONGTIEN, String DIACHI, String GHICHI  ) {
        this.IDHOADON = IDHOADON;
        this.TONGTIEN = TONGTIEN;
        this.IDCTHOADON = IDCTHOADON;
        this.GHICHU = GHICHI;
        this.DIACHI = DIACHI;
        this.IDTAIKHOAN = IDTAIKHOAN;
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
}
