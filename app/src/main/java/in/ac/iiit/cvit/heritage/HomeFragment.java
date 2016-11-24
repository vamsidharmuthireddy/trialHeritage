package in.ac.iiit.cvit.heritage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private static final String LOGTAG = "Heritage";
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sessionManager = new SessionManager();
        String packageName = sessionManager
                .getStringSessionPreferences(
                        getActivity(), getString(R.string.package_name), getString(R.string.default_package_value));

        packageName = packageName.toLowerCase();
        Bitmap bitmap;
        TextView textView = (TextView) root.findViewById(R.id.home_text);
        ImageView imageView = (ImageView) root.findViewById(R.id.home_image);
        //final String[] packageNames = getResources().getStringArray(R.array.download_packages);

        //add the cases for the rest of the included packages
        switch (packageName){
            case ("golconda"):
                textView.setText(getString(R.string.home_golconda));
                bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.heritage6);
                imageView.setImageBitmap(bitmap);
                break;
        }


        return root;
    }
}
