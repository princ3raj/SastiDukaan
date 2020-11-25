package sasti.dukaan.grocerystorecompany.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import sasti.dukaan.grocerystorecompany.R;
import sasti.dukaan.grocerystorecompany.model.Cart;

public class CartListAdapter extends FirebaseRecyclerAdapter<Cart, CartListAdapter.CartViewHolder> {

    public Long TotalPrice = 0L;
    private OnItemClickListener listener;
    private Context context;


    public CartListAdapter(
            @NonNull FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }


    @Override
    protected void
    onBindViewHolder(@NonNull CartViewHolder holder,
                     final int position, @NonNull Cart model) {


        holder.ProductName.setText(model.getProduct_name());

        holder.ProductPrice.setText(String.format("Price:%s", model.getProduct_price().toString()));

        holder.ProductQuantity.setText("Quantity: " + model.getProduct_quantity());

        Long OneTypeProductPrice = model.getProduct_price() * Integer.parseInt(model.getProduct_quantity());
        TotalPrice = TotalPrice + OneTypeProductPrice;

        listener.TotalPrice(TotalPrice);


    }


    @NonNull
    @Override
    public CartViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_items_layout, parent, false);
        return new CartListAdapter.CartViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }

    public interface OnItemClickListener {

        void OnItemClick(View view, int position, boolean isLongClick);

        void TotalPrice(Long total);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView ProductName, ProductQuantity, ProductPrice;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            ProductPrice = itemView.findViewById(R.id.cart_item_product_price);
            ProductName = itemView.findViewById(R.id.cart_item_product_name);
            ProductQuantity = itemView.findViewById(R.id.cart_item_product_quantity);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    listener.OnItemClick(v, position, false);


                }
            });

        }

    }


}

