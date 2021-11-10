package nhattan.lnt.clothesshop.FragmentApp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nhattan.lnt.clothesshop.ChatBotActivity;
import nhattan.lnt.clothesshop.ContactActivity;
import nhattan.lnt.clothesshop.IntroductActivity;
import nhattan.lnt.clothesshop.R;

public class SettingFragment extends Fragment implements View.OnClickListener{

    private View view;
    TextView txt_Gioithieu, txt_Lienhe, txt_Trogiup;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);

        AnhXa();

        return view;
    }

    private void AnhXa() {
        txt_Gioithieu = view.findViewById(R.id.txtGioiThieu);
        txt_Lienhe = view.findViewById(R.id.txtLienHe);
        txt_Trogiup = view.findViewById(R.id.txtTrogiup);

        txt_Gioithieu.setOnClickListener(this);
        txt_Lienhe.setOnClickListener(this);
        txt_Trogiup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.txtGioiThieu:
                setOnClick_GioiThieu();
                ;break;
            case R.id.txtLienHe:
                setOnClick_LienHe();
                ;break;
            case R.id.txtTrogiup:
                setOnClick_TroGiup();
                ;break;

        }

    }

    private void setOnClick_TroGiup() {
        startActivity(new Intent(getActivity(), ChatBotActivity.class));
    }

    private void setOnClick_LienHe() {
        startActivity(new Intent(getActivity(), ContactActivity.class));
    }

    private void setOnClick_GioiThieu() {
        startActivity(new Intent(getActivity(), IntroductActivity.class));
    }
}