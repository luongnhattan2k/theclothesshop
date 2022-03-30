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
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class OrderActivity extends AppCompatActivity {

    ListView Listview_Kiemtra;
    ArrayList<DatHangDTO> datHangDTOS;
    ArrayList<GioHangDTO> gioHangDTOArrayList;
    DatHangDAO adapter;
    public static Database database;
    Button btn_Dathangkt;
    ImageButton ibtn_Exit;
    TextView txt_Tongtiendathang;
    EditText edt_Diachigiaohang, edt_Ghichu;
    int tong;
    Spinner spnCategory;
    CategoryDAO categoryDAO;
    int idcthd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        database = new Database(OrderActivity.this,"ClothesDatabase",null,2);

        AnhXa();
        NgayHienTai();
        Listview_Kiemtra = (ListView) findViewById(R.id.listview_danhsachsp_dathang);

        datHangDTOS = new ArrayList<>();
        adapter = new DatHangDAO(OrderActivity.this, R.layout.product_oder, datHangDTOS);
        Listview_Kiemtra.setAdapter(adapter);
        registerForContextMenu(Listview_Kiemtra);

        categoryDAO = new CategoryDAO(this, R.layout.item_selected, getListCategort());
        spnCategory.setAdapter(categoryDAO);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderActivity.this, "Bạn chọn hình thức giao hàng " + categoryDAO.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GetData();
    }

    private void NgayHienTai() {

    }

    private List<CategoryDTO> getListCategort() {
        List<CategoryDTO> list = new ArrayList<>();

        list.add(new CategoryDTO("Giao hàng nhanh - Free Ship", "1"));
        list.add(new CategoryDTO("Giao hàng tiết kiệm - Free Ship","2"));

        return list;
    }

    @Override
    public void onStart() {
        Tongtien();
        GetData();
        super.onStart();
    }

    private void Tongtien() {
        Cursor cursor = HomeFragment.database.Getdata("SELECT SUM ( THANHTIEN ) FROM GIOHANG WHERE IDTK = "
                + Login.taiKhoanDTO.getMATK());
        cursor.moveToNext();
        tong = cursor.getInt(0);
        txt_Tongtiendathang.setText("Tổng tiền: " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(tong) + " VNĐ"));
    }

    private void AnhXa() {
        spnCategory = findViewById(R.id.spn_category_dathang);
        txt_Tongtiendathang = findViewById(R.id.txtTongtiendathang);
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
                                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);
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

                if(database.HoaDonChuaCoTrongHD()){
                    idcthd = 1;
                }
                else {
                    Cursor cursor = database.Getdata("SELECT IDCTHOADON FROM CHITIETHOADON ORDER BY IDCTHOADON DESC");
                    cursor.moveToNext();
                    idcthd = cursor.getInt(0) + 1;
                }
                for (int position = 0; position<GioHangDAO.sanPhamGioHangList.size(); position++)
                {
                    GioHangDTO themhoadon = GioHangDAO.sanPhamGioHangList.get(position);
                    database.INSERT_CTHOADON(idcthd, Login.taiKhoanDTO.getMATK(), Login.taiKhoanDTO.getTENTK(), themhoadon.getIDSP(), themhoadon.getTENSANPHAM(),
                            ngaydat ,themhoadon.getSOLUONG(), themhoadon.getTHANHTIEN(), tong, ghichu, diachi);
                    database.UPDATE_SOLUONG(themhoadon.getIDSP(),themhoadon.getSOLUONG());
                }
                database.INSERT_HOADON(Login.taiKhoanDTO.getMATK(), Login.taiKhoanDTO.getTENTK(), idcthd, tong, ngaydat, diachi, ghichu);
                database.DELETE_GIOHANGALL(Login.taiKhoanDTO.getMATK());
                GetData();
                Tongtien();
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

    private void GetData() {
        //get data
        Cursor cursor = database.Getdata("SELECT * FROM GIOHANG WHERE IDTK = " + Login.taiKhoanDTO.getMATK());
        datHangDTOS.clear();
//        gioHangDTOArrayList.clear();
        while (cursor.moveToNext())
        {
            datHangDTOS.add(new DatHangDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            ));
        }
        adapter.notifyDataSetChanged();

        String Diachi = Login.taiKhoanDTO.getDIACHI();
        edt_Diachigiaohang.setText(Diachi);
    }
}