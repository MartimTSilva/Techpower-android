package com.example.techpower.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.techpower.models.Product;
import com.example.techpower.R;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Product> mProductArrayList;

    public ProductListAdapter(Context context, ArrayList<Product> productArrayList) {
        mContext = context;
        mProductArrayList = productArrayList;
    }

    @Override
    public int getCount() {
        return mProductArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_product, null);
        }

        ViewHolderList viewHolder = (ViewHolderList) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderList(convertView);
            convertView.setTag(viewHolder);
        }

        return convertView;
    }

    private class ViewHolderList {
        private TextView mTextViewProductName;
        private TextView mTextViewProductPrice;
        private ImageView mImageViewProductImage;

        public ViewHolderList(View view) {
            mTextViewProductName = view.findViewById(R.id.product_name);
            mTextViewProductPrice = view.findViewById(R.id.product_price);
            mImageViewProductImage = view.findViewById(R.id.product_image);
        }

        public void update(int position) {
            // Add product info to card
            Product product = mProductArrayList.get(position);
            mTextViewProductName.setText(product.getName());
            mTextViewProductPrice.setText(product.getPrice());

            // Add image to image view on card
            Glide.with(mContext)
                    .load(product.getImage())
                    .placeholder(R.drawable.no_image)
                    .thumbnail(0f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageViewProductImage);
        }
    }
}
