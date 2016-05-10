package edu.umd.wmj1217.deal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    final static String TAG = "DealsApp";
    public static ListingAdapter listingAdapter;
    public static ListView m_list;


    public static ArrayList<SaleListing> electronics = new ArrayList<>();
    public static ArrayList<SaleListing> home = new ArrayList<>();
    public static ArrayList<SaleListing> foodAndWine = new ArrayList<>();
    public static ArrayList<SaleListing> shirts = new ArrayList<>();
    public static ArrayList<SaleListing> couponBook = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        SearchView searchView = (SearchView)findViewById(R.id.search_view);
        searchView.setQueryHint("Search for deal");
//        searchView.onActionViewExpanded();
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

                myIntent.putExtra("Id", listing.getID());
                myIntent.putExtra("Title", listing.getTitle());
                myIntent.putExtra("Description", listing.getDescription());
                myIntent.putExtra("Url", listing.getItemUrl());
                myIntent.putExtra("Image", listing.getImageUrl());
                myIntent.putExtra("SalePrice", listing.getSalePrice() + "");
                myIntent.putExtra("ListPrice", listing.getListPrice() + "");

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
        WootFetcher fetch = new WootFetcher(null, (List) new ArrayList<>());
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

        WootFetcher fetch;

        if(id == R.id.clothing){
            if (shirts.isEmpty()) {
                fetch = new WootFetcher("shirt", shirts);
            } else {
                loadDealList(shirts);
            }
        } else if (id == R.id.electronics){
            if (shirts.isEmpty()) {
                fetch = new WootFetcher("electronics", electronics);
            } else {
                loadDealList(electronics);
            }
        } else if(id == R.id.food){
            if (shirts.isEmpty()) {
                fetch = new WootFetcher("wine", foodAndWine);
            } else {
                loadDealList(foodAndWine);
            }
        } else if (id == R.id.travel) {
            if (shirts.isEmpty()) {
                fetch = new WootFetcher("home", home);
            } else {
                loadDealList(home);
            }
        } else {
            System.out.println("Coupon book clicked!");
            loadDealList(couponBook);
        }

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadDealList(List<SaleListing> list) {
        listingAdapter.clear();
        Iterator<SaleListing> it = list.iterator();
        while (it.hasNext()) {
            listingAdapter.add(it.next());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }



}

