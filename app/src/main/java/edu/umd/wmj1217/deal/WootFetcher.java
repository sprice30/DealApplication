package edu.umd.wmj1217.deal;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by seankallungal on 4/27/16.
 */
public class WootFetcher {
    public static final String TAG = "WootFetcher";

    public List<SaleListing> listArray;
    final String DEFAULT_DOMAIN = "www.woot.com";
    final String DEFAULT_URL = "http://api.woot.com/2/events.json?key=71c1a475436e487383411769833f9539&select=Offers.Items,Offers.Features,Offers.Title,Offers.Url,Photos&site=";

    public WootFetcher(String domain, List<SaleListing> listArray) {
        this.listArray = listArray;
        new NetworkingTask().execute(domain);

    }

    public class NetworkingTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return getItems(params[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            System.out.println("POSTEXE");

            if (result) {
                // update list adapter in main activity
                // TODO - make this code better, remove static variables
                MainActivity.listingAdapter.clear();
                getList();
            }
        }
    }

    public void getList() {
        Iterator<SaleListing> it = listArray.iterator();
        while (it.hasNext()) {
            MainActivity.listingAdapter.add(it.next());
        }
    }

    private boolean getItems(String domain) throws IOException {
        HttpURLConnection connection = null;

        try {
            String site = (domain == null) ? DEFAULT_DOMAIN : domain + ".woot.com";
            URL url = new URL(DEFAULT_URL + site);
            Scanner scan = new Scanner(url.openStream());
            String str = new String();
            while (scan.hasNext())
                str += scan.nextLine();
            scan.close();

            if (str != null) {
                JSONArray obj = new JSONArray(str);
                listArray.clear();
                for (int i = 1; i < obj.length(); i++) {
                    JSONObject offer = obj.getJSONObject(i).getJSONArray("Offers").getJSONObject(0);
                    String itemUrl = offer.getString("Url");
                    String title = offer.getString("Title");
                    String feature = offer.getString("Features");
                    JSONArray items = offer.getJSONArray("Items");
                    String id = items.getJSONObject(0).getString("Id");
                    double salePrice = Double.parseDouble(items.getJSONObject(0).getString("SalePrice"));
                    double listPrice = 0;
                    String imageUrl = "";
                    try {
                        listPrice = Double.parseDouble(items.getJSONObject(0).getString("ListPrice"));
                    }
                    catch (Exception e) {
                        listPrice = 0;
                    }
                    try {
                        imageUrl = obj.getJSONObject(i).getJSONArray("Photos").getJSONObject(0).getString("Url");
                    } catch (Exception e) {
                        Log.d(TAG, "Unable to obtain image URL for " + title);
                    }

                    listArray.add(new SaleListing(id, title, feature, itemUrl, imageUrl, salePrice, listPrice));
                }
            }
            return true;

        } catch (MalformedURLException e) {
            throw new IOException("Invalid URL.", e);
        } catch (NullPointerException e) {
            Intent result = new Intent();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }
}
