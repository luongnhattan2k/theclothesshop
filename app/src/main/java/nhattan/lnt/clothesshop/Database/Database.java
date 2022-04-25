package nhattan.lnt.clothesshop.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.DTO.GopYDTO;
import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.DTO.TinTucDTO;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Database(Context context) {
        super(context,"ClothesDatabase",null,2);
    }

    // Truy vấn không trả kết quả: INSERT, CREATE, UPDATE, DELETE, ...
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    // Truy vấn có trả kết quả: SELECT
    public Cursor Getdata(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }


    public boolean SPChuaCoTrongGH(int IDTK, int IDSP){
        Cursor cursor = Getdata("SELECT * FROM GIOHANG WHERE IDTK = " + IDTK + " AND IDSP = " + IDSP );
        while (cursor.moveToNext()) {
            return false;
        }
        return true;
    }

    public boolean SPDaCoTrongGH(int IDTK, int IDSP, String SIZE){
        Cursor cursor = Getdata("SELECT * FROM GIOHANG WHERE IDTK = " + IDTK + " AND IDSP = " + IDSP + " AND SIZE = '" + SIZE +"'" );
        while (cursor.moveToNext()) {
            return false;
        }
        return true;
    }

    public boolean HoaDonChuaCoTrongHD(){
        Cursor cursor = Getdata("SELECT IDCTHOADON FROM CHITIETHOADON " );
        while (cursor.moveToNext()) {
            return false;
            // DA CO TON TAI TRONG HOA DON
        }
        return true;
        // CHUA ID HOA DON
    }

    public void INSERT_HOADON(int IDTK,String TenTK, int IDCTHOADON, double TONGTIEN, String NGAYDAT,
                              String THANGDAT, String NAMDAT, String DIACHI, String GHICHU, int TINHTRANG,
                              int SDT)
    {
        QueryData("INSERT INTO " + CreateDatabase.tbl_HOADON +
                " ( "
                + CreateDatabase.tbl_HOADON_IDTAIKHOAN + " , "
                + CreateDatabase.tbl_HOADON_TENTAIKHOAN + " , "
                + CreateDatabase.tbl_HOADON_IDCTHOADON + " , "
                + CreateDatabase.tbl_HOADON_TONGTIEN + " , "
                + CreateDatabase.tbl_HOADON_NGAYDAT + " , "
                + CreateDatabase.tbl_HOADON_THANGDAT + " , "
                + CreateDatabase.tbl_HOADON_NAMDAT + " , "
                + CreateDatabase.tbl_HOADON_DIACHI + " , "
                + CreateDatabase.tbl_HOADON_GHICHU + " , "
                + CreateDatabase.tbl_HOADON_TINHTRANG + " , "
                + CreateDatabase.tbl_HOADON_SDT
                + " ) VALUES ('" + IDTK + "', '" + TenTK + "', '" + IDCTHOADON + "', '" + TONGTIEN + "', '"
                + NGAYDAT + "', '" + THANGDAT + "', '" + NAMDAT + "', '" + DIACHI + "', '" + GHICHU + "', '"
                + TINHTRANG + "', '" + SDT + "') ");
    }

    public void INSERT_CTHOADON(int IDCTHOADON, int IDTK, String TenTK, int IDSP, String TenSP, String NgayDat,
                                int Soluong, int thanhtien, double tonghoadon, String ghichu, String diachi,
                                String size, int tinhtrang, int sdt)
    {
        QueryData("INSERT INTO " + CreateDatabase.tbl_CHITIETHOADON +
                " ( "
                + CreateDatabase.tbl_CHITIETHOADON_IDCTHOADON + " , "
                + CreateDatabase.tbl_CHITIETHOADON_IDSANPHAM + " , "
                + CreateDatabase.tbl_CHITIETHOADON_IDTAIKHOAN + " , "
                + CreateDatabase.tbl_CHITIETHOADON_TENTAIKHOAN+ " , "
                + CreateDatabase.tbl_CHITIETHOADON_TENSANPHAM + " , "
                + CreateDatabase.tbl_CHITIETHOADON_NGAYDAT + " , "
                + CreateDatabase.tbl_CHITIETHOADON_SOLUONG + " , "
                + CreateDatabase.tbl_CHITIETHOADON_THANHTIEN + " , "
                + CreateDatabase.tbl_CHITIETHOADON_TONGHOADON + " , "
                + CreateDatabase.tbl_CHITIETHOADON_GHICHU + " , "
                + CreateDatabase.tbl_CHITIETHOADON_DIACHI + " , "
                + CreateDatabase.tbl_CHITIETHOADON_SIZE + " , "
                + CreateDatabase.tbl_CHITIETHOADON_TINHTRANG + " , "
                + CreateDatabase.tbl_CHITIETHOADON_SDT
                + " ) VALUES ( '" + IDCTHOADON + "', '" + IDSP + "', '" + IDTK + "', '" + TenTK + "', '"
                + TenSP + "' , '" + NgayDat + "' , '" + Soluong + "', '" + thanhtien + "', '" + tonghoadon + "','"
                + ghichu + "', '" + diachi + "', '" + size + "', '" + tinhtrang + "', '" + sdt + "') ");
    }

    public void DELETE_GIOHANGALL(int IDGIOHANG){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM GIOHANG WHERE IDGIOHANG = "+ IDGIOHANG;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.executeInsert();
    }


    public void SPGH(int IDTK, byte[] hinh, int IDSP, String TenSP, int Soluong, int thanhtien, String size){
        if(SPChuaCoTrongGH(IDTK, IDSP)){
            QueryData("INSERT INTO " + CreateDatabase.tbl_GIOHANG +
                    " ( "
                    + CreateDatabase.tbl_GIOHANG_IDTK + " , "
                    + CreateDatabase.tbl_GIOHANG_HINHANH + " , "
                    + CreateDatabase.tbl_GIOHANG_IDSP + " , "
                    + CreateDatabase.tbl_GIOHANG_TENSANPHAM + " , "
                    + CreateDatabase.tbl_GIOHANG_SOLUONG + " , "
                    + CreateDatabase.tbl_GIOHANG_THANHTIEN + " , "
                    + CreateDatabase.tbl_GIOHANG_SIZE
                    + " ) VALUES ( " + IDTK + " , " + null + " , " + IDSP + " , '" + TenSP + "' , " + Soluong + " , "
                    + thanhtien + ", '" + size + "' ) ");

            SQLiteDatabase database = getWritableDatabase();
            String sql = "UPDATE GIOHANG SET HINHANH = ? WHERE IDTK="+ IDTK + " AND IDSP=" + IDSP ;
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindBlob(1,hinh);
            statement.executeInsert();

        } else if (SPDaCoTrongGH(IDTK, IDSP, size)){
            QueryData("INSERT INTO " + CreateDatabase.tbl_GIOHANG +
                    " ( "
                    + CreateDatabase.tbl_GIOHANG_IDTK + " , "
                    + CreateDatabase.tbl_GIOHANG_HINHANH + " , "
                    + CreateDatabase.tbl_GIOHANG_IDSP + " , "
                    + CreateDatabase.tbl_GIOHANG_TENSANPHAM + " , "
                    + CreateDatabase.tbl_GIOHANG_SOLUONG + " , "
                    + CreateDatabase.tbl_GIOHANG_THANHTIEN + " , "
                    + CreateDatabase.tbl_GIOHANG_SIZE
                    + " ) VALUES ( " + IDTK + " , " + null + " , " + IDSP + " , '" + TenSP + "' , " + Soluong + " , "
                    + thanhtien + ", '" + size + "' ) ");

            SQLiteDatabase database = getWritableDatabase();
            String sql = "UPDATE GIOHANG SET HINHANH = ? WHERE IDTK="+ IDTK + " AND IDSP=" + IDSP + " AND SIZE= '" + size + "'";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindBlob(1,hinh);
            statement.executeInsert();

        } else {
            QueryData("UPDATE " + CreateDatabase.tbl_GIOHANG + " SET "
                    + CreateDatabase.tbl_GIOHANG_SOLUONG + " = "+CreateDatabase.tbl_GIOHANG_SOLUONG + " + " + Soluong
                    + " WHERE " + CreateDatabase.tbl_GIOHANG_IDTK + " = " + IDTK+ " AND " + CreateDatabase.tbl_GIOHANG_IDSP + " = " + IDSP + " AND "
                    + CreateDatabase.tbl_GIOHANG_SIZE + " = '" + size + "'");
        }
    }

    public void UPDATE_GIOHANG(int IDSP, int SL)
    {
        QueryData("UPDATE " + CreateDatabase.tbl_GIOHANG + " SET "
                + CreateDatabase.tbl_GIOHANG_SOLUONG + " = " + CreateDatabase.tbl_GIOHANG_SOLUONG + " + " + SL +
                " WHERE " + CreateDatabase.tbl_GIOHANG_IDSP + " = " + IDSP)
        ;
    }

    public void UPDATE_LOAITK(int IDTK, int Loaitk)
    {
        QueryData("UPDATE " + CreateDatabase.tbl_TAIKHOAN + " SET "
                + CreateDatabase.tbl_TAIKHOAN_LOAITK + " = '" + Loaitk +
                "' WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = " + IDTK)
        ;
    }

    public void UPDATE_GIOHANG_THEM(int IDSP, String SIZE)
    {
        QueryData("UPDATE " + CreateDatabase.tbl_GIOHANG + " SET "
                + CreateDatabase.tbl_GIOHANG_SOLUONG + " = "+CreateDatabase.tbl_GIOHANG_SOLUONG + " + " + 1 +
                " WHERE " + CreateDatabase.tbl_GIOHANG_IDSP + " = " + IDSP + " AND " + CreateDatabase.tbl_GIOHANG_SIZE + " = '" + SIZE + "'")
        ;
    }

    public void UPDATE_GIOHANG_TRU(int IDSP, String SIZE)
    {
        QueryData("UPDATE " + CreateDatabase.tbl_GIOHANG + " SET "
                + CreateDatabase.tbl_GIOHANG_SOLUONG + " = "+CreateDatabase.tbl_GIOHANG_SOLUONG + " - " + 1 +
                " WHERE " + CreateDatabase.tbl_GIOHANG_IDSP + " = " + IDSP + " AND " + CreateDatabase.tbl_GIOHANG_SIZE + " = '" + SIZE + "'")
        ;
    }

    public CTHoaDonDTO LoadCTHD(int IDCTHOADON)
    {
        Cursor cursor = Getdata("SELECT * FROM CHITIETHOADON WHERE IDCTHOADON = " + IDCTHOADON );
        while (cursor.moveToNext()) {
            return new CTHoaDonDTO(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getInt(12),
                    cursor.getInt(13)
            );
        }
        return null;

    }


    public TaiKhoanDTO Load(int IDTK)
    {
        Cursor cursor = Getdata("SELECT * FROM TAIKHOAN WHERE IDTAIKHOAN = " + IDTK );
        while (cursor.moveToNext()) {
            return new TaiKhoanDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            );
        }
        return null;

    }

    public void INSERT_GOPY(String tentaikhoan, int sdt, String noidung){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO GOPY VALUES(null,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, tentaikhoan);
        statement.bindDouble(2, sdt);
        statement.bindString(3, noidung);

        statement.executeInsert();
    }

    public void UPDATE_SOLUONG(int IDSP,int Soluong)
    {
        QueryData("UPDATE " + CreateDatabase.tbl_SANPHAM + " SET "
                + CreateDatabase.tbl_SANPHAM_SOLUONG + " = "+CreateDatabase.tbl_SANPHAM_SOLUONG + " - " + Soluong +
                " WHERE " + CreateDatabase.tbl_GIOHANG_IDSP + " = " + IDSP)
        ;
    }


    public SanPhamDTO SANPHAM(int IDSP){
        Cursor cursor = Getdata("SELECT * FROM SANPHAM WHERE IDSP = " + IDSP );
        while (cursor.moveToNext()) {
            return new SanPhamDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            );

        }
        return null;
    }

    // region Tài Khoản
    public void CapNhatMatKhau(int IDTAIKHOAN, String MATKHAU){
        QueryData("UPDATE " + CreateDatabase.tbl_TAIKHOAN + " SET " + CreateDatabase.tbl_TAIKHOAN_MATKHAU + " = '" + MATKHAU + "' WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = " + IDTAIKHOAN);
    }

    public boolean isMatKhau(int IDTAIKHOAN, String MATKHAU){
        Cursor cursor = Getdata("SELECT * FROM " + CreateDatabase.tbl_TAIKHOAN + " WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = " + IDTAIKHOAN + " AND " + CreateDatabase.tbl_TAIKHOAN_MATKHAU + " = '" + MATKHAU + "'");
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public void CapNhatTaiKhoan(int IDTAIKHOAN,byte[] HINHANH, int SDT, String EMAIL, String DIACHI){
        QueryData("UPDATE " + CreateDatabase.tbl_TAIKHOAN + " SET " + CreateDatabase.tbl_TAIKHOAN_SDT + " = '" + SDT + "', " + CreateDatabase.tbl_TAIKHOAN_EMAIL + " = '" + EMAIL +
                "' , " + CreateDatabase.tbl_TAIKHOAN_DIACHI + " = '" + DIACHI + "' WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = '" + IDTAIKHOAN +"'");

        String sql = "UPDATE TAIKHOAN SET HINHANH = ? WHERE IDTAIKHOAN= " + IDTAIKHOAN ;
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,HINHANH);
        statement.executeInsert();
    }

    public boolean isTonTaiTK(String IDTAIKHOAN){
        Cursor cursor = Getdata("SELECT * FROM " + CreateDatabase.tbl_TAIKHOAN + " WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = '" + IDTAIKHOAN + "'");
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public ArrayList<TaiKhoanDTO> QuanLyTaiKhoan(int QUYEN){
        ArrayList<TaiKhoanDTO> list = new ArrayList<>();
        Cursor cursor = Getdata("SELECT * FROM TAIKHOAN WHERE QUYEN >=" + QUYEN);
        while (cursor.moveToNext()){
            list.add(new TaiKhoanDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            ));
        }
        return list;
    }

    public void XoaTK(int IDTAIKHOAN){
        QueryData("DELETE FROM TAIKHOAN WHERE IDTAIKHOAN = '" + IDTAIKHOAN + "'");
    }

    public TaiKhoanDTO TTTaiKhoan(int IDTAIKHOAN){
        Cursor cursor = Getdata("SELECT * FROM TAIKHOAN WHERE IDTAIKHOAN = '" + IDTAIKHOAN + "'");
        while (cursor.moveToNext()){
            return new TaiKhoanDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            );
        }
        return null;
    }

    public void CapNhatTaiKhoan_ADMIN(int IDTAIKHOAN, String TENTAIKHOAN, String MATKHAU, byte[] BACKGROUND, int SDT, String EMAIL, String DIACHI, int QUYEN, String LOAITK){
        QueryData("UPDATE TAIKHOAN SET TENTAIKHOAN = '" + TENTAIKHOAN + "' , MATKHAU ='" + MATKHAU + "', SDT = '" + SDT
                + "', EMAIL = '" + EMAIL + "', DIACHI = '" + DIACHI + "', QUYEN = '" + QUYEN
                + "', LOAITK = '" + LOAITK + "'  WHERE IDTAIKHOAN = '" + IDTAIKHOAN + "'");

        String sql = "UPDATE TAIKHOAN SET HINHANH = ? WHERE IDTAIKHOAN= " + IDTAIKHOAN ;
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,BACKGROUND);
        statement.executeInsert();
    }
    // endregion
    // end

    //region Video
    public ArrayList<SanPhamDTO> QuanLySaNPham(String IDDANHMUC){
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        Cursor cursor = Getdata("SELECT * FROM SANPHAM WHERE IDDANHMUC = '" + IDDANHMUC + "'");
        while (cursor.moveToNext()){
            list.add(new SanPhamDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            ));
        }
        return list;
    }


    public void XoaHD(int IDTHOADON){
        QueryData("DELETE FROM HOADON WHERE IDHOADON = '" + IDTHOADON + "'");
    }

    public void XoaSPGH(int IDGIOHANG){
        QueryData("DELETE  FROM GIOHANG WHERE IDGIOHANG = '"+ IDGIOHANG + "'");
    }

    public void DELETE_GIOHANG(int IDSP, int IDTK){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM GIOHANG WHERE IDSP = "+ IDSP + " AND IDTK= " + IDTK  ;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.executeInsert();
    }

    //region Video
    public ArrayList<HoaDonDTO> QuanLyHoaDon(){
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        Cursor cursor = Getdata("SELECT * FROM HOADON");
        while (cursor.moveToNext()){
            list.add(new HoaDonDTO(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getInt(10),
                    cursor.getInt(11)
            ));
        }
        return list;
    }

    //region Video
    public ArrayList<HoaDonDTO> QuanLyHoaDon_TimKiem(String IDHOADON){
        ArrayList<HoaDonDTO> list = new ArrayList<>();
        Cursor cursor = Getdata("SELECT * FROM HOADON WHERE IDHOADON LIKE '%" + IDHOADON +"%'");
        while (cursor.moveToNext()){
            list.add(new HoaDonDTO(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getInt(10),
                    cursor.getInt(11)
            ));
        }
        return list;
    }

    public boolean isTonTaiTaiKhoan(String TENTAIKHOAN){
        Cursor cursor = Getdata("SELECT * FROM TAIKHOAN WHERE TENTAIKHOAN = '" + TENTAIKHOAN + "'");
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public boolean isTonTaiSanPham(int IDSP){
        Cursor cursor = Getdata("SELECT * FROM SANPHAM WHERE IDSP = '" + IDSP + "'");
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public void XoaSanPham(int IDSP){
        QueryData("DELETE FROM SANPHAM WHERE IDSP = '" + IDSP + "'");
    }

    public SanPhamDTO TTSANPHAM(int IDSP){
        Cursor cursor = Getdata("SELECT * FROM SANPHAM WHERE IDSP = '" + IDSP + "'");
        while (cursor.moveToNext()){
            return new SanPhamDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            );
        }
        return null;
    }

    public void CapNhatHoaDon_NguoiDung(int IDCTHD, int TINHTRANG){
        QueryData("UPDATE HOADON SET TINHTRANG = '" + TINHTRANG + "' WHERE IDCTHOADON = '" + IDCTHD + "'");
    }

    public void CapNhatCTHoaDon_NguoiDung(int IDCTHD, int TINHTRANG){
        QueryData("UPDATE CHITIETHOADON SET TINHTRANG = '" + TINHTRANG + "' WHERE IDCTHOADON = '" + IDCTHD + "'");
    }


    public void CapNhatHoaDon(int IDCTHD, String GHICHU, String DIACHI, int TINHTRANG, int SDT){
        QueryData("UPDATE HOADON SET DIACHI = '" + DIACHI + "' , GHICHU = '" + GHICHU
                + "', SDT = '" + SDT + "', TINHTRANG = '" + TINHTRANG + "' WHERE IDCTHOADON = '" + IDCTHD + "'");
    }

    public void CapNhatCTHoaDon(int IDCTHD, String GHICHU, String DIACHI, int TINHTRANG, int SDT){
        QueryData("UPDATE CHITIETHOADON SET DIACHI = '" + DIACHI + "' , GHICHU = '" + GHICHU
                + "', SDT = '" + SDT + "', TINHTRANG = '" + TINHTRANG + "' WHERE IDCTHOADON = '" + IDCTHD + "'");
    }

    public void CapNhatSanPham(int IDSP, byte[] BACKGROUND, String TENSANPHAM, int GIA, int SL, String MOTA, int IDDANHMUC){
        QueryData("UPDATE SANPHAM SET TENSANPHAM = '" + TENSANPHAM
                + "' , GIA ='" + GIA + "', SOLUONG = '" + SL + "', MOTA = '" + MOTA + "', IDDANHMUC = '" + IDDANHMUC
                + "'  WHERE IDSP = '" + IDSP + "'");

        String sql = "UPDATE SANPHAM SET HINHANH = ? WHERE IDSP= " + IDSP ;
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,BACKGROUND);
        statement.executeInsert();
    }


    public void ThemSanPham(byte[] hinh, String ten, int  Gia, int soluong, String mota, int danhmuc, int spmoi ) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(CreateDatabase.tbl_SANPHAM_HINHANH, hinh);
        cv.put(CreateDatabase.tbl_SANPHAM_TENSANPHAM, ten);
        cv.put(CreateDatabase.tbl_SANPHAM_GIA, Gia);
        cv.put(CreateDatabase.tbl_SANPHAM_IDDANHMUC, danhmuc);
        cv.put(CreateDatabase.tbl_SANPHAM_SOLUONG, soluong);
        cv.put(CreateDatabase.tbl_SANPHAM_MOTA, mota);
        cv.put(CreateDatabase.tbl_SANPHAM_IDSP_NEW, spmoi);


        database.insert( CreateDatabase.tbl_SANPHAM, null, cv );

    }

    public void UPDATE_SANPHAM(String ten,byte[] hinh,int SOLUONG,int  GIA,int IDDANHMUC,int SPNEW, int IDSP ){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TENSANPHAM", ten);
        values.put("GIA", GIA);
        values.put("SOLUONG", SOLUONG);
        values.put("IDDANHMUC", IDDANHMUC);
        values.put("SPNEW", SPNEW);

        sqLiteDatabase.update("SANPHAM",values,"IDSP =" + IDSP,null);


        String sql = "UPDATE SANPHAM SET HINHANH = ? WHERE IDSP="+ IDSP ;
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,hinh);
        statement.executeInsert();
    }

    // endregion

    public ArrayList<GopYDTO> QuanLyGopY(){
        ArrayList<GopYDTO> list = new ArrayList<>();
        Cursor cursor = Getdata("SELECT * FROM GOPY");
        while (cursor.moveToNext()){
            list.add(new GopYDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)
            ));
        }
        return list;
    }

    public GopYDTO TTGOPY(int IDGY){
        Cursor cursor = Getdata("SELECT * FROM GOPY WHERE IDGOPY = '" + IDGY + "'");
        while (cursor.moveToNext()){
            return new GopYDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)
            );
        }
        return null;
    }

    public void ThemTinTuc(byte[] hinh, String tieude, String noidung, String ngaydang ) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(CreateDatabase.tbl_TINTUC_HINHANH, hinh);
        cv.put(CreateDatabase.tbl_TINTUC_TIEUDE, tieude);
        cv.put(CreateDatabase.tbl_TINTUC_NOIDUNG, noidung);
        cv.put(CreateDatabase.tbl_TINTUC_NGAYDANG, ngaydang);
        cv.put(CreateDatabase.tbl_TINTUC_TINMOI, 1);

        database.insert( CreateDatabase.tbl_TINTUC, null, cv );
    }

    public ArrayList<TinTucDTO> QuanLyTinTuc(){
        ArrayList<TinTucDTO> list = new ArrayList<>();
        Cursor cursor = Getdata("SELECT * FROM TINTUC");
        while (cursor.moveToNext()){
            list.add(new TinTucDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4)
            ));
        }
        return list;
    }

    public void XoaTinTuc(int IDTINTUC){
        QueryData("DELETE FROM TINTUC WHERE IDTINTUC = '" + IDTINTUC + "'");
    }

    public void XoaGY(int IDGY){
        QueryData("DELETE FROM GOPY WHERE IDGOPY = '" + IDGY + "'");
    }

        @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
