package edu.umd.wmj1217.deal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by samprice on 4/26/16.
 */
public class CouponDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_details);
    }

    public void onCancel(View v){
    Intent myIntent = new Intent(CouponDetails.this, MainActivity.class);
        startActivity(myIntent);
    }

}
