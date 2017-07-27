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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import victorylink.com.flickerapp.Controllers.DownloadController;
import victorylink.com.flickerapp.Models.Constants;
import victorylink.com.flickerapp.Other.Parsers.Photo;
import victorylink.com.flickerapp.Other.data.FlickerDbHelper;
import victorylink.com.flickerapp.Other.data.PhotoRecord;
import victorylink.com.flickerapp.R;


public class PhotoDetailAdapter extends
        RecyclerView.Adapter<victorylink.com.flickerapp.Views.Adapter.PhotoDetailAdapter.ViewHolder>
        implements Filterable {

    private ArrayList<Photo> dataset;
    private ArrayList<Photo> filteredList;
    private HashMap<String, PhotoRecord> favoriteMap;
    private HashMap<String,File> downloadMap;
    Photo photoItem = null ;
    FlickerDbHelper database;
    private Context context = null;

    public PhotoDetailAdapter(ArrayList<Photo> newDataset, Context newContext) {
        dataset = newDataset;
        filteredList = newDataset;
        context = newContext;
        database = new FlickerDbHelper(context);
        favoriteMap = database.getAllFavoritePhotos("islam");
       // downloadMap = database.getAllDownloadedPhotos(Profile);
        if (favoriteMap == null) {
            favoriteMap = new HashMap<>();
            downloadMap = new HashMap<>();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_image_layout, parent, false);
        return new ViewHolder(v);
    }

    //call the Download controller
    private void downloadImage(String url,String photoId)
    {
        try {
            DownloadController controller = new DownloadController(context);

            controller.DownloadPngImage(url,photoId);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        photoItem = filteredList.get(position);

        Picasso.with(context).load(Constants.getPhotoUrl(photoItem)).into(holder.getPhoto());
        holder.getTitle().setText(photoItem.getTitle());

/*
        if (favoriteMap.containsKey(photoItem.getId())) {
            holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.on));
            holder.favorite.setChecked(true);
        }
        else
        {
            holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.off));
            holder.favorite.setChecked(false);
        }
        if (downloadMap.containsKey(photoItem.getId())) {
            holder.download.setChecked(true);
            holder.download.setAlpha(1);
        }else
        {
            holder.download.setChecked(false);
        }

        printMap(favoriteMap);
*/

        holder.download.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Checked", isChecked + "");
                if (isChecked) {
                    holder.download.setAlpha(1);
                    downloadImage(Constants.getPhotoUrl(photoItem),photoItem.getId());

                } else {
                    holder.download.setAlpha((float) 0.4);
/*
                    PhotoRecord downloadedItem = PhotoRecord.setPhotoRecord(photoItem, "Islam");
                    downloadedItem.setDownloaded(1);
                    downloadedItem.setImage(DbBitmapUtility.
                            getBytes(DownloadModel.getBitmapFromURL(Constants.getPhotoUrl(photoItem))));

                    downloadMap.put(photoItem.getId(), downloadedItem);

                    database.updatePhotoRecord(downloadedItem.getPhotoId(), downloadedItem);
  */
                }

            }
        });


        holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PhotoRecord favoriteItem = PhotoRecord.setPhotoRecord(photoItem, "Islam");
                    if (database.addPhotoRecordToDB(favoriteItem)) {
                        holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.on));
                        favoriteMap.put(photoItem.getId(), favoriteItem);
                    }
                } else {
                    database.deleteFavoritePhoto(photoItem.getId());
                    holder.favorite.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.off));
                    favoriteMap.remove(photoItem.getId());
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
        public ImageView photo;
        public TextView title;
        public ToggleButton favorite;
        public ToggleButton download;


        private final Context context;


        public ViewHolder(View itemView) {
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