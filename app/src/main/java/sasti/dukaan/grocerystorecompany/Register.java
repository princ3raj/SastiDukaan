package sasti.dukaan.grocerystorecompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                //get all the values

                String First_name,Last_name,User_id,Phone_number, Password;

                First_name=first_name.getEditText().getText().toString();
                Last_name=last_name.getEditText().getText().toString();
                User_id=username.getEditText().getText().toString();
                Phone_number=phone_number.getEditText().getText().toString();
                Password=password.getEditText().getText().toString();

                 UserHelper helperClass= new UserHelper(First_name,Last_name,User_id,Phone_number,Password);
                reference.child(User_id).setValue(helperClass);
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
}