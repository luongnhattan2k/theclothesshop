package nhattan.lnt.clothesshop.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

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
        Cursor tro = Getdata("SELECT * FROM GIOHANG WHERE IDTK = " + IDTK + " AND IDSP = " + IDSP );
        while (tro.moveToNext()) {
            return false;
        }
        return true;
    }

    public boolean HoaDonChuaCoTrongHD(){
        Cursor tro = Getdata("SELECT IDCTHOADON FROM CHITIETHOADON " );
        while (tro.moveToNext()) {
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

    public void DELETE_GIOHANG(int IDTK){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE  FROM GIOHANG WHERE IDTK = "+ IDTK ;
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

    public void UPDATE_TAIKHOAN(int sdt, String email, String diachi, int Id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TAIKHOAN SET SDT = ?, EMAIL = ?, DIACHI = ? WHERE IDTAIKHOAN=" + Id;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1, sdt);
        statement.bindString(2, email);
        statement.bindString(3, diachi);

        statement.executeUpdateDelete();
//        statement.executeInsert();
    }

    public void INSERT_IMAGE(byte[] hinh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO GIOHANG VALUES(null,?,null,null,null,null,null,null)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,hinh);

        statement.executeInsert();
    }

    public TaiKhoanDTO Load(int IDTK)
    {
        Cursor cursor = Getdata("SELECT * FROM TAIKHOAN WHERE IDTAIKHOAN = " + IDTK );
        while (cursor.moveToNext()) {
            return new TaiKhoanDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
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

    public void UPDATE_MATKHAU(String matkhau, int Id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TAIKHOAN SET MATKHAU = ? WHERE IDTAIKHOAN=" + Id;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, matkhau);

        statement.executeUpdateDelete();
//        statement.executeInsert();
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
                    cursor.getString(5)
            );

        }
        return null;
    }

    // region Tài Khoản
    public void CapNhatMatKhau(int IDTAIKHOAN, String MATKHAU){
        QueryData("UPDATE " + CreateDatabase.tbl_TAIKHOAN + " SET " + CreateDatabase.tbl_TAIKHOAN_MATKHAU + " = '" + MATKHAU + "' WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = " + IDTAIKHOAN);
    }

    public boolean isMatKhau(int IDTAIKHOAN, String MATKHAU){
        Cursor tro = Getdata("SELECT * FROM " + CreateDatabase.tbl_TAIKHOAN + " WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = " + IDTAIKHOAN + " AND " + CreateDatabase.tbl_TAIKHOAN_MATKHAU + " = '" + MATKHAU + "'");
        while (tro.moveToNext()){
            return true;
        }
        return false;
    }

    public void CapNhatTaiKhoan(int IDTAIKHOAN, int SDT, String EMAIL, String DIACHI){
        QueryData("UPDATE " + CreateDatabase.tbl_TAIKHOAN + " SET " + CreateDatabase.tbl_TAIKHOAN_SDT + " = '" + SDT + "', " + CreateDatabase.tbl_TAIKHOAN_EMAIL + " = '" + EMAIL +
                "' , " + CreateDatabase.tbl_TAIKHOAN_DIACHI + " = '" + DIACHI + "' WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = '" + IDTAIKHOAN +"'");
    }

    public boolean isTonTaiTK(String IDTAIKHOAN){
        Cursor tro = Getdata("SELECT * FROM " + CreateDatabase.tbl_TAIKHOAN + " WHERE " + CreateDatabase.tbl_TAIKHOAN_IDTK + " = '" + IDTAIKHOAN + "'");
        while (tro.moveToNext()){
            return true;
        }
        return false;
    }

        @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
