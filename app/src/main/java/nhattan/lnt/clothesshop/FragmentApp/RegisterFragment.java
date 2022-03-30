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
import android.widget.Toast;

import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;


public class RegisterFragment extends Fragment implements View.OnClickListener{

    private  View view;
    EditText edt_TaiKhoan, edt_MatKhau, edt_SDT, edt_Email, edt_Nhaplaimatkhau;
    ImageButton btn_exit;
    Button btn_DangKy;
    TaiKhoanDAO taiKhoanDAO;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register, container, false);

        AnhXa();

        return view;
    }

    private void AnhXa() {
        edt_TaiKhoan = view.findViewById(R.id.edtTaikhoan_dk);
        edt_MatKhau = view.findViewById(R.id.edtMatkhau_dk);
        edt_Nhaplaimatkhau = view.findViewById(R.id.edtNhaplaimatkhau_dk);
        edt_SDT = view.findViewById(R.id.edtSdt_dk);
        edt_Email = view.findViewById(R.id.edtEmail_dk);
        btn_DangKy = view.findViewById(R.id.btnDangky);

        btn_DangKy.setOnClickListener(this);

        taiKhoanDAO = new TaiKhoanDAO(getActivity());
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnDangky:
                TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
                String sTaiKhoan = edt_TaiKhoan.getText().toString();
                String sMatKhau = edt_MatKhau.getText().toString();
                String sNhapLaiMatKhau = edt_Nhaplaimatkhau.getText().toString();
                String sSDT = edt_SDT.getText().toString();
                String sEmail = edt_Email.getText().toString();

                if (sTaiKhoan == null || sTaiKhoan.isEmpty()){
                    Toast.makeText(getActivity(), "Vui Lòng Nhập Tài Khoản !", Toast.LENGTH_SHORT).show();
                } else if (sMatKhau == null || sMatKhau.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui Lòng Nhập Mật Khẩu !", Toast.LENGTH_SHORT).show();
                } else if (sEmail == null || sEmail.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui Lòng Nhập email !", Toast.LENGTH_SHORT).show();
                } else if (!sMatKhau.equals(sNhapLaiMatKhau)){
                    Toast.makeText(getActivity(), "Mật khẩu không trùng khớp !", Toast.LENGTH_SHORT).show();
                } else if (sSDT.length() < 9 || sSDT.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng kiểm tra lại số điện thoại !", Toast.LENGTH_SHORT).show();
                } else if (sTaiKhoan == taiKhoanDTO.getTENTK()) {
                    Toast.makeText(getActivity(), "Tài khoản đã được đăng ký !", Toast.LENGTH_SHORT).show();
                } else {
                    taiKhoanDTO.setTENTK(sTaiKhoan);
                    taiKhoanDTO.setMATKHAU(sMatKhau);
                    taiKhoanDTO.setSDT(Integer.parseInt(sSDT));
                    taiKhoanDTO.setEMAIL(sEmail);

                    long kiemtra = taiKhoanDAO.ThemTaiKhoan(taiKhoanDTO);
                    if (kiemtra != 0){
                        Toast.makeText(getActivity(), "Đăng Ký Thành Công !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Đăng Ký Thất Bại !", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}