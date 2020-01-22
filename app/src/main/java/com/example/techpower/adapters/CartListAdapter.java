package com.example.techpower.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.techpower.R;
import com.example.techpower.models.CartItem;
import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;

import java.util.ArrayList;

public class CartListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<CartItem> mCartArrayList;
    private  OnCartItemClickListener mOnCartItemClickListener;

    public CartListAdapter(Context Context, ArrayList<CartItem> CartArrayList, OnCartItemClickListener onCartItemClickListener) {
        mContext = Context;
        mCartArrayList = CartArrayList;
        mOnCartItemClickListener = onCartItemClickListener;
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
            viewHolder = new ViewHolderList(convertView, mOnCartItemClickListener);
            convertView.setTag(viewHolder);
        }
        viewHolder.update(position);

        return convertView;
    }


    private class ViewHolderList  implements  View.OnClickListener{
        private TextView mTextViewProductName;
        private TextView mTextViewProductPrice;
        private TextView mTextViewProductQuantity;
        private ImageView mImageViewProductImage;
        private ImageButton mButtonPlus;
        private ImageButton mButtonMinus;
        private ImageButton mButtonRemove;
        private OnCartItemClickListener onCartItemClickListener;
        private int mPostition;
        private int product_id;


        public ViewHolderList(View view, OnCartItemClickListener onCartItemClickListener) {
            mTextViewProductName = view.findViewById(R.id.textView_name);
            mTextViewProductPrice = view.findViewById(R.id.textView_price);
            mTextViewProductQuantity = view.findViewById(R.id.textView_quantity);
            mImageViewProductImage = view.findViewById(R.id.imageView_Product);
            mButtonPlus = view.findViewById(R.id.button_plus);
            mButtonMinus = view.findViewById(R.id.button_minus);
            mButtonRemove = view.findViewById(R.id.imageButton_delete);
            this.onCartItemClickListener = onCartItemClickListener;

            view.setOnClickListener(this);
        }

        public void update(final int position) {
            //Add product info to card
            final CartItem item = SingletonStore.getInstance(mContext).getCartItem(position);
            Product product = SingletonStore.getInstance(mContext).getProduct(item.getId());
            mTextViewProductName.setText(product.getName());
            mTextViewProductPrice.setText(product.getPrice() + "€");
            mTextViewProductQuantity.setText(String.valueOf(item.getQuantity()));
            mPostition = position;
            product_id = item.getId();
            // Add image to image view on card
            Glide.with(mContext)
                    .load(product.getImage())
                    .placeholder(R.drawable.no_image)
                    .thumbnail(0f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageViewProductImage);

            //Add 1 to quantity
            mButtonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getQuantity() >= 5) {
                        Toast.makeText(mContext, "You can't purchase more than 5 of an Item", Toast.LENGTH_SHORT).show();
                    } else {
                        item.setQuantity(item.getQuantity() + 1);
                        mTextViewProductQuantity.setText(String.valueOf(item.getQuantity()));
                    }
                }
            });
            //Remove 1 from quantity with confirmation to delete in case quantity = 0
            mButtonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getQuantity() != 1) {
                        item.setQuantity(item.getQuantity() - 1);
                        mTextViewProductQuantity.setText(String.valueOf(item.getQuantity()));
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage(R.string.cart_quantity_0_alert_dialog)
                                .setPositiveButton((R.string.cart_item_delete_alert_ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mCartArrayList.remove(item);
                                        notifyDataSetChanged();
                                    }
                                }).setNegativeButton((R.string.cart_item_delete_alert_cancel), null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });
            //Remove cart item with confirmation
            mButtonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(R.string.cart_delete_alert_dialog)
                            .setPositiveButton((R.string.cart_item_delete_alert_ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mCartArrayList.remove(item);
                                    notifyDataSetChanged();
                                }
                            }).setNegativeButton((R.string.cart_item_delete_alert_cancel), null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            onCartItemClickListener.onCartItemClick(mPostition, product_id);
        }
    }

    public interface OnCartItemClickListener {
        void onCartItemClick(int position, int id);
    }
}

