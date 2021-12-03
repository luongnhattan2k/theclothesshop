package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.ADAPTER.SearchAdapter;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class TimKiem extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    EditText edt_tk;
    ListView listview_tk;
    ImageButton img_search_Tk;
    ArrayList<SanPhamDTO> sanPhamDTOArrayList;
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        Anhxa();
        listview_tk = findViewById(R.id.listview_tk);
        sanPhamDTOArrayList = new ArrayList<>();
        adapter = new SearchAdapter(TimKiem.this, R.layout.search, sanPhamDTOArrayList);
        listview_tk.setAdapter(adapter);
        listview_tk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TimKiem.this, ProductionActivity.class);


                intent.putExtra("idtk",position);
                startActivity(intent);
            }
        });


        edt_tk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GetDataALL();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                GetData(edt_tk.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void GetData(String ten) {
        //get data
        Cursor cursor = HomeFragment.database.Getdata("SELECT * FROM SANPHAM WHERE TENSANPHAM LIKE '%" + ten +"%'" );
        sanPhamDTOArrayList.clear();
        while (cursor.moveToNext())
        {
            sanPhamDTOArrayList.add(new SanPhamDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)

            ));
        }
        adapter.notifyDataSetChanged();
    }
    private void GetDataALL() {
        //get data
        Cursor cursor = HomeFragment.database.Getdata("SELECT * FROM SANPHAM ");
        sanPhamDTOArrayList.clear();
        while (cursor.moveToNext())
        {
            sanPhamDTOArrayList.add(new SanPhamDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            ));
        }
        adapter.notifyDataSetChanged();
    }

    private void Anhxa() {
        edt_tk = findViewById(R.id.edt_tk);
        img_search_Tk = findViewById(R.id.img_search_Tk);

    }
}