package nhattan.lnt.clothesshop.FragmentApp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import nhattan.lnt.clothesshop.ADAPTER.SliderAdapter;
import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DTO.SliderItem;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.ProductionActivity;
import nhattan.lnt.clothesshop.R;

public class HomeFragment extends Fragment{

    private View view;

    public static Database database;
    GridView gridView_SanPham_new, gridView_SanPham_hot;
    ViewPager2 viewPagerBackground;
    Handler sliderHandler = new Handler();
    ArrayList<SanPhamDTO> sanPhamDTOArrayList_new, sanPhamDTOArrayList_hot;
    SanPhamDAO adapter_new, adapter_hot;
    int tong = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        database = new Database(getActivity(),"ClothesDatabase",null,2);

        gridView_SanPham_new = view.findViewById(R.id.gridviewSanPham_new);
        sanPhamDTOArrayList_new = new ArrayList<>();
        adapter_new = new SanPhamDAO(HomeFragment.this, R.layout.product_layout_moi, sanPhamDTOArrayList_new);
        gridView_SanPham_new.setAdapter(adapter_new);
        gridView_SanPham_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int id, long l) {
                Intent intent = new Intent(getActivity(), ProductionActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        registerForContextMenu(gridView_SanPham_new);

//        gridView_SanPham_hot = view.findViewById(R.id.gridviewSanPham_hot);
//        sanPhamDTOArrayList_hot = new ArrayList<>();
//        adapter_hot = new SanPhamDAO(HomeFragment.this, R.layout.product_layout_hot, sanPhamDTOArrayList_hot);
//        gridView_SanPham_hot.setAdapter(adapter_hot);
//        gridView_SanPham_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int id, long l) {
//                Intent intent = new Intent(getActivity(), ProductionActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
//            }
//        });
//        registerForContextMenu(gridView_SanPham_hot);

        GetData_New();
        GetData_Hot();

        //Start Auto Scroll background
        viewPagerBackground = view.findViewById(R.id.viewPagerImageSilde);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.bg_1));
        sliderItems.add(new SliderItem(R.drawable.bg_2));
        sliderItems.add(new SliderItem(R.drawable.bg_3));
        sliderItems.add(new SliderItem(R.drawable.bg_4));
        sliderItems.add(new SliderItem(R.drawable.bg_5));

        viewPagerBackground.setAdapter(new SliderAdapter(sliderItems, viewPagerBackground));

        viewPagerBackground.setClipToPadding(false);
        viewPagerBackground.setClipChildren(false);
        viewPagerBackground.setOffscreenPageLimit(3);
        viewPagerBackground.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPagerBackground.setPageTransformer(compositePageTransformer);

        viewPagerBackground.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, 3000);
            }
        });
        //End Auto Scroll background

        return view;
    }

    @Override
    public void onStart() {
        Load();
        super.onStart();
    }

    private void Load() {
        Cursor cursor = HomeFragment.database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE IDTAIKHOAN = "
                + Login.taiKhoanDTO.getMATK());
        cursor.moveToNext();
        tong = cursor.getInt(0);

        if (tong > 5000000) {
            HomeFragment.database.UPDATE_LOAITK(Login.taiKhoanDTO.getMATK(), 2);
        } else {
            HomeFragment.database.UPDATE_LOAITK(Login.taiKhoanDTO.getMATK(), 1);
        }
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPagerBackground.setCurrentItem(viewPagerBackground.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);

    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(slideRunnable, 3000);

    }

    private void GetData_New() {
        //get data
        Cursor cursor = database.Getdata("SELECT * FROM SANPHAM WHERE SPNEW = 1");
        sanPhamDTOArrayList_new.clear();
        while (cursor.moveToNext())
        {
            sanPhamDTOArrayList_new.add(new SanPhamDTO(
                    cursor.getInt(0),
                    cursor.getBlob(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9)
            ));
        }
        adapter_new.notifyDataSetChanged();
    }

    private void GetData_Hot() {
        //get data
//        Cursor cursor = database.Getdata("SELECT * FROM SANPHAM WHERE SPHOT = 1");
//        sanPhamDTOArrayList_hot.clear();
//        while (cursor.moveToNext())
//        {
//            sanPhamDTOArrayList_hot.add(new SanPhamDTO(
//                    cursor.getInt(0),
//                    cursor.getBlob(1),
//                    cursor.getString(2),
//                    cursor.getInt(3),
//                    cursor.getInt(4),
//                    cursor.getString(5),
//                    cursor.getInt(6),
//                    cursor.getInt(7),
//                    cursor.getInt(8),
//                    cursor.getInt(9)
//            ));
//        }
//        adapter_hot.notifyDataSetChanged();
    }
}