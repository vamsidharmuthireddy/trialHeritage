package in.ac.iiit.cvit.heritage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PackagesListAdapter extends ArrayAdapter<HeritagePackage> {

    public PackagesListAdapter(Context context, ArrayList<HeritagePackage> heritagePackages) {
        super(context, R.layout.row_package, heritagePackages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_row_package = inflater.inflate(R.layout.row_package, parent, false);

        HeritagePackage heritagePackage = getItem(position);
        ImageView imageview_package_image = (ImageView) view_row_package.findViewById(R.id.rowitem_imageview_package_image);
        TextView textview_package_name = (TextView) view_row_package.findViewById(R.id.rowitem_textview_package_name);
        Button button_view_package = (Button) view_row_package.findViewById(R.id.rowitem_button_package_button);

        return view_row_package;
    }
}
