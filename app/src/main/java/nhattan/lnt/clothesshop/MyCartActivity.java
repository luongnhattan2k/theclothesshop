package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.GioHangDAO;
import nhattan.lnt.clothesshop.DTO.GioHangDTO;
import nhattan.lnt.clothesshop.Database.Database;

public class MyCartActivity extends AppCompatActivity {
    ListView Listview_SanPham;
    ArrayList<GioHangDTO> sanPhamArrayList;
    GioHangDAO adapter;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        database = new Database(this,"ClothesDatabase",null,2);

        //        database.QueryData("CREATE TABLE IF NOT EXISTS DoAn(Id INTEGER PRIMARY KEY AUTOINCREMENT" +
//                ", Ten VARCHAR(150), MoTa VARCHAR(250), HinhAnh BLOB)");

        Listview_SanPham = (ListView) findViewById(R.id.listview_danhsachsp_gohang);

        sanPhamArrayList = new ArrayList<>();
        adapter = new GioHangDAO(this, R.layout.product_mycart, sanPhamArrayList);
        Listview_SanPham.setAdapter(adapter);
//        Listview_SanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), Products_information_activity.class);
//
//
//                intent.putExtra("id",i);
//                startActivity(intent);
//
//            }
//        });
        registerForContextMenu(Listview_SanPham);

        GetData();
    }

    private void GetData() {
        //get data
        Cursor cursor = database.Getdata("SELECT * FROM GIOHANG");
        sanPhamArrayList.clear();
        while (cursor.moveToNext())
        {
            sanPhamArrayList.add(new GioHangDTO(
                    cursor.getInt(0),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            ));
        }
        adapter.notifyDataSetChanged();
    }
}