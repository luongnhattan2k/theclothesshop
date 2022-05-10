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

import nhattan.lnt.clothesshop.DTO.TinTucDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.R;

public class CapNhat_TinTuc extends AppCompatActivity {

    TinTucDTO tinTucDTO;
    Database database;
    EditText edt_Matintuc, edt_Tieudetintuc, edt_Ngaydangtintuc, edt_Noidungtintuc;
    ImageView img_Hinhtintuc;
    ImageButton ibtnExit_qltt;
    Button btn_Capnhat_qltt, btn_Huy_qltt;
    private boolean isEnabled;
    int IDTINTUC;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_tin_tuc);

        Intent intent = getIntent();
        IDTINTUC = intent.getIntExtra("IDTINTUC",1);
        AnhXa();

        if (String.valueOf(IDTINTUC) == null){
            return;
        }
        tinTucDTO = database.TTTINTUC(IDTINTUC);

        edt_Matintuc.setText(String.valueOf(IDTINTUC));
        edt_Matintuc.setEnabled(false);
        edt_Tieudetintuc.setText(tinTucDTO.getTIEUDE());
        edt_Ngaydangtintuc.setText(tinTucDTO.getNGAYDANG());
        edt_Noidungtintuc.setText(tinTucDTO.getNOIDUNG());
        byte[] hinhAnh = tinTucDTO.getHINHANH();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
        img_Hinhtintuc.setImageBitmap(bitmap);

    }

    private void enableControl(){
        img_Hinhtintuc.setEnabled(isEnabled);
        edt_Tieudetintuc.setEnabled(isEnabled);
        edt_Ngaydangtintuc.setEnabled(isEnabled);
        edt_Noidungtintuc.setEnabled(isEnabled);

    }

    private void AnhXa() {
        database = new Database(this);
        edt_Matintuc = findViewById(R.id.edt_Matintuc);
        edt_Tieudetintuc = findViewById(R.id.edt_Tieudetintuc);
        edt_Ngaydangtintuc = findViewById(R.id.edt_Ngaydangtintuc);
        edt_Noidungtintuc = findViewById(R.id.edt_Noidungtintuc);
        img_Hinhtintuc = findViewById(R.id.img_Hinhtintuc);
        btn_Capnhat_qltt = findViewById(R.id.btnCapnhat_qltt);
        btn_Huy_qltt = findViewById(R.id.btnHuy_qltt);
        enableControl();

        registerForContextMenu(img_Hinhtintuc);
        img_Hinhtintuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_Hinhtintuc.showContextMenu();
            }
        });

        ibtnExit_qltt = findViewById(R.id.ibtnExit_qltt);
        ibtnExit_qltt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_Huy_qltt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_Capnhat_qltt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnabled = !isEnabled;
                enableControl();
                if (isEnabled){
                    btn_Capnhat_qltt.setText("Lưu");
                }
                else{
                    btn_Capnhat_qltt.setText("Cập nhật");
                    int IDTT = Integer.parseInt(edt_Matintuc.getText().toString());
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) img_Hinhtintuc.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    String Tieude = edt_Tieudetintuc.getText().toString();
                    String Ngaydang = edt_Ngaydangtintuc.getText().toString();
                    String Noidung = edt_Noidungtintuc.getText().toString();

                    if (edt_Tieudetintuc.getText().length() != 0 && edt_Ngaydangtintuc.getText().length() != 0
                            && edt_Noidungtintuc.getText().length() != 0 ) {
                        database.CapNhatTinTuc(IDTT, Tieude, Noidung, hinhAnh, Ngaydang);
                        Toast.makeText(CapNhat_TinTuc.this, "Thành Công !", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(CapNhat_TinTuc.this, "Thất Bại !", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CapNhat_TinTuc.this,"Không cho phép mở Camera", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(CapNhat_TinTuc.this," Không cho phép mở folder", Toast.LENGTH_LONG).show();
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
            img_Hinhtintuc.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_Hinhtintuc.setImageBitmap(bitmap);
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