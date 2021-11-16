package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageButton ibtn_Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Anhxa();

        GetData();
    }

    private void Anhxa() {
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

    private void GetData() {

        if (Login.taiKhoanDTO.getMATK() == -1)
        {
            Toast.makeText(this, "Bạn chưa đăng nhập !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        }
    }


}