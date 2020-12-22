package com.cobacobms.finalproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.entity.Product;
import com.cobacobms.finalproject.ui.ProductActivity;
import com.cobacobms.finalproject.utilities.CobacoApp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements Filterable {

    private List<Product> lst;
    private List<Product> tempList;
    private LayoutInflater mInflater;
    private Context context;
    private String ListType;
    private int defaultColor;

    public ProductListAdapter(Context context, List<Product> lst, String ListType) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.lst = lst;
        this.tempList = lst;
        this.ListType = ListType;
        this.defaultColor = ((CobacoApp) context.getApplicationContext()).getDefaultColor(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (ListType.equals(String.valueOf(R.string.TwoColumn)))
            view = mInflater.inflate(R.layout.adapter_product_list_2, parent, false);
        else
            view = mInflater.inflate(R.layout.adapter_product_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        final Product product = lst.get(position);

        String imgAddress = "http://www.cobacobms.com/img/ah150/prd/" + product.getPicId() + "/" + product.getPicName();
        Picasso.get().load(imgAddress).into(holder.imgProductPic);
        holder.tvProductname.setText(product.getProductName());
        if (ListType.equals(String.valueOf(R.string.OneColumn))) {
            if (product.getProductName2() != null)
                holder.tvProductname2.setText(product.getProductName2());
            holder.tvCategory.setText(product.getCategory());
            holder.tvCategoryLable.setTextColor(defaultColor);
        }

        holder.s1.setImageResource(R.drawable.ic_baseline_star_border_24);
        holder.s2.setImageResource(R.drawable.ic_baseline_star_border_24);
        holder.s3.setImageResource(R.drawable.ic_baseline_star_border_24);
        holder.s4.setImageResource(R.drawable.ic_baseline_star_border_24);
        holder.s5.setImageResource(R.drawable.ic_baseline_star_border_24);

        holder.s1.setColorFilter(defaultColor);
        holder.s2.setColorFilter(defaultColor);
        holder.s3.setColorFilter(defaultColor);
        holder.s4.setColorFilter(defaultColor);
        holder.s5.setColorFilter(defaultColor);
        holder.tvProductname.setTextColor(defaultColor);
        holder.btnShowProduct.setTextColor(defaultColor);

        switch (product.getRank()) {
            case 1: {
                holder.s1.setImageResource(R.drawable.ic_baseline_star_24);
            }
            break;
            case 2: {
                holder.s1.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s2.setImageResource(R.drawable.ic_baseline_star_24);
            }
            break;
            case 3: {
                holder.s1.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s2.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s3.setImageResource(R.drawable.ic_baseline_star_24);
            }
            break;
            case 4: {
                holder.s1.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s2.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s3.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s4.setImageResource(R.drawable.ic_baseline_star_24);
            }
            break;
            case 5: {
                holder.s1.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s2.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s3.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s4.setImageResource(R.drawable.ic_baseline_star_24);
                holder.s5.setImageResource(R.drawable.ic_baseline_star_24);
            }
            break;
            default:
                break;
        }

        holder.btnShowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("ID", product.getID());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResult = new FilterResults();

                String[] filterStrings = String.valueOf(charSequence).split(" ");

                ArrayList<Product> newList = new ArrayList<>();
                ArrayList<Product> newList2 = new ArrayList<>();
                int i = 0;
                for (String item : filterStrings) {
                    if (i == 0) {
                        for (Product item2 : tempList) {
                            if (item2.getProductName().toLowerCase().contains(item.toLowerCase()))
                                newList.add(item2);
                        }
                        newList2.addAll(newList);
                        i++;
                    } else {
                        newList.removeAll(newList);
                        for (Product item2 : newList2) {
                            if (item2.getProductName().toLowerCase().contains(item.toLowerCase()))
                                newList.add(item2);
                        }
                        newList2.removeAll(newList2);
                        newList2.addAll(newList);
                    }
                }

                filterResult.values = newList;
                return filterResult;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lst = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductPic, s1, s2, s3, s4, s5;
        TextView tvProductname, tvProductname2, tvCategory,tvCategoryLable;
        Button btnShowProduct;

        ViewHolder(View itemView) {
            super(itemView);

            imgProductPic = itemView.findViewById(R.id.imgProductPic);
            tvProductname = itemView.findViewById(R.id.tvProductname);
            tvProductname2 = itemView.findViewById(R.id.tvProductname2);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            btnShowProduct = itemView.findViewById(R.id.btnShowProduct);
            tvCategoryLable = itemView.findViewById(R.id.tvCategoryLable);

            s1 = itemView.findViewById(R.id.imgStar1);
            s2 = itemView.findViewById(R.id.imgStar2);
            s3 = itemView.findViewById(R.id.imgStar3);
            s4 = itemView.findViewById(R.id.imgStar4);
            s5 = itemView.findViewById(R.id.imgStar5);
        }
    }
}
