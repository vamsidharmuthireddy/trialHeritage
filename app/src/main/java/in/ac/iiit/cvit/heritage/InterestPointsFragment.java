package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestPointsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<InterestPoint> interestPoints;

    private static final String LOGTAG = "Heritage";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_interest_points, container, false);

        interestPoints = ((MainActivity) this.getActivity()).interestPoints;

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_interest_points);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new InterestPointsRecyclerViewAdapter(interestPoints);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(
                new RecyclerViewOnItemClickListener(getActivity(), new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(position);
                        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.cardview_text);
                        String text = textView.getText().toString();

                        Intent intent_interest_point = new Intent(getActivity(), InterestPointActivity.class);
                        intent_interest_point.putExtra("interest_point", text);
                        startActivity(intent_interest_point);
                    }
                })
        );

        return root;
    }
}
