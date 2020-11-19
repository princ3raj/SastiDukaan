package sasti.dukaan.grocerystorecompany;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static  int SPLASH_SCREEN=3000;


    Animation topAnim, bottomAnim;



    ImageView AppLogo;
    TextView WelcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.botton_animation);

        //Hooks
        AppLogo=findViewById(R.id.applogo);
        WelcomeText=findViewById(R.id.welcome_text);


        AppLogo.setAnimation(topAnim);
        WelcomeText.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(MainActivity.this,Login.class);

                Pair[] pairs= new Pair[2];
                pairs[0]= new Pair<View,String>(AppLogo,"logo_image");
                pairs[1]= new Pair<View,String>(WelcomeText,"logo_text");

                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);

                startActivity(intent,activityOptions.toBundle());
                finish();

            }
        },SPLASH_SCREEN);

    }
}