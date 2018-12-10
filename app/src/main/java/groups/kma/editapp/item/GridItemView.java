package groups.kma.editapp.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import groups.kma.editapp.R;

public class GridItemView extends FrameLayout {
    private ImageView imgItem;
    private FrameLayout layout;
    public GridItemView(@NonNull Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_item, this);
        imgItem = (ImageView) getRootView().findViewById(R.id.imgSelect);
        layout = (FrameLayout) getRootView().findViewById(R.id.frameSelect);
    }
    public void display(String path, boolean isSelected) {
        Bitmap myBitmap = BitmapFactory.decodeFile(path);
        imgItem.setImageBitmap(myBitmap);
        display(isSelected);
    }

    public void display(boolean isSelected) {
        layout.setBackgroundResource(isSelected ? R.color.blue_border : R.color.grey_border);
    }
}
