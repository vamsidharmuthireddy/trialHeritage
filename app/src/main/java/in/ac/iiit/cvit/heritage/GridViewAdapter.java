package in.ac.iiit.cvit.heritage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<InterestPoint> {

    private ArrayList<InterestPoint> _interestPoints;

    private static final String LOGTAG = "Heritage";

    public GridViewAdapter(Context context, int viewResourceId, ArrayList<InterestPoint> interestPoints) {
        super(context, viewResourceId, interestPoints);
        _interestPoints = interestPoints;
    }

    @Override
    public int getCount() {
        return _interestPoints.size();
    }

    @Override
    public InterestPoint getItem(int position) {
        return _interestPoints.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_interest_point, null);
        }

        InterestPoint interestPoint = getItem(position);
        TextView textview_interest_point = (TextView) view.findViewById(R.id.textview_interest_point_name);

        textview_interest_point.setText(interestPoint.get("title"));

        return view;
    }
}
