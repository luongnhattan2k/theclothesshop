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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.ADAPTER.QuanLyTinTucAdapter;
import nhattan.lnt.clothesshop.DTO.TinTucDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Manager.CapNhat_TinTuc;
import nhattan.lnt.clothesshop.Manager.Them_TinMoi;
import nhattan.lnt.clothesshop.R;

public class Quanly_TinTuc extends Fragment {

    RecyclerView recV_DanhSachTinTuc;
    Database database;
    TinTucDTO tinTucDTO;
    ArrayList<TinTucDTO> listTinTuc;
    QuanLyTinTucAdapter adapter;
    Button btnThemtinmoi;
    private Toolbar toolbar;
    private View view;

    public Quanly_TinTuc() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quanly__tin_tuc, container, false);

        database = new Database(getActivity());
        recV_DanhSachTinTuc = view.findViewById(R.id.recV_DanhSachTinTuc);
        registerForContextMenu(recV_DanhSachTinTuc);


        listTinTuc = new ArrayList<>();
        adapter = new QuanLyTinTucAdapter(this, listTinTuc);
        Load();
        recV_DanhSachTinTuc.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recV_DanhSachTinTuc.setAdapter(adapter);

        btnThemtinmoi = view.findViewById(R.id.btnThemtinmoi);
        btnThemtinmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Them_TinMoi.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        Load();
        super.onStart();
    }

    private void Load() {
        listTinTuc.clear();
        listTinTuc.addAll(database.QuanLyTinTuc());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = QuanLyTinTucAdapter.getPosition();
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.iSua:
                Intent iCapnhattintuc = new Intent(getActivity(), CapNhat_TinTuc.class);
                iCapnhattintuc.putExtra("IDTINTUC", listTinTuc.get(position).getIDTINTUC());
                startActivity(iCapnhattintuc);
                Toast.makeText(getActivity(), "Tin tức số: " + listTinTuc.get(position).getIDTINTUC(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.iXoa:
                tinTucDTO = listTinTuc.get(position);
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
                database.XoaTinTuc(tinTucDTO.getIDTINTUC());
                database.XoaTinTucMoi(tinTucDTO.getTIEUDE());
//                getActivity().onBackPressed();
                Load();
                Toast.makeText(getActivity(), "Bạn vừa xóa tin " + tinTucDTO.getIDTINTUC(), Toast.LENGTH_SHORT).show();
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