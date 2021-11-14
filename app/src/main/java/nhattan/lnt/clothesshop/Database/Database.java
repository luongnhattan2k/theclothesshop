package nhattan.lnt.clothesshop.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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

    public boolean SPChuaCoTrongGH(int IDTK,int IDSP){
        Cursor tro = Getdata("SELECT * FROM GIOHANG WHERE IDTK = " + IDTK + " AND IDSP = " + IDSP );
        while (tro.moveToNext()) {
            return false;
        }
        return true;
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

    public void UPDATE(String tentaikhoan,String matkhau, int sdt, String email, String diachi, int Id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE TAIKHOAN SET TENTAIKHOAN = ? , MATKHAU = ?, SDT = ?, EMAIL = ?, DIACHI = ? WHERE IDTAIKHOAN=" + Id;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, tentaikhoan);
        statement.bindString(2, matkhau);
        statement.bindDouble(3, sdt);
        statement.bindString(4, email);
        statement.bindString(5, diachi);

    }

    public void _IMAGE(byte[] hinh){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO GIOHANG VALUES(null,?,null,null,null,null,null,null)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1,hinh);

        statement.executeInsert();
    }

        @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
