package victorylink.com.flickerapp.Views.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import victorylink.com.flickerapp.Controllers.DbController;
import victorylink.com.flickerapp.Controllers.DownloadController;
import victorylink.com.flickerapp.Other.Parsers.Photo;
import victorylink.com.flickerapp.Other.database.PhotoRecord;
import victorylink.com.flickerapp.R;


public class FavoritePhotosAdapter extends
        RecyclerView.Adapter<FavoritePhotosAdapter.ViewHolder>
        implements Filterable {

    private ArrayList<PhotoRecord> photoArrayList;
    private ArrayList<PhotoRecord> filteredList;
    private HashMap<String, PhotoRecord> favoriteListMap;
    private Photo photoItem = null;
    private DbController dbController;
    private Context context = null;
    private DownloadController controller;

    public FavoritePhotosAdapter(ArrayList<PhotoRecord> arrayList, Context newContext) {
        photoArrayList = arrayList;
        filteredList = arrayList;
        context = newContext;
        controller = new DownloadController(context);
        dbController = new DbController(context);
        favoriteListMap = dbController.getAllFavoritePhotosFromDb("islam");
        if (favoriteListMap == null) {
            favoriteListMap = new HashMap<>();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_image_layout, parent, false);
        return new ViewHolder(v);
    }

    //call the Download controller
    private void downloadImage(String url, String photoId) {
        try {

            controller.DownloadJpgImage(url, photoId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.photoItem = filteredList.get(position);


        Picasso.with(context).load(holder.photoItem.getPhotoUrl()).into(holder.getPhoto());


        holder.getTitle().setText(holder.photoItem.getPhotoTitle());


        if (favoriteListMap.containsKey(holder.photoItem.getPhotoId())) {
            holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.on));
            holder.favorite.setChecked(true);

        } else {
            holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.off));
            holder.favorite.setChecked(false);
        }

        printMap(favoriteListMap);


        holder.download.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Checked", isChecked + "");
                if (isChecked) {
                    holder.download.setAlpha(1);

                    downloadImage(holder.photoItem.getPhotoUrl(), holder.photoItem.getPhotoId());

                } else {
                    holder.download.setAlpha((float) 0.4);

                }
            }
        });


        holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //mark an item from recycle view as favorite and insert the record to DB
                if (isChecked) {

                    holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.on));

                } //mark an item from recycle view as un favorite and insert the record to DB
                else {
                    holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.off));
                    dbController.deletePhotoFromDb(holder.photoItem.getPhotoId());
                    favoriteListMap = dbController.getAllFavoritePhotosFromDb("islam");
                    swapArray(dbController.getArrayListFromMap(favoriteListMap));
                }

            }
        });
        holder.title.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + photoItem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void printMap(HashMap<String, PhotoRecord> favoriteMap) {
        for (Map.Entry<String, PhotoRecord> iter : favoriteMap.entrySet()) {
            Log.v("MAP", "Key = " + iter.getKey());
        }
    }

    public void swapArray(ArrayList<PhotoRecord> dataArray) {
        this.photoArrayList = dataArray;
        this.filteredList = dataArray;

        Log.v("TAG", "size on set = " + this.photoArrayList.size() + "");

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

                    filteredList = photoArrayList;
                } else {

                    ArrayList<PhotoRecord> mFilteredList = new ArrayList<>();

                    for (PhotoRecord item : photoArrayList) {

                        if (item.getPhotoTitle().toLowerCase().contains(charString)) {
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
                filteredList = (ArrayList<PhotoRecord>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView photo;
        TextView title;
        PhotoRecord photoItem;
        ToggleButton favorite;
        ToggleButton download;


        private final Context context;


        ViewHolder(View itemView) {
            // create a new view
            super(itemView);
            context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            title = (TextView) itemView.findViewById(R.id.photo_title);
            favorite = (ToggleButton) itemView.findViewById(R.id.favorite);
            download = (ToggleButton) itemView.findViewById(R.id.download);
            favorite.setChecked(false);
            favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.off));
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