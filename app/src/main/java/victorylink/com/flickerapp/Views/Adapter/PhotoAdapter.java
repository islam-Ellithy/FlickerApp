package victorylink.com.flickerapp.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import victorylink.com.flickerapp.Other.Constants;
import victorylink.com.flickerapp.Other.database.FlickerDbHelper;
import victorylink.com.flickerapp.Other.database.PhotoRecord;
import victorylink.com.flickerapp.Other.Parsers.Photo;
import victorylink.com.flickerapp.R;

/**
 * Created by MrHacker on 7/17/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> implements Filterable {
    private ArrayList<Photo> dataset;
    private ArrayList<Photo> filteredList;

    FlickerDbHelper database;
    private Context context = null;
    private OnAdapterInteractionListener adapterInteractionListener;

    public PhotoAdapter(ArrayList<Photo> newDataset, Context newContext) {
        dataset = newDataset;
        filteredList = newDataset;
        context = newContext;
        database = new FlickerDbHelper(context);
    }

    public void setAdapterInteractionListener(OnAdapterInteractionListener adapterInteractionListener) {
        this.adapterInteractionListener = adapterInteractionListener;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Photo photoItem = filteredList.get(position);

        Picasso.with(context).load(Constants.getPhotoUrl(photoItem)).into(holder.getPhoto());
        holder.getTitle().setText(photoItem.getTitle());

        holder.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapterInteractionListener.onAdapterInteraction(photoItem.getOwner());

            }
        });
    }

    private void printMap(HashMap<String, PhotoRecord> favoriteMap) {
        for (Map.Entry<String, PhotoRecord> iter : favoriteMap.entrySet()) {
            Log.v("MAP", "Key = " + iter.getKey());
        }
    }

    public void swapArray(ArrayList<Photo> dataArray) {
        this.dataset = dataArray;
        this.filteredList = dataArray;

        Log.v("TAG", "size on set = " + this.dataset.size() + "");

        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (filteredList != null)
            return filteredList.size();
        return 0;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    filteredList = dataset;
                } else {

                    ArrayList<Photo> mFilteredList = new ArrayList<>();

                    for (Photo item : dataset) {

                        if (item.getTitle().toLowerCase().contains(charString)) {
                            mFilteredList.add(item);
                        }
                    }

                    filteredList = mFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Photo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        private ImageView photo;
        private TextView title;
        private Button forward;

        private final Context context;

        public ViewHolder(View itemView) {
            // create a new view
            super(itemView);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            title = (TextView) itemView.findViewById(R.id.photo_title);
            forward = (Button) itemView.findViewById(R.id.forward_btn);
        }

        public ImageView getPhoto() {
            return photo;
        }

        public TextView getTitle() {
            return title;
        }
    }

    public interface OnAdapterInteractionListener {
        // TODO: Update argument type and name
        void onAdapterInteraction(String userId);
    }

}
