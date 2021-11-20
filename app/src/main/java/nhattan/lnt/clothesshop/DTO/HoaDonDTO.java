package nhattan.lnt.clothesshop.DTO;

public class HoaDonDTO {
    int IDHOADON,TONGTIEN,IDCTHOADON;
    String GHICHI,DIACHI;
    int IDTAIKHOAN;

    public HoaDonDTO(int IDHOADON, int TONGTIEN, int IDCTHOADON, String GHICHI, String DIACHI, int IDTAIKHOAN) {
        this.IDHOADON = IDHOADON;
        this.TONGTIEN = TONGTIEN;
        this.IDCTHOADON = IDCTHOADON;
        this.GHICHI = GHICHI;
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

    public String getGHICHI() {
        return GHICHI;
    }

    public void setGHICHI(String GHICHI) {
        this.GHICHI = GHICHI;
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
