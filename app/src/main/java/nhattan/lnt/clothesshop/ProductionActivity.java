package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import nhattan.lnt.clothesshop.DAO.CategoryDAO;
import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DTO.CategoryDTO;
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
    Spinner spnCategory;
    CategoryDAO categoryDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        Anhxa();

        categoryDAO = new CategoryDAO(this, R.layout.item_selected, getListCategort());
        spnCategory.setAdapter(categoryDAO);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ProductionActivity.this, "Bạn chọn size " + categoryDAO.getItem(position).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GetData();
    }

    private List<CategoryDTO> getListCategort() {
        List<CategoryDTO> list = new ArrayList<>();

        list.add(new CategoryDTO("S"));
        list.add(new CategoryDTO("M"));
        list.add(new CategoryDTO("L"));
        list.add(new CategoryDTO("XL"));
        list.add(new CategoryDTO("2XL"));

        return list;
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
        productName = findViewById(R.id.product_name);
        productContent = findViewById(R.id.product_content);
        productPrice = findViewById(R.id.product_price);
        productImage = findViewById(R.id.product_image);
        productQuantity = findViewById(R.id.product_quantity);
        spnCategory = findViewById(R.id.spn_category);

        btn_Themgiohang = findViewById(R.id.btnThemvaogiohang);
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