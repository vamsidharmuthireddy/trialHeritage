package in.ac.iiit.cvit.heritage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NearbyPointsRecyclerViewAdapter extends RecyclerView.Adapter<NearbyPointsRecyclerViewAdapter.DataObjectHolder> {
    /**
     * This class is called from NearbyPointsFragment after we get nearby interest points
     * This class sets the picture and text(Title) on the NearbyPointsFragment's recycler view
     */
    private Context context;
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

    /**
     * This method has the sortedInterestPoints obtained from NearbyPointsFragment
     * @param interestPoints contains the sorted list of three nearest interest points
     */
    public NearbyPointsRecyclerViewAdapter(ArrayList<InterestPoint> interestPoints, Context _context) {
        context = _context;

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

        SessionManager sessionManager = new SessionManager();
        final String packageName = sessionManager
                .getStringSessionPreferences(
                        context, context.getString(R.string.package_name), context.getString(R.string.default_package_value));


        textView.setText(interestPoints.get(position).get(context.getString(R.string.interest_point_title)));
        imageView.setImageBitmap(interestPoints.get(position).getImage(packageName,context.getString(R.string.interest_point_title)));
        Log.v("NearybyRecycle","getImage");
    }

    @Override
    public int getItemCount() {
        return interestPoints.size();
    }
}
