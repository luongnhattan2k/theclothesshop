package nhattan.lnt.clothesshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nhattan.lnt.clothesshop.DAO.CategoryDAO;
import nhattan.lnt.clothesshop.DAO.DatHangDAO;
import nhattan.lnt.clothesshop.DAO.GioHangDAO;
import nhattan.lnt.clothesshop.DTO.CategoryDTO;
import nhattan.lnt.clothesshop.DTO.DatHangDTO;
import nhattan.lnt.clothesshop.DTO.GioHangDTO;
import nhattan.lnt.clothesshop.Database.Database;

public class OrderActivity extends AppCompatActivity {

    ListView Listview_Kiemtra;
    ArrayList<DatHangDTO> datHangDTOS;
    ArrayList<GioHangDTO> gioHangDTOArrayList;
    DatHangDAO adapter;
    GioHangDAO gioHangDAO;
    public static Database database;
    Button btn_Dathangkt;
    ImageButton ibtn_Exit;
    TextView txt_Tongtiendathang, txt_Tienhang, txt_Tienship, txt_Tiengiamgia;
    EditText edt_Diachigiaohang, edt_Ghichu;
    int Tongtiensp = 0;
    int Tienship = 0;
    int Tiengiamgia;
    int Thanhtien = 0;
    Spinner spnCategory;
    CategoryDAO categoryDAO;
    int idcthd;
    String dateString_ngay, dateString_thang, dateString_nam;
    String ship = "Giao hàng nhanh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        database = new Database(OrderActivity.this,"ClothesDatabase",null,2);

        AnhXa();
        NgayHienTai();

        categoryDAO = new CategoryDAO(this, R.layout.item_selected, getListCategort());
        spnCategory.setAdapter(categoryDAO);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderActivity.this, "Bạn chọn hình thức giao hàng " + categoryDAO.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                ship = categoryDAO.getItem(position).getName();
                GetData();
                Toast.makeText(OrderActivity.this, "Loại tài khoản " + Login.taiKhoanDTO.getLOAITK(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GetData();
    }

    private void NgayHienTai() {

    }

    @Override
    public void onStart() {
        Tongtien();
        GetData();
        super.onStart();
    }

    private void Tongtien() {
//        Cursor cursor = HomeFragment.database.Getdata("SELECT SUM ( THANHTIEN ) FROM GIOHANG WHERE IDTK = "
//                + Login.taiKhoanDTO.getMATK());
//        cursor.moveToNext();
//        tong = cursor.getInt(0);

        for (int i = 0; i < gioHangDAO.mangmuahang.size(); i++) {
            Tongtiensp = Tongtiensp + (gioHangDAO.mangmuahang.get(i).getTHANHTIEN()* gioHangDAO.mangmuahang.get(i).getSOLUONG());
        }

    }

    private void AnhXa() {
        spnCategory = findViewById(R.id.spn_category_dathang);
        txt_Tongtiendathang = findViewById(R.id.txtTongtiendathang);
        txt_Tienhang = findViewById(R.id.txtTienhang);
        txt_Tienship = findViewById(R.id.txtTienshiphang);
        txt_Tiengiamgia = findViewById(R.id.txtTiengiamgia);
        edt_Diachigiaohang = findViewById(R.id.edtDiachigiaohang);
        edt_Ghichu = findViewById(R.id.edtGhichu);
        TextView tdate = findViewById(R.id.edtNgayDat);

        Thread t = new Thread() {
            @Override
            public void run () {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat sdf_thang = new SimpleDateFormat("MM/yyyy");
                                SimpleDateFormat sdf_nam = new SimpleDateFormat("yyyy");
                                dateString_ngay = sdf_ngay.format(date);
                                dateString_thang = sdf_thang.format(date);
                                dateString_nam = sdf_nam.format(date);
//                                Log.d("Ngày: ", dateString_ngay);
//                                Log.d("Tháng: ", dateString_thang);
//                                Log.d("Năm: ", dateString_nam);
                                tdate.setText(dateString_ngay);
                            }
                        });
                    }
                } catch (InterruptedException e ) { }
            }
        };
        t.start();

        btn_Dathangkt= findViewById(R.id.btnDathangkt);
        btn_Dathangkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String diachi =  edt_Diachigiaohang.getText().toString();
                String ghichu = edt_Ghichu.getText().toString();
                String ngaydat = tdate.getText().toString();
                String thangdat = dateString_thang;
                String namdat = dateString_nam;

                if(database.HoaDonChuaCoTrongHD()){
                    idcthd = 1;
                }
                else {
                    Cursor cursor = database.Getdata("SELECT IDCTHOADON FROM CHITIETHOADON ORDER BY IDCTHOADON DESC");
                    cursor.moveToNext();
                    idcthd = cursor.getInt(0) + 1;
                }
                for (int position = 0; position<gioHangDAO.mangmuahang.size(); position++)
                {
                    GioHangDTO themhoadon = gioHangDAO.mangmuahang.get(position);
                    database.INSERT_CTHOADON(idcthd, Login.taiKhoanDTO.getMATK(), Login.taiKhoanDTO.getTENTK(), themhoadon.getIDSP(), themhoadon.getTENSANPHAM(),
                            ngaydat ,themhoadon.getSOLUONG(), themhoadon.getTHANHTIEN(), Thanhtien, ghichu, diachi, themhoadon.getSIZE());
                    database.UPDATE_SOLUONG(themhoadon.getIDSP(),themhoadon.getSOLUONG());
                }
                database.INSERT_HOADON(Login.taiKhoanDTO.getMATK(), Login.taiKhoanDTO.getTENTK(), idcthd, Thanhtien, ngaydat, thangdat, namdat, diachi, ghichu);
                for (int i = 0; i < gioHangDAO.mangmuahang.size(); i++) {
                    database.DELETE_GIOHANGALL(gioHangDAO.mangmuahang.get(i).getIDGIOHANG());
                }

                GetData();
                Tongtien();
                gioHangDAO.mangmuahang.clear();
                Intent iHome = new Intent(OrderActivity.this, HomeActivity.class);
                Toast.makeText(OrderActivity.this, "Đặt hàng thành công !", Toast.LENGTH_SHORT).show();
//                Toast.makeText(OrderActivity.this, " ssss : " + idcthd , Toast.LENGTH_SHORT).show();
                startActivity(iHome);
            }
        });

        ibtn_Exit = findViewById(R.id.ibtnExitdathang);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private List<CategoryDTO> getListCategort() {
        List<CategoryDTO> list = new ArrayList<>();
        list.add(new CategoryDTO("Giao hàng nhanh (2 - 3 ngày)", "1"));
        list.add(new CategoryDTO("Giao hàng tiết kiệm (5 - 6 ngày)", "2"));
        return list;
    }

    private void GetData() {
        String Diachi = Login.taiKhoanDTO.getDIACHI();
        String LoaiTk = Login.taiKhoanDTO.getLOAITK();

        if (ship == "Giao hàng nhanh (2 - 3 ngày)") {
            Tienship = 30000;
        } else if (ship == "Giao hàng tiết kiệm (5 - 6 ngày)") {
            Tienship = 20000;
        } else if (LoaiTk == "VIP") {
            Tiengiamgia = 30000;
        } else if (LoaiTk == "NOR") {
            Tiengiamgia = 0;
        }

        edt_Diachigiaohang.setText(Diachi);
        txt_Tienhang.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Tongtiensp)));
        txt_Tienship.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Tienship)));
        txt_Tiengiamgia.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Tiengiamgia)));
        Thanhtien = Tongtiensp + Tienship - Tiengiamgia;
        txt_Tongtiendathang.setText("Tổng tiền: " + (NumberFormat.getNumberInstance(Locale.US).format(Thanhtien) + " VNĐ"));
    }
}