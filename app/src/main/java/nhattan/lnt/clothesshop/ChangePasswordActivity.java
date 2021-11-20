package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageButton ibtn_Exit;
    EditText edt_Matkhauhientai, edt_Matkhaumoi, edt_Nhaplaimatkhaumoi;
    Button btn_Doimatkhau, btn_Huy;

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
                String Matkhau = edt_Matkhauhientai.getText().toString().trim();
                String Matkhaumoi = edt_Matkhaumoi.getText().toString().trim();
                String Nhaplaimatkhau = edt_Nhaplaimatkhaumoi.getText().toString().trim();

                if (Matkhau.isEmpty() || Matkhaumoi.isEmpty() || Nhaplaimatkhau.isEmpty())
                {
                    messenge("Hãy nhập đầy đủ thông tin !!");
//                }else if (Matkhaumoi != Nhaplaimatkhau){
//                    messenge("Mật khẩu mới không trùng khớp !");
                } else {
                    HomeFragment.database.UPDATE_MATKHAU(
                            Matkhaumoi,
                            Login.taiKhoanDTO.getMATK()
                    );
                    messenge("Đổi mật khẩu thành công !");
                    startActivity(new Intent(ChangePasswordActivity.this, Login.class));
                }

            }
        });

        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iExit = new Intent(ChangePasswordActivity.this, HomeActivity.class);
                iExit.putExtra("ExitUser", R.id.nav_profile);
                startActivity(iExit);
            }
        });
    }

    public void messenge (String messenge){
        Toast.makeText(ChangePasswordActivity.this, messenge, Toast.LENGTH_LONG).show();
    }

    private void GetData() {

        if (Login.taiKhoanDTO.getMATK() == -1)
        {
            Toast.makeText(this, "Bạn chưa đăng nhập !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        }
    }


}