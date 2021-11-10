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


public class RegisterFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener{

    private  View mView;
    EditText edt_TaiKhoan, edt_MatKhau, edt_NgaySinh, edt_SDT, edt_Email, edt_NhapLaiMatKhau;
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

        mView = inflater.inflate(R.layout.fragment_register, container, false);

        AnhXa();

        return mView;
    }

    private void AnhXa() {
        edt_TaiKhoan = mView.findViewById(R.id.edtTaikhoan);
        edt_MatKhau = mView.findViewById(R.id.edtMatkhau);
        edt_NgaySinh = mView.findViewById(R.id.edtNgaySinh);
        edt_SDT = mView.findViewById(R.id.edtSDT);
        edt_Email = mView.findViewById(R.id.edtEmail);
        btn_DangKy = mView.findViewById(R.id.btnDangky);

        btn_DangKy.setOnClickListener(this);
        edt_NgaySinh.setOnFocusChangeListener(this);
        taiKhoanDAO = new TaiKhoanDAO(getActivity());
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnDangky:
                String sTaiKhoan = edt_TaiKhoan.getText().toString();
                String sMatKhau = edt_MatKhau.getText().toString();
                String sNgaySinh = edt_NgaySinh.getText().toString();
                int sSDT = Integer.parseInt(edt_SDT.getText().toString());
                String sEmail = edt_Email.getText().toString();

                if (sTaiKhoan == null || sTaiKhoan.equals("")){
                    Toast.makeText(getActivity(), "Vui Lòng Nhập Tài Khoản !", Toast.LENGTH_LONG).show();
                } else if (sMatKhau == null || sMatKhau.equals("")) {
                    Toast.makeText(getActivity(), "Vui Lòng Nhập Mật Khẩu !", Toast.LENGTH_LONG).show();
                } else if (sEmail == null || sEmail.equals("")) {
                    Toast.makeText(getActivity(), "Vui Lòng Nhập email !", Toast.LENGTH_LONG).show();
                } else {
                    TaiKhoanDTO taiKhoanDTO;
                    taiKhoanDTO = new TaiKhoanDTO();
                    taiKhoanDTO.setTENTK(sTaiKhoan);
                    taiKhoanDTO.setMATKHAU(sMatKhau);
                    taiKhoanDTO.setNGAYSINH(sNgaySinh);
                    taiKhoanDTO.setSDT(sSDT);
                    taiKhoanDTO.setEMAIL(sEmail);

                    long kiemtra = taiKhoanDAO.ThemTaiKhoan(taiKhoanDTO);
                    if (kiemtra != 0){
                        Toast.makeText(getActivity(), "Đăng Ký Thành Công !", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Đăng Ký Thất Bại !", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        int id = view.getId();
        switch (id){
            case R.id.edtNgaySinh:
                if (hasFocus){
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(), "Ngày Sinh");
                }
                ;break;
        }
    }
}