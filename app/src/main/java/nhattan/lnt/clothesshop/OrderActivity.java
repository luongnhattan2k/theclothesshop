package nhattan.lnt.clothesshop;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nhattan.lnt.clothesshop.ADAPTER.MyApplication;
import nhattan.lnt.clothesshop.DAO.CategoryDAO;
import nhattan.lnt.clothesshop.DAO.DatHangDAO;
import nhattan.lnt.clothesshop.DAO.GioHangDAO;
import nhattan.lnt.clothesshop.DTO.CategoryDTO;
import nhattan.lnt.clothesshop.DTO.DatHangDTO;
import nhattan.lnt.clothesshop.DTO.GioHangDTO;
import nhattan.lnt.clothesshop.Database.Database;

public class OrderActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
    ListView Listview_Kiemtra;
    ArrayList<DatHangDTO> datHangDTOS;
    ArrayList<GioHangDTO> gioHangDTOArrayList;
    DatHangDAO adapter;
    GioHangDAO gioHangDAO;
    public static Database database;
    Button btn_Dathangkt;
    ImageButton ibtn_Exit;
    TextView txt_Tongtiendathang, txt_Tienhang, txt_Tienship, txt_Tienvoucher, txt_Loaitaikhoan, txt_Freeship;
    EditText edt_Diachigiaohang, edt_Ghichu;
    int Tongtiensp = 0;
    int Tienship = 0;
    double Tienvoucher = 0;
    double Thanhtien = 0;
    Spinner spnCategory;
    CategoryDAO categoryDAO;
    int idcthd;
    String dateString_ngay, dateString_thang, dateString_nam;
    String ship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        database = new Database(OrderActivity.this,"ClothesDatabase",null,2);

        AnhXa();
        GetData();
        NgayHienTai();

        categoryDAO = new CategoryDAO(this, R.layout.item_selected, getListCategort());
        spnCategory.setAdapter(categoryDAO);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(OrderActivity.this, "Bạn chọn hình thức giao hàng " + categoryDAO.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                ship = categoryDAO.getItem(position).getName();
                GetData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        txt_Tienvoucher = findViewById(R.id.txt_Tienvoucher);
        txt_Loaitaikhoan = findViewById(R.id.txt_Loaitaikhoan);
        txt_Freeship = findViewById(R.id.txt_Freeship);
        edt_Diachigiaohang = findViewById(R.id.edtDiachigiaohang);
        btn_Dathangkt= findViewById(R.id.btnDathangkt);
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


        btn_Dathangkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
                String diachi =  edt_Diachigiaohang.getText().toString();
                String ghichu = edt_Ghichu.getText().toString();
                String ngaydat = tdate.getText().toString();
                String thangdat = dateString_thang;
                String namdat = dateString_nam;

                if(database.HoaDonChuaCoTrongHD()){
                    idcthd = 1;
                } else {
                    Cursor cursor = database.Getdata("SELECT IDCTHOADON FROM CHITIETHOADON ORDER BY IDCTHOADON DESC");
                    cursor.moveToNext();
                    idcthd = cursor.getInt(0) + 1;
                }
                for (int position = 0; position<gioHangDAO.mangmuahang.size(); position++)
                {
                    GioHangDTO themhoadon = gioHangDAO.mangmuahang.get(position);
                    database.INSERT_CTHOADON(idcthd, Login.taiKhoanDTO.getMATK(), Login.taiKhoanDTO.getTENTK(),
                            themhoadon.getIDSP(), themhoadon.getTENSANPHAM(), ngaydat ,themhoadon.getSOLUONG(),
                            themhoadon.getTHANHTIEN(), Thanhtien, ghichu, diachi, themhoadon.getSIZE(), 1,
                            Login.taiKhoanDTO.getSDT());
                    database.UPDATE_SOLUONG(themhoadon.getIDSP(),themhoadon.getSOLUONG());
                }
                database.INSERT_HOADON(Login.taiKhoanDTO.getMATK(), Login.taiKhoanDTO.getTENTK(), idcthd,
                        Thanhtien, ngaydat, thangdat, namdat, diachi, ghichu, 1, Login.taiKhoanDTO.getSDT());
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

    private void sendNotification() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Thông báo !")
                .setContentText("Đặt hàng thành công !")
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(bitmap)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificatonId(), notification);

    }

    private int getNotificatonId() {
        return (int) new Date().getTime();
    }

    private List<CategoryDTO> getListCategort() {
        List<CategoryDTO> list = new ArrayList<>();
        list.add(new CategoryDTO("Giao hàng nhanh (2 - 3 ngày)", "1"));
        list.add(new CategoryDTO("Giao hàng tiết kiệm (5 - 6 ngày)", "2"));
        return list;
    }

    private void GetData() {
        String Diachi = Login.taiKhoanDTO.getDIACHI();

        if (ship == "Giao hàng nhanh (2 - 3 ngày)") {
            if (Login.taiKhoanDTO.getLOAITK() == 2) {
                txt_Loaitaikhoan.setText("(VIP)");
                txt_Freeship.setText("-  FreeShip");
                txt_Tienvoucher.setText("- 10%");
                Tienvoucher = (Thanhtien*0.1);
                Tienship = 0;
            } else {
                txt_Loaitaikhoan.setText("(Thường)");
                txt_Freeship.setText("");
                txt_Tienvoucher.setText("0");
                Tienvoucher = 0;
                Tienship = 36000;
            }
        } else {
            if (Login.taiKhoanDTO.getLOAITK() == 2) {
                txt_Loaitaikhoan.setText("(VIP)");
                txt_Freeship.setText("-  FreeShip");
                txt_Tienvoucher.setText("- 10%");
                Tienvoucher = (Thanhtien*0.1);
                Tienship = 0;
            } else {
                txt_Loaitaikhoan.setText("(Thường)");
                txt_Freeship.setText("");
                txt_Tienvoucher.setText("0");
                Tienvoucher = 0;
                Tienship = 24000;
            }
        }

        edt_Diachigiaohang.setText(Diachi);
        txt_Tienhang.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Tongtiensp)));
        txt_Tienship.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Tienship)));
        Thanhtien = Tongtiensp + Tienship - Tienvoucher;
        txt_Tongtiendathang.setText("Tổng tiền: " + (NumberFormat.getNumberInstance(Locale.US).format(Thanhtien) + " VNĐ"));
    }
}