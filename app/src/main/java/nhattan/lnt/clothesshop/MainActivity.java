package nhattan.lnt.clothesshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

import nhattan.lnt.clothesshop.Database.CreateDatabase;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottieAnimationView ;
    private static int TIME = 2000 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        final String isLoggedIn = sharedPreferences.getString("History", "");

        AnhXa();

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

    }

    private void AnhXa(){
        lottieAnimationView = findViewById(R.id.animationView);
        lottieAnimationView.animate();
    }
}