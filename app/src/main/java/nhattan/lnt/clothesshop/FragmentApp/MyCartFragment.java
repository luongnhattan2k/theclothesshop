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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import nhattan.lnt.clothesshop.DAO.GioHangDAO;
import nhattan.lnt.clothesshop.DTO.EventBus.TinhTongEvent;
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
    GioHangDTO gioHangDTO;
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
                if (adapter.isEmpty()) {
                    Toast.makeText(getActivity(), "Giỏ hàng chưa có sản phẩm. Vui lòng thêm sản phẩm vào giỏ !", Toast.LENGTH_SHORT).show();
                } else if (adapter.mangmuahang.isEmpty()){
                    Toast.makeText(getActivity(), "Bạn chưa chọn sản phẩm để đặt hàng !", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(getActivity(), OrderActivity.class));
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        Tongtien();
        adapter.mangmuahang.clear();
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien (TinhTongEvent event){
        if (event != null) {
            Tongtien();
        }
    }

    private void Tongtien() {
//        Cursor cursor = HomeFragment.database.Getdata("SELECT SUM ( THANHTIEN ) FROM GIOHANG WHERE IDTK = "
//                + Login.taiKhoanDTO.getMATK());
//        cursor.moveToNext();
//        tong = cursor.getInt(0);

        long Tongtiensp = 0;
        for (int i = 0; i < adapter.mangmuahang.size(); i++) {
            Tongtiensp = Tongtiensp + (adapter.mangmuahang.get(i).getTHANHTIEN()* adapter.mangmuahang.get(i).getSOLUONG());
        }
        txt_Tongtien.setText("Tổng tiền: " + String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Tongtiensp) + " VNĐ"));
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
        } else if (sanPhamArrayList.isEmpty()){
            Toast.makeText(getActivity(), "Bạn chưa mua hàng !", Toast.LENGTH_SHORT).show();
        }
    }
}