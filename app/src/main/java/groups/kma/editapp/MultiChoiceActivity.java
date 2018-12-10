package groups.kma.editapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import groups.kma.editapp.adapter.Gallery;
import groups.kma.editapp.adapter.ImageAdapter;
import groups.kma.editapp.item.GridItemView;

public class MultiChoiceActivity extends AppCompatActivity {
    private GridView gridView;
    private ArrayList<String> selectedStrings;
    private ArrayList<String> path;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice);
        addControls();
        addEvents();
    }

    private void addEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedIndex = adapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    adapter.selectedPositions.remove(selectedIndex);
                    ((GridItemView) view).display(false);
                    selectedStrings.remove((String) parent.getItemAtPosition(position));
                } else {
                    adapter.selectedPositions.add(position);
                    ((GridItemView) view).display(true);
                    selectedStrings.add((String) parent.getItemAtPosition(position));
                }
            }
        });
    }

    private void addControls() {
        gridView = this.<GridView>findViewById(R.id.gallery);
        selectedStrings = new ArrayList<>();
        path = getFilePath();
        adapter = new ImageAdapter(path, this);
        gridView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.next_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.btnNext) {
            if (selectedStrings.size()<1){
                Toast.makeText(getApplicationContext(),"You must choose at least one image!",
                        Toast.LENGTH_SHORT).show();
            }
            else if (selectedStrings.size()==1) {
                intent = new Intent(this, GridActivity.class);
                intent.putStringArrayListExtra("SELECTED_LETTER", selectedStrings);
                Log.i("Number", "" + selectedStrings.size());
                startActivity(intent);
            }else if (selectedStrings.size()==2) {
                intent = new Intent(this, TwoGridActivity.class);
                intent.putStringArrayListExtra("SELECTED_LETTER", selectedStrings);
                Log.i("Number", "" + selectedStrings.size());
                startActivity(intent);
            }else if (selectedStrings.size()==3) {
                intent = new Intent(this, ThreeGridActivity.class);
                intent.putStringArrayListExtra("SELECTED_LETTER", selectedStrings);
                Log.i("Number", "" + selectedStrings.size());
                startActivity(intent);
            }else {
                Toast.makeText(this, "Support later", Toast.LENGTH_SHORT).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getFilePath() {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;
        if (u != null) {
            c = managedQuery(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst())) {
            do {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try {
                    dirList.add(tempDir);
                } catch (Exception e) {

                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);

        }

        for (int i = 0; i < dirList.size(); i++) {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if (imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();

                    }
                    if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            ) {


                        String path = imagePath.getAbsolutePath();
                        resultIAV.add(path);

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resultIAV;
    }
}
