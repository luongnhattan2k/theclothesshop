package nhattan.lnt.clothesshop.DTO;

public class CTHoaDonDTO {
    int IDCTHOADON, IDSANPHAM,IDTAIKHOAN;
    String TENSANPHAM;
    int SOLUONG,THANHTIEN;

    public CTHoaDonDTO(int IDCTHOADON, int IDSANPHAM, int IDTAIKHOAN, String TENSANPHAM, int SOLUONG, int THANHTIEN) {
        this.IDCTHOADON = IDCTHOADON;
        this.IDSANPHAM = IDSANPHAM;
        this.IDTAIKHOAN = IDTAIKHOAN;
        this.TENSANPHAM = TENSANPHAM;
        this.SOLUONG = SOLUONG;
        this.THANHTIEN = THANHTIEN;
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
}
