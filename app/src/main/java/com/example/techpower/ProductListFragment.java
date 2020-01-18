package com.example.techpower;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.content.Intent;
import android.widget.Toast;

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
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        // Set on list item click
        mListViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);
                Intent product_intent = new Intent(getActivity(), ProductDetailsActivity.class);
                product_intent.putExtra("ID", product.getId());
                startActivity(product_intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SingletonStore.getInstance(getContext()).getAllProductsAPI(getContext(), SingletonStore.isConnectedInternet(getContext()));

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Set fab click listener
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CartActivity.class);
                startActivity(intent);
            }
        });

        // Add Singleton Listener
        SingletonStore.getInstance(getContext()).setProductListener(this);

        return view;
    }

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
                //Create array to store all products with the searched term
                ArrayList<Product> filteredProducts = new ArrayList<Product>();
                //Looks for products with the searched term in the title and adds it to the array
                for (Product product : SingletonStore.getInstance(getContext()).getProductsDB()) {
                    if (product.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredProducts.add(product);
                    }
                }
                //Shows search result
                mListViewProducts.setAdapter(new ProductListAdapter(getContext(), filteredProducts));

                return true;
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
        SingletonStore.getInstance(getContext()).getAllProductsAPI(getContext(), SingletonStore.isConnectedInternet(getContext()));
    }
}
