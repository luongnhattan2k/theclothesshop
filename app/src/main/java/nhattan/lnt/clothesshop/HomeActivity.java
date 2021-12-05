package nhattan.lnt.clothesshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import de.hdodenhof.circleimageview.CircleImageView;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.FragmentApp.MyCartFragment;
import nhattan.lnt.clothesshop.FragmentApp.SettingFragment;
import nhattan.lnt.clothesshop.FragmentApp.MenClothesFragment;
import nhattan.lnt.clothesshop.FragmentApp.NewBornFragment;
import nhattan.lnt.clothesshop.FragmentApp.UserFragment;
import nhattan.lnt.clothesshop.FragmentApp.WomenClothesFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final float END_SCALE = 0.7f;

    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_MEN = 2;
    private static final int FRAGMENT_WOMEN = 3;
    private static final int FRAGMENT_NEWBORN = 4;
    private static final int FRAGMENT_PROFILE = 5;
    private static final int FRAGMENT_MYCART = 6;
    private static final int FRAGMENT_SETTING = 7;

    private int currentFragment = FRAGMENT_HOME;

    public static TaiKhoanDTO taiKhoanDTO = new TaiKhoanDTO();
    EditText edtSearch;
    // Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LinearLayout contentView;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    // Drawer

    private TextView txt_TenTaiKhoan;
    private CircleImageView cimg_User;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AnhXa();

        Intent intent = getIntent();
        int GioHangFra = intent.getIntExtra("ThemGioHang", R.id.nav_home);
        if(GioHangFra == R.id.nav_mycart){
            navigationView.setCheckedItem(GioHangFra);
            replaceFragment(new MyCartFragment());
        }

        HienThiTen();
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TimKiem.class));
            }
        });
    }

    @Override
    protected void onStart() {
        Menu menu = navigationView.getMenu();
        if(Login.taiKhoanDTO.getMATK() == -1){
            menu.findItem(R.id.nav_logout).setVisible(false);
        }else {
            menu.findItem(R.id.nav_logging).setVisible(false);
        }
        super.onStart();
    }

    @SuppressLint("RestrictedApi")
    private void AnhXa() {
        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.home_nav_view);
        contentView = findViewById(R.id.content_View);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        edtSearch = findViewById(R.id.edtSearch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hotline: 0342.171.558 \nEmail: luongnhattan2k@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        // Drawer

        replaceFragment(new HomeFragment());

        animateNavigation();

    }

    private void animateNavigation() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.vang_nhat));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaleOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleX(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaleOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);

            }
        });
    }

    private void HienThiTen() {
        View view = navigationView.inflateHeaderView(R.layout.header);
        txt_TenTaiKhoan = view.findViewById(R.id.txtTennguoidung);
        cimg_User = view.findViewById(R.id.cimgUser);

        txt_TenTaiKhoan.setText(Login.taiKhoanDTO.getTENTK());
        txt_TenTaiKhoan.setTextColor(Color.WHITE);

        if (Login.taiKhoanDTO.getHINHANH() == null){
            cimg_User.setImageResource(R.drawable.baseline_account_circle_white_24);
        } else {
            byte[] hinhAnh = Login.taiKhoanDTO.getHINHANH();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
            cimg_User.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logging) {
            SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Remember", "Failed");
            editor.putString("token", null);
            editor.apply();

            startActivity(new Intent(this, Login.class));
            finish();
        }else if (id == R.id.nav_logout){
            Intent intent = new Intent(this, HomeActivity.class);
            Login.taiKhoanDTO = new TaiKhoanDTO();
            startActivity(intent);
        }else if (id == R.id.nav_home) {
            if (FRAGMENT_HOME != currentFragment) {
                replaceFragment(new HomeFragment());
                currentFragment = FRAGMENT_HOME;
            }
        }else if (id == R.id.nav_men) {
            if (FRAGMENT_MEN != currentFragment) {
                replaceFragment(new MenClothesFragment());
                currentFragment = FRAGMENT_MEN;
            }
        }else if (id == R.id.nav_women) {
            if (FRAGMENT_WOMEN != currentFragment) {
                replaceFragment(new WomenClothesFragment());
                currentFragment = FRAGMENT_WOMEN;
            }
        }else if (id == R.id.nav_new_born) {
            if (FRAGMENT_NEWBORN != currentFragment) {
                replaceFragment(new NewBornFragment());
                currentFragment = FRAGMENT_NEWBORN;
            }
        }else if (id == R.id.nav_profile) {
            if (FRAGMENT_PROFILE != currentFragment) {
                replaceFragment(new UserFragment());
                currentFragment = FRAGMENT_PROFILE;
            }
        }else if (id == R.id.nav_mycart) {
            if (FRAGMENT_MYCART != currentFragment) {
                replaceFragment(new MyCartFragment());
                currentFragment = FRAGMENT_MYCART;
            }
        }else if (id == R.id.nav_setting) {
            if (FRAGMENT_SETTING != currentFragment) {
                replaceFragment(new SettingFragment());
                currentFragment = FRAGMENT_SETTING;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void  replaceFragment (Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}