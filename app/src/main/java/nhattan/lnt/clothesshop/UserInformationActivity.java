package nhattan.lnt.clothesshop;

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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import nhattan.lnt.clothesshop.DAO.SanPhamDAO;
import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;

public class UserInformationActivity extends AppCompatActivity {

    private EditText edt_Taikhoan, edt_Sdt, edt_Email, edt_Diachi;
    private Button btn_Capnhat, btn_Huy;
    private ImageButton ibtn_Exit;
    private CircleImageView cimg_Hinhdaidien;
    private boolean isEnabled;
    int id;

    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        Anhxa();
//        Intent intent = getIntent();
//        id = intent.getIntExtra("id",1123);
//        Toast.makeText(this,"id cua ban la" + id, Toast.LENGTH_LONG).show();

        GetData();
    }

    @Override
    protected void onStart() {
        GetData();
        super.onStart();
    }

    private void Anhxa() {
        edt_Taikhoan = findViewById(R.id.edtTaikhoan);
        edt_Sdt = findViewById(R.id.edtSdt);
        edt_Email = findViewById(R.id.edtEmail);
        edt_Diachi = findViewById(R.id.edtDiachi);
        cimg_Hinhdaidien = findViewById(R.id.imgHinhDaiDien);

        registerForContextMenu(cimg_Hinhdaidien);

        cimg_Hinhdaidien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cimg_Hinhdaidien.showContextMenu();
            }
        });

        ibtn_Exit = findViewById(R.id.ibtnExit);
        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_Huy = findViewById(R.id.btnHuyCN);
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_Capnhat = findViewById(R.id.btnCapnhat);
        btn_Capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnabled = !isEnabled;
                enableControl();
                int Sdt = Integer.parseInt(edt_Sdt.getText().toString().trim());
                String Email = edt_Email.getText().toString();
                String DiaChi = edt_Diachi.getText().toString();


                if (isEnabled){
                    btn_Capnhat.setText("Lưu");
                }
                else{
                    btn_Capnhat.setText("Cập nhật");
                    // chuyen data image view -> mang byte[]
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) cimg_Hinhdaidien.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();

                    HomeFragment.database.CapNhatTaiKhoan(Login.taiKhoanDTO.getMATK(), hinhAnh, Sdt, Email, DiaChi);
                    Toast.makeText(UserInformationActivity.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    Intent iHome = new Intent(UserInformationActivity.this, HomeActivity.class);
                    Login.taiKhoanDTO = new TaiKhoanDTO();
                    startActivity(iHome);
                }
            }
        });

    }

    private void enableControl() {
        cimg_Hinhdaidien.setEnabled(isEnabled);
        edt_Sdt.setEnabled(isEnabled);
        edt_Email.setEnabled(isEnabled);
        edt_Diachi.setEnabled(isEnabled);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_capnhat_user, menu);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            cimg_Hinhdaidien.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                cimg_Hinhdaidien.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
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
                    Toast.makeText(UserInformationActivity.this," Bạn không cho phép mở camera", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(UserInformationActivity.this," Bạn không cho phép mở folder", Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void GetData() {
        if (Login.taiKhoanDTO.getMATK() == -1)
        {
            Toast.makeText(this, "Bạn chưa đăng nhập !", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        } else {
            //get data
            int id = Login.taiKhoanDTO.getMATK();
            TaiKhoanDTO taiKhoanDTO = HomeFragment.database.Load(id);
            String tentaikhoan = taiKhoanDTO.getTENTK();
            int sdt = taiKhoanDTO.getSDT();
            String email = taiKhoanDTO.getEMAIL();
            String diachi = taiKhoanDTO.getDIACHI();
            enableControl();

            edt_Taikhoan.setText(tentaikhoan);
            edt_Sdt.setText(String.valueOf(sdt));
            edt_Email.setText(email);
            edt_Diachi.setText(diachi);
            edt_Taikhoan.setEnabled(false);
            if (Login.taiKhoanDTO.getHINHANH() == null){
                cimg_Hinhdaidien.setImageResource(R.drawable.baseline_person_24);
            } else {
                byte[] hinhAnh = Login.taiKhoanDTO.getHINHANH();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
                cimg_Hinhdaidien.setImageBitmap(bitmap);
            }
        }
    }
}