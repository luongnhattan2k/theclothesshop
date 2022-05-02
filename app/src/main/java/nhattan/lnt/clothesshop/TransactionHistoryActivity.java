package nhattan.lnt.clothesshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.HoaDonDAO;
import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class TransactionHistoryActivity extends AppCompatActivity {

    ListView lv_Lichsugiaodich;
    ArrayList<HoaDonDTO> hoaDonArrayList;
    HoaDonDAO adapter;
    TextView txtthongbao;
    int IDCTHOADON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        HomeFragment.database = new Database(TransactionHistoryActivity.this,"ClothesDatabase",null,2);
        AnhXa();
        lv_Lichsugiaodich = findViewById(R.id.lv_lichsugiaodich);

        hoaDonArrayList = new ArrayList<>();
        adapter = new HoaDonDAO(TransactionHistoryActivity.this, R.layout.list_trasaction_history, hoaDonArrayList);
        lv_Lichsugiaodich.setAdapter(adapter);
        registerForContextMenu(lv_Lichsugiaodich);
        lv_Lichsugiaodich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TransactionHistoryActivity.this, TransactionHistoryDetailsActivity.class);
                HoaDonDTO hoaDonDTO = HoaDonDAO.ListHoaDon.get(i);
                IDCTHOADON = hoaDonDTO.getIDCTHOADON();
                intent.putExtra("IDCT", IDCTHOADON);
                startActivity(intent);
            }
        });

        GetData();
    }

    @Override
    protected void onStart() {
        GetData();
        super.onStart();
    }

    private void AnhXa() {
        ImageButton ibtn_Exit = findViewById(R.id.ibtnExitlichsugiaodich);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void GetData() {
        //get data
        Cursor cursor = HomeFragment.database.Getdata("SELECT * FROM HOADON WHERE IDTAIKHOAN = " +
                Login.taiKhoanDTO.getMATK() + " ORDER BY IDHOADON DESC");
        hoaDonArrayList.clear();
        while (cursor.moveToNext())
        {
            hoaDonArrayList.add(new HoaDonDTO(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getInt(10),
                    cursor.getInt(11)

            ));
        }
        adapter.notifyDataSetChanged();

        if (hoaDonArrayList.isEmpty()){
            Toast.makeText(TransactionHistoryActivity.this, "Bạn chưa mua hàng !", Toast.LENGTH_SHORT).show();
        }

    }
}
