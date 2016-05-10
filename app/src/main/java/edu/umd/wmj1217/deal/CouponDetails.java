package edu.umd.wmj1217.deal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by samprice on 4/26/16.
 */
public class CouponDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_details);
        Intent intent = getIntent();

        // get data from intent and set content
        final TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText(intent.getStringExtra("Title"));
        titleText.setTextColor(Color.BLACK);

        final TextView descriptionText = (TextView) findViewById(R.id.description);
        descriptionText.setText(Html.fromHtml(intent.getStringExtra("Description")));
        descriptionText.setTextColor(Color.BLACK);

        final TextView saleText = (TextView) findViewById(R.id.salePrice);
        saleText.setText(intent.getStringExtra("SalePrice"));


        final ImageView image = (ImageView) findViewById(R.id.imageView);
        new DownloadImageTask(image)
                .execute(intent.getStringExtra("Image"));
    }

    public void onCancel(View v){
        finish();
    }

    public void onSaveClick(View v){
        String id = getIntent().getStringExtra("Id");
        String title = getIntent().getStringExtra("Title");
        String description = getIntent().getStringExtra("Description");
        String itemUrl = getIntent().getStringExtra("Url");
        String imageUrl = getIntent().getStringExtra("Image");
        System.out.println(getIntent().getStringExtra("SalePrice"));

        double salePrice = Double.valueOf(getIntent().getStringExtra("SalePrice").substring(1).replaceAll(",", ""));
        double listPrice = Double.valueOf(getIntent().getStringExtra("ListPrice").substring(1).replaceAll(",", ""));

        Toast.makeText(getApplicationContext(), title + " was saved to Coupon Book", Toast.LENGTH_SHORT).show();

        MainActivity.couponBook.add(new SaleListing(id, title, description, itemUrl, imageUrl, salePrice, listPrice));

    }

    public void onDealClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("Url")));
        startActivity(browserIntent);
    }
    public void onShareClick(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this deal!");
        intent.putExtra(Intent.EXTRA_TEXT,"Check out this deal for " + getIntent().getStringExtra("Title") + "! " + getIntent().getStringExtra("Url"));
        startActivity(Intent.createChooser(intent, "Share this deal for " + getIntent().getStringExtra("Title")));
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public DownloadImageTask(ImageView imageV) {
            this.image = imageV;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Exception: ", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
    }

}
