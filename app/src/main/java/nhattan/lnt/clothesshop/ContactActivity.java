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

public class ContactActivity extends AppCompatActivity {

    ImageButton ibtn_Exit;
    EditText edt_Tentaikhoan, edt_Sdt, edt_NoiDunggopy;
    Button btn_Gopy, btn_Thoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Anhxa();

        GetData();
    }

    private void GetData() {
        String tentaikhoan = Login.taiKhoanDTO.getTENTK();
        int sdt = Login.taiKhoanDTO.getSDT();

        edt_Tentaikhoan.setText(tentaikhoan);
        edt_Sdt.setText(String.valueOf(sdt));
    }

    private void Anhxa() {
        edt_Tentaikhoan = findViewById(R.id.edtTaikhoangopy);
        edt_Sdt = findViewById(R.id.edtSdtgopy);
        edt_NoiDunggopy = findViewById(R.id.edtNoidunggopy);
        btn_Gopy = findViewById(R.id.btnGopy);
        btn_Gopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment.database.INSERT_GOPY(
                        edt_Tentaikhoan.getText().toString().trim(),
                        Integer.parseInt(edt_Sdt.getText().toString().trim()),
                        edt_NoiDunggopy.getText().toString().trim()
                );
                Toast.makeText(ContactActivity.this, "Gửi góp ý thành công !", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ContactActivity.this, HomeActivity.class));
            }
        });

        btn_Thoat = findViewById(R.id.btnThoatgopy);
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactActivity.this, HomeActivity.class));
            }
        });

        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactActivity.this, HomeActivity.class));
            }
        });
    }

}