package nhattan.lnt.clothesshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.DanhGiaDAO;
import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class DanhGiaActivity extends AppCompatActivity {

    int IDCTHOADON;
    ListView lv_danhgiasanpham;
    ArrayList<CTHoaDonDTO> ctHoaDonDTOArrayList;
    DanhGiaDAO adapter;
    CTHoaDonDTO ctHoaDonDTO;
    ImageButton ibtnExitdanhgiasanpham;
    TextView txt_Hoanthanhdanhgia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);

        Intent intent = getIntent();
        IDCTHOADON = intent.getIntExtra("IDCTHOADON",1);
        AnhXa();
        lv_danhgiasanpham = findViewById(R.id.lv_danhgiasanpham);

        ctHoaDonDTOArrayList = new ArrayList<>();
        adapter = new DanhGiaDAO(DanhGiaActivity.this, R.layout.product_custom_danhgia, ctHoaDonDTOArrayList);
        lv_danhgiasanpham.setAdapter(adapter);

        GetData();
    }

    @Override
    protected void onStart() {
        GetData();
        super.onStart();
    }

    private void AnhXa() {
        txt_Hoanthanhdanhgia = findViewById(R.id.txt_Hoanthanhdanhgia);
        txt_Hoanthanhdanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.database.CapNhatCTHoaDon_NguoiDung(IDCTHOADON, 7);
                HomeFragment.database.CapNhatHoaDon_NguoiDung(IDCTHOADON, 7);
                Toast.makeText(DanhGiaActivity.this, "Hoàn tất đánh giá !", Toast.LENGTH_SHORT).show();
                GetData();
                onBackPressed();
            }
        });

        ibtnExitdanhgiasanpham = findViewById(R.id.ibtnExitdanhgiasanpham);
        ibtnExitdanhgiasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
}