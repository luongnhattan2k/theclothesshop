package nhattan.lnt.clothesshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    TextView txt_Mahoadon,txt_Tonghoadon, txt_Tennguoidat, txt_Ngaydat, txt_Noidungghichu, txt_Diachidathang;

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
                    cursor.getString(11)
            ));
        }
        adapter.notifyDataSetChanged();

        CTHoaDonDTO ctHoaDonDTO = HomeFragment.database.LoadCTHD(IDCTHOADON);
        if (ctHoaDonDTO == null)
            return;
        txt_Mahoadon.setText("Mã hóa đơn: " + "FASH" + ctHoaDonDTO.getIDCTHOADON());
        txt_Tonghoadon.setText("Tổng hóa đơn: " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ctHoaDonDTO.getTONGHOADON())) + " VNĐ");
        txt_Tennguoidat.setText("Tên người đặt: " + ctHoaDonDTO.getTENTAIKHOAN());
        txt_Ngaydat.setText("Ngày đặt: " + ctHoaDonDTO.getNGAYDAT());
        txt_Noidungghichu.setText("Ghi chú: " + ctHoaDonDTO.getGHICHU());
        txt_Diachidathang.setText("Địa chỉ: " + ctHoaDonDTO.getDIACHI());
    }

    private void AnhXa() {
        txt_Mahoadon = findViewById(R.id.txtMahoadon);
        txt_Tonghoadon = findViewById(R.id.txtTonghoadon);
        txt_Tennguoidat = findViewById(R.id.txtTennguoidat);
        txt_Ngaydat = findViewById(R.id.txtNgaydat);
        txt_Noidungghichu = findViewById(R.id.txtNoidungghichu);
        txt_Diachidathang = findViewById(R.id.txtDiachidathang);

        ImageButton ibtn_Exit = findViewById(R.id.ibtnExitchitiethoadon);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}