package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.FragmentApp.MyCartFragment;

public class ProductionActivity extends AppCompatActivity {

    SanPhamDTO sanPhamDTO;
    TextView productName, productPrice, productContent, productQuantity;
    ImageView productImage;
    ImageButton ibtn_Exit;
    Button btn_Themgiohang;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        Anhxa();

        btn_Themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Login.taiKhoanDTO.getMATK() == -1)
                {
                    Toast.makeText(ProductionActivity.this, "Bạn hãy đăng nhập để có thể mua hàng !", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ProductionActivity.this, Login.class));
                }else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) productImage.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();

                    int SL = Integer.parseInt(productQuantity.getText().toString());
                    sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
                    HomeFragment.database.SPGH(
                            Login.taiKhoanDTO.getMATK(),
                            hinhAnh,
                            sanPhamDTO.getMaSP(),
                            sanPhamDTO.getTenSP(),
                            SL,
                            SL * sanPhamDTO.getGiaSP()
                    );
                    Toast.makeText(ProductionActivity.this," Đã thêm vào giỏ hàng" + " " + SL,Toast.LENGTH_LONG).show();
                    Intent iGiohang = new Intent(ProductionActivity.this, HomeActivity.class);
                    iGiohang.putExtra("ThemGioHang", R.id.nav_mycart);

                    startActivity(iGiohang);
                }
            }
        });

        GetData();
    }

    private void GetData() {
        //get data
        SanPhamDTO sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
        String ten = sanPhamDTO.getTenSP();
        String mota = sanPhamDTO.getMotaSP();
        String gia = "" + sanPhamDTO.getGiaSP();


        productName.setText(ten);
        productContent.setText(mota);
        productPrice.setText(gia + " VNĐ");
        byte[] hinhAnh = sanPhamDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        productImage.setImageBitmap(bitmap);
    }

    private void Anhxa() {
        productName = (TextView) findViewById(R.id.product_name);
        productContent = (TextView) findViewById(R.id.product_content);
        productPrice = (TextView) findViewById(R.id.product_price);
        productImage = (ImageView) findViewById(R.id.product_image);
        productQuantity = (TextView) findViewById(R.id.product_quantity);
        btn_Themgiohang = (Button) findViewById(R.id.btnThemvaogiohang);


        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExit();
            }
        });

    }


    private void getExit() {
        startActivity(new Intent(ProductionActivity.this, HomeActivity.class));
    }
}