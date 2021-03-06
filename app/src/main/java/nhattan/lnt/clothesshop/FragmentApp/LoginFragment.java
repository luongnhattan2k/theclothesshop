package nhattan.lnt.clothesshop.FragmentApp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.ForgotPassword;
import nhattan.lnt.clothesshop.HomeActivity;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.Manager.HomeManager;
import nhattan.lnt.clothesshop.R;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private View view;
    TextView txt_DangKy, txt_QuenMK;
    Button btn_DangNhap;
    EditText edt_TK, edt_MK;
    TaiKhoanDAO taiKhoanDAO;
    String mUsername = "";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        AnhXa();

        return view;
    }

    private void AnhXa() {
        txt_DangKy = view.findViewById(R.id.txtDangky);
        txt_QuenMK = view.findViewById(R.id.txtQuenmk);
        btn_DangNhap = view.findViewById(R.id.btnDangNhap);
        edt_TK = view.findViewById(R.id.edtTaikhoan_dn);
        edt_MK = view.findViewById(R.id.edtMatkhau_dn);

        taiKhoanDAO = new TaiKhoanDAO(getActivity());
        btn_DangNhap.setOnClickListener(this);
        txt_QuenMK.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangNhap:
                setBtn_Login();
                break;
        }
    }

    private void setBtn_Login(){
        String sTentaikhoan = edt_TK.getText().toString();
        String sMatkhau = edt_MK.getText().toString();
        TaiKhoanDTO kiemtra = taiKhoanDAO.KiemTraDangNhap(sTentaikhoan, sMatkhau);
        mUsername = sTentaikhoan;

        if (kiemtra != null){
            Login.taiKhoanDTO = kiemtra;
            Toast.makeText(getActivity(), "????ng Nh???p Th??nh C??ng ! ", Toast.LENGTH_SHORT).show();
            if (Login.taiKhoanDTO.getQUYEN() == 0){
                startActivity(new Intent(getActivity(), HomeManager.class));
            } else {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        } else {
            Toast.makeText(getActivity(), "????ng Nh???p Th???t B???i !", Toast.LENGTH_SHORT).show();
        }
    }

    public String getUsername() {
        return mUsername;
    }

}