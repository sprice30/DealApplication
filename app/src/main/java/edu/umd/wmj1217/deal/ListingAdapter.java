package edu.umd.wmj1217.deal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListingAdapter extends BaseAdapter implements Filterable {

    private List<SaleListing> mItems = new ArrayList<SaleListing>();
    private List<SaleListing> saveItems = new ArrayList<SaleListing>();
    private List<SaleListing> filteredItems = new ArrayList<>();
    private final Context mContext;
    private ItemFilter mFilter = new ItemFilter();

    private static final String TAG = "Lab-LocationLab";

    public ListingAdapter(Context context) {

        mContext = context;

    }

    // Add a ToDoItem to the adapter
    // Notify observers that the data set has changed

    public void add(SaleListing item) {

        mItems.add(item);
        saveItems.add(item);
        notifyDataSetChanged();

    }

    // Clears the list adapter of all items.

    public void clear() {
        System.out.println("IN CLEAR");
        mItems.clear();
        saveItems.clear();
        notifyDataSetChanged();

    }

    // Returns the number of ToDoItems

    @Override
    public int getCount() {

        return mItems.size();

    }

    // Retrieve the number of ToDoItems

    @Override
    public SaleListing getItem(int pos) {
        return mItems.get(pos);

    }

    // Get the ID for the ToDoItem
    // In this case it's just the position

    @Override
    public long getItemId(int pos) {

        return pos;

    }

    public Filter getFilter() {
        return mFilter;
    }

    // Create a View for the ToDoItem at specified position
    // Remember to check whether convertView holds an already allocated View
    // before created a new View.
    // Consider using the ViewHolder pattern to make scrolling more efficient
    // See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        Warning -- these fields have not yet been accounted for:
//        private String ID;
//        private String itemUrl;
//        private double listPrice;

        final SaleListing saleListing = (SaleListing) getItem(position);

        LinearLayout itemLayout = (LinearLayout) LinearLayout.inflate(mContext, R.layout.coupon_item, null);



//        final TextView descriptionText = (TextView) itemLayout.findViewById(R.id.description);
//        descriptionText.setText(saleListing.getDescription());

//        final TextView salePriceText = (TextView) itemLayout.findViewById(R.id.salePrice);
//        salePriceText.setText("" + saleListing.getSalePrice());

        // TODO in future - set onClickListeners
        final Button cancelButton;
        final Button shareButton;

        final TextView titleText = (TextView) itemLayout.findViewById(R.id.titleMain);
        titleText.setText(saleListing.getTitle());
        titleText.setTextColor(Color.BLACK);

        final ImageView flag = (ImageView) itemLayout.findViewById(R.id.imageViewMain);
        new DownloadImageTask(flag)
                .execute(saleListing.getImageUrl());

        // Return the View you just created
        return itemLayout;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;

        public DownloadImageTask(ImageView imageV) {
            this.image = imageV;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            System.out.println("URL: " + urldisplay);
            if (urldisplay == null) {
                System.out.println("URL is null");
            } else if (urldisplay.equals("")) {
                System.out.println("URL is empty");
            }
            Bitmap bitmap = null;
            if (urldisplay.length() > 0) {
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Exception: ", e.getMessage());
                    e.printStackTrace();
                }
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SaleListing> list = saveItems;

            int count = list.size();
            final ArrayList<SaleListing> nlist = new ArrayList<>(count);

            SaleListing filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getTitle().toLowerCase().contains(filterString) ||
                        filterableString.getDescription().toLowerCase().contains(filterString)) {
                    System.out.println(filterableString.getTitle());
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            ArrayList<SaleListing> hold  = (ArrayList<SaleListing>) results.values;
            if (hold.size() > 0 || constraint.toString().length() > 0) {
                mItems = hold;
            }
            else {
                mItems = saveItems;
            }
            notifyDataSetChanged();
        }

    }
}
