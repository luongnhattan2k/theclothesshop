package nhattan.lnt.clothesshop.DTO;

public class GioHangDTO {

    int IDGIOHANG, IDSP, IDTK, SOLUONG, THANHTIEN;
    byte[] ImageSP;
    String TENSANPHAM, SIZE;

    public GioHangDTO(int IDGIOHANG, byte[] imageSP, int IDSP, String TENSANPHAM, int IDTK, int SOLUONG, int THANHTIEN, String SIZE) {
        this.IDGIOHANG = IDGIOHANG;
        this.ImageSP = imageSP;
        this.IDSP = IDSP;
        this.TENSANPHAM = TENSANPHAM;
        this.IDTK = IDTK;
        this.SOLUONG = SOLUONG;
        this.THANHTIEN = THANHTIEN;
        this.SIZE = SIZE;
    }

    public GioHangDTO(){
        this.IDGIOHANG = -1;
    }

    public int getIDGIOHANG() {
        return IDGIOHANG;
    }

    public void setIDGIOHANG(int IDGIOHANG) {
        this.IDGIOHANG = IDGIOHANG;
    }

    public int getIDSP() {
        return IDSP;
    }

    public void setIDSP(int IDSP) {
        this.IDSP = IDSP;
    }

    public String getTENSANPHAM() {
        return TENSANPHAM;
    }

    public void setTENSANPHAM(String TENSANPHAM) {
        this.TENSANPHAM = TENSANPHAM;
    }

    public int getIDTK() {
        return IDTK;
    }

    public void setIDTK(int IDTK) {
        this.IDTK = IDTK;
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

    public byte[] getImageSP() {
        return ImageSP;
    }

    public void setImageSP(byte[] imageSP) {
        ImageSP = imageSP;
    }

    public String getSIZE() {
        return SIZE;
    }

    public void setSIZE(String SIZE) {
        this.SIZE = SIZE;
    }
}
