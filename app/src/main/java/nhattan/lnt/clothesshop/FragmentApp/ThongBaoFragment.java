package nhattan.lnt.clothesshop.FragmentApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import nhattan.lnt.clothesshop.R;

public class ThongBaoFragment extends Fragment {

    private View view;

    public ThongBaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_thong_bao, container, false);


        return view;
    }
}