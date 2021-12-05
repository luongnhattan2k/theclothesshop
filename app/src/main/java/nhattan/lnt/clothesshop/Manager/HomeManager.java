package nhattan.lnt.clothesshop.Manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.FragmentApp.MenClothesFragment;
import nhattan.lnt.clothesshop.FragmentApp.MyCartFragment;
import nhattan.lnt.clothesshop.FragmentApp.NewBornFragment;
import nhattan.lnt.clothesshop.FragmentApp.SettingFragment;
import nhattan.lnt.clothesshop.FragmentApp.UserFragment;
import nhattan.lnt.clothesshop.FragmentApp.WomenClothesFragment;
import nhattan.lnt.clothesshop.HomeActivity;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.Manager.FragmentManager.Quanly_Gopy;
import nhattan.lnt.clothesshop.Manager.FragmentManager.Quanly_HoaDon;
import nhattan.lnt.clothesshop.Manager.FragmentManager.Quanly_Sanpham;
import nhattan.lnt.clothesshop.Manager.FragmentManager.Quanly_Taikhoan;
import nhattan.lnt.clothesshop.R;

public class HomeManager extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final float END_SCALE = 0.7f;

    private static final int FRAGMENT_QL_SANPHAM = 1;
    private static final int FRAGMENT_QL_TAIKHOAN = 2;
    private static final int FRAGMENT_QL_HOADON = 3;
    private static final int FRAGMENT_QL_GOPY = 4;

    private int currentFragment = FRAGMENT_QL_SANPHAM;

    // Drawer
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LinearLayout contentView;
    private Toolbar toolbar;
    // Drawer

    TextView txt_TenTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_manager);

        AnhXa();

        HienThiTen();
    }

    @Override
    protected void onStart() {
        Menu menu = navigationView.getMenu();
        if(Login.taiKhoanDTO.getMATK() == -1){
            menu.findItem(R.id.nav_logout_manager).setVisible(false);
        }else {
            menu.findItem(R.id.nav_logging_manager).setVisible(false);
        }
        super.onStart();
    }

    private void AnhXa() {
        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout_manager);
        navigationView = findViewById(R.id.home_nav_view_manager);
        contentView = findViewById(R.id.content_View_manager);
        toolbar = findViewById(R.id.toolbar_manager);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        // Drawer

        replaceFragment(new Quanly_Sanpham());

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
        View view = navigationView.inflateHeaderView(R.layout.header_manager);
        txt_TenTaiKhoan = view.findViewById(R.id.txtTennguoidung);

        txt_TenTaiKhoan.setText(Login.taiKhoanDTO.getTENTK());
        txt_TenTaiKhoan.setTextColor(Color.WHITE);
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

        if (id == R.id.nav_logging_manager) {
            SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Remember", "Failed");
            editor.putString("token", null);
            editor.apply();

            startActivity(new Intent(this, Login.class));
            finish();
        }else if (id == R.id.nav_logout_manager){
            Intent intent = new Intent(this, HomeActivity.class);
            Login.taiKhoanDTO = new TaiKhoanDTO();
            startActivity(intent);
        }else if (id == R.id.nav_quanly_sanpham) {
            if (FRAGMENT_QL_SANPHAM != currentFragment) {
                replaceFragment(new Quanly_Sanpham());
                currentFragment = FRAGMENT_QL_SANPHAM;
            }
        }else if (id == R.id.nav_quanly_taikhoan) {
            if (FRAGMENT_QL_TAIKHOAN != currentFragment) {
                replaceFragment(new Quanly_Taikhoan());
                currentFragment = FRAGMENT_QL_TAIKHOAN;
            }
        }else if (id == R.id.nav_quanly_hoadon) {
            if (FRAGMENT_QL_HOADON != currentFragment) {
                replaceFragment(new Quanly_HoaDon());
                currentFragment = FRAGMENT_QL_HOADON;
            }
        }else if (id == R.id.nav_quanly_gopy) {
            if (FRAGMENT_QL_GOPY != currentFragment) {
                replaceFragment(new Quanly_Gopy());
                currentFragment = FRAGMENT_QL_GOPY;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame_manager, fragment);
        fragmentTransaction.commit();
    }
}