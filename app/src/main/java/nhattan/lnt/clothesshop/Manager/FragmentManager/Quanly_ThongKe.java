package nhattan.lnt.clothesshop.Manager.FragmentManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nhattan.lnt.clothesshop.R;

public class Quanly_ThongKe extends Fragment {

    private View view;

    public Quanly_ThongKe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quanly__thong_ke, container, false);


        return view;
    }
}