package nhattan.lnt.clothesshop.FragmentApp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.GioHangDAO;
import nhattan.lnt.clothesshop.DTO.GioHangDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;


public class MyCartFragment extends Fragment {

    private View view;
    ListView Listview_SanPham;
    ArrayList<GioHangDTO> sanPhamArrayList;
    GioHangDAO adapter;
    Database database;

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        database = new Database(getActivity(),"ClothesDatabase",null,2);

        //        database.QueryData("CREATE TABLE IF NOT EXISTS DoAn(Id INTEGER PRIMARY KEY AUTOINCREMENT" +
//                ", Ten VARCHAR(150), MoTa VARCHAR(250), HinhAnh BLOB)");

        Listview_SanPham = (ListView) view.findViewById(R.id.listview_danhsachsp_gohang);

        sanPhamArrayList = new ArrayList<>();
        adapter = new GioHangDAO(MyCartFragment.this, R.layout.product_mycart, sanPhamArrayList);
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

        return view;
    }

    private void GetData() {
        //get data
        Cursor cursor = database.Getdata("SELECT * FROM GIOHANG WHERE IDTK = " + Login.taiKhoanDTO.getMATK());
        sanPhamArrayList.clear();
        while (cursor.moveToNext())
        {
            sanPhamArrayList.add(new GioHangDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            ));
        }
        adapter.notifyDataSetChanged();

        if (Login.taiKhoanDTO.getMATK() == -1)
        {
            Toast.makeText(getActivity(), "Bạn hãy đăng nhập để có thể mua hàng !", Toast.LENGTH_LONG).show();
        }else if (sanPhamArrayList.isEmpty()){
            Toast.makeText(getActivity(), "Bạn chưa mua hàng !", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getActivity(), "id cua may la : "+ Login.taiKhoanDTO.getMATK(), Toast.LENGTH_LONG).show();
    }
}