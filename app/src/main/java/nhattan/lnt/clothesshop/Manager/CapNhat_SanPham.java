package nhattan.lnt.clothesshop.Manager;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.R;

public class CapNhat_SanPham extends AppCompatActivity {

    EditText edt_Idsp ,edt_Tensanpham, edt_Gia, edt_Soluong, edt_Mota, edt_Danhmuc;
    ImageView img_HinhAnh;
    ImageButton ibtn_Exit;
    Button btn_CapNhatSP, btnHuy_qltt;
    private boolean isEnabled;
    Database database;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_san_pham);

        AnhXa();

        Intent iCapNhat = getIntent();
        int IDSANPHAM = iCapNhat.getIntExtra("IDSANPHAM", 1);

        if (String.valueOf(IDSANPHAM) == null){
            return;
        }

        SanPhamDTO sanPhamDTO = database.TTSANPHAM(IDSANPHAM);

        edt_Idsp.setText(String.valueOf(IDSANPHAM));
        edt_Idsp.setEnabled(false);
        edt_Tensanpham.setText(sanPhamDTO.getTenSP());
        edt_Gia.setText(String.valueOf(sanPhamDTO.getGiaSP()));
        edt_Soluong.setText(String.valueOf(sanPhamDTO.getSl_SP()));
        edt_Mota.setText(sanPhamDTO.getMotaSP());
        edt_Danhmuc.setText(String.valueOf(sanPhamDTO.getID_DANHMUC()));
        byte[] hinhAnh = sanPhamDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        img_HinhAnh.setImageBitmap(bitmap);
    }

    private void enableControl(){
        img_HinhAnh.setEnabled(isEnabled);
        edt_Tensanpham.setEnabled(isEnabled);
        edt_Gia.setEnabled(isEnabled);
        edt_Soluong.setEnabled(isEnabled);
        edt_Mota.setEnabled(isEnabled);
        edt_Danhmuc.setEnabled(isEnabled);
    }


    private void AnhXa() {
        database = new Database(this);
        img_HinhAnh = findViewById(R.id.img_Hinhanh);
        edt_Idsp = findViewById(R.id.edtIDSP);
        edt_Tensanpham = findViewById(R.id.edtTensanpham);
        edt_Gia = findViewById(R.id.edtGia);
        edt_Soluong = findViewById(R.id.edtSL);
        edt_Mota = findViewById(R.id.edtMota);
        edt_Danhmuc = findViewById(R.id.edtDanhmuc);
        ibtn_Exit = findViewById(R.id.ibtnExitcapnhatsp);
        btn_CapNhatSP = findViewById(R.id.btnCapNhatSP);
        enableControl();

        registerForContextMenu(img_HinhAnh);
        img_HinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_HinhAnh.showContextMenu();
            }
        });


        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_CapNhatSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnabled = !isEnabled;
                enableControl();
                if (isEnabled){
                    btn_CapNhatSP.setText("Lưu");
                }
                else{
                    btn_CapNhatSP.setText("Cập nhật");
                    int IDSP = Integer.parseInt(edt_Idsp.getText().toString());
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) img_HinhAnh.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    String TenSanPham = edt_Tensanpham.getText().toString();
                    int Gia = Integer.parseInt(edt_Gia.getText().toString());
                    int SoLuong = Integer.parseInt(edt_Soluong.getText().toString());
                    String MoTa = edt_Mota.getText().toString();
                    int DanhMuc = Integer.parseInt(edt_Danhmuc.getText().toString());

                    if (edt_Tensanpham.getText().length() != 0 && edt_Gia.getText().length() != 0 && edt_Soluong.getText().length() != 0
                            && edt_Mota.getText().length() != 0 && edt_Danhmuc.getText().length() !=0) {
                        database.CapNhatSanPham(IDSP, hinhAnh, TenSanPham, Gia, SoLuong, MoTa, DanhMuc);
                        Toast.makeText(CapNhat_SanPham.this, "Cập Nhật Sản Phẩm Thành Công !", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(CapNhat_SanPham.this, "Cập Nhật Sản Phẩm Thất Bại !", Toast.LENGTH_SHORT).show();
                    }
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
                    Toast.makeText(CapNhat_SanPham.this,"Không cho phép mở Camera", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(CapNhat_SanPham.this," Không cho phép mở folder", Toast.LENGTH_LONG).show();
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
            img_HinhAnh.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_HinhAnh.setImageBitmap(bitmap);
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