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
import java.text.SimpleDateFormat;

import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.R;

public class Them_TinMoi extends AppCompatActivity {

    EditText edtTieudetintuc_add, edtNgaydangtintuc_add, edtNoidungtintuc_add;
    ImageView img_HinhAnhTinTuc_add;
    ImageButton ibtn_Exitthemtin_add;
    Button btnThemtintuc;
    private boolean isEnabled;
    Database database;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    String Ngaydang, Tieude, Noidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tin_moi);

        AnhXa();
    }

    private void AnhXa() {
        database = new Database(this);
        img_HinhAnhTinTuc_add = findViewById(R.id.img_Hinhanhtintuc_Add);
        edtTieudetintuc_add = findViewById(R.id.edtTieudetintuc_Add);
        edtNgaydangtintuc_add = findViewById(R.id.edtNgaydangtintuc_Add);
        edtNoidungtintuc_add = findViewById(R.id.edtNoidungtintuc_Add);
        ibtn_Exitthemtin_add = findViewById(R.id.ibtnExitthemtintuc);
        btnThemtintuc = findViewById(R.id.btnThemtintuc);

        registerForContextMenu(img_HinhAnhTinTuc_add);

        Thread t = new Thread() {
            @Override
            public void run () {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd/MM/yyyy");
                                Ngaydang = sdf_ngay.format(date);
                                edtNgaydangtintuc_add.setText(Ngaydang);
                            }
                        });
                    }
                } catch (InterruptedException e ) { }
            }
        };
        t.start();

        img_HinhAnhTinTuc_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_HinhAnhTinTuc_add.showContextMenu();
            }
        });

        ibtn_Exitthemtin_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnThemtintuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img_HinhAnhTinTuc_add.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] hinhAnh = byteArray.toByteArray();

                Tieude = edtTieudetintuc_add.getText().toString().trim();
                Noidung = edtNoidungtintuc_add.getText().toString().trim();
                if (edtTieudetintuc_add.getText().length() != 0 && edtNoidungtintuc_add.getText().length() != 0) {

                    HomeFragment.database.ThemTinTuc(
                            hinhAnh,
                            Tieude,
                            Noidung,
                            Ngaydang
                    );
                    Toast.makeText(Them_TinMoi.this, "Thêm Tin Mới Thành Công !", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(Them_TinMoi.this, " Thất Bại !", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Them_TinMoi.this,"Không cho phép mở Camera", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(Them_TinMoi.this," Không cho phép mở folder", Toast.LENGTH_LONG).show();
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
            img_HinhAnhTinTuc_add.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_HinhAnhTinTuc_add.setImageBitmap(bitmap);
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