package nhattan.lnt.clothesshop.Manager.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.ADAPTER.QuanLyHoaDonAdapter;
import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Manager.Chitiethoadon_manager;
import nhattan.lnt.clothesshop.R;


public class Quanly_HoaDon extends Fragment {

    private View view;
    RecyclerView recV_DanhSachSanPham_qlhd;
    Database database;
    HoaDonDTO hoaDonDTO;
    ArrayList<HoaDonDTO> listHoaDon;
    QuanLyHoaDonAdapter adapter;
    TextView txt_Ngaydathang;
    Button btn_Chonngay;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quanly__hoadon, container, false);

        database = new Database(getActivity());
        AnhXa();
        registerForContextMenu(recV_DanhSachSanPham_qlhd);

        listHoaDon = new ArrayList<>();
        adapter = new QuanLyHoaDonAdapter(this, listHoaDon);
        Load();



        recV_DanhSachSanPham_qlhd.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recV_DanhSachSanPham_qlhd.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        Load();
        super.onStart();
    }

    private void Load() {
        listHoaDon.clear();
        listHoaDon.addAll(database.QuanLyHoaDon());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = QuanLyHoaDonAdapter.getPosition();
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.iXemhd:
                Intent iXemhoadon = new Intent(getActivity(), Chitiethoadon_manager.class);
                iXemhoadon.putExtra("IDQLCTHD", listHoaDon.get(position).getIDCTHOADON());
                Toast.makeText(getActivity(), "Mã hóa đơn là FASH" + listHoaDon.get(position).getIDCTHOADON(), Toast.LENGTH_SHORT).show();
                startActivity(iXemhoadon);
                return true;
            case R.id.iXoahd:
                hoaDonDTO = listHoaDon.get(position);
                ShowDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn có chắc muốn xóa nó hay không ?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.XoaHD(hoaDonDTO.getIDHOADON());
                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                Load();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void AnhXa() {
        txt_Ngaydathang = view.findViewById(R.id.txtNgaydathang_ql);
        btn_Chonngay = view.findViewById(R.id.btnChonngaydat);
        recV_DanhSachSanPham_qlhd = view.findViewById(R.id.recV_DanhSachSanPham_qlhd);

    }


}