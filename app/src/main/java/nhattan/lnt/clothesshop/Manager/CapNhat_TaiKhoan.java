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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;

public class CapNhat_TaiKhoan extends AppCompatActivity {

    EditText edtIDTK_QLTK, edtTENTK_QLTK, edtLOAITK_QLTK, edtEMAIL_QLTK, edtSDT_QLTK,
            edtMATKHAU_QLTK, edtQUYEN_QLTK, edtDIACHI_QLTK;
    Button btnCAPNHAT_QLTK;
    CircleImageView img_HINHANH_QLTK;
    Database database;
    ImageButton ibtnExit_qltk;

    //    private Account account;
    private boolean isEnabled;
    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_tai_khoan);

        AnhXa();
        Intent intent = getIntent();
        int IDTK = intent.getIntExtra("IDTK", 1);

        if (String.valueOf(IDTK) == null){
            return;
        }
        TaiKhoanDTO taiKhoan = database.TTTaiKhoan(IDTK);

        edtIDTK_QLTK.setText(String.valueOf(IDTK));
        edtIDTK_QLTK.setEnabled(false);
        edtTENTK_QLTK.setText(taiKhoan.getTENTK());
        edtTENTK_QLTK.setEnabled(false);
        edtMATKHAU_QLTK.setText(taiKhoan.getMATKHAU());
        edtSDT_QLTK.setText(String.valueOf(taiKhoan.getSDT()));
        edtDIACHI_QLTK.setText(taiKhoan.getDIACHI());
        edtQUYEN_QLTK.setText(String.valueOf(taiKhoan.getQUYEN()));
        edtEMAIL_QLTK.setText(taiKhoan.getEMAIL());
        edtLOAITK_QLTK.setText(taiKhoan.getLOAITK());

        if (Login.taiKhoanDTO.getHINHANH() == null){
            img_HINHANH_QLTK.setImageResource(R.drawable.baseline_account_circle_24);
        } else {
            byte[] hinhAnh = taiKhoan.getHINHANH();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0,hinhAnh.length);
            img_HINHANH_QLTK.setImageBitmap(bitmap);
        }
    }

    private void enableControl(){
        img_HINHANH_QLTK.setEnabled(isEnabled);
        edtMATKHAU_QLTK.setEnabled(isEnabled);
        edtSDT_QLTK.setEnabled(isEnabled);
        edtEMAIL_QLTK.setEnabled(isEnabled);
        edtDIACHI_QLTK.setEnabled(isEnabled);
        edtQUYEN_QLTK.setEnabled(isEnabled);
        edtLOAITK_QLTK.setEnabled(isEnabled);
    }

    private void AnhXa() {
        database = new Database(this);
        img_HINHANH_QLTK = findViewById(R.id.img_user_ql_taikhoan);
        edtIDTK_QLTK = findViewById(R.id.edt_idtk_ql_taikhoan);
        edtTENTK_QLTK = findViewById(R.id.edt_tentaikhoan_ql_taikhoan);
        edtMATKHAU_QLTK= findViewById(R.id.edt_matkhau_ql_taikhoan);
        edtSDT_QLTK = findViewById(R.id.edt_sdt_ql_taikhoan);
        edtEMAIL_QLTK = findViewById(R.id.edt_mail_ql_taikhoan);
        edtDIACHI_QLTK = findViewById(R.id.edt_diachi_ql_taikhoan);
        edtQUYEN_QLTK = findViewById(R.id.edt_quyen_ql_taikhoan);
        edtLOAITK_QLTK = findViewById(R.id.edt_loaitk_ql_taikhoan);
        enableControl();

        registerForContextMenu(img_HINHANH_QLTK);
        img_HINHANH_QLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_HINHANH_QLTK.showContextMenu();
            }
        });

        ibtnExit_qltk = findViewById(R.id.ibtnExit_qltk);
        ibtnExit_qltk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnCAPNHAT_QLTK = findViewById(R.id.btn_capnhat_ql_taikhoan);
        btnCAPNHAT_QLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEnabled = !isEnabled;
                enableControl();
                if (isEnabled){
                    btnCAPNHAT_QLTK.setText("Lưu");
                }
                else{
                    btnCAPNHAT_QLTK.setText("Cập nhật");

                    BitmapDrawable bitmapDrawable = (BitmapDrawable) img_HINHANH_QLTK.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();

                    int idtk = Integer.parseInt(edtIDTK_QLTK.getText().toString());
                    String tentaikhoan = edtTENTK_QLTK.getText().toString();
                    String email = edtEMAIL_QLTK.getText().toString();
                    int sdt = Integer.parseInt(edtSDT_QLTK.getText().toString());
                    String matkhau = edtMATKHAU_QLTK.getText().toString();
                    int quyen = Integer.parseInt(edtQUYEN_QLTK.getText().toString());
                    String diachi = edtDIACHI_QLTK.getText().toString();
                    String loaitk = edtLOAITK_QLTK.getText().toString();

                    if (edtSDT_QLTK.getText().length() != 0 && edtEMAIL_QLTK.getText().length() != 0
                            && edtMATKHAU_QLTK.getText().length() != 0 && edtLOAITK_QLTK.getText().length() !=0 && edtQUYEN_QLTK.getText().length() !=0) {
                        database.CapNhatTaiKhoan_ADMIN(idtk, tentaikhoan, matkhau, hinhAnh, sdt, email, diachi, quyen, loaitk);
                        Toast.makeText(CapNhat_TaiKhoan.this, "Đã lưu tài khoản !", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(CapNhat_TaiKhoan.this, "Hãy nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(CapNhat_TaiKhoan.this,"Không cho phép mở Camera", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(CapNhat_TaiKhoan.this," Không cho phép mở folder", Toast.LENGTH_LONG).show();
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
            img_HINHANH_QLTK.setImageBitmap(bitmap);
        }
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_HINHANH_QLTK.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
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
}