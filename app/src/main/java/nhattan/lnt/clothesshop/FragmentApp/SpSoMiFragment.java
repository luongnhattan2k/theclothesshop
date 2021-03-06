package nhattan.lnt.clothesshop.FragmentApp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.ProductionActivity;
import nhattan.lnt.clothesshop.R;

public class SpSoMiFragment extends Fragment {

    private View view;

    Database database;
    GridView gridView_SanPham;
    ArrayList<SanPhamDTO> sanPhamDTOArrayList;
    SanPhamDAO adapter;

    public SpSoMiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sp_so_mi, container, false);

        database = new Database(getActivity(),"ClothesDatabase",null,2);

        gridView_SanPham = view.findViewById(R.id.gridviewSanPham_somi);
        sanPhamDTOArrayList = new ArrayList<>();
        adapter = new SanPhamDAO(SpSoMiFragment.this, R.layout.product_layout, sanPhamDTOArrayList);

        gridView_SanPham.setAdapter(adapter);
        gridView_SanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int id, long l) {
                Intent intent = new Intent(getActivity(), ProductionActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        registerForContextMenu(gridView_SanPham);

        GetData();

        return view;
    }

    private void GetData() {
        //get data
        Cursor cursor = database.Getdata("SELECT * FROM SANPHAM WHERE IDDANHMUC = 3");
        while (cursor.moveToNext())
        {
            sanPhamDTOArrayList.add(new SanPhamDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            ));
        }
        adapter.notifyDataSetChanged();
    }
}