package nhattan.lnt.clothesshop.FragmentApp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import nhattan.lnt.clothesshop.DAO.GioHangDAO;
import nhattan.lnt.clothesshop.DTO.GioHangDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.OrderActivity;
import nhattan.lnt.clothesshop.R;


public class MyCartFragment extends Fragment {

    private View view;
    ListView Listview_SanPham;
    ArrayList<GioHangDTO> sanPhamArrayList;
    GioHangDAO adapter;
    Database database;
    Button btn_Dathang;
    TextView txt_Tongtien;

    int tong;

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        database = new Database(getActivity(),"ClothesDatabase",null,2);

        AnhXa();
        Listview_SanPham = view.findViewById(R.id.listview_danhsachsp_gohang);

        sanPhamArrayList = new ArrayList<>();
        adapter = new GioHangDAO(MyCartFragment.this, R.layout.product_mycart, sanPhamArrayList);
        Listview_SanPham.setAdapter(adapter);
        registerForContextMenu(Listview_SanPham);

        GetData();

        btn_Dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        Tongtien();
        super.onStart();
    }

    private void Tongtien() {
        Cursor cursor = HomeFragment.database.Getdata("SELECT SUM ( THANHTIEN ) FROM GIOHANG WHERE IDTK = "
                + Login.taiKhoanDTO.getMATK());
        cursor.moveToNext();
        tong = cursor.getInt(0);
        txt_Tongtien.setText("Tổng tiền: " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(tong) + " VNĐ"));
    }

    private void AnhXa() {
        txt_Tongtien = view.findViewById(R.id.txtTongtien);
        btn_Dathang= view.findViewById(R.id.btnDathang);
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
            Toast.makeText(getActivity(), "Bạn hãy đăng nhập để có thể mua hàng !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), Login.class));
        }else if (sanPhamArrayList.isEmpty()){
            Toast.makeText(getActivity(), "Bạn chưa mua hàng !", Toast.LENGTH_SHORT).show();
        }
    }
}