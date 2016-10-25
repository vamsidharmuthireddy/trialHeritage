package in.ac.iiit.cvit.heritage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class InterestPoint {

    private int _id;
    private HashMap<String, String> details;

    private static final String LOGTAG = "Heritage";

    public InterestPoint() {
        details = new HashMap<String, String>();
    }

    public void set(String key, String value) {
        details.put(key, value);
    }

    public String get(String key) {
        return details.get(key);
    }

    public Bitmap getImage() {
        String image_path = Environment.getExternalStorageDirectory() + "/heritage/extracted/golconda/" + details.get("image") + ".JPG";
        File imageFile = new File(image_path);
        if (imageFile.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

            return bitmap;
        }
        return null;
    }

    public ArrayList<Bitmap> getImages() {
        String[] image_names = {"a1", "a2", "a3", "a4", "a5"};
        ArrayList<Bitmap> image_bitmaps = new ArrayList<Bitmap>();

        for (int i=0; i<image_names.length; i++) {
            String image_path = Environment.getExternalStorageDirectory() + "/heritage/extracted/golconda/" + image_names[i] + ".JPG";
            File imageFile = new File(image_path);
            if (imageFile.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
                image_bitmaps.add(bitmap);
            }
        }

        return image_bitmaps;
    }

    double distance(double iLat, double iLong){
        double pLat, pLong;
        double dLat, dLong;
        double sum;

        pLat = Double.parseDouble(details.get("lat"));
        pLong = Double.parseDouble(details.get("long"));

        /* Eucleadean distance. Should work. */
        dLat = pLat - iLat;
        dLong = pLong - iLong;
        sum = dLat * dLat + dLong * dLong;
        return Math.sqrt(sum);
    }

}
