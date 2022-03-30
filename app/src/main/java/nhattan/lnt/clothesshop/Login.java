package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import nhattan.lnt.clothesshop.DAO.TaiKhoanDAO;
import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.FragmentApp.LoginFragment;
import nhattan.lnt.clothesshop.FragmentApp.RegisterFragment;
import nhattan.lnt.clothesshop.FragmentApp.UserFragment;

public class Login extends AppCompatActivity{

    public static TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
    TextView login_btn, signup_btn;
    ImageButton ibtn_Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AnhXa();

    }

    private void AnhXa() {
        login_btn = findViewById(R.id.login);
        signup_btn = findViewById(R.id.signup);
        ibtn_Exit = findViewById(R.id.ibtnExit_login);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLoginFragment();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSignUpFragment();
            }
        });

        ibtn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, HomeActivity.class));
            }
        });

        loadLoginFragment();
    }

    private void loadLoginFragment()
    {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.log_sign_layout, loginFragment);
        transaction.commit();
    }

    private void loadSignUpFragment()
    {
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.log_sign_layout, registerFragment);
        transaction.commit();
    }

}