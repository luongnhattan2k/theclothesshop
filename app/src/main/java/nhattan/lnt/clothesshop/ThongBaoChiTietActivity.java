package nhattan.lnt.clothesshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import nhattan.lnt.clothesshop.DTO.TinTucDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class ThongBaoChiTietActivity extends AppCompatActivity {

    TinTucDTO tinTucDTO;
    TextView txt_Tieudetintuc, txt_Noidungtintuc, txt_Ngaydangtintuc, txt_Bantinso;
    ImageView img_Hinhanhtintuc;
    ImageButton ibtnExit_tintuc;
    int IDTINTUC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao_chi_tiet);

        Intent intent = getIntent();
        IDTINTUC = intent.getIntExtra("IDTHONGBAOTINTUC",1);
        Log.e("Tin tức thứ ",String.valueOf(IDTINTUC));

        AnhXa();
    }

    private void AnhXa() {
        txt_Tieudetintuc = findViewById(R.id.txt_Tieudetintuc);
        txt_Noidungtintuc = findViewById(R.id.txt_Noidungtintuc);
        txt_Ngaydangtintuc = findViewById(R.id.txt_Ngaydangtintuc);
        txt_Bantinso = findViewById(R.id.txt_Bantinso);
        img_Hinhanhtintuc = findViewById(R.id.img_Hinhanhtintuc);
        ibtnExit_tintuc = findViewById(R.id.ibtnExit_tintuc);

        tinTucDTO = HomeFragment.database.LoadTinTuc(IDTINTUC);
        if (tinTucDTO == null)
            return;

        byte[] hinhAnh = tinTucDTO.getHINHANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        img_Hinhanhtintuc.setImageBitmap(bitmap);
        txt_Bantinso.setText(("Bản tin số: " + tinTucDTO.getIDTINTUC()));
        txt_Tieudetintuc.setText(tinTucDTO.getTIEUDE());
        txt_Noidungtintuc.setText(tinTucDTO.getNOIDUNG());
        txt_Ngaydangtintuc.setText(tinTucDTO.getNGAYDANG());

        ibtnExit_tintuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongBaoChiTietActivity.this, HomeActivity.class);
                intent.putExtra("Tintuc", R.id.nav_Tintuc);
                startActivity(intent);
            }
        });
    }
}