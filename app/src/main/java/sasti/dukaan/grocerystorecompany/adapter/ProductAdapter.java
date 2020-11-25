package sasti.dukaan.grocerystorecompany.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import sasti.dukaan.grocerystorecompany.DashBoard;

import sasti.dukaan.grocerystorecompany.ProductDetailsActivity;
import sasti.dukaan.grocerystorecompany.R;
import sasti.dukaan.grocerystorecompany.model.Products;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class ProductAdapter extends FirebaseRecyclerAdapter<
        Products, ProductAdapter.ProductViewholder>  {

    private OnItemClickListener listener;

    private Context context;




    public ProductAdapter(
            @NonNull FirebaseRecyclerOptions<Products> options)
    {
        super(options);
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull ProductViewholder holder,
                     final int position, @NonNull Products model)
    {


        holder.product_price.setText(String.format("Rs.%s", model.getP_price().toString()));


        Picasso.get().load(model.getP_image()).into(holder.product_image);








    }



    @NonNull
    @Override
    public ProductViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_layout, parent, false);
        return new ProductAdapter.ProductViewholder(view);
    }








    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class ProductViewholder extends RecyclerView.ViewHolder{
        TextView product_price;
        ImageView product_image;


        public ProductViewholder(@NonNull View itemView)
        {
            super(itemView);
            product_price = itemView.findViewById(R.id.product_price);
            product_image = itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getAdapterPosition();

                    listener.OnItemClick(v,position,false);

                }
            });

        }

    }

    public interface OnItemClickListener{

        void OnItemClick(View view,int position,boolean isLongClick);
    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener=listener;
    }





}

