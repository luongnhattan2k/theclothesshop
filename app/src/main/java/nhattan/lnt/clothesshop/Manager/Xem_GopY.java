package nhattan.lnt.clothesshop.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import nhattan.lnt.clothesshop.DTO.GopYDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.R;

public class Xem_GopY extends AppCompatActivity {
    EditText edt_Tentk, edt_Sdt, edt_Noidung, edt_Idgy;
    Button btn_Thoat;
    ImageButton ibtn_Exit;
    Database database;
    private boolean isEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_gop_y);

        AnhXa();
        Intent intent = getIntent();
        int IDGY = intent.getIntExtra("IDGY", 1);

        if (String.valueOf(IDGY) == null){
            return;
        }
        GopYDTO gopYDTO = database.TTGOPY(IDGY);

        edt_Tentk.setText(gopYDTO.getTENTK());
        edt_Sdt.setText(String.valueOf(gopYDTO.getSDT()));
        edt_Noidung.setText(gopYDTO.getNOIDUNG());

    }

    private void enableControl(){
        edt_Tentk.setEnabled(isEnabled);
        edt_Sdt.setEnabled(isEnabled);
        edt_Noidung.setEnabled(isEnabled);
    }

    private void AnhXa() {
        database = new Database(this);
        edt_Tentk = findViewById(R.id.edtTaikhoangopy_ql);
        edt_Sdt = findViewById(R.id.edtSdtgopy_ql);
        edt_Noidung = findViewById(R.id.edtNoidunggopy_ql);
        enableControl();

        btn_Thoat = findViewById(R.id.btnThoatgopy_ql);
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ibtn_Exit = findViewById(R.id.ibtnExit_qlgopy);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}