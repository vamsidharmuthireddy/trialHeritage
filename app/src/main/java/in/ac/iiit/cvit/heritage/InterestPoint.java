package in.ac.iiit.cvit.heritage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InterestPoint {

    private int _id;
    private HashMap<String, String> details;
    private SessionManager sessionManager;

    private static final String dataLocation = "Android/data/in.ac.iiit.cvit.heritage/files/extracted/";

    private static final String imageType = ".JPG";
    private static final String latitudeTag = "lat";
    private static final String longitudeTag = "long";
    private static final String imageTag = "image";
    private static final String imagesTag = "images";

    private static final String imagesNameSplitter = ",";


    private static final String LOGTAG = "Heritage";

    public InterestPoint() {
        details = new HashMap<String, String>();

    }

    /**
     * This method sets the interest point
     *
     * @param key   It's the key for an attribute in xml file
     * @param value It's the value assigned for a particular key in the xml file
     */
    public void set(String key, String value) {
        details.put(key, value);
    }

    public String get(String key) {
        return details.get(key);
    }

    /**
     * This class is used to get the image related to a particular interest point
     *
     * @return Image of Interest point in Bitmap data type
     */
    public Bitmap getImage(String packageName, String interestPointName) {

        packageName = packageName.toLowerCase();

        String imageName = details.get(imageTag);
 //       Log.v("getImage","reached getImage");


        String image_path =  dataLocation + packageName + "/" + imageName + imageType;

        File imageFile = new File(Environment.getExternalStorageDirectory(),image_path);
//        Log.v("getImage", Environment.getExternalStorageDirectory()+image_path);
        if(imageFile.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    //            bitmap = bitmap.createScaledBitmap(bitmap, 627, 353, false);
   //             Log.v("getImage", imageName + ".JPG");

                return bitmap;
            }

        return null;
    }


    /**
     * This class is called from ImagePagerFragmentActivity when Image button is clicked
     * This class is used to get all the images related to a particular interest point.
     * This class is not hard coded.
     *
     * @return Images of Interest point in Bitmap Array data type
     */
    public ArrayList<Bitmap> getImages(String packageName, String interestPointName) {

        packageName = packageName.toLowerCase();

//        String[] image_names = {"a1", "a2", "a3", "a4", "a5"};

        String allImages = details.get(imagesTag);

        //Log.v("getImages",interestPointName);

//        Log.v("getImages",allImages);
        List<String> imagesList = Arrays.asList(allImages.split(imagesNameSplitter));

        ArrayList<Bitmap> image_bitmaps = new ArrayList<Bitmap>();


        for (int i = 0; i < imagesList.size(); i++) {
            String imageName = imagesList.get(i);
//            Log.v("getImages",imageName);
            String image_path = dataLocation + packageName + "/" + imageName + imageType;

            File imageFile = new File(Environment.getExternalStorageDirectory(),image_path);
            if (imageFile.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
//                bitmap = bitmap.createScaledBitmap(bitmap, 627, 353, false);
                image_bitmaps.add(bitmap);
            }
        }

        return image_bitmaps;

    }


    private double betweenDistance;

    double distance(double iLat, double iLong) {
        double pLat, pLong;
        double dLat, dLong;
        double sum;

        pLat = Double.parseDouble(details.get(latitudeTag));
        pLong = Double.parseDouble(details.get(longitudeTag));

//        Log.d("InterestPoint:distance", "pLat="+ pLat);
//        Log.d("InterestPoint:distance", "pLong="+ pLong);

        /* Euclidean distance. Should work. */
        dLat = pLat - iLat;
        dLong = pLong - iLong;
        sum = dLat * dLat + dLong * dLong;

        betweenDistance = Math.sqrt(sum);

        return Math.sqrt(sum);
    }

    /**
     * This method is called from NearbyPointsFragment. This method gives the view angle of the interest point
     * based on user's current location and mobile's direction
     *
     * @param iLat  latitude
     * @param iLong longitude
     * @return view angle from mobile's direction od axis
     */
    double giveAngle(double iLat, double iLong, double[] coEfficients) {

        double pLat;
        double pLong;
        double angle = 0;
        double perpDist = 0;


        double a = coEfficients[0];
        double b = coEfficients[1];
        double c = coEfficients[2];

//         Log.d("giveAngle", "a="+ a);
//         Log.d("giveAngle", "b="+ b);
//         Log.d("giveAngle", "c="+ c);

        pLat = Double.parseDouble(details.get(latitudeTag));
        pLong = Double.parseDouble(details.get(longitudeTag));
        //        Log.d("InterestPoint:distance", "pLat="+ pLat);
        //        Log.d("InterestPoint:distance", "pLong="+ pLong);

        perpDist = (a * pLat + b * pLong + c) / (Math.sqrt((Math.pow(a, 2) + Math.pow(b, 2))));
//         Log.d("giveAngle:perpDist", "perpDist="+ perpDist);

        angle = Math.asin(perpDist / betweenDistance);

        return angle;
    }

}
