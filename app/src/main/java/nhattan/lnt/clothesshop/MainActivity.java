package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import nhattan.lnt.clothesshop.Database.CreateDatabase;

public class MainActivity extends AppCompatActivity {

    private static int TIME = 2000 ;
    Animation topAnim, bottomAnim;
    ImageView imgLogo;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        final String isLoggedIn = sharedPreferences.getString("History", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLoggedIn.equals("true")) {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }else
                {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            }
        }, TIME) ;

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imgLogo = findViewById(R.id.img_logo);
        logo = findViewById(R.id.txt_Logo);
        slogan = findViewById(R.id.txt_slogan);

        imgLogo.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

    }

}