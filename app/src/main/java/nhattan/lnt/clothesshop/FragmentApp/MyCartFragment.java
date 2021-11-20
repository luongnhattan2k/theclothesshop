package nhattan.lnt.clothesshop.FragmentApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        //        database.QueryData("CREATE TABLE IF NOT EXISTS DoAn(Id INTEGER PRIMARY KEY AUTOINCREMENT" +
//                ", Ten VARCHAR(150), MoTa VARCHAR(250), HinhAnh BLOB)");

        AnhXa();
        Listview_SanPham = (ListView) view.findViewById(R.id.listview_danhsachsp_gohang);

        sanPhamArrayList = new ArrayList<>();
        adapter = new GioHangDAO(MyCartFragment.this, R.layout.product_mycart, sanPhamArrayList);
        Listview_SanPham.setAdapter(adapter);
        registerForContextMenu(Listview_SanPham);

        GetData();

        btn_Dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showdialog();
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

//    private void showdialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_dathang,null);
//        final EditText DiaChi = view.findViewById(R.id.edtDiachigiaohang);
//        final EditText GhiChu = view.findViewById(R.id.edtGhichu);
//
//        builder.setView(view);
//        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                int idcthd;
//
//                if(HomeFragment.database.HoaDonChuaCoTrongHD()){
//                    idcthd = 1;
//                }
//                else {
//                    Cursor cursor = HomeFragment.database.Getdata("SELECT IDCTHOADON FROM CHITIETHOADON ORDER BY IDCTHOADON DESC LIMIT 1 OFFSET 1");
//                    cursor.moveToNext();
//                    idcthd = cursor.getInt(0) + 1;
//                }
//                Toast.makeText(getActivity(), "Đặt hàng thành công !" + idcthd, Toast.LENGTH_LONG).show();
//
//                for (int position = 0; position<GioHangDAO.sanPhamGioHangList.size();position++)
//                {
//                    GioHangDTO themhoadon = GioHangDAO.sanPhamGioHangList.get(position);
//                    HomeFragment.database.INSERT_CTHOADON(idcthd, themhoadon.getIDTK(), themhoadon.getIDSP(), themhoadon.getTENSANPHAM(),
//                            themhoadon.getSOLUONG(), themhoadon.getTHANHTIEN());
//                    HomeFragment.database.UPDATE_SOLUONG(themhoadon.getIDSP(),themhoadon.getSOLUONG());
//
//                }
//                HomeFragment.database.INSERT_HOADON(tong,idcthd,DiaChi.getText().toString(),GhiChu.getText().toString(),Login.taiKhoanDTO.getMATK());
//                HomeFragment.database.DELETE_GIOHANG(Login.taiKhoanDTO.getMATK());
//                GetData();
//                Tongtien();
//            }
//        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getActivity(),"Bạn vừa từ chối đặt hàng !",Toast.LENGTH_LONG).show();
//            }
//        });
//        builder.show();
//    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId())
        {
            case R.id.menu_edit_item:


                return true;
            case R.id.menu_delete_item:

                GioHangDTO gioHang = GioHangDAO.sanPhamGioHangList.get(info.position);
                HomeFragment.database.DELETE_GIOHANG(
                        gioHang.getIDSP(),
                        gioHang.getIDTK()
                );

                Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_LONG).show();
                GetData();
                Tongtien();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void AnhXa() {
        txt_Tongtien = (TextView) view.findViewById(R.id.txtTongtien);
        btn_Dathang= (Button) view.findViewById(R.id.btnDathang);
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
            startActivity(new Intent(getActivity(), Login.class));
        }else if (sanPhamArrayList.isEmpty()){
            Toast.makeText(getActivity(), "Bạn chưa mua hàng !", Toast.LENGTH_LONG).show();
        }
    }
}