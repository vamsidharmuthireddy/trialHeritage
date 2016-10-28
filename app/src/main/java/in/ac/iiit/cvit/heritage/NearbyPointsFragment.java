package in.ac.iiit.cvit.heritage;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class NearbyPointsFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

    private static final String LOGTAG = "Heritage:Nearby";
    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private ArrayList<InterestPoint> sortedInterestPoints;
    private ArrayList<InterestPoint> interestPoints;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private Context _context;

    private static final int TRUNCATION_LIMIT = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nearby_points, container, false);
        _context = getActivity();
        mGoogleApiClient = null;
        createLocationClients();

        interestPoints = ((MainActivity) this.getActivity()).interestPoints;
        sortedInterestPoints = new ArrayList<InterestPoint>();
        for(int i=0; i<Math.min(TRUNCATION_LIMIT, interestPoints.size()); i++){
            sortedInterestPoints.add(interestPoints.get(i));
        }

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_nearby_points);
        recyclerView.setHasFixedSize(true);
        recyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        refreshRecyclerView();

        return root;
    }

    private void refreshRecyclerView() {
        recyclerViewAdapter = new NearbyPointsRecyclerViewAdapter(sortedInterestPoints);
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
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");


        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    private void createLocationClients(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(_context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        //Log.d(LOGTAG, "Clients Created");
    }

    @Override
    public void onConnected(Bundle bundle){
        //Log.d(LOGTAG, "Running onConnected");
        if (ActivityCompat.checkSelfPermission(_context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(_context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //Log.d(LOGTAG, "Permission Denied");
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            // Display lat long on screen
            /*
            setContentView(R.layout.activity_main);
            TextView textView = (TextView) findViewById(R.id.coordinates);
            textView.setText("latitude: " + currentLatitude + " longitude: " + currentLongitude);
            */
            //Log.d(LOGTAG, "Creating toast, onConnected");
            //Toast.makeText(_context, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }

        boolean mRequestingLocationUpdates = true;
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        //Log.d(LOGTAG, "Calling startLocationUpdates");
        if (ActivityCompat.checkSelfPermission(_context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(_context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            //Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        //Toast.makeText(_context, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        computeNearby(currentLatitude, currentLongitude);
        refreshRecyclerView();

    }

    public void computeNearby(Double currentLatitude, Double currentLongitude) {
        ArrayList<Pair<Double, Integer>> Indices = new ArrayList<>();
        double distance;
        Pair<Double, Integer> P;

        gettingViewAngle gettingViewAngle = new gettingViewAngle();
        ArrayList<Pair<Double, Integer>> angleIndices = new ArrayList<>();
        double angle;
        Pair<Double, Integer> angleP;


        for(int i=0; i<interestPoints.size(); i++){
            //getting the distance of all the interest points from the current location
            distance = interestPoints.get(i).distance(currentLatitude, currentLongitude);
            Log.d(LOGTAG, "Distance = "+distance);
            P = new Pair(distance, i);
            Indices.add(P);

            //getting the angle of all the interest points from the current location line of view
            angle = interestPoints.get(i).giveAngle(currentLatitude,currentLongitude);
            Log.d(LOGTAG, "angle = "+ angle);
            angleP = new Pair(angle, i);
            angleIndices.add(angleP);
        }

        //Arranging the distances in their ascending order
        Collections.sort(Indices, new Comparator<Pair<Double, Integer>>() {
            @Override
            public int compare(final Pair<Double, Integer> left, final Pair<Double, Integer> right) {
                if (left.first < right.first){
                    return -1;
                }
                else if (left.first == right.first){
                    return 0;
                }
                else{
                    return 1;
                }
            }
        });

        //just adding the sorted list to the main list with new indices
        //not needed?
        for(int i=0; i<interestPoints.size(); i++){
            distance = Indices.get(i).first;
            Log.d(LOGTAG, "SDistance = "+distance);
            P = new Pair(distance, i);
            Indices.add(P);
        }

        //setting the order of interest points
        InterestPoint interestPoint;
        for (int i=0; i<Math.min(TRUNCATION_LIMIT, interestPoints.size()); i++) {
            interestPoint = interestPoints.get(Indices.get(i).second);
            sortedInterestPoints.set(i, interestPoint);
        }

        /**
         * Edited by vamsi
         */
        ArrayList<Pair<Double, Integer>> finalAngleIndices = new ArrayList<>();
        //setting the angle in the ascending order of distances
        //arranging the angles in the order of distances
        for(int i=0; i<interestPoints.size(); i++){

            int key = Indices.get(i).second;    //getting the nearest interest point number

            double tempAngle = angleIndices.get(key).first; //getting the angle of nearest interest point

            Log.d(LOGTAG, "nearest Distance angles= "+tempAngle);
            Pair tempP = new Pair(tempAngle, key);
            finalAngleIndices.add(tempP);
        }

        //continue from here
        //sort the first three nearest distance points based on their view angles

        /*Write your code here */

        //setting the order of three points based on the view angles of the nearest points
        for (int i=0; i<Math.min(TRUNCATION_LIMIT, interestPoints.size()); i++) {
            interestPoint = interestPoints.get(finalAngleIndices.get(i).second);
            sortedInterestPoints.set(i, interestPoint);
        }

    }
}
