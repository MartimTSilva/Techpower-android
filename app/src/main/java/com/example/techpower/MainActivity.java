package com.example.techpower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.techpower.models.CartItem;
import com.example.techpower.models.Category;
import com.example.techpower.models.SingletonStore;
import com.example.techpower.models.User;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private NavigationView navigationView;
    private Menu menu;
    private ArrayList<CartItem> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer);

        menu = navigationView.getMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();

        View header = navigationView.getHeaderView(0);
        TextView mNameTextView = header.findViewById(R.id.textView_user_name);
        TextView mEmailTextView = header.findViewById(R.id.textView_user_email);
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        mNameTextView.setText(preferences.getString("username", "Guest"));
        mEmailTextView.setText(preferences.getString("email", "guest@email.com"));

        Fragment fragment = new ProductListFragment();
        setTitle("Techpower");
        mFragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        cart = SingletonStore.getInstance(this).getCart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        menu.removeGroup(R.string.nav_categories);
        menu.removeGroup(R.string.nav_account);

        SingletonStore.getInstance(getApplicationContext()).getAllCategoriesAPI(getApplicationContext(), SingletonStore.isConnectedInternet(getApplicationContext()));
        // Add category submenu
        ArrayList<Category> categoryList = SingletonStore.getInstance(getApplicationContext()).getCategoriesDB();
        Menu categoriesSubMenu = menu.addSubMenu(R.string.nav_categories, Menu.NONE, 0, R.string.nav_categories);
        for (final Category category: categoryList) {
            //Adds all the categories and sets a listener
            categoriesSubMenu.add(Menu.NONE, category.getId(), Menu.NONE,category.getName()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //Gets the ID of the clicked category
                    int category_id = menuItem.getItemId();

                    //Gets products by category
                    SingletonStore.getInstance(getApplicationContext()).getProductsByCategory(getApplicationContext(), category_id);

                    //Changes title to the category name
                    setTitle(category.getName());

                    return false;
                }
            });
        }
        navigationView.invalidate();

        // Add account submenu
        SharedPreferences preferences = getSharedPreferences(getString(R.string.app_preferences), MODE_PRIVATE);
        Menu accountSubMenu = menu.addSubMenu(R.string.nav_account, Menu.NONE, 1, R.string.nav_account);
        if (preferences.getString("authkey", null) == null) {
            accountSubMenu.add(Menu.NONE, R.string.nav_login, Menu.NONE, R.string.nav_login);
            accountSubMenu.add(Menu.NONE, R.string.nav_signup, Menu.NONE, R.string.nav_signup);
        } else {
            accountSubMenu.add(Menu.NONE, R.string.nav_userPage, Menu.NONE, R.string.nav_userPage);
            accountSubMenu.add(Menu.NONE, R.string.nav_logout, Menu.NONE, R.string.nav_logout);
        }
        navigationView.invalidate();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.string.nav_login:
                Intent login_intent = new Intent(this, LoginActivity.class);
                startActivity(login_intent);
                break;

            case R.string.nav_signup:
                Intent signup_intent = new Intent(this, SignUpActivity.class);
                startActivity(signup_intent);
                break;

            case R.string.nav_logout:
                User.deleteUser(getApplicationContext());
                finish();
                startActivity(getIntent());
                Toast.makeText(getApplicationContext(), R.string.logout_success, Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_settings:
                Intent settings_intent = new Intent(this, SettingsActivity.class);
                startActivity(settings_intent);
                break;

            case R.string.nav_userPage:
                Intent userPage_intent = new Intent(this, UserActivity.class);
                startActivity(userPage_intent);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
