package nhattan.lnt.clothesshop.Manager.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.ADAPTER.QuanLyTaiKhoanAdapter;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Manager.CapNhat_TaiKhoan;
import nhattan.lnt.clothesshop.R;


public class Quanly_Taikhoan extends Fragment {
    private View view;
    RecyclerView recV_DanhSachTaiKhoan;
    Database database;
    TaiKhoanDTO taiKhoanDTO;
    ArrayList<TaiKhoanDTO> listTaiKhoan;
    QuanLyTaiKhoanAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quanly__taikhoan, container, false);

        database = new Database(getActivity());
        recV_DanhSachTaiKhoan = view.findViewById(R.id.recV_DanhSachTaikhoan);
        registerForContextMenu(recV_DanhSachTaiKhoan);

        listTaiKhoan = new ArrayList<>();
        adapter = new QuanLyTaiKhoanAdapter(this, listTaiKhoan);
        Load();

        recV_DanhSachTaiKhoan.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recV_DanhSachTaiKhoan.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
        Load();
        super.onStart();
    }

    public void Load(){
        listTaiKhoan.clear();
        listTaiKhoan.addAll(database.QuanLyTaiKhoan(1));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = QuanLyTaiKhoanAdapter.getPosition();
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.iSua:
                Intent iCapnhat = new Intent(getActivity(), CapNhat_TaiKhoan.class);
                iCapnhat.putExtra("IDTK", listTaiKhoan.get(position).getMATK());
                Toast.makeText(getActivity(), "ID tài khoản là: " + listTaiKhoan.get(position).getMATK(), Toast.LENGTH_SHORT).show();
                startActivity(iCapnhat);
                return true;
            case R.id.iXoa:
                taiKhoanDTO = listTaiKhoan.get(position);
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
                database.XoaTK(taiKhoanDTO.getMATK());
                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
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
}