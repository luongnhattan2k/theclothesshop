package nhattan.lnt.clothesshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import nhattan.lnt.clothesshop.DAO.CTHoaDonDAO;
import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class TransactionHistoryDetailsActivity extends AppCompatActivity {

    CTHoaDonDTO ctHoaDonDTO;
    HoaDonDTO hoaDonDTO;
    ListView lv_Chitiethoadon;
    ArrayList<CTHoaDonDTO> ctHoaDonDTOArrayList;
    CTHoaDonDAO adapter;
    int IDCTHOADON;
    TextView txt_Mahoadon,txt_Tonghoadon, txt_Tennguoidat, txt_Sdtdathang,
            txt_Ngaydat, txt_Noidungghichu, txt_Diachidathang, txtTinhtrangdonhang_chitiet;
    StateProgressBar stateProgressBar;
    Button btn_Danhgiasanpham, btn_Danhanduochang, btn_Huydonhang;

    String[] descriptionData = {"Chờ\nduyệt", "Đóng\nhàng", "Xuất\nđơn", "Đang\ngiao", "Đã\ngiao"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_details);

        HomeFragment.database = new Database(TransactionHistoryDetailsActivity.this,"ClothesDatabase",null,2);

        Intent intent = getIntent();
        IDCTHOADON = intent.getIntExtra("IDCT",1);
        Log.e("CHECK",String.valueOf(IDCTHOADON));
        AnhXa();
        lv_Chitiethoadon = findViewById(R.id.lv_chitiethoadon);

        ctHoaDonDTOArrayList = new ArrayList<>();
        adapter = new CTHoaDonDAO(TransactionHistoryDetailsActivity.this, R.layout.list_transaction_history_details, ctHoaDonDTOArrayList);
        lv_Chitiethoadon.setAdapter(adapter);
        registerForContextMenu(lv_Chitiethoadon);

        GetData();
    }

    @Override
    protected void onStart() {
        GetData();
        super.onStart();
    }

    private void GetData() {
        //get data
        Cursor cursor = HomeFragment.database.Getdata("SELECT * FROM CHITIETHOADON WHERE IDCTHOADON = " + IDCTHOADON);
        ctHoaDonDTOArrayList.clear();
        while (cursor.moveToNext())
        {
            ctHoaDonDTOArrayList.add(new CTHoaDonDTO(
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
            ));
        }
        adapter.notifyDataSetChanged();

        switch (ctHoaDonDTO.getTINHTRANG()) {
            case 1:
                btn_Huydonhang.setEnabled(true);
                btn_Huydonhang.setBackgroundTintList(ContextCompat.getColorStateList(TransactionHistoryDetailsActivity.this, R.color.red));
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Chờ duyệt đơn");
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case 2:
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Đang đóng hàng");
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                break;
            case 3:
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Xuất đơn hàng");
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
            case 4:
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Đang giao");
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                break;
            case 5:
                btn_Danhanduochang.setEnabled(true);
                btn_Danhanduochang.setBackgroundTintList(ContextCompat.getColorStateList(TransactionHistoryDetailsActivity.this, R.color.red));
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Giao hàng thành công");
                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                break;
            case 6:
                btn_Danhgiasanpham.setEnabled(true);
                btn_Danhgiasanpham.setBackgroundTintList(ContextCompat.getColorStateList(TransactionHistoryDetailsActivity.this, R.color.red));
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Đã nhận hàng");
                stateProgressBar.setAllStatesCompleted(true);
                break;
            case 7:
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Đã gửi đánh giá");
                stateProgressBar.setAllStatesCompleted(true);
                break;
            default:
                txtTinhtrangdonhang_chitiet.setText("Tình trạng đơn hàng: Đơn đã hủy");
                break;

        }

    }

    private void AnhXa() {
        txt_Mahoadon = findViewById(R.id.txtMahoadon);
        txt_Tonghoadon = findViewById(R.id.txtTonghoadon);
        txt_Tennguoidat = findViewById(R.id.txtTennguoidat);
        txt_Ngaydat = findViewById(R.id.txtNgaydat);
        txt_Noidungghichu = findViewById(R.id.txtNoidungghichu);
        txt_Diachidathang = findViewById(R.id.txtDiachidathang);
        txt_Sdtdathang = findViewById(R.id.txtSdtdathang);
        txtTinhtrangdonhang_chitiet = findViewById(R.id.txtTinhtrangdonhang_chitiet);
        btn_Danhgiasanpham = findViewById(R.id.btn_Danhgiasanpham);
        btn_Danhgiasanpham.setEnabled(false);
        btn_Danhanduochang = findViewById(R.id.btn_Danhanduochang);
        btn_Danhanduochang.setEnabled(false);
        btn_Huydonhang = findViewById(R.id.btn_Huydonhang);
        btn_Huydonhang.setEnabled(false);
        stateProgressBar = (StateProgressBar) findViewById(R.id.stapro_menu);
        stateProgressBar.setStateDescriptionData(descriptionData);

        ctHoaDonDTO = HomeFragment.database.LoadCTHD(IDCTHOADON);
        if (ctHoaDonDTO == null)
            return;
        txt_Mahoadon.setText("Mã hóa đơn: " + "FASH" + ctHoaDonDTO.getIDCTHOADON());
        txt_Tonghoadon.setText("Tổng hóa đơn: " + NumberFormat.getNumberInstance(Locale.US).format(ctHoaDonDTO.getTONGHOADON()) + " VNĐ");
        txt_Tennguoidat.setText("Tên người đặt: " + ctHoaDonDTO.getTENTAIKHOAN());
        txt_Ngaydat.setText("Ngày đặt: " + ctHoaDonDTO.getNGAYDAT());
        txt_Noidungghichu.setText("Ghi chú: " + ctHoaDonDTO.getGHICHU());
        txt_Diachidathang.setText("Địa chỉ: " + ctHoaDonDTO.getDIACHI());
        txt_Sdtdathang.setText("Số điện thoại: 0" + ctHoaDonDTO.getSDT());

        btn_Danhgiasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransactionHistoryDetailsActivity.this, DanhGiaActivity.class);
                IDCTHOADON = ctHoaDonDTO.getIDCTHOADON();
                intent.putExtra("IDCTHOADON", IDCTHOADON);
                Toast.makeText(TransactionHistoryDetailsActivity.this, "ID " + ctHoaDonDTO.getIDCTHOADON(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        btn_Huydonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog_HuyHang();
            }
        });

        btn_Danhanduochang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog_NhanHang();
            }
        });

        ImageButton ibtn_Exit = findViewById(R.id.ibtnExitchitiethoadon);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void ShowDialog_NhanHang() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TransactionHistoryDetailsActivity.this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Xác nhận bạn đã nhận được hàng !");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn_Danhgiasanpham.setEnabled(true);
                btn_Danhgiasanpham.setBackgroundTintList(ContextCompat.getColorStateList(TransactionHistoryDetailsActivity.this, R.color.red));
                HomeFragment.database.CapNhatCTHoaDon_NguoiDung(ctHoaDonDTO.getIDCTHOADON(), 6);
                HomeFragment.database.CapNhatHoaDon_NguoiDung(ctHoaDonDTO.getIDCTHOADON(), 6);
                GetData();
                onBackPressed();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void ShowDialog_HuyHang() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TransactionHistoryDetailsActivity.this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn có chắc là muốn hủy đơn này không !");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn_Danhgiasanpham.setEnabled(true);
                btn_Danhgiasanpham.setBackgroundTintList(ContextCompat.getColorStateList(TransactionHistoryDetailsActivity.this, R.color.red));
                HomeFragment.database.CapNhatCTHoaDon_NguoiDung(ctHoaDonDTO.getIDCTHOADON(), 0);
                HomeFragment.database.CapNhatHoaDon_NguoiDung(ctHoaDonDTO.getIDCTHOADON(), 0);
                GetData();
                onBackPressed();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }
}