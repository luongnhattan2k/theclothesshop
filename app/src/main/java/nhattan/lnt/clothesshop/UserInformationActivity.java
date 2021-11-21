package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class UserInformationActivity extends AppCompatActivity {

    EditText edt_Taikhoan, edt_Sdt, edt_Email, edt_Diachi;
    Button btn_Capnhat;
    ImageButton ibtn_Exit;
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
        edt_Sdt = findViewById(R.id.edtSdt);
        edt_Email = findViewById(R.id.edtEmail);
        edt_Diachi = findViewById(R.id.edtDiachi);

        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_Capnhat = findViewById(R.id.btnCapnhat);
        btn_Capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.database.UPDATE(
                        edt_Taikhoan.getText().toString().trim(),
                        Integer.parseInt(edt_Sdt.getText().toString().trim()),
                        edt_Email.getText().toString().trim(),
                        edt_Diachi.getText().toString().trim(),
                        Login.taiKhoanDTO.getMATK()
                );
                GetData();
                Toast.makeText(UserInformationActivity.this,"Cập nhật thành công !", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserInformationActivity.this, HomeActivity.class));
            }
        });

    }

    private void GetData() {
        //get data
        int id = Login.taiKhoanDTO.getMATK();
        TaiKhoanDTO taiKhoanDTO = HomeFragment.database.Load(id);
        String tentaikhoan = taiKhoanDTO.getTENTK();
        int sdt = taiKhoanDTO.getSDT();
        String email = taiKhoanDTO.getEMAIL();
        String diachi = taiKhoanDTO.getDIACHI();

        edt_Taikhoan.setText(tentaikhoan);
        edt_Sdt.setText(String.valueOf(sdt));
        edt_Email.setText(email);
        edt_Diachi.setText(diachi);

        if (Login.taiKhoanDTO.getMATK() == -1)
        {
            Toast.makeText(this, "Bạn chưa đăng nhập !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        }
    }
}