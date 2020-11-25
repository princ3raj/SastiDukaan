package sasti.dukaan.grocerystorecompany;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;
import sasti.dukaan.grocerystorecompany.Prevalent.Prevalent;
import sasti.dukaan.grocerystorecompany.adapter.CartListAdapter;
import sasti.dukaan.grocerystorecompany.model.Cart;

public class CartActivity extends AppCompatActivity implements CartListAdapter.OnItemClickListener {

    private static final String TAG = "CartActivity";
    private Button TotalAmountButton, PlaceOrderButton;
    private RecyclerView CartItemRecyclerView;
    private Long TotalShippingPrice;
    private DatabaseReference mbase;
    private CartListAdapter adapter;
    private String currentOnlineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Log.d(TAG, "onCreate: called ");

        Paper.init(this);
        currentOnlineUser = Paper.book().read(Prevalent.UserUsernameKey);

        Log.d(TAG, "onCreate: " + currentOnlineUser);


        CartItemRecyclerView = findViewById(R.id.cart_items_recycler_view);
        CartItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlaceOrderButton = findViewById(R.id.place_order_button);
        TotalAmountButton = findViewById(R.id.total_amount);
        PlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, ShippingDetailsActivity.class);
                intent.putExtra("total_cost", TotalShippingPrice);
                startActivity(intent);
                finish();
            }
        });


        mbase = FirebaseDatabase.getInstance().getReference()
                .child("Cart List")
                .child("User View")
                .child(currentOnlineUser)
                .child("Products");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(mbase, Cart.class)
                .build();


        Log.d(TAG, "onCreate: " + options.getSnapshots());

        adapter = new CartListAdapter(options);
        CartItemRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new CartListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, final int position, boolean isLongClick) {


                CharSequence[] options = new CharSequence[]
                        {

                                "Edit",
                                "Remove"

                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Cart Options:");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (which == 0) {

                            Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("product_id_from_cart", adapter.getItem(position).getProduct_id());
                            intent.putExtra("product_name_from_cart", adapter.getItem(position).getProduct_name());
                            intent.putExtra("product_price_from_cart", adapter.getItem(position).getProduct_price());
                            intent.putExtra("product_image_from_cart", adapter.getItem(position).getProduct_image());

                            startActivity(intent);


                        }

                        if (which == 1) {

                            mbase.child(adapter.getItem(position).getProduct_id().toString())
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                Toast.makeText(CartActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
                                                finish();
                                                startActivity(getIntent());

                                            }

                                        }
                                    });


                        }


                    }
                });
                builder.show();


            }

            @Override
            public void TotalPrice(Long total) {


                TotalAmountButton.setText(String.format("Total:- Rs.%s", total.toString()));

                TotalShippingPrice = total;


            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void OnItemClick(View view, int position, boolean isLongClick) {

    }

    @Override
    public void TotalPrice(Long total) {


    }
}