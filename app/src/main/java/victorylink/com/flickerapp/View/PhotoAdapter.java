package victorylink.com.flickerapp.View;

import android.content.Context;
import android.content.Intent;
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

import victorylink.com.flickerapp.Model.Photo;
import victorylink.com.flickerapp.R;

/**
 * Created by MrHacker on 7/17/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> implements Filterable {
    private ArrayList<Photo> dataset;
    private ArrayList<Photo> filteredList;

    private Context context = null;
    private boolean isMain;

    public PhotoAdapter(ArrayList<Photo> newDataset, Context newContext, boolean isMain) {
        dataset = newDataset;
        filteredList = newDataset ;
        context = newContext;
        this.isMain = isMain;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (!isMain) {
              holder.forward.setVisibility(View.GONE);
        }

        final Photo photoItem = filteredList.get(position);
        Picasso.with(context).load("http://farm" + photoItem.getFarm() + ".staticflickr.com/" + photoItem.getServer() + "/" + photoItem.getId() + "_" + photoItem.getSecret() + ".jpg").into(holder.getPhoto());
        holder.getTitle().setText(photoItem.getTitle());

        holder.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("userId",photoItem.getOwner());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public void setDataset(ArrayList<Photo> dataset) {


        this.dataset = dataset ;
        this.filteredList = dataset ;

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
                filterResults.values = filteredList ;
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

        public Button getForward() {
            return forward;
        }

        public void setForward(Button forward) {
            this.forward = forward;
        }

        public ImageView getPhoto() {
            return photo;
        }

        public void setPhoto(ImageView photo) {
            this.photo = photo;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }
    }
}
