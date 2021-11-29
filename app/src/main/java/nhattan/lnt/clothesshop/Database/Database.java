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

import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;

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

    public void DELETE_GIOHANG(int IDSP, int IDTK){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM GIOHANG WHERE IDSP = "+ IDSP + " AND IDTK= " + IDTK  ;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.executeInsert();
    }


    public boolean SPChuaCoTrongGH(int IDTK,int IDSP){
        Cursor cursor = Getdata("SELECT * FROM GIOHANG WHERE IDTK = " + IDTK + " AND IDSP = " + IDSP );
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

    public void INSERT_HOADON(int IDTK, int IDCTHOADON, int TONGTIEN, String DIACHI, String GHICHU)
    {
        QueryData("INSERT INTO " + CreateDatabase.tbl_HOADON +
                " ( "
                + CreateDatabase.tbl_HOADON_IDTAIKHOAN + " , "
                + CreateDatabase.tbl_HOADON_IDCTHOADON + " , "
                + CreateDatabase.tbl_HOADON_TONGTIEN + " , "
                + CreateDatabase.tbl_HOADON_DIACHI + " , "
                + CreateDatabase.tbl_HOADON_GHICHU +
                " ) VALUES ( '" + IDTK + "', '" + IDCTHOADON + "', '" + TONGTIEN + "', '" + DIACHI + "', '" + GHICHU + "') ");
    }

    public void INSERT_CTHOADON(int IDCTHOADON,int IDTK, int IDSP, String TenSP, int Soluong, int thanhtien)
    {
        QueryData("INSERT INTO " + CreateDatabase.tbl_CHITIETHOADON +
                " ( "
                + CreateDatabase.tbl_CHITIETHOADON_IDCTHOADON + " , "
                + CreateDatabase.tbl_CHITIETHOADON_IDSANPHAM + " , "
                + CreateDatabase.tbl_CHITIETHOADON_IDTAIKHOAN+ " , "
                + CreateDatabase.tbl_CHITIETHOADON_TENSANPHAM + " , "
                + CreateDatabase.tbl_CHITIETHOADON_SOLUONG + " , "
                + CreateDatabase.tbl_CHITIETHOADON_TONGTIEN
                + " ) VALUES ( '" + IDCTHOADON + "', '" + IDSP + "', '" + IDTK+ "', '" + TenSP + "' , '" + Soluong + "', '"
                + thanhtien + "') ");
    }

    public void DELETE_GIOHANGALL(int IDTK){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM GIOHANG WHERE IDTK = "+ IDTK;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();


        statement.executeInsert();
    }


    public void SPGH(int IDTK, byte[] hinh, int IDSP, String TenSP, int Soluong, int thanhtien){
        if(SPChuaCoTrongGH(IDTK, IDSP)){
            QueryData("INSERT INTO " + CreateDatabase.tbl_GIOHANG +
                    " ( "
                    + CreateDatabase.tbl_GIOHANG_IDTK + " , "
                    + CreateDatabase.tbl_GIOHANG_HINHANH + " , "
                    + CreateDatabase.tbl_GIOHANG_IDSP + " , "
                    + CreateDatabase.tbl_GIOHANG_TENSANPHAM + " , "
                    + CreateDatabase.tbl_GIOHANG_SOLUONG + " , "
                    + CreateDatabase.tbl_GIOHANG_THANHTIEN
                    + " ) VALUES ( " + IDTK + " , " + null + " , " + IDSP+" , '" + TenSP + "' , " + Soluong + " , "
                    + thanhtien + " ) ");

            SQLiteDatabase database = getWritableDatabase();
            String sql = "UPDATE GIOHANG SET HINHANH = ? WHERE IDTK="+ IDTK + " AND IDSP=" + IDSP ;
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.bindBlob(1,hinh);
            statement.executeInsert();
        }
        else {
            QueryData("UPDATE " + CreateDatabase.tbl_GIOHANG + " SET "
                    + CreateDatabase.tbl_GIOHANG_SOLUONG + " = "+CreateDatabase.tbl_GIOHANG_SOLUONG + " + " + Soluong + " , "
                    + CreateDatabase.tbl_GIOHANG_THANHTIEN + " = " + CreateDatabase.tbl_GIOHANG_THANHTIEN + " + " + thanhtien
                    + " WHERE " + CreateDatabase.tbl_GIOHANG_IDTK + " = " + IDTK+ " AND "
                    + CreateDatabase.tbl_GIOHANG_IDSP + " = " + IDSP)
            ;
        }
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
                    cursor.getString(7),
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
                    cursor.getInt(7)
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

    public void CapNhatTaiKhoan(int IDTAIKHOAN, int SDT, String EMAIL, String DIACHI){
        QueryData("UPDATE " + CreateDatabase.tbl_TAIKHOAN + " SET " + CreateDatabase.tbl_TAIKHOAN_SDT + " = '" + SDT + "', " + CreateDatabase.tbl_TAIKHOAN_EMAIL + " = '" + EMAIL +
                "' , " + CreateDatabase.tbl_TAIKHOAN_DIACHI + " = '" + DIACHI + "' WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = '" + IDTAIKHOAN +"'");
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
                    cursor.getString(7),
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
                    cursor.getString(7),
                    cursor.getInt(8)
            );
        }
        return null;
    }

    public void CapNhatTaiKhoan_ADMIN(int IDTAIKHOAN, String TENTAIKHOAN, String MATKHAU, byte[] BACKGROUND, int SDT, String EMAIL, String NGAYSINH, String DIACHI, int QUYEN){
        QueryData("UPDATE TAIKHOAN SET TENTAIKHOAN = '" + TENTAIKHOAN + "' , MATKHAU ='" + MATKHAU + "', SDT = '" + SDT
                + "', EMAIL = '" + EMAIL + "', NGAYSINH = '" + NGAYSINH + "', DIACHI = '" + DIACHI
                + "', QUYEN = '" + QUYEN + "'  WHERE IDTAIKHOAN = '" + IDTAIKHOAN + "'");

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
                    cursor.getInt(7)
            ));
        }
        return list;
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
                    cursor.getInt(7)
            );
        }
        return null;
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

        @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
