package nhattan.lnt.clothesshop.Manager;

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
import nhattan.lnt.clothesshop.R;

public class Chitiethoadon_manager extends AppCompatActivity {

    CTHoaDonDTO ctHoaDonDTO;
    HoaDonDTO hoaDonDTO;
    ListView lv_Chitiethoadon_ql;
    ArrayList<CTHoaDonDTO> ctHoaDonDTOArrayList;
    CTHoaDonDAO adapter;
    int IDCTHOADON;
    TextView txt_Mahoadon_ql,txt_Tonghoadon_ql, txt_Tennguoidat_ql, txt_Ngaydat_ql, txt_Noidungghichu_ql, txt_Diachidathang_ql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiethoadon_manager);

        HomeFragment.database = new Database(Chitiethoadon_manager.this,"ClothesDatabase",null,2);

        Intent intent = getIntent();
        IDCTHOADON = intent.getIntExtra("IDQLCTHD",1);
        Log.e("CHECK",String.valueOf(IDCTHOADON));
        AnhXa();
        lv_Chitiethoadon_ql = findViewById(R.id.lv_chitiethoadon_ql);

        ctHoaDonDTOArrayList = new ArrayList<>();
        adapter = new CTHoaDonDAO(Chitiethoadon_manager.this, R.layout.list_transaction_history_details, ctHoaDonDTOArrayList);
        lv_Chitiethoadon_ql.setAdapter(adapter);
        registerForContextMenu(lv_Chitiethoadon_ql);

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
                    cursor.getString(10)
            ));
        }
        adapter.notifyDataSetChanged();

        CTHoaDonDTO ctHoaDonDTO = HomeFragment.database.LoadCTHD(IDCTHOADON);
        if (ctHoaDonDTO == null)
            return;
        txt_Mahoadon_ql.setText("FASH" + ctHoaDonDTO.getIDCTHOADON());
        txt_Tonghoadon_ql.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(ctHoaDonDTO.getTONGHOADON())) + " VNĐ");
        txt_Tennguoidat_ql.setText(ctHoaDonDTO.getTENTAIKHOAN());
        txt_Ngaydat_ql.setText(ctHoaDonDTO.getNGAYDAT());
        txt_Noidungghichu_ql.setText(ctHoaDonDTO.getGHICHU());
        txt_Diachidathang_ql.setText(ctHoaDonDTO.getDIACHI());
    }

    private void AnhXa() {
        txt_Mahoadon_ql = findViewById(R.id.txtMahoadon_ql);
        txt_Tonghoadon_ql = findViewById(R.id.txtTonghoadon_ql);
        txt_Tennguoidat_ql = findViewById(R.id.txtTennguoidat_ql);
        txt_Ngaydat_ql = findViewById(R.id.txtNgaydat_ql);
        txt_Noidungghichu_ql = findViewById(R.id.txtNoidungghichu_ql);
        txt_Diachidathang_ql = findViewById(R.id.txtDiachidathang_ql);

        txt_Mahoadon_ql.setEnabled(false);
        txt_Tonghoadon_ql.setEnabled(false);
        txt_Tennguoidat_ql.setEnabled(false);
        txt_Ngaydat_ql.setEnabled(false);
        txt_Noidungghichu_ql.setEnabled(false);
        txt_Diachidathang_ql.setEnabled(false);

        ImageButton ibtn_Exit = findViewById(R.id.ibtnExitchitiethoadon_ql);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}