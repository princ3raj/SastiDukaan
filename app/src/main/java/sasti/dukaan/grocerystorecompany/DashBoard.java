package sasti.dukaan.grocerystorecompany;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;
import sasti.dukaan.grocerystorecompany.Prevalent.Prevalent;
import sasti.dukaan.grocerystorecompany.adapter.ProductAdapter;
import sasti.dukaan.grocerystorecompany.model.Products;

public class DashBoard extends AppCompatActivity implements ProductAdapter.OnItemClickListener {


    private static final String TAG = "DashBoard";

    private FloatingActionButton FloatingCartButton;

    TextView DashBoard_userName;


    private RecyclerView recyclerView;
    ProductAdapter adapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        FloatingCartButton = findViewById(R.id.floating_cart_button);
        FloatingCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this, CartActivity.class);
                startActivity(intent);
            }
        });


        DashBoard_userName = findViewById(R.id.dashboard_user_name);

        Paper.init(this);
        String UserUsernameKey = Paper.book().read(Prevalent.UserUsernameKey);
        DashBoard_userName.setText(UserUsernameKey);







        // Create a instance of the database and get
        // its reference
        mbase = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = findViewById(R.id.products_recycler_view);

        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Products> options
                = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(mbase, Products.class)
                .build();


        adapter = new ProductAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);


      adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
          @Override
          public void OnItemClick(View view, int position, boolean isLongClick) {


              Intent intent =new Intent(DashBoard.this,ProductDetailsActivity.class);
              intent.putExtra("p_name",adapter.getItem(position).getP_name());
              intent.putExtra("p_description",adapter.getItem(position).getP_description());
              intent.putExtra("p_image",adapter.getItem(position).getP_image());
              intent.putExtra("p_price",adapter.getItem(position).getP_price());
              intent.putExtra("p_id",adapter.getItem(position).getP_id());

              startActivity(intent);



              Log.d(TAG, "OnItemClick: "+adapter.getItem(position).getP_name());

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
}