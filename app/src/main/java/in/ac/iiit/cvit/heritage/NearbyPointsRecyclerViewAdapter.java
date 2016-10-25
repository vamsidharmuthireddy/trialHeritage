package in.ac.iiit.cvit.heritage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NearbyPointsRecyclerViewAdapter extends RecyclerView.Adapter<NearbyPointsRecyclerViewAdapter.DataObjectHolder> {

    private ArrayList<InterestPoint> interestPoints;
    private static final String LOGTAG = "Heritage";

    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public DataObjectHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.cardview_image);
            this.textView = (TextView) view.findViewById(R.id.cardview_text);
        }
    }

    public NearbyPointsRecyclerViewAdapter(ArrayList<InterestPoint> interestPoints) {
        this.interestPoints = interestPoints;
        notifyDataSetChanged();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image_text, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        ImageView imageView = holder.imageView;
        TextView textView = holder.textView;

        textView.setText(interestPoints.get(position).get("title"));
        imageView.setImageBitmap(interestPoints.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return interestPoints.size();
    }
}
