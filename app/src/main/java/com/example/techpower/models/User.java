package com.example.techpower.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.techpower.R;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int id;
    private String username, email, password, firstName, lastName, phone, address, nif, postal_code, city, country;

    public User(String username, String email, String password, String firstName, String lastName, String phone, String address, String nif, String postal_code, String city, String country) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.nif = nif;
        this.postal_code = postal_code;
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPostalCode() {
        return postal_code;
    }

    public void setPostalCode(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static void saveUser(Context context, JSONObject response, SharedPreferences preferences){
        try {
            // Saves authkey and user information in the shared preferences
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id", response.getString("id"));
            editor.putString("username", response.getString("username"));
            editor.putString("authkey", response.getString("auth_key"));
            editor.putString("email", response.getString("email"));
            editor.putString("firstName", response.getString("firstName"));
            editor.putString("lastName", response.getString("lastName"));
            editor.putString("phone", response.getString("phone"));
            editor.putString("address", response.getString("address"));
            editor.putString("nif", response.getString("nif"));
            editor.putString("postal_code", response.getString("postal_code"));
            editor.putString("city", response.getString("city"));
            editor.putString("country", response.getString("country"));
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(Context context){
        try {
            // Removes user data from shared preferences
            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.app_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("id");
            editor.remove("username");
            editor.remove("authkey");
            editor.remove("email");
            editor.remove("firstName");
            editor.remove("lastName");
            editor.remove("phone");
            editor.remove("address");
            editor.remove("nif");
            editor.remove("postal_code");
            editor.remove("city");
            editor.remove("country");
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUsername(String username){
        // Vê se o utilizador inseriu um username
        if (TextUtils.isEmpty(username)) {
            return false;
        } else if (username.length() > 255) {
            return false;
        } else if (username.length() < 2) {
            return false;
        }
        return true;
    }

    public static boolean checkEmail(String email){
        // Vê se o utilizador inseriu um e-amil e se é válido
        if (TextUtils.isEmpty(email)) {
            return false;
        } else if (!email.contains("@")) {
            return false;
        } else if (email.length() > 255) {
            return false;
        } else if (email.length() < 6) {
            return false;
        }
        return true;
    }

    public static boolean checkFirstName(String firstName){
        // Vê se o utilizador inseriu um nome
        if (TextUtils.isEmpty(firstName)) {
            return false;
        } else if (firstName.length() > 50) {
            return false;
        } else if (firstName.length() < 2) {
            return false;
        }
        return true;
    }

    public static boolean checkLastName(String lastName){
        // Vê se o utilizador inseriu um apelido
        if (TextUtils.isEmpty(lastName)) {
            return false;
        } else if (lastName.length() > 50) {
            return false;
        } else if (lastName.length() < 2) {
            return false;
        }
        return true;
    }

    public static boolean checkAddress(String address){
        // Vê se o utilizador inseriu uma morada
        if (TextUtils.isEmpty(address)) {
            return false;
        } else if (address.length() > 255) {
            return false;
        } else if (address.length() < 2) {
            return false;
        }
        return true;
    }

    public static boolean checkCity(String city){
        // Vê se o utilizador inseriu uma cidade
        if (TextUtils.isEmpty(city)) {
            return false;
        } else if (city.length() > 255) {
            return false;
        } else if (city.length() < 2) {
            return false;
        }
        return true;
    }

    public static boolean checkCountry(String country){
        // Vê se o utilizador inseriu um país
        if (TextUtils.isEmpty(country)) {
            return false;
        } else if (country.length() > 255) {
            return false;
        } else if (country.length() < 2) {
            return false;
        }
        return true;
    }

    public static boolean checkPostalCode(String postal_code){
        // Vê se o utilizador inseriu um código postal
        if (TextUtils.isEmpty(postal_code)) {
            return false;
        } else if (postal_code.length() > 8) {
            return false;
        } else if (postal_code.length() < 4) {
            return false;
        }
        return true;
    }

    public static boolean checkPhone(String phone){
        // Vê se o utilizador inseriu um telefone
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else if (phone.length() != 9) {
            return false;
        }
        return true;
    }

    public static boolean checkNif(String nif){
        // Vê se o utilizador inseriu um nif
        if (TextUtils.isEmpty(nif)) {
            return false;
        } else if (nif.length() != 9) {
            return false;
        }
        return true;
    }
}