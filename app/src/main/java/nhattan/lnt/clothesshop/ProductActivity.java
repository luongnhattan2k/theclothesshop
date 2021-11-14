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

public class ProductActivity extends AppCompatActivity {

    SanPhamDTO sanPhamDTO;
    TextView productName, productPrice, productContent;
    EditText productQuantity;
    ImageView productImage, img_Mycart, img_Home;
    ImageButton ibtn_Exit;
    Button btn_Themgiohang,btn_Thanhtoan;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        Anhxa();

        btn_Themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Toast.makeText(ProductActivity.this," Đã thêm vào giỏ hàng" + SL,Toast.LENGTH_LONG).show();
                Intent iGiohang = new Intent(ProductActivity.this, HomeActivity.class);
                iGiohang.putExtra("Trang", R.id.nav_profile);

                startActivity(iGiohang);
            }
        });

        GetData();
    }

    private void GetData() {
        //get data
        SanPhamDTO sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
        String ten = sanPhamDTO.getTenSP();
        String mota = sanPhamDTO.getMotaSP();
        String gia = "Giá: " + sanPhamDTO.getGiaSP();


        productName.setText(ten);
        productContent.setText(mota);
        productPrice.setText(gia);
        byte[] hinhAnh = sanPhamDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        productImage.setImageBitmap(bitmap);
    }

    private void Anhxa() {
        productName = (TextView) findViewById(R.id.product_name);
        productContent = (TextView) findViewById(R.id.product_content);
        productPrice = (TextView) findViewById(R.id.product_price);
        productImage = (ImageView) findViewById(R.id.product_image);
        productQuantity = (EditText) findViewById(R.id.product_quantity);
        btn_Themgiohang = (Button) findViewById(R.id.btnThemvaogiohang);
        img_Home = (ImageView) findViewById(R.id.imgTrangChu);
        img_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHome();
            }
        });


        img_Mycart = (ImageView) findViewById(R.id.imgGoGioHang);
        img_Mycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyCart();
            }
        });

        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExit();
            }
        });

    }

    private void getHome() {
        startActivity(new Intent(ProductActivity.this, HomeActivity.class));
    }

    private void getMyCart() {
        startActivity(new Intent(ProductActivity.this, HomeActivity.class));
    }


    private void getExit() {
        startActivity(new Intent(ProductActivity.this, HomeActivity.class));
    }
}