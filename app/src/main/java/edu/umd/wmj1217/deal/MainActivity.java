package edu.umd.wmj1217.deal;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.*;


public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    final static String TAG = "DealsApp";
    public static ListingAdapter listingAdapter;
    public static ListView m_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SearchView searchView = (SearchView)findViewById(R.id.search_view);
        searchView.setQueryHint("Search coupon");
        searchView.onActionViewExpanded();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // get list
        m_list = (ListView) findViewById(R.id.list);

        // instantiate and set list adapter
        listingAdapter = new ListingAdapter(getApplicationContext());
        m_list.setAdapter(listingAdapter);

        // set on click listenter
        m_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // on item click, go to coupon_details
                Intent myIntent = new Intent(MainActivity.this, CouponDetails.class);
                SaleListing listing = listingAdapter.getItem(position);
                myIntent.putExtra("Title", listing.getTitle());
                myIntent.putExtra("SalePrice", listing.getSalePrice() + "");
                myIntent.putExtra("Description", listing.getDescription());
                myIntent.putExtra("Image", listing.getImageUrl());
                myIntent.putExtra("Url", listing.getItemUrl());
                startActivity(myIntent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(listingAdapter!=null) {
                    System.out.println(newText);
                    listingAdapter.getFilter().filter(newText);
                    return true;
                }
                return false;
            }
        });
        // fetch data
        // TODO - Loading icon while waiting for result
        WootFetcher fetch = new WootFetcher(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.clothing){
            WootFetcher fetch1 = new WootFetcher("shirt");
        }else if (id == R.id.electronics){
            WootFetcher fetch2 = new WootFetcher("electronics");
        }else if(id == R.id.food){
            WootFetcher fetch3 = new WootFetcher("wine");
        }else{//id == R.id.travel
            WootFetcher fetch4 = new WootFetcher("home");
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }



}

