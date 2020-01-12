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
import com.example.techpower.R;
import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;

import java.util.ArrayList;
import java.util.Map;

public class CartListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Map<Integer, Integer> mCartArrayList;
    private ArrayList<Product> mProductArrayList;

    public CartListAdapter(Context Context, Map<Integer, Integer> CartArrayList) {
        mContext = Context;
        mCartArrayList = CartArrayList;

    }

    @Override
    public int getCount() {
        return mCartArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCartArrayList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.cart_item, parent, false);
        }

        ViewHolderList viewHolder = (ViewHolderList) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderList(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(position);

        return convertView;
    }

    private class ViewHolderList
    {

        private TextView mTextViewProductName;
        private TextView mTextViewProductPrice;
        private TextView mTextViewProductQuantity;
        private ImageView mImageViewProductImage;

        public ViewHolderList(View view)
        {
            mTextViewProductName = view.findViewById(R.id.textView_productName);
            mTextViewProductPrice = view.findViewById(R.id.textView_productPrice);
            mTextViewProductQuantity = view.findViewById(R.id.textView_quantity);
            mImageViewProductImage = view.findViewById(R.id.textView_productName);
        }

        public void update(int position)
        {
            //Add product info to card
            //for (int i = 0; i >= mCartArrayList.size(); i++)
            for (Map.Entry<Integer, Integer> entry: mCartArrayList.entrySet())
            {
                Product product = SingletonStore.getInstance(mContext).getProduct(entry.getKey());
                mTextViewProductName.setText(product.getName());
                mTextViewProductPrice.setText(product.getPrice() + "€");
                mTextViewProductQuantity.setText(entry.getValue());

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
}

