package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;

public class UserInformationActivity extends AppCompatActivity {

    EditText edt_Taikhoan, edt_Matkhau, edt_Sdt, edt_Email, edt_Diachi;
    Button btn_Capnhat;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        Anhxa();
//        Intent intent = getIntent();
//        id = intent.getIntExtra("id",1123);
//        Toast.makeText(this,"id cua ban la" + id, Toast.LENGTH_LONG).show();

        GetData();
    }

    private void Anhxa() {
        edt_Taikhoan = findViewById(R.id.edtTaikhoan);
        edt_Matkhau = findViewById(R.id.edtMatkhau);
        edt_Sdt = findViewById(R.id.edtSdt);
        edt_Email = findViewById(R.id.edtEmail);
        edt_Diachi = findViewById(R.id.edtDiachi);

        btn_Capnhat = findViewById(R.id.btnCapnhat);


    }

    private void GetData() {
        //get data
        String tentaikhoan = HomeActivity.taiKhoanDTO.getTENTK();
        String matkhau = HomeActivity.taiKhoanDTO.getMATKHAU();
        int sdt = HomeActivity.taiKhoanDTO.getSDT();
        String email = HomeActivity.taiKhoanDTO.getEMAIL();
        String diachi = HomeActivity.taiKhoanDTO.getDIACHI();

        edt_Taikhoan.setText(tentaikhoan);
        edt_Matkhau.setText(matkhau);
        edt_Sdt.setText(sdt);
        edt_Email.setText(email);
        edt_Diachi.setText(diachi);

    }
}