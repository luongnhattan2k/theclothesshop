package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.CTHoaDonDAO;
import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class TransactionHistoryDetailsActivity extends AppCompatActivity {

    ListView lv_Chitiethoadon;
    ArrayList<CTHoaDonDTO> ctHoaDonDTOArrayList;
    CTHoaDonDAO adapter;
    int IDCTHOADON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_details);

        HomeFragment.database = new Database(TransactionHistoryDetailsActivity.this,"ClothesDatabase",null,2);

        Intent intent = getIntent();
        IDCTHOADON = intent.getIntExtra("IDCT",1);
//        Toast.makeText(TransactionHistoryDetailsActivity.this, "ssss : " + IDCTHOADON, Toast.LENGTH_SHORT).show();

        AnhXa();
        lv_Chitiethoadon = (ListView) findViewById(R.id.lv_chitiethoadon);

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
                    cursor.getInt(4),
                    cursor.getInt(5)
            ));
        }
        adapter.notifyDataSetChanged();
    }

    private void AnhXa() {
        ImageButton ibtn_Exit = findViewById(R.id.ibtnExitchitiethoadon);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}