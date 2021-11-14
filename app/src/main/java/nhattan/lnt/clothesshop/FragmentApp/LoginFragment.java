package nhattan.lnt.clothesshop.FragmentApp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.ForgotPassword;
import nhattan.lnt.clothesshop.HomeActivity;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;

public class LoginFragment extends Fragment implements View.OnClickListener{

    private View mView;
    TextView txt_DangKy, txt_QuenMK;
    Button btn_DangNhap;
    EditText edt_TK, edt_MK;
    ImageButton ibtn_exit;
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
        mView = inflater.inflate(R.layout.fragment_login, container, false);

        AnhXa();

        return mView;
    }

    private void AnhXa() {
        txt_DangKy = mView.findViewById(R.id.txtDangky);
        txt_QuenMK = mView.findViewById(R.id.txtQuenmk);
        btn_DangNhap = mView.findViewById(R.id.btnDangnhap);
        edt_TK = mView.findViewById(R.id.edtTaikhoan);
        edt_MK = mView.findViewById(R.id.edtMatkhau);

        taiKhoanDAO = new TaiKhoanDAO(getActivity());
        btn_DangNhap.setOnClickListener(this);
        txt_QuenMK.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangnhap:
                setBtn_Login();
                ;break;

            case R.id.txtQuenmk:
                setTxt_QuenMK();
                ;break;
        }
    }

    private void setBtn_Login(){
        String sTentaikhoan = edt_TK.getText().toString();
        String sMatkhau = edt_MK.getText().toString();
        TaiKhoanDTO kiemtra = taiKhoanDAO.KiemTraDangNhap(sTentaikhoan, sMatkhau);
        mUsername = sTentaikhoan;

        if (kiemtra != null){
            Login.taiKhoanDTO = kiemtra;
            Toast.makeText(getActivity(), "Đăng Nhập Thành Công !", Toast.LENGTH_LONG).show();
            Intent iTrangchu = new Intent(getActivity(), HomeActivity.class);

            startActivity(iTrangchu);
        } else {
            Toast.makeText(getActivity(), "Đăng Nhập Thất Bại !", Toast.LENGTH_LONG).show();
        }
    }

    public String getUsername() {
        return mUsername;
    }

    private void setTxt_QuenMK(){
        Intent iQuenmk = new Intent(getActivity(), ForgotPassword.class);
        startActivity(iQuenmk);
    }

}