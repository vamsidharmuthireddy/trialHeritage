package in.ac.iiit.cvit.heritage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageViewFragment extends Fragment {

    private Bitmap bitmap;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_imageview, container, false);

        Bundle bundle = getArguments();
        bitmap = ((ImagePagerFragmentActivity) this.getActivity())._images.get(bundle.getInt("image_number"));
        imageView = (ImageView) root.findViewById(R.id.imageview_slider);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(bitmap);

        return root;
    }
}
