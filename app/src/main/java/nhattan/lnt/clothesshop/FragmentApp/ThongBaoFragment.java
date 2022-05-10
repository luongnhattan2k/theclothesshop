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

import nhattan.lnt.clothesshop.DAO.TinTucDAO;
import nhattan.lnt.clothesshop.DTO.TinTucDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;
import nhattan.lnt.clothesshop.ThongBaoChiTietActivity;

public class ThongBaoFragment extends Fragment {

    private View view;

    Database database;
    GridView gridView_Tintuc;
    ArrayList<TinTucDTO> tinTucDTOArrayList;
    TinTucDAO adapter;
    int IDTHONGTINTINTUC;

    public ThongBaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thong_bao, container, false);

        database = new Database(getActivity(),"ClothesDatabase",null,2);
        gridView_Tintuc = view.findViewById(R.id.gridviewTinTuc);
        tinTucDTOArrayList = new ArrayList<>();
        adapter = new TinTucDAO(ThongBaoFragment.this, R.layout.product_custom_tintuc, tinTucDTOArrayList);
        gridView_Tintuc.setAdapter(adapter);
        gridView_Tintuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(HomeFragment.database.TinTucMoi(
                        Login.taiKhoanDTO.getMATK(),
                        adapter.ListTinTuc.get(i).getIDTINTUC()))
                {
                    HomeFragment.database.TinTucCu(
                            Login.taiKhoanDTO.getMATK(),
                            adapter.ListTinTuc.get(i).getIDTINTUC());
                }


                Intent intent = new Intent(getActivity(), ThongBaoChiTietActivity.class);
                TinTucDTO tinTucDTO = adapter.ListTinTuc.get(i);
                IDTHONGTINTINTUC = tinTucDTO.getIDTINTUC();
                intent.putExtra("IDTHONGBAOTINTUC", IDTHONGTINTINTUC);
                startActivity(intent);
            }
        });
        GetData();
        registerForContextMenu(gridView_Tintuc);
        return view;
    }

    @Override
    public void onStart() {
        GetData();
        super.onStart();
    }

    private void GetData() {
        //get data
        Cursor cursor = database.Getdata("SELECT * FROM TINTUC ORDER BY IDTINTUC DESC");
        tinTucDTOArrayList.clear();
        while (cursor.moveToNext())
        {
            tinTucDTOArrayList.add(new TinTucDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4)
            ));
        }
        adapter.notifyDataSetChanged();

    }
}