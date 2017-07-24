package victorylink.com.flickerapp.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import victorylink.com.flickerapp.Models.Constants;
import victorylink.com.flickerapp.Models.data.PhotoRecord;
import victorylink.com.flickerapp.Models.data.FlickerDbHelper;
import victorylink.com.flickerapp.Parsers.Photo;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Activity.DetailActivity;

/**
 * Created by MrHacker on 7/17/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> implements Filterable {
    private ArrayList<Photo> dataset;
    private ArrayList<Photo> filteredList;
    private HashMap<String, PhotoRecord> favoriteMap;
    FlickerDbHelper database;
    private Context context = null;
    private boolean isMain;

    public PhotoAdapter(ArrayList<Photo> newDataset, Context newContext, boolean isMain) {
        dataset = newDataset;
        filteredList = newDataset;
        context = newContext;
        this.isMain = isMain;
        database = new FlickerDbHelper(context);
        favoriteMap = database.getAllFavoritePhotos("Islam");
        if (favoriteMap == null) {
            favoriteMap = new HashMap<>();
        }
    }


    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Photo photoItem = filteredList.get(position);

        if (favoriteMap.containsKey(photoItem.getId())) {
            holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.on));
            holder.favorite.setChecked(true);
        } else {
            holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.off));
            holder.favorite.setChecked(false);
        }

        if (!isMain) {
            printMap(favoriteMap);
            holder.forward.setVisibility(View.GONE);
            holder.favorite.setVisibility(View.VISIBLE);

            holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        database.deleteFavoritePhoto(photoItem.getId());
                        holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.off));
                        favoriteMap.remove(photoItem.getId());
                       // holder.favorite.setChecked(false);
                    } else {
                        PhotoRecord favoriteItem = PhotoRecord.setPhotoRecord(photoItem, "Islam");
                        if (database.addPhotoRecordToDB(favoriteItem)) {
                            holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.on));
                            favoriteMap.put(photoItem.getId(), favoriteItem);
                       //     holder.favorite.setChecked(true);
                        }
                    }
                    printMap(favoriteMap);
                }
            });
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + photoItem.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        Picasso.with(context).load(Constants.getPhotoUrl(photoItem)).into(holder.getPhoto());
        holder.getTitle().setText(photoItem.getTitle());

        holder.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("userId", photoItem.getOwner());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
        return filteredList.size();
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
        private ToggleButton favorite;
        private final Context context;

        public ViewHolder(View itemView) {
            // create a new view
            super(itemView);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            title = (TextView) itemView.findViewById(R.id.photo_title);
            forward = (Button) itemView.findViewById(R.id.forward_btn);
            favorite = (ToggleButton) itemView.findViewById(R.id.favorite);
        }

        public ImageView getPhoto() {
            return photo;
        }

        public TextView getTitle() {
            return title;
        }
    }
}
