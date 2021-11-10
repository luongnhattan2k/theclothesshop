package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;

public class ProductActivity extends AppCompatActivity {

    TextView productName, productPrice, productContent, productQuantity;
    ImageView productImage;
    ImageButton ibtn_Exit;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",1123);
        Anhxa();
        GetData();
    }

    private void GetData() {
        //get data
        SanPhamDTO sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
        String ten = sanPhamDTO.getTenSP();
        String mota = sanPhamDTO.getMotaSP();
        String gia = "Giá: " + sanPhamDTO.getGiaSP();
        String sl = "Sl: " + sanPhamDTO.getSl_SP();


        productName.setText(ten);
        productContent.setText(mota);
        productPrice.setText(gia);
        productQuantity.setText(sl);
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

        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExit();
            }
        });

    }

    private void getExit()
    {
        Intent iExit = new Intent(ProductActivity.this, HomeActivity.class);
        startActivity(iExit);
    }
}