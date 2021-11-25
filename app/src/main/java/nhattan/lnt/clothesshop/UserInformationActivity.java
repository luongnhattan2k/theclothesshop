package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class UserInformationActivity extends AppCompatActivity {

    private EditText edt_Taikhoan, edt_Sdt, edt_Email, edt_Diachi;
    private Button btn_Capnhat;
    private ImageButton ibtn_Exit;
    private boolean isEnabled;
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
                isEnabled = !isEnabled;
                enableControl();
                int Sdt = Integer.parseInt(edt_Sdt.getText().toString().trim());
                String Email = edt_Email.getText().toString();
                String DiaChi = edt_Diachi.getText().toString();
                if (isEnabled){
                    btn_Capnhat.setText("Lưu");
                }
                else{
                    btn_Capnhat.setText("Cập nhật");
                    HomeFragment.database.CapNhatTaiKhoan(Login.taiKhoanDTO.getMATK(), Sdt, Email, DiaChi);
                    Toast.makeText(UserInformationActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });

    }

    private void enableControl() {
        edt_Sdt.setEnabled(isEnabled);
        edt_Email.setEnabled(isEnabled);
        edt_Diachi.setEnabled(isEnabled);
    }

    private void GetData() {
        if (Login.taiKhoanDTO.getMATK() == -1)
        {
            Toast.makeText(this, "Bạn chưa đăng nhập !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        } else {
            //get data
            int id = Login.taiKhoanDTO.getMATK();
            TaiKhoanDTO taiKhoanDTO = HomeFragment.database.Load(id);
            String tentaikhoan = taiKhoanDTO.getTENTK();
            int sdt = taiKhoanDTO.getSDT();
            String email = taiKhoanDTO.getEMAIL();
            String diachi = taiKhoanDTO.getDIACHI();
            enableControl();

            edt_Taikhoan.setText(tentaikhoan);
            edt_Sdt.setText(String.valueOf(sdt));
            edt_Email.setText(email);
            edt_Diachi.setText(diachi);
            edt_Taikhoan.setEnabled(false);
        }
    }
}