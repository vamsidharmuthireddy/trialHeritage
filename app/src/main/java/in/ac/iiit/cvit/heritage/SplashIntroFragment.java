package in.ac.iiit.cvit.heritage;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashIntroFragment extends Fragment {

    private int pageNumber;
    private TextView textView;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        pageNumber = bundle.getInt("page_number");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = getActivity().getLayoutInflater().inflate(R.layout.fragment_splash_intro, container, false);
        root.setTag(pageNumber);

        Bitmap bitmap;
        String[] string_intro = getActivity().getResources().getStringArray(R.array.intro_caption);
        textView = (TextView) root.findViewById(R.id.textview_splash_intro);
        //textView.setText(string_intro[pageNumber]);
        imageView = (ImageView) root.findViewById(R.id.imageview_splash_intro);

        switch (pageNumber) {
            case 0:
                bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.heritage2);
                imageView.setImageBitmap(bitmap);
                break;
            case 1:
                bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.heritage6);
                imageView.setImageBitmap(bitmap);
                break;
            case 2:
                bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.heritage9);
                imageView.setImageBitmap(bitmap);
                break;
            default:
                break;
        }

        return root;
    }

    private Bitmap decodeBitmap(Resources resources, int resourseId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        return BitmapFactory.decodeResource(resources, resourseId, options);
    }
}
