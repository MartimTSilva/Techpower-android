package com.example.techpower.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.techpower.R;
import com.example.techpower.models.CartItem;
import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;

import java.util.ArrayList;
import java.util.Map;

public class CartListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<CartItem> mCartArrayList;

    public CartListAdapter(Context Context, ArrayList<CartItem> CartArrayList) {
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
            try {
                convertView = mLayoutInflater.inflate(R.layout.cart_item, null);
            } catch (Exception ex) {
                Log.e("Techpower", "getView: ", ex.fillInStackTrace());
            }
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
        private Button mButtonPlus;
        private Button mButtonMinus;
        private ImageButton mButtonRemove;

        public ViewHolderList(View view)
        {
            mTextViewProductName = view.findViewById(R.id.textView_name);
            mTextViewProductPrice = view.findViewById(R.id.textView_price);
            mTextViewProductQuantity = view.findViewById(R.id.textView_quantity);
            mImageViewProductImage = view.findViewById(R.id.imageView_Product);
            mButtonPlus = view.findViewById(R.id.button_plus);
            mButtonMinus = view.findViewById(R.id.button_minus);
            mButtonRemove = view.findViewById(R.id.imageButton_delete);
        }

        public void update(final int position)
        {
            //Add product info to card
            final CartItem item = SingletonStore.getInstance(mContext).getCartItem(position);
            Product product = SingletonStore.getInstance(mContext).getProduct(item.getId());
            mTextViewProductName.setText(product.getName());
            mTextViewProductPrice.setText(product.getPrice() + "€");
            mTextViewProductQuantity.setText(String.valueOf(item.getQuantity()));

            // Add image to image view on card
            Glide.with(mContext)
                    .load(product.getImage())
                    .placeholder(R.drawable.no_image)
                    .thumbnail(0f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageViewProductImage);

            //Remove item from cart
            mButtonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setQuantity(item.getQuantity()+1);
                    mTextViewProductQuantity.setText(String.valueOf(item.getQuantity()));
                }
            });

            mButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getQuantity()!=1){
                        item.setQuantity(item.getQuantity()-1);
                        mTextViewProductQuantity.setText(String.valueOf(item.getQuantity()));
                    }
                    else {
                        mCartArrayList.remove(item);
                        //TODO update list after delete
                    }
                }
            });

            mButtonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCartArrayList.remove(item);
                    //TODO update list after delete
                }
            });
        }
    }
}

