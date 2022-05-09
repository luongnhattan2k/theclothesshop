package nhattan.lnt.clothesshop.Manager.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import nhattan.lnt.clothesshop.R;

public class Quanly_DanhGia extends Fragment {

    View view;
    public Quanly_DanhGia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quanly__danh_gia, container, false);

        return view;
    }
}