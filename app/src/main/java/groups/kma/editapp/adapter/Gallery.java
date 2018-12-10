package groups.kma.editapp.adapter;

import android.graphics.Bitmap;

public class Gallery {
    private String path;
    boolean isChecked = false;

    public Gallery(String path, boolean isChecked) {
        this.path = path;
        this.isChecked = isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void toggleChecked() {
        isChecked = !isChecked;
    }
}
