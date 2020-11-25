package sasti.dukaan.grocerystorecompany;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;
import sasti.dukaan.grocerystorecompany.Prevalent.Prevalent;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView product_price,product_name;
    private ImageView product_image;

    private String userUsernameKey;

    private Button AddToCartButton;
    private ElegantNumberButton elegantNumberButton;

    private Long p_id, p_price;
    private String p_name,p_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Paper.init(this);
        userUsernameKey=Paper.book().read(Prevalent.UserUsernameKey);

        elegantNumberButton=findViewById(R.id.elegant_number_button);
        AddToCartButton=findViewById(R.id.add_to_cart);
        product_price=findViewById(R.id.product_detail_price);
        product_name=findViewById(R.id.product_detail_name);
        product_image=findViewById(R.id.product_detail_image);

        p_name=getIntent().getStringExtra("p_name");
        p_description=getIntent().getStringExtra("p_description");
        String p_image=getIntent().getStringExtra("p_image");
        p_price=getIntent().getLongExtra("p_price",0);
        p_id=getIntent().getLongExtra("p_id",0);


        product_name.setText(p_name);
        product_price.setText(String.format("Rs.%d", p_price));



        Picasso.get().load(p_image).into(product_image);
        
        AddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCartList();
            }
        });







    }

    private void addToCartList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar  calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime()) ;


        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentDate.format(calForDate.getTime());


       final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("product_id",p_id);
        cartMap.put("product_name",p_name);
        cartMap.put("product_price",p_price);
        cartMap.put("product_quantity",elegantNumberButton.getNumber() );
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);


        cartListRef.child("User View").child(userUsernameKey)
                .child("Products").child(p_id.toString())
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()){


                            cartListRef.child("Admin  View").child(userUsernameKey)
                                    .child("Products").child(p_id.toString())
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                Toast.makeText(ProductDetailsActivity.this, "Added to cart list", Toast.LENGTH_SHORT).show();
                                            }



                                        }
                                    });

                        }

                    }

                });


    }
}