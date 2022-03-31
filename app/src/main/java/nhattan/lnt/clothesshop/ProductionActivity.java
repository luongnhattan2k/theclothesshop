package nhattan.lnt.clothesshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import nhattan.lnt.clothesshop.ADAPTER.SearchAdapter;
import nhattan.lnt.clothesshop.DAO.CategoryDAO;
import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DTO.CategoryDTO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class ProductionActivity extends AppCompatActivity {

    SanPhamDTO sanPhamDTO;
    TextView productName, productPrice, productContent, productQuantity;
    ImageView productImage, img_Them, img_Tru;
    ImageButton ibtn_Exit;
    Button btn_Themgiohang;
    int id, idtk;
    Spinner spnCategory;
    CategoryDAO categoryDAO;
    int SLM = 0;


    @Override
    protected void onStart() {
        if(idtk==2){
            sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
        }else
        {
            sanPhamDTO = SearchAdapter.sanPhamDTOList.get(idtk);
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",1);
        idtk = intent.getIntExtra("idtk",2);
        Anhxa();

        categoryDAO = new CategoryDAO(this, R.layout.item_selected, getListCategort());
        spnCategory.setAdapter(categoryDAO);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ProductionActivity.this, "Bạn chọn size " + categoryDAO.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GetData();
        SuKien();
    }



    private List<CategoryDTO> getListCategort() {
        List<CategoryDTO> list = new ArrayList<>();

        list.add(new CategoryDTO("S", "1"));
        list.add(new CategoryDTO("M", "2"));
        list.add(new CategoryDTO("L", "3"));
        list.add(new CategoryDTO("XL", "4"));
        list.add(new CategoryDTO("2XL", "5"));

        return list;
    }

    private void GetData() {
        if(idtk==2){
            sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
        }else
        {
            sanPhamDTO = SearchAdapter.sanPhamDTOList.get(idtk);
        }

        //get data
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
        productName = findViewById(R.id.product_name_ct);
        productContent = findViewById(R.id.product_content);
        productPrice = findViewById(R.id.product_price_ct);
        productImage = findViewById(R.id.product_image_ct);
        productQuantity = findViewById(R.id.product_quantity);
        spnCategory = findViewById(R.id.spn_category);
        img_Them = findViewById(R.id.imgThemsl);
        img_Tru = findViewById(R.id.imgXoasl);
        btn_Themgiohang = findViewById(R.id.btnThemvaogiohang);
        ibtn_Exit = findViewById(R.id.ibtnExit);

    }

    private void SuKien(){
        img_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SLM = Integer.parseInt(productQuantity.getText().toString());
                if (SLM < sanPhamDTO.getSl_SP()){
                    SLM += 1;
                    productQuantity.setText(String.valueOf(SLM));
                }
            }
        });

        img_Tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SLM = Integer.parseInt(productQuantity.getText().toString());
                if (SLM >= 2){
                    SLM -= 1;
                    productQuantity.setText(String.valueOf(SLM));
                } else {
                    Toast.makeText(ProductionActivity.this, "Không thể giảm số lượng được nữa !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Login.taiKhoanDTO.getMATK() == -1)
                {
                    Toast.makeText(ProductionActivity.this, "Bạn hãy đăng nhập để có thể mua hàng !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProductionActivity.this, Login.class));
                }else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) productImage.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();

                    int SL = Integer.parseInt(productQuantity.getText().toString());

                    if(idtk==2){
                        sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
                    }else
                    {
                        sanPhamDTO = SearchAdapter.sanPhamDTOList.get(idtk);
                    }
                    HomeFragment.database.SPGH(
                            Login.taiKhoanDTO.getMATK(),
                            hinhAnh,
                            sanPhamDTO.getMaSP(),
                            sanPhamDTO.getTenSP(),
                            SL,
                            SL * sanPhamDTO.getGiaSP()
                    );
                    Toast.makeText(ProductionActivity.this," Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                    Intent iGiohang = new Intent(ProductionActivity.this, HomeActivity.class);
                    iGiohang.putExtra("DenGioHang", R.id.nav_mycart);
                    startActivity(iGiohang);
                }
            }
        });

        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}