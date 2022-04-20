package nhattan.lnt.clothesshop.Manager;

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

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import nhattan.lnt.clothesshop.DAO.CTHoaDonDAO;
import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.R;

public class Chitiethoadon_manager extends AppCompatActivity {

    CTHoaDonDTO ctHoaDonDTO;
    HoaDonDTO hoaDonDTO;
    Database database;
    ListView lv_Chitiethoadon_ql;
    private boolean isEnabled;
    ArrayList<CTHoaDonDTO> ctHoaDonDTOArrayList;
    CTHoaDonDAO adapter;
    int IDCTHOADON,IDTK;
    TextView txt_Mahoadon_ql,txt_Tonghoadon_ql, txt_Tennguoidat_ql, txt_Ngaydat_ql,
            txt_Noidungghichu_ql, txt_Diachidathang_ql, txtSdtnguoidat_ql, txtTinhtrangdonhang_ql;
    Button btnCapNhat_hoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiethoadon_manager);

        Intent intent = getIntent();
        IDCTHOADON = intent.getIntExtra("IDQLCTHD",1);

        AnhXa();

        Log.e("CHECK",String.valueOf(IDCTHOADON));
        lv_Chitiethoadon_ql = findViewById(R.id.lv_chitiethoadon_ql);
        ctHoaDonDTOArrayList = new ArrayList<>();
        adapter = new CTHoaDonDAO(Chitiethoadon_manager.this, R.layout.list_transaction_history_details, ctHoaDonDTOArrayList);
        lv_Chitiethoadon_ql.setAdapter(adapter);
        registerForContextMenu(lv_Chitiethoadon_ql);

        GetData();
        SuKien();
    }

    @Override
    protected void onStart() {
        GetData();
        SuKien();
        super.onStart();
    }

    private void SuKien() {
        ctHoaDonDTO = HomeFragment.database.LoadCTHD(IDCTHOADON);
        if (ctHoaDonDTO == null)
            return;

        txt_Mahoadon_ql.setText("FASH" + (IDCTHOADON));
        txt_Tonghoadon_ql.setText((NumberFormat.getNumberInstance(Locale.US).format(ctHoaDonDTO.getTONGHOADON())) + " VNĐ");
        txt_Tennguoidat_ql.setText(ctHoaDonDTO.getTENTAIKHOAN());
        txt_Ngaydat_ql.setText(ctHoaDonDTO.getNGAYDAT());
        txt_Noidungghichu_ql.setText(ctHoaDonDTO.getGHICHU());
        txt_Diachidathang_ql.setText(ctHoaDonDTO.getDIACHI());
        txtSdtnguoidat_ql.setText("0" + ctHoaDonDTO.getSDT());
        txtTinhtrangdonhang_ql.setText(String.valueOf(ctHoaDonDTO.getTINHTRANG()));

        btnCapNhat_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnabled = !isEnabled;
                enableControl();
                if (isEnabled){
                    btnCapNhat_hoadon.setText("Lưu");
                }
                else{
                    btnCapNhat_hoadon.setText("Cập nhật");
                    int idchitiethoadon = Integer.parseInt(String.valueOf(IDCTHOADON));
                    String diachi = txt_Diachidathang_ql.getText().toString();
                    String ghichu = txt_Noidungghichu_ql.getText().toString();
                    int sdt = Integer.parseInt(txtSdtnguoidat_ql.getText().toString());
                    int tinhtrang = Integer.parseInt(txtTinhtrangdonhang_ql.getText().toString());

                    if (diachi.isEmpty() || ghichu.isEmpty())
                    {
                        Toast.makeText(Chitiethoadon_manager.this, "Vui lòng thử lại !", Toast.LENGTH_SHORT).show();
                    } else {
                        HomeFragment.database.CapNhatCTHoaDon(idchitiethoadon, ghichu, diachi, tinhtrang, sdt);
                        HomeFragment.database.CapNhatHoaDon(idchitiethoadon, ghichu, diachi, tinhtrang, sdt);
                        Toast.makeText(Chitiethoadon_manager.this, "Thành công !", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }
        });
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
    }

    private void enableControl(){
        txt_Noidungghichu_ql.setEnabled(isEnabled);
        txt_Diachidathang_ql.setEnabled(isEnabled);
        txtSdtnguoidat_ql.setEnabled(isEnabled);
        txtTinhtrangdonhang_ql.setEnabled(isEnabled);
    }

    private void AnhXa() {
        txt_Mahoadon_ql = findViewById(R.id.txtMahoadon_ql);
        txt_Tonghoadon_ql = findViewById(R.id.txtTonghoadon_ql);
        txt_Tennguoidat_ql = findViewById(R.id.txtTennguoidat_ql);
        txt_Ngaydat_ql = findViewById(R.id.txtNgaydat_ql);
        txt_Noidungghichu_ql = findViewById(R.id.txtNoidungghichu_ql);
        txt_Diachidathang_ql = findViewById(R.id.txtDiachidathang_ql);
        txtSdtnguoidat_ql = findViewById(R.id.txtSdtnguoidat_ql);
        txtTinhtrangdonhang_ql = findViewById(R.id.txtTinhtrangdonhang_ql);
        btnCapNhat_hoadon = findViewById(R.id.btnCapNhat_hoadon);
        ImageButton ibtn_Exit = findViewById(R.id.ibtnExitchitiethoadon_ql);

        txt_Mahoadon_ql.setEnabled(false);
        txt_Tonghoadon_ql.setEnabled(false);
        txt_Tennguoidat_ql.setEnabled(false);
        txt_Ngaydat_ql.setEnabled(false);
        txt_Noidungghichu_ql.setEnabled(false);
        txt_Diachidathang_ql.setEnabled(false);
        txtSdtnguoidat_ql.setEnabled(false);
        txtTinhtrangdonhang_ql.setEnabled(false);

        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}