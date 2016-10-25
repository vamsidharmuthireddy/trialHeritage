package in.ac.iiit.cvit.heritage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PackagesDownloaderAdapter extends ArrayAdapter<HeritagePackage> {

    public PackagesDownloaderAdapter(Context context, ArrayList<HeritagePackage> heritagePackages) {
        super(context, R.layout.row_package_download, heritagePackages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view_row_package_download = inflater.inflate(R.layout.row_package_download, parent, false);

        HeritagePackage heritagePackage = getItem(position);
        TextView textview_package_name = (TextView) view_row_package_download.findViewById(R.id.rowitem_textview_package_name);
        Button button_package_download = (Button) view_row_package_download.findViewById(R.id.rowitem_button_package_button);

        button_package_download.setEnabled(false);

        return view_row_package_download;
    }
}
