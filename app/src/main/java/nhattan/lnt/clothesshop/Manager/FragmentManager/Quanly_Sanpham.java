package nhattan.lnt.clothesshop.Manager.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.ADAPTER.QuanLySanPhamAdapter;
import nhattan.lnt.clothesshop.DAO.CategoryDAO;
import nhattan.lnt.clothesshop.DTO.CategoryDTO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Manager.CapNhat_SanPham;
import nhattan.lnt.clothesshop.Manager.Them_SanPham;
import nhattan.lnt.clothesshop.R;

public class Quanly_Sanpham extends Fragment {
    Spinner spn_Qlsanpham;
    CategoryDAO categoryDAO;
    RecyclerView recV_DanhSachSanPham;
    Database database;
    SanPhamDTO sanPhamDTO;
    ArrayList<SanPhamDTO> listSanpham;
    ArrayList<CategoryDTO> listCategory;
    QuanLySanPhamAdapter adapter;
    Button btnThemspmoi;
    private Toolbar toolbar;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quanly__sanpham, container, false);

        database = new Database(getActivity());
        spn_Qlsanpham = view.findViewById(R.id.spn_qlsanpham);
        recV_DanhSachSanPham = view.findViewById(R.id.recV_DanhSachSanPham);

        listCategory = getListCategort();
        categoryDAO = new CategoryDAO(getActivity(), R.layout.item_selected, listCategory);
        spn_Qlsanpham.setAdapter(categoryDAO);
        registerForContextMenu(recV_DanhSachSanPham);


        listSanpham = new ArrayList<>();
        adapter = new QuanLySanPhamAdapter(this, listSanpham);
        spn_Qlsanpham.setSelection(0);
        Load();

        recV_DanhSachSanPham.setLayoutManager( new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL,false));
        recV_DanhSachSanPham.setAdapter(adapter);

        spn_Qlsanpham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Load();
                Toast.makeText(getActivity(), "Bạn vừa chọn " + categoryDAO.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnThemspmoi = view.findViewById(R.id.btnThemspmoi);
        btnThemspmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Them_SanPham.class));
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
        listSanpham.clear();
        listSanpham.addAll(database.QuanLySaNPham(listCategory.get(getPos()).getIDDANHMUC()));
        adapter.notifyDataSetChanged();
    }

    private int getPos() {
        for(int i =0; i < listCategory.size();i++)
        {
            if (i == spn_Qlsanpham.getSelectedItemPosition()){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = QuanLySanPhamAdapter.getPosition();
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.iSua:
                Intent iCapnhatsp = new Intent(getActivity(), CapNhat_SanPham.class);
                iCapnhatsp.putExtra("IDSANPHAM", listSanpham.get(position).getMaSP());
                startActivity(iCapnhatsp);
//                Toast.makeText(getActivity(), "ádasd" + listSanpham.get(position).getMaSP(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.iXoa:
                sanPhamDTO = listSanpham.get(position);
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
                database.XoaSanPham(sanPhamDTO.getMaSP());
//                getActivity().onBackPressed();
                Load();
                Toast.makeText(getActivity(), "Bạn vừa xóa " + sanPhamDTO.getTenSP(), Toast.LENGTH_SHORT).show();
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

    private ArrayList<CategoryDTO> getListCategort() {
        ArrayList<CategoryDTO> list = new ArrayList<>();

        list.add(new CategoryDTO("Áo Thun","1"));
        list.add(new CategoryDTO("Áo Khác","2"));
        list.add(new CategoryDTO("Áo Sơ Mi","3"));

        return list;
    }

}