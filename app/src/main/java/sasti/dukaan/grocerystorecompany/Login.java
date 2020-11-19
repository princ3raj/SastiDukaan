package sasti.dukaan.grocerystorecompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {



  //shortcut for typing this "logt"
    private static final String TAG = "Login";
    Button newUserSignupButton, SignInButton, ForgotPassword;
    ImageView logoImage;
    TextView signin_slogan;
    TextInputLayout username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);







        newUserSignupButton=findViewById(R.id.new_user_signp);

        SignInButton=findViewById(R.id.signin_button);
        ForgotPassword=findViewById(R.id.forgot_password_button);

        logoImage=findViewById(R.id.logo_image_id);
        signin_slogan=findViewById(R.id.sigin_slogan_id);
        username=findViewById(R.id.email);
        password=findViewById(R.id.password);



        newUserSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,Register.class);
                Pair[] pairs = new Pair[7];
                pairs[0]= new Pair<View,String>(logoImage,"logo_image");
                pairs[1]= new Pair<View,String>(signin_slogan,"logo_text");
                pairs[2]= new Pair<View,String>(username,"email_tran");
                pairs[3]= new Pair<View,String>(password,"password_tran");
                pairs[4]= new Pair<View,String>(ForgotPassword,"forgot_password_tran");
                pairs[5]= new Pair<View,String>(SignInButton,"sigin_tran");
                pairs[6]= new Pair<View,String>(newUserSignupButton,"new_user_tran");

                ActivityOptions activityOptions=ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);




                startActivity(intent,activityOptions.toBundle());

            }
        });


        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }
        });


    }


    private Boolean validateUsername()
    {

        String val= username.getEditText().getText().toString();


        if (val.isEmpty()){

            username.setError("Field can't be empty");
            return  false;
        } else
        {
            username.setError(null);
            username.setErrorEnabled(false);
            return  true;

        }


    }

    private Boolean validatePassword()
    {
        String val= password.getEditText().getText().toString();


        if (val.isEmpty())
        {

            password.setError("Field cannot be empty");
            return  false;
        } else
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return  true;

        }


    }

    public  void loginUser()
    {

        //validate login info
        if(!validateUsername() | !validatePassword()){

            return;

        }else
        {
            isUser();
        }


    }

    private void isUser() {

        final String userEnteredUsername=username.getEditText().getText().toString().trim();
        final String userEnteredPassword=password.getEditText().getText().toString().trim()  ;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Query checkUser=reference.orderByChild("username").equalTo(userEnteredUsername );

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);


                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    Log.d(TAG, "password from db: "+passwordFromDB);

                    Log.d(TAG, "user entered username: "+userEnteredUsername);

                    Log.d(TAG, "user entered pass: "+userEnteredPassword);



                    if(Objects.equals(passwordFromDB, userEnteredPassword))
                    {

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String usernameIdFromDB= snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String first_nameFromDB= snapshot.child(userEnteredUsername).child("first_name").getValue(String.class);
                        String last_nameFromDB= snapshot.child(userEnteredUsername).child("last_name").getValue(String.class);
                        String phoneNumberFromDB= snapshot.child(userEnteredUsername).child("phone_number").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(),Dashboard.class);

                        intent.putExtra("username",usernameIdFromDB);
                        intent.putExtra("first_name",first_nameFromDB);
                        intent.putExtra("last_name",last_nameFromDB);
                        intent.putExtra("phone_number",phoneNumberFromDB);
                        intent.putExtra("password",passwordFromDB);


                        startActivity(intent);




                    } else {


                        password.setError("Wrong Password");
                        password.requestFocus();
                    }



                } else{

                    username.setError("No such User  exist");
                    username.requestFocus();
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }


}