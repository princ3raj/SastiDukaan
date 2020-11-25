package sasti.dukaan.grocerystorecompany;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;
import sasti.dukaan.grocerystorecompany.Prevalent.Prevalent;


public class ShippingDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ShippingDetailsActivity";

    private TextInputLayout FullName, PhoneNumber, Address, ZipCode, City;
    private Button CompleteOrderButton, TotalShippingCost;

    private Long totalShippingPrice = 0L;
    private String currentOnlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details);

        Paper.init(this);
        currentOnlineUser = Paper.book().read(Prevalent.UserUsernameKey);

        totalShippingPrice = getIntent().getLongExtra("total_cost", 0);
        totalShippingPrice = totalShippingPrice + 10;


        //setting up hooks
        FullName = findViewById(R.id.full_name);
        PhoneNumber = findViewById(R.id.phone_number);
        Address = findViewById(R.id.address);
        ZipCode = findViewById(R.id.zip_code);
        City = findViewById(R.id.city);
        CompleteOrderButton = findViewById(R.id.complete_order_button);
        TotalShippingCost = findViewById(R.id.total_shipping_cost);

        TotalShippingCost.setText(String.format("Rs.%s(delivery charges included)", totalShippingPrice.toString()));


        CompleteOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CompleteOrder();

            }
        });


    }

    private void CompleteOrder() {

        if (!validateFullName() | !validatePhoneNumber() | !validateAddress() | !validateZipCode() | !validateCity()) {
            return;
        }


        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final DatabaseReference OrderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(currentOnlineUser);
        HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("Total Amount", totalShippingPrice);
        orderMap.put("Full Name", FullName.getEditText().getText().toString());
        orderMap.put("Phone Number", PhoneNumber.getEditText().getText().toString());
        orderMap.put("Address", Address.getEditText().getText().toString());
        orderMap.put("Date", saveCurrentDate);
        orderMap.put("Time", saveCurrentTime);
        orderMap.put("Zip Code", ZipCode.getEditText().getText().toString());
        orderMap.put("City", City.getEditText().getText().toString());
        orderMap.put("state", "not shipped");

        OrderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                FirebaseDatabase.getInstance().getReference()
                        .child("Cart List")
                        .child("User View")
                        .child(currentOnlineUser)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (task.isSuccessful()) {
                                    Toast.makeText(ShippingDetailsActivity.this, "your order has been placed successfully", Toast.LENGTH_SHORT).show();

                                }


                            }
                        });

            }
        });


    }

    private Boolean validateFullName() {

        String val = FullName.getEditText().getText().toString();


        if (val.isEmpty()) {

            FullName.setError("Field can't be empty");
            return false;
        } else {
            FullName.setError(null);
            FullName.setErrorEnabled(false);
            return true;

        }


    }

    private Boolean validatePhoneNumber() {

        String val = PhoneNumber.getEditText().getText().toString();


        if (val.isEmpty()) {

            PhoneNumber.setError("Field can't be empty");
            return false;
        } else {
            PhoneNumber.setError(null);
            PhoneNumber.setErrorEnabled(false);
            return true;

        }


    }

    private Boolean validateAddress() {

        String val = Address.getEditText().getText().toString();


        if (val.isEmpty()) {

            Address.setError("Field can't be empty");
            return false;
        } else {
            Address.setError(null);
            Address.setErrorEnabled(false);
            return true;

        }


    }

    private Boolean validateZipCode() {

        String val = ZipCode.getEditText().getText().toString();


        if (val.isEmpty()) {

            ZipCode.setError("Field can't be empty");
            return false;
        } else {
            ZipCode.setError(null);
            ZipCode.setErrorEnabled(false);
            return true;

        }


    }

    private Boolean validateCity() {

        String val = City.getEditText().getText().toString();


        if (val.isEmpty()) {

            City.setError("Field can't be empty");
            return false;
        } else {
            City.setError(null);
            City.setErrorEnabled(false);
            return true;

        }


    }


}