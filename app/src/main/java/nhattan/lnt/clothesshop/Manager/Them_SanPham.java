package nhattan.lnt.clothesshop.Manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.R;

public class Them_SanPham extends AppCompatActivity {
    EditText edtTensanpham_add, edtGia_add, edtSoluong_add, edtMota_add, edtDanhmuc_add;
    ImageView img_HinhAnh_add;
    ImageButton ibtn_Exit_add;
    Button btnThemSP;
    private boolean isEnabled;
    Database database;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        AnhXa();

    }


    private void AnhXa() {
        database = new Database(this);
        img_HinhAnh_add = findViewById(R.id.img_Hinhanh_Add);
        edtTensanpham_add = findViewById(R.id.edtTensanpham_Add);
        edtGia_add = findViewById(R.id.edtGia_Add);
        edtSoluong_add = findViewById(R.id.edtSL_Add);
        edtMota_add = findViewById(R.id.edtMota_Add);
        edtDanhmuc_add = findViewById(R.id.edtDanhmuc_Add);
        ibtn_Exit_add = findViewById(R.id.ibtnExitthemsp);
        btnThemSP = findViewById(R.id.btnThemSP);

        registerForContextMenu(img_HinhAnh_add);
        img_HinhAnh_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_HinhAnh_add.showContextMenu();
            }
        });

        ibtn_Exit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img_HinhAnh_add.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] hinhAnh = byteArray.toByteArray();

                String TenSanPham = edtTensanpham_add.getText().toString().trim();
                int Gia = Integer.parseInt(edtGia_add.getText().toString().trim());
                int SoLuong = Integer.parseInt(edtSoluong_add.getText().toString().trim());
                String MoTa = edtMota_add.getText().toString().trim();
                int DanhMuc = Integer.parseInt(edtDanhmuc_add.getText().toString().trim());
                int SpNew = 1;

                if (edtTensanpham_add.getText().length() != 0 && edtGia_add.getText().length() != 0 && edtSoluong_add.getText().length() != 0
                        && edtMota_add.getText().length() != 0 && edtDanhmuc_add.getText().length() !=0) {
//                    database.ThemSanPham(hinhAnh, TenSanPham, Gia, SoLuong, MoTa, DanhMuc, SpNew);

                    HomeFragment.database.ThemSanPham(
                            hinhAnh,
                            TenSanPham,
                            Gia,
                            SoLuong,
                            MoTa,
                            DanhMuc,
                            SpNew
                    );

                    Toast.makeText(Them_SanPham.this, "Thêm Sản Phẩm Thành Công !", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(Them_SanPham.this, "Thêm Sản Phẩm Thất Bại !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CODE_CAMERA);
                }else
                {
                    Toast.makeText(Them_SanPham.this,"Không cho phép mở Camera", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CODE_FOLDER:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,REQUEST_CODE_FOLDER);
                }else
                {
                    Toast.makeText(Them_SanPham.this," Không cho phép mở folder", Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_HinhAnh_add.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_HinhAnh_add.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_capnhatsp, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iCamera:
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(iCamera,REQUEST_CODE_CAMERA);
                return true;
            case R.id.iFolder:
                Intent iFolder = new Intent(Intent.ACTION_PICK);
                iFolder.setType("image/*");
                startActivityForResult(iFolder,REQUEST_CODE_FOLDER);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}