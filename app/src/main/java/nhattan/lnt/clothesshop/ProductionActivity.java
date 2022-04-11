package nhattan.lnt.clothesshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

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
    CategoryDTO categoryDTO;
    TextView productName, productPrice, productContent;
    ImageView productImage;
    ImageButton ibtn_Exit;
    Button btn_Themgiohang;
    int id, idtk;
    Spinner spnCategory;
    CategoryDAO categoryDAO;
    int SLM = 0;
    String size = "S";


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

        GetData();
        SuKien();
    }

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
        spnCategory = findViewById(R.id.spn_category);
        btn_Themgiohang = findViewById(R.id.btnThemvaogiohang);
        ibtn_Exit = findViewById(R.id.ibtnExit);

    }

    private void SuKien(){
        btn_Themgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductionActivity.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet, findViewById(R.id.bottomSheetContainer)
                );

                TextView txt_Tensanpham_sheet = bottomSheetView.findViewById(R.id.txt_Tensanpham_sheet);
                TextView txt_Giasanpham_sheet = bottomSheetView.findViewById(R.id.txt_Giasanpham_sheet);
                TextView txt_Soluongsanpham_sheet = bottomSheetView.findViewById(R.id.txt_Soluongsanpham_sheet);
                ImageView img_Xoasl_sheet = bottomSheetView.findViewById(R.id.img_Xoasl_sheet);
                ImageView img_Themsl_sheet = bottomSheetView.findViewById(R.id.img_Themsl_sheet);
                ImageView img_sanpham_sheet = bottomSheetView.findViewById(R.id.img_sanpham_sheet);
                Spinner spn_category_sheet = bottomSheetView.findViewById(R.id.spn_category_sheet);
                Button btn_Themvaogiohang_sheet = bottomSheetView.findViewById(R.id.btn_Themvaogiohang_sheet);

                categoryDAO = new CategoryDAO(ProductionActivity.this, R.layout.item_selected, getListCategort());
                spn_category_sheet.setAdapter(categoryDAO);

                if(idtk==2){
                    sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
                }else
                {
                    sanPhamDTO = SearchAdapter.sanPhamDTOList.get(idtk);
                }

                //get data
                String ten = sanPhamDTO.getTenSP();
                String gia = "" + sanPhamDTO.getGiaSP();

                txt_Tensanpham_sheet.setText(ten);
                txt_Giasanpham_sheet.setText(gia + " VNĐ");

                byte[] hinhAnh = sanPhamDTO.getImageSP();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
                img_sanpham_sheet.setImageBitmap(bitmap);

                spn_category_sheet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(ProductionActivity.this, "Bạn chọn size " + categoryDAO.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                        size = categoryDAO.getItem(position).getName();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                img_Themsl_sheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SLM = Integer.parseInt(txt_Soluongsanpham_sheet.getText().toString());
                        if (SLM < sanPhamDTO.getSl_SP()){
                            SLM += 1;
                            txt_Soluongsanpham_sheet.setText(String.valueOf(SLM));
                        }
                    }
                });

                img_Xoasl_sheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SLM = Integer.parseInt(txt_Soluongsanpham_sheet.getText().toString());
                        if (SLM >= 2){
                            SLM -= 1;
                            txt_Soluongsanpham_sheet.setText(String.valueOf(SLM));
                        } else {
                            Toast.makeText(ProductionActivity.this, "Không thể giảm số lượng được nữa !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_Themvaogiohang_sheet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Login.taiKhoanDTO.getMATK() == -1)
                        {
                            Toast.makeText(ProductionActivity.this, "Bạn hãy đăng nhập để có thể mua hàng !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ProductionActivity.this, Login.class));
                        } else {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) img_sanpham_sheet.getDrawable();
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                            byte[] hinhAnh_sheet = byteArray.toByteArray();

                            int SL = Integer.parseInt(txt_Soluongsanpham_sheet.getText().toString());

                            if(idtk==2){
                                sanPhamDTO = SanPhamDAO.sanPhamDTOList.get(id);
                            } else {
                                sanPhamDTO = SearchAdapter.sanPhamDTOList.get(idtk);
                            }

                            HomeFragment.database.SPGH(
                                    Login.taiKhoanDTO.getMATK(),
                                    hinhAnh_sheet,
                                    sanPhamDTO.getMaSP(),
                                    sanPhamDTO.getTenSP(),
                                    SL,
                                    sanPhamDTO.getGiaSP(),
                                    size
                            );

                            Toast.makeText(ProductionActivity.this," Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                            Intent iGiohang = new Intent(ProductionActivity.this, HomeActivity.class);
                            iGiohang.putExtra("DenGioHang", R.id.nav_mycart);
                            startActivity(iGiohang);
                        }
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
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