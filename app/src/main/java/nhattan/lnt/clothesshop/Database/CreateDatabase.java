package nhattan.lnt.clothesshop.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    public static String tbl_TAIKHOAN = "TAIKHOAN";
    public static String tbl_QUYEN = "QUYEN";
    public static String tbl_SANPHAM = "SANPHAM";
    public static String tbl_DANHMUCSANPHAM = "DANHMUCSANPHAM";
    public static String tbl_HOADON = "HOADON";
    public static String tbl_CHITIETHOADON = "CHITIETHOADON";
    public static String tbl_GIOHANG = "GIOHANG";
    public static String tbl_GOPY = "GOPY";

    public static String tbl_TAIKHOAN_IDTK = "IDTAIKHOAN";
    public static String tbl_TAIKHOAN_TENTAIKHOAN = "TENTAIKHOAN";
    public static String tbl_TAIKHOAN_MATKHAU = "MATKHAU";
    public static String tbl_TAIKHOAN_HINHANH = "HINHANH";
    public static String tbl_TAIKHOAN_SDT = "SDT";
    public static String tbl_TAIKHOAN_EMAIL = "EMAIL";
    public static String tbl_TAIKHOAN_DIACHI = "DIACHI";
    public static String tbl_TAIKHOAN_QUYEN = "QUYEN";

    public static String tbl_QUYEN_IDQUYEN = "MAQUYEN";
    public static String tbl_QUYEN_TENQUYEN = "TENQUYEN";

    public static String tbl_SANPHAM_IDSP = "IDSP";
    public static String tbl_SANPHAM_TENSANPHAM = "TENSANPHAM";
    public static String tbl_SANPHAM_GIA = "GIA";
    public static String tbl_SANPHAM_SOLUONG = "SOLUONG";
    public static String tbl_SANPHAM_HINHANH = "HINHANH";
    public static String tbl_SANPHAM_MOTA = "MOTA";
    public static String tbl_SANPHAM_IDDANHMUC = "IDDANHMUC";
    public static String tbl_SANPHAM_IDSP_NEW = "SPNEW";

    public static String tbl_DANHMUCSANPHAM_IDDANHMUC = "IDDANHMUC";
    public static String tbl_DANHMUCSANPHAM_TENDANHMUC = "TENDANHMUC";

    public static String tbl_HOADON_IDHOADON = "IDHOADON";
    public static String tbl_HOADON_IDCTHOADON = "IDCTHOADON";
    public static String tbl_HOADON_IDTAIKHOAN = "IDTAIKHOAN";
    public static String tbl_HOADON_TENTAIKHOAN = "TENTAIKHOAN";
    public static String tbl_HOADON_TONGTIEN = "TONGTIEN";
    public static String tbl_HOADON_DIACHI = "DIACHI";
    public static String tbl_HOADON_NGAYDAT = "NGAYDAT";
    public static String tbl_HOADON_GHICHU = "GHICHU";

    public static String tbl_CHITIETHOADON_IDCTHOADON = "IDCTHOADON";
    public static String tbl_CHITIETHOADON_IDTAIKHOAN = "IDTAIKHOAN";
    public static String tbl_CHITIETHOADON_TENTAIKHOAN = "TENTAIKHOAN";
    public static String tbl_CHITIETHOADON_IDSANPHAM = "IDSANPHAM";
    public static String tbl_CHITIETHOADON_TENSANPHAM = "TENSANPHAM";
    public static String tbl_CHITIETHOADON_NGAYDAT = "NGAYDAT";
    public static String tbl_CHITIETHOADON_SOLUONG = "SOLUONG";
    public static String tbl_CHITIETHOADON_THANHTIEN = "THANHTIEN";
    public static String tbl_CHITIETHOADON_TONGHOADON = "TONGHOADON";
    public static String tbl_CHITIETHOADON_GHICHU = "GHICHU";
    public static String tbl_CHITIETHOADON_DIACHI = "DIACHI";

    public static String tbl_GIOHANG_IDGIOHANG = "IDGIOHANG";
    public static String tbl_GIOHANG_IDSP = "IDSP";
    public static String tbl_GIOHANG_TENSANPHAM = "TENSANPHAM";
    public static String tbl_GIOHANG_IDTK = "IDTK";
    public static String tbl_GIOHANG_SOLUONG = "SOLUONG";
    public static String tbl_GIOHANG_THANHTIEN = "THANHTIEN";
    public static String tbl_GIOHANG_GHICHU = "GHICHU";
    public static String tbl_GIOHANG_HINHANH = "HINHANH";

    public static String tbl_GOPY_IDGOPY = "IDGOPY";
    public static String tbl_GOPY_TENTAIKHOAN = "TENTAIKHOAN";
    public static String tbl_GOPY_SDT = "SDT";
    public static String tbl_GOPY_NOIDUNG = "NOIDUNG";


    public CreateDatabase(@Nullable Context context) {
        super(context, "ClothesDatabase", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TAIKHOAN = "CREATE TABLE " + tbl_TAIKHOAN + " (" + tbl_TAIKHOAN_IDTK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tbl_TAIKHOAN_TENTAIKHOAN + " TEXT , " + tbl_TAIKHOAN_MATKHAU + " TEXT, " + tbl_TAIKHOAN_HINHANH + " BLOB, " + tbl_TAIKHOAN_SDT + " INTEGER, "
                + tbl_TAIKHOAN_EMAIL + " TEXT, "+ tbl_TAIKHOAN_DIACHI + " TEXT, " + tbl_TAIKHOAN_QUYEN + " INTEGER )";

        String QUYEN = "CREATE TABLE " + tbl_QUYEN + "(" + tbl_QUYEN_IDQUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tbl_QUYEN_TENQUYEN + " TEXT )";

        String SANPHAM = "CREATE TABLE " + tbl_SANPHAM + "(" + tbl_SANPHAM_IDSP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tbl_SANPHAM_HINHANH + " BLOB, "+ tbl_SANPHAM_TENSANPHAM + " TEXT , " + tbl_SANPHAM_GIA + " INTEGER , "
                + tbl_SANPHAM_SOLUONG + " INTEGER, " + tbl_SANPHAM_MOTA + " TEXT, " + tbl_SANPHAM_IDDANHMUC + " INTEGER, "
                + tbl_SANPHAM_IDSP_NEW + " INTEGER )";

        String DANHMUCSANPHAM = "CREATE TABLE " + tbl_DANHMUCSANPHAM + "(" + tbl_DANHMUCSANPHAM_IDDANHMUC + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tbl_DANHMUCSANPHAM_TENDANHMUC + " TEXT )";

        String HOADON = "CREATE TABLE " + tbl_HOADON + "(" + tbl_HOADON_IDHOADON + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tbl_HOADON_IDTAIKHOAN + " INTEGER, " + tbl_HOADON_TENTAIKHOAN + " TEXT, " + tbl_HOADON_IDCTHOADON + " INTEGER, " + tbl_HOADON_TONGTIEN + " INTEGER, "
                + tbl_HOADON_DIACHI + " TEXT," + tbl_HOADON_NGAYDAT + " DATE, " + tbl_HOADON_GHICHU + " TEXT )";

        String CTHOADON = "CREATE TABLE " + tbl_CHITIETHOADON + "(" + tbl_CHITIETHOADON_IDCTHOADON + " INTEGER, "
                + tbl_CHITIETHOADON_IDSANPHAM + " INTEGER, " + tbl_CHITIETHOADON_IDTAIKHOAN + " INTEGER, " + tbl_CHITIETHOADON_TENTAIKHOAN + " TEXT, " + tbl_CHITIETHOADON_TENSANPHAM + " TEXT, "
                + tbl_CHITIETHOADON_NGAYDAT + " DATE, " + tbl_CHITIETHOADON_SOLUONG + " INTEGER, " + tbl_CHITIETHOADON_GHICHU + " TEXT, "
                + tbl_CHITIETHOADON_THANHTIEN + " INTEGER ," + tbl_CHITIETHOADON_TONGHOADON + " INTEGER ," + tbl_CHITIETHOADON_GHICHU + " TEXT," + tbl_CHITIETHOADON_DIACHI + " TEXT)";

        String GIOHANG = "CREATE TABLE " + tbl_GIOHANG + "(" + tbl_GIOHANG_IDGIOHANG + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tbl_GIOHANG_HINHANH + " BLOB, " + tbl_GIOHANG_IDSP + " INTEGER, " + tbl_GIOHANG_TENSANPHAM + " TEXT, "
                + tbl_GIOHANG_IDTK + " INTEGER, " + tbl_GIOHANG_SOLUONG + " INTEGER, " + tbl_GIOHANG_THANHTIEN + " INTEGER, "
                + tbl_GIOHANG_GHICHU + " TEXT )";

        String GOPY = "CREATE TABLE " + tbl_GOPY + "(" + tbl_GOPY_IDGOPY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + tbl_GOPY_TENTAIKHOAN + " TEXT, " + tbl_GOPY_SDT + " INTEGER, " + tbl_GOPY_NOIDUNG + " TEXT )";

        db.execSQL(TAIKHOAN);
        db.execSQL(QUYEN);
        db.execSQL(SANPHAM);
        db.execSQL(DANHMUCSANPHAM);
        db.execSQL(HOADON);
        db.execSQL(CTHOADON);
        db.execSQL(GIOHANG);
        db.execSQL(GOPY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase open (){
        return this.getWritableDatabase();
    }
}
