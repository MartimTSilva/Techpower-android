package com.example.techpower;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.techpower.adapters.ProductListAdapter;
import com.example.techpower.listeners.ProductListener;
import com.example.techpower.models.Product;
import com.example.techpower.models.SingletonStore;
import com.example.techpower.utils.ProductJsonParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment implements ProductListener {

    private ListView mListViewProducts;
    private ProductListAdapter mProductListAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        // Add options menu
        setHasOptionsMenu(true);

        mListViewProducts = view.findViewById(R.id.listView_products);

        // Set on list item click
        mListViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: Add intent to open product details
            }
        });

        // Set fab click listener
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add intent to open shopping cart activity
            }
        });

        // Add Singleton Listener
        SingletonStore.getInstance(getContext()).setProductListener(this);

        return view;
    }

    // TODO: Finish search
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Add menu_search as options menu
        inflater.inflate(R.menu.menu_search, menu);

        // Set item click listener
        MenuItem itemSearch = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onRefreshProductList(ArrayList<Product> productArrayList) {
        mProductListAdapter = new ProductListAdapter(getContext(), productArrayList);
        mListViewProducts.setAdapter(mProductListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonStore.getInstance(getContext()).getAllProductsAPI(getContext(), ProductJsonParser.isConnectionInternet(getContext()));
    }
}
