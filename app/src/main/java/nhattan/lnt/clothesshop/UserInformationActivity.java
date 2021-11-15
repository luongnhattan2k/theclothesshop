package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;

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
                Intent iExit = new Intent(UserInformationActivity.this, HomeActivity.class);
                iExit.putExtra("ExitUser", R.id.nav_profile);
                startActivity(iExit);
            }
        });

        btn_Capnhat = findViewById(R.id.btnCapnhat);

    }

    private void GetData() {
        //get data
        String tentaikhoan = Login.taiKhoanDTO.getTENTK();
        int sdt = Login.taiKhoanDTO.getSDT();
        String email = Login.taiKhoanDTO.getEMAIL();
        String diachi = Login.taiKhoanDTO.getDIACHI();

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