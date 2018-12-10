package groups.kma.editapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import groups.kma.editapp.R;
import groups.kma.editapp.item.GridItemView;

public class ImageAdapter extends BaseAdapter{
    private Context context;
    ArrayList<String> listData;
    public List selectedPositions;

    public ImageAdapter(ArrayList<String> listData, Context context) {
        this.listData=listData;
        this.context = context;
        selectedPositions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        GridItemView customView = (convertView == null) ? new GridItemView(context) :
                (GridItemView) convertView;
        customView.display(listData.get(i), selectedPositions.contains(i));

        return customView;
    }

}
