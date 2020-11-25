package sasti.dukaan.grocerystorecompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Register extends AppCompatActivity {




    // email is named as username everywhere , so no confusion should be left

    Button AlreadyRegisterLogin,SignUp;

    TextInputLayout first_name, last_name,username,password, phone_number;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        first_name=findViewById(R.id.first_name);
        last_name=findViewById(R.id.last_name);
        username=findViewById(R.id.email);
        password=findViewById(R.id.password);
        phone_number=findViewById(R.id.phone_number);
        SignUp=findViewById(R.id.sign_up);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode=FirebaseDatabase.getInstance();
                reference=rootNode.getReference("Users");




                registerUser(view);






            }
        });

        AlreadyRegisterLogin=findViewById(R.id.existing_user_login);
        AlreadyRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);

            }
        });
    }


    private Boolean validateFirstName(){
        String val=first_name.getEditText().getText().toString();
        if(val.isEmpty()){
            first_name.setError("Field cannot be empty");
            return false;
        }else
        {
           first_name.setError(null);
           first_name.setErrorEnabled(false);
           return  true;

        }
    }
    private Boolean validateLastName(){
        String val=last_name.getEditText().getText().toString();
        if(val.isEmpty()){
            last_name.setError("Field cannot be empty");
            return false;
        }else
        {
            last_name.setError(null);
            first_name.setErrorEnabled(false);
            return  true;

        }
    }
    private Boolean validateUsername(){

        String noWhiteSpace="\\A\\w{4,20}\\z";
        String val=username.getEditText().getText().toString();
        if(val.isEmpty()){
            username.setError("Field cannot be empty");
            return false;
        }else if(val.length()>=15)
        {

            username.setError("Username too long");
            return false;

        }
        else if(!val.matches(noWhiteSpace)){

            username.setError("White spaces are not allowed");

            return false;

        } else

        {
            username.setError(null);
            first_name.setErrorEnabled(false);
            return  true;

        }
    }
    private Boolean validatePhoneNumber(){
        String val=phone_number.getEditText().getText().toString();
        if(val.isEmpty()){
            phone_number.setError("Field cannot be empty");
            return false;
        }else if( val.length()>11){

            phone_number.setError("Phone number's too long");
            return  false;

        } else
        {
            phone_number.setError(null);
            phone_number.setErrorEnabled(false);
            return  true;

        }
    }
    private Boolean validatePassword(){
        String val=password.getEditText().getText().toString();

        String passwordVal="^"+
                "(?=.*[a-zA-Z])"+
                "(?=.*[@#$%^&+=])"+
                "(?=\\S+$)"+
                ".{4,}"+
                "$";

        if(val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        }

         else if( !val.matches(passwordVal)){

             password.setError("Password is too weak");
             return  false;


        }
        {
            password.setError(null);
            password.setErrorEnabled(false);
            return  true;

        }
    }




    public  void registerUser(View view){
        //get all the values in string
        //get all the values

        if (!validateFirstName() | !validateLastName() | !validateUsername() | !validatePhoneNumber() | !validatePassword()){
            return;
        }

        String First_name,Last_name,User_id,Phone_number, Password;

        First_name=first_name.getEditText().getText().toString();
        Last_name=last_name.getEditText().getText().toString();
        User_id=username.getEditText().getText().toString();
        Phone_number=phone_number.getEditText().getText().toString();
        Password=password.getEditText().getText().toString();

        UserHelper helperClass= new UserHelper(First_name,Last_name,User_id,Phone_number,Password);
        reference.child(User_id).setValue(helperClass);

        isUser();


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




                    if(Objects.equals(passwordFromDB, userEnteredPassword))
                    {

                        username.setError(null);
                        username.setErrorEnabled(false);

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