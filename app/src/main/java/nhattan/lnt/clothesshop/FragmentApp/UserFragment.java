package nhattan.lnt.clothesshop.FragmentApp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nhattan.lnt.clothesshop.ChangePasswordActivity;
import nhattan.lnt.clothesshop.ChatBotActivity;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;
import nhattan.lnt.clothesshop.TransactionHistoryActivity;
import nhattan.lnt.clothesshop.UserInformationActivity;


public class UserFragment extends Fragment implements View.OnClickListener{

    private View mView;
    TextView txt_Baomat, txt_Dichvu, txt_Lichsu;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user, container, false);

        AnhXa();

        return mView;
    }

    private void AnhXa() {
        txt_Baomat = mView.findViewById(R.id.txtBaomat);
        txt_Dichvu = mView.findViewById(R.id.txtDoimatkhau);
        txt_Lichsu = mView.findViewById(R.id.txtLichsu);

        txt_Baomat.setOnClickListener(this);
        txt_Dichvu.setOnClickListener(this);
        txt_Lichsu.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.txtBaomat:
                setOnClick_ThongTinNguoiDung();
                ;break;
            case R.id.txtDoimatkhau:
                setOnClick_DoiMatKhau();
                ;break;
            case R.id.txtLichsu:
                setOnClick_LichSuGiaoDich();
                ;break;
        }
    }

    private void setOnClick_LichSuGiaoDich() {
        startActivity(new Intent(getActivity(), TransactionHistoryActivity.class));
    }

    private void setOnClick_DoiMatKhau() {
        startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
    }

    private void setOnClick_ThongTinNguoiDung() {
        startActivity(new Intent(getActivity(), UserInformationActivity.class));
    }

}