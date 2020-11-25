package sasti.dukaan.grocerystorecompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.paperdb.Paper;
import sasti.dukaan.grocerystorecompany.Prevalent.Prevalent;

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


        Paper.init(this);
        String UserUsernameKey=Paper.book().read(Prevalent.UserUsernameKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

        if(UserUsernameKey!="" && UserPasswordKey!=""){


            if (!TextUtils.isEmpty(UserUsernameKey) && !TextUtils.isEmpty(UserPasswordKey)){
                
                allowAccess(UserUsernameKey,UserPasswordKey);


            }
        }

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


                String UserUsernameKey=Paper.book().read(Prevalent.UserUsernameKey);
                String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

                if(UserUsernameKey==null && UserPasswordKey==null){



                        startActivity(intent,activityOptions.toBundle());
                        finish();



                }



            }
        },SPLASH_SCREEN);

    }



    private void allowAccess( final String userEnteredUsername, final  String  userEnteredPassword ) {




        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser=reference.orderByChild("username").equalTo(userEnteredUsername );

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){




                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);




                    if(Objects.equals(passwordFromDB, userEnteredPassword))
                    {


                        String usernameIdFromDB= snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String first_nameFromDB= snapshot.child(userEnteredUsername).child("first_name").getValue(String.class);
                        String last_nameFromDB= snapshot.child(userEnteredUsername).child("last_name").getValue(String.class);
                        String phoneNumberFromDB= snapshot.child(userEnteredUsername).child("phone_number").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), DashBoard.class);

                        intent.putExtra("username",usernameIdFromDB);
                        intent.putExtra("first_name",first_nameFromDB);
                        intent.putExtra("last_name",last_nameFromDB);
                        intent.putExtra("phone_number",phoneNumberFromDB);
                        intent.putExtra("password",passwordFromDB);


                        startActivity(intent);
                        finish();




                    } else {



                    }



                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}