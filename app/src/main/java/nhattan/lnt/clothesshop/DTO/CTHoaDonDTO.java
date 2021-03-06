package nhattan.lnt.clothesshop.DTO;

public class CTHoaDonDTO {
    int IDCTHOADON, IDSANPHAM,IDTAIKHOAN,SOLUONG,THANHTIEN,TONGHOADON,TINHTRANG,SDT;
    String TENSANPHAM, NGAYDAT, GHICHU, TENTAIKHOAN, DIACHI, SIZE;

    public CTHoaDonDTO(int IDCTHOADON, int IDSANPHAM, int IDTAIKHOAN, String TENTAIKHOAN,
                       String TENSANPHAM, String NGAYDAT, int SOLUONG, int THANHTIEN, int TONGHOADON,
                       String GHICHU, String DIACHI, String SIZE, int TINHTRANG, int SDT) {
        this.IDCTHOADON = IDCTHOADON;
        this.IDSANPHAM = IDSANPHAM;
        this.IDTAIKHOAN = IDTAIKHOAN;
        this.TENTAIKHOAN = TENTAIKHOAN;
        this.TENSANPHAM = TENSANPHAM;
        this.NGAYDAT = NGAYDAT;
        this.SOLUONG = SOLUONG;
        this.THANHTIEN = THANHTIEN;
        this.TONGHOADON = TONGHOADON;
        this.GHICHU = GHICHU;
        this.DIACHI = DIACHI;
        this.SIZE = SIZE;
        this.TINHTRANG = TINHTRANG;
        this.SDT = SDT;
    }

    public CTHoaDonDTO() {

    }

    public int getIDCTHOADON() {
        return IDCTHOADON;
    }

    public void setIDCTHOADON(int IDCTHOADON) {
        this.IDCTHOADON = IDCTHOADON;
    }

    public int getIDSANPHAM() {
        return IDSANPHAM;
    }

    public void setIDSANPHAM(int IDSANPHAM) {
        this.IDSANPHAM = IDSANPHAM;
    }

    public int getIDTAIKHOAN() {
        return IDTAIKHOAN;
    }

    public void setIDTAIKHOAN(int IDTAIKHOAN) {
        this.IDTAIKHOAN = IDTAIKHOAN;
    }

    public String getTENSANPHAM() {
        return TENSANPHAM;
    }

    public void setTENSANPHAM(String TENSANPHAM) {
        this.TENSANPHAM = TENSANPHAM;
    }

    public int getSOLUONG() {
        return SOLUONG;
    }

    public void setSOLUONG(int SOLUONG) {
        this.SOLUONG = SOLUONG;
    }

    public int getTHANHTIEN() {
        return THANHTIEN;
    }

    public void setTHANHTIEN(int THANHTIEN) {
        this.THANHTIEN = THANHTIEN;
    }

    public String getNGAYDAT() {
        return NGAYDAT;
    }

    public void setNGAYDAT(String NGAYDAT) {
        this.NGAYDAT = NGAYDAT;
    }

    public String getGHICHU() {
        return GHICHU;
    }

    public void setGHICHU(String GHICHU) {
        this.GHICHU = GHICHU;
    }

    public String getTENTAIKHOAN() {
        return TENTAIKHOAN;
    }

    public void setTENTAIKHOAN(String TENTAIKHOAN) {
        this.TENTAIKHOAN = TENTAIKHOAN;
    }

    public int getTONGHOADON() {
        return TONGHOADON;
    }

    public void setTONGHOADON(int TONGHOADON) {
        this.TONGHOADON = TONGHOADON;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public String getSIZE() {
        return SIZE;
    }

    public void setSIZE(String SIZE) {
        this.SIZE = SIZE;
    }

    public int getTINHTRANG() {
        return TINHTRANG;
    }

    public void setTINHTRANG(int TINHTRANG) {
        this.TINHTRANG = TINHTRANG;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }
}
