package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageButton ibtn_Exit;
    EditText edt_Matkhauhientai, edt_Matkhaumoi, edt_Nhaplaimatkhaumoi;
    Button btn_Doimatkhau, btn_Huy;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Anhxa();

        GetData();
    }

    private void Anhxa() {
        edt_Matkhauhientai = findViewById(R.id.edtMatkhauhientai);
        edt_Matkhaumoi = findViewById(R.id.edtMatkhaumoi);
        edt_Nhaplaimatkhaumoi = findViewById(R.id.edtNhaplaimatkhaumoi);
        btn_Huy = findViewById(R.id.btnHuy);
        btn_Doimatkhau = findViewById(R.id.btnDoimatkhau);
        btn_Doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_Matkhauhientai.getText().length() !=0 && edt_Matkhaumoi.getText().length() != 0 && edt_Nhaplaimatkhaumoi.getText().length() != 0){
                    if(HomeFragment.database.isMatKhau(Login.taiKhoanDTO.getMATK(), edt_Matkhauhientai.getText().toString())){
                        if(edt_Matkhaumoi.getText().toString().equals(edt_Nhaplaimatkhaumoi.getText().toString())){
                            HomeFragment.database.CapNhatMatKhau(Login.taiKhoanDTO.getMATK(), edt_Matkhaumoi.getText().toString());
                            Toast.makeText(ChangePasswordActivity.this,"Đổi mật khẩu thành công !",Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới không trùng khớp !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Nhập mật khẩu cũ không đúng !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Nhập dữ liệu chưa đủ !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void messenge (String messenge){
        Toast.makeText(ChangePasswordActivity.this, messenge, Toast.LENGTH_SHORT).show();
    }

    private void GetData() {
        if (Login.taiKhoanDTO.getMATK() == -1)
        {
            Toast.makeText(this, "Bạn chưa đăng nhập !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
        }
    }


}