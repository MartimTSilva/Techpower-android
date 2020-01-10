//package com.example.techpower.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.example.techpower.R;
//import com.example.techpower.models.Product;
//import com.example.techpower.models.SingletonStore;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class CartListAdapter extends BaseAdapter {
//    private Context mContext;
//    private LayoutInflater mLayoutInflater;
//    private HashMap mCartArrayList;
//
//    public CartListAdapter(Context context, ArrayList<Cart> cartArrayList) {
//        mContext = context;
//        mCartArrayList = cartArrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return CartListAdapter.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return CartListAdapter.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (mLayoutInflater == null) {
//            mLayoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
//        }
//
//        if (convertView == null) {
//            convertView = mLayoutInflater.inflate(R.layout.cart_item, null);
//        }
//
//        ViewHolderList viewHolder = (ViewHolderList) convertView.getTag();
//        if (viewHolder == null) {
//            viewHolder = new ViewHolderList(convertView);
//            convertView.setTag(viewHolder);
//        }
//
//        viewHolder.update(position);
//
//        return convertView;
//    }
//
//    private class ViewHolderList {
//        private TextView mTextViewProductName;
//        private TextView mTextViewProductPrice;
//        private TextView mTextViewProductQuantity;
//        private ImageView mImageViewProductImage;
//
//        public ViewHolderList(View view) {
//            mTextViewProductName = view.findViewById(R.id.textView_productName);
//            mTextViewProductPrice = view.findViewById(R.id.textView_productPrice);
//            mTextViewProductQuantity = view.findViewById(R.id.textView_productQuantity);
//            mImageViewProductImage = view.findViewById(R.id.textView_productName);
//        }
//
//        public void update(int position) {
//            // Add product info to card
//            Cart cart = CartListAdapter.get(position);
//            Product product = SingletonStore.getInstance(mContext).getProduct(cart.getId);
//            mTextViewProductName.setText(product.getName());
//            mTextViewProductPrice.setText(product.getPrice() + "€");
//            mTextViewProductQuantity.setText(Cart.getQuantity);
//
//            // Add image to image view on card
//            Glide.with(mContext)
//                    .load(product.getImage())
//                    .placeholder(R.drawable.no_image)
//                    .thumbnail(0f)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(mImageViewProductImage);
//        }
//    }
//}
