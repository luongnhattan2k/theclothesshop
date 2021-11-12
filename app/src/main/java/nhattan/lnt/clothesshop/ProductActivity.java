package nhattan.lnt.clothesshop;

import static nhattan.lnt.clothesshop.HomeActivity.taiKhoanDTO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class ProductActivity extends AppCompatActivity {

    SanPhamDTO sanPhamDTO;
    TextView productName, productPrice, productContent;
    EditText productQuantity;
    ImageView productImage;
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
                int SL = Integer.parseInt(productQuantity.getText().toString());
                sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
                HomeFragment.database.SPGH(
                        taiKhoanDTO.getMATK(),
                        sanPhamDTO.getMaSP(),
                        sanPhamDTO.getTenSP(),
                        SL,
                        SL * sanPhamDTO.getGiaSP()
                );
                Toast.makeText(ProductActivity.this," Đã thêm vào giỏ hàng" + SL,Toast.LENGTH_LONG).show();
                startActivity(new Intent(ProductActivity.this, MyCartActivity.class));
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


        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExit();
            }
        });

    }


    private void getExit() {
        Intent iExit = new Intent(ProductActivity.this, HomeActivity.class);
        startActivity(iExit);
    }
}