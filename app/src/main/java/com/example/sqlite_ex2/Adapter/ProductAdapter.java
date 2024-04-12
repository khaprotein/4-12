package com.example.sqlite_ex2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlite_ex2.R;
import com.example.sqlite_ex2.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    Context context;
    int item_layout;
    List<Product> productList;

    public ProductAdapter(Context context, int item_layout, List<Product> productList) {
        this.context = context;
        this.item_layout = item_layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(item_layout, null);

            //Linking view
            viewHolder.txtInfo = convertView.findViewById(R.id.txtInfo);
            viewHolder.imvEdit = convertView.findViewById(R.id.imvEdit);
            viewHolder.imvDelete = convertView.findViewById(R.id.imvDelete);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Binding data
        Product product = productList.get(position);
        viewHolder.txtInfo.setText(product.getProductName() + " - " + String.format("%.0f đ", product.getProductPrice()));

        return convertView;
    }

    public static class ViewHolder{
        TextView txtInfo;
        ImageView imvEdit, imvDelete;
    }
}
