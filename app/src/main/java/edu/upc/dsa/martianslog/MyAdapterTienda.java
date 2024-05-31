package edu.upc.dsa.martianslog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.upc.dsa.martianslog.models.Product;
import edu.upc.dsa.martianslog.service.ApiService;

public class MyAdapterTienda extends RecyclerView.Adapter<MyAdapterTienda.ViewHolder>{

    private List<Product> productList;
    ApiService apiService;
    ProgressBar progressBar1;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtDescription;
        public TextView txtPrice;
        public ImageView icon;
        public View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            txtName = (TextView) itemView.findViewById(R.id.name);
            txtDescription = (TextView) itemView.findViewById(R.id.descrition);
            txtPrice = (TextView) itemView.findViewById(R.id.price);
            icon = (ImageView) itemView.findViewById(R.id.icon);

        }
    }

    public void setData(List<Product> myDataset) {
        productList = myDataset;
        notifyDataSetChanged();
    }

    public void add(int position, Product item) {
        productList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        productList.remove(position);
        notifyItemRemoved(position);
    }

    public MyAdapterTienda() {
        productList = new ArrayList<>();
    }

    public MyAdapterTienda(List<Product> myDataset) {
        productList = myDataset;
    }

    // Create new views (invoked by the layout manager)

    @NonNull
    @Override
    public MyAdapterTienda.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyAdapterTienda.ViewHolder vh = new MyAdapterTienda.ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapterTienda.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Product p = productList.get(position);
        final String name = p.getName();
        final String description = p.getDescription();
        final double price = p.getPrice();
        holder.txtName.setText(name);
        holder.txtDescription.setText(description);
        holder.txtPrice.setText("Precio: " + price);

        /*Glide.with(holder.icon.getContext())
                .load(ApiService.API_URL+p.url) //url que quiero cargar
                .into(holder.icon);//imagen que quiero cargar

         */

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
