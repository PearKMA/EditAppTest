package groups.kma.editapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import groups.kma.editapp.adapter.GridAdapter;

public class ThreeGridActivity extends AppCompatActivity implements GridAdapter.CallbackInterface{
    RecyclerView rvMenu;
    ArrayList<Integer> listItem;
    GridAdapter adapter;
    ArrayList<String> stringArrayList;
    LinearLayout main_layout,createLayout;
    public static final int PICK_IMAGE = 1;
    public static final int REQUEST_IMAGE = 200;
    ImageView[] images = new ImageView[3];
    FrameLayout[] frameLayouts=new FrameLayout[3];
    String view;
    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private boolean isZoomAndRotate;
    private boolean isOutSide;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;
    private float xCoOrdinate, yCoOrdinate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_grid);
        addControls();

    }
    private void addControls() {
        rvMenu =(RecyclerView)findViewById(R.id.recMenu);
        rvMenu.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,false);
        rvMenu.setLayoutManager(layoutManager);
        listItem=prepareData();
        adapter = new GridAdapter(this, listItem);
        rvMenu.setAdapter(adapter);
        main_layout= this.<LinearLayout>findViewById(R.id.main_layout);
        createLayout= (LinearLayout) findViewById(R.id.layoutSet);
        createLayout.setOrientation(LinearLayout.HORIZONTAL);
        createView();
    }
    private void addEvents() {
        final float b1=images[0].getY();
        final float b2=images[1].getY();
        final float b3=images[2].getY();
        images[0].setOnTouchListener(new View.OnTouchListener() {
            private long startTime=0;
            private long endTime=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startTime = event.getEventTime();
                        break;
                    case MotionEvent.ACTION_UP:
                        endTime = event.getEventTime();
                        if (endTime-startTime<100)
                        {
                            showPictureDialog("img1");
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Set default", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            float x=frameLayouts[0].getWidth();
                                            float y=images[0].getWidth();
                                            images[0].setRotation(0);
                                            images[0].setX((x-y)/2);
                                            images[0].setY(b1);
                                            images[0].setScaleX(1);
                                            images[0].setScaleY(1);
                                        }
                                    });
                            snackbar.show();
                        }
                        break;
                }
                v.getParent().requestDisallowInterceptTouchEvent(true); //specific to my project
                ImageView view = (ImageView) v;
                view.bringToFront();
                viewTransformation(view, event);
                return true;
            }
        });
        images[1].setOnTouchListener(new View.OnTouchListener() {
            private long startTime=0;
            private long endTime=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startTime = event.getEventTime();
                        break;
                    case MotionEvent.ACTION_UP:
                        endTime = event.getEventTime();
                        if (endTime-startTime<100)
                        {
                            showPictureDialog("img2");
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Set default", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            float x=frameLayouts[1].getWidth();
                                            float y=images[1].getWidth();
                                            images[1].setRotation(0);
                                            images[1].setX((x-y)/2);
                                            images[1].setY(b2);
                                            images[1].setScaleX(1);
                                            images[1].setScaleY(1);
                                        }
                                    });
                            snackbar.show();
                        }
                        break;
                }
                v.getParent().requestDisallowInterceptTouchEvent(true); //specific to my project
                ImageView view = (ImageView) v;
                view.bringToFront();
                viewTransformation(view, event);
                return true;
            }
        });
        images[2].setOnTouchListener(new View.OnTouchListener() {
            private long startTime=0;
            private long endTime=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startTime = event.getEventTime();
                        break;
                    case MotionEvent.ACTION_UP:
                        endTime = event.getEventTime();
                        if (endTime-startTime<100)
                        {
                            showPictureDialog("img3");
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Set default", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            float x=frameLayouts[2].getWidth();
                                            float y=images[2].getWidth();
                                            images[2].setRotation(0);
                                            images[2].setX((x-y)/2);
                                            images[2].setY(b3);
                                            images[2].setScaleX(1);
                                            images[2].setScaleY(1);
                                        }
                                    });
                            snackbar.show();
                        }
                        break;
                }
                v.getParent().requestDisallowInterceptTouchEvent(true); //specific to my project
                ImageView view = (ImageView) v;
                view.bringToFront();
                viewTransformation(view, event);
                return true;
            }
        });
    }

    private ArrayList<Integer> prepareData() {
        ArrayList<Integer> mnuShapes=new ArrayList<>();
        mnuShapes.add(R.drawable.mnu3_hor);
        mnuShapes.add(R.drawable.mnu3_ver);
        mnuShapes.add(R.drawable.mnu3_cus1);
        return mnuShapes;
    }

    @Override
    public void onHandleSelection(int position) {
        switch (position){
            case 0:
                createLayout.removeAllViews();
                createLayout.setOrientation(LinearLayout.HORIZONTAL);
                createView();
                break;
            case 1:
                createLayout.removeAllViews();
                createLayout.setOrientation(LinearLayout.VERTICAL);
                createView();
                break;
            case 2:
                createCustomView();
                break;
        }
    }

    private void createCustomView() {
        createLayout.removeAllViews();
        createLayout.setOrientation(LinearLayout.VERTICAL);
        //create top view
        FrameLayout frameLayout=new FrameLayout(this);
        frameLayout.setId(R.id.fr1);
        ImageView img =new ImageView(this);
        img.setId(R.id.img1);
        img.setImageResource(R.drawable.select);
        frameLayout.addView(img, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        createLayout.addView(frameLayout,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,2));
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        //create left view
        FrameLayout frameLayout1=new FrameLayout(this);
        frameLayout1.setId(R.id.fr2);
        ImageView img1 =new ImageView(this);
        img1.setId(R.id.img2);
        img1.setImageResource(R.drawable.select);
        frameLayout1.addView(img1, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        ll.addView(frameLayout1,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1));
        //create right view
        FrameLayout frameLayout2=new FrameLayout(this);
        frameLayout2.setId(R.id.fr3);
        ImageView img2 =new ImageView(this);
        img2.setId(R.id.img3);
        img2.setImageResource(R.drawable.select);
        frameLayout2.addView(img2, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        ll.addView(frameLayout2,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1));
        createLayout.addView(ll,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1));
        frameLayouts[0]=(FrameLayout) findViewById(R.id.fr1);
        frameLayouts[1]=(FrameLayout) findViewById(R.id.fr2);
        frameLayouts[2]=(FrameLayout) findViewById(R.id.fr3);
        images[0]=(ImageView)findViewById(R.id.img1);
        images[1]=(ImageView)findViewById(R.id.img2);
        images[2]=(ImageView)findViewById(R.id.img3);
        addImage();
        addEvents();
    }

    private void createView() {
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,1);
        FrameLayout.LayoutParams layoutParams1=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT
            ,FrameLayout.LayoutParams.MATCH_PARENT);
        for (int j=0;j<3;j++) {
            //create frame1
            FrameLayout frame = new FrameLayout(this);
            frame.setId(j+5);
            frame.setLayoutParams(layoutParams);
            //create imageview1
            ImageView img1 = new ImageView(this);
            img1.setLayoutParams(layoutParams1);
            img1.setId(j);
            frame.addView(img1,layoutParams1);
            createLayout.addView(frame);
            frameLayouts[j]=(FrameLayout)findViewById(j+5) ;
            images[j]= (ImageView) findViewById(j);
        }

        addImage();
        addEvents();
    }

    private void addImage() {
        stringArrayList = getIntent().getStringArrayListExtra("SELECTED_LETTER");
        assert stringArrayList != null;
        if (stringArrayList.size() > 0) {
            for (int i = 0; i < stringArrayList.size(); i++) {
                    Log.i("REP", "" + stringArrayList.get(i).toString());
                    Bitmap myBitmap = BitmapFactory.decodeFile(stringArrayList.get(i));
                    images[i].setAdjustViewBounds(true);
                    images[i].setImageBitmap(myBitmap);
                    images[i].setScaleY(2);
                    images[i].setScaleX(2);
            }
        }
    }

    private void showPictureDialog(String str) {
        if (str.equalsIgnoreCase("img1"))
        {
            view = str;
        }
        if (str.equalsIgnoreCase("img2"))
        {
            view=str;
        }
        if (str.equalsIgnoreCase("img3"))
        {
            view=str;
        }
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItem = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        choosePhotoFromGallery();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE);
    }

    private void choosePhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMAGE &&resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (view.equalsIgnoreCase("img1")){

                        images[0].setBackgroundResource(android.R.color.transparent);
                        images[0].setImageBitmap(bitmap);
                    }
                    if (view.equalsIgnoreCase("img2")){
                        images[1].setBackgroundResource(android.R.color.transparent);
                        images[1].setImageBitmap(bitmap);
                    }
                    if (view.equalsIgnoreCase("img3")){
                        images[2].setBackgroundResource(android.R.color.transparent);
                        images[2].setImageBitmap(bitmap);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == REQUEST_IMAGE &&resultCode == Activity.RESULT_OK) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            if (view.equalsIgnoreCase("img1")){
                images[0].setScaleType(ImageView.ScaleType.FIT_CENTER);
                images[0].setImageBitmap(thumbnail);
            }
            if (view.equalsIgnoreCase("img2")){
                images[1].setScaleType(ImageView.ScaleType.FIT_CENTER);
                images[1].setImageBitmap(thumbnail);
            }if (view.equalsIgnoreCase("img3")){
                images[2].setScaleType(ImageView.ScaleType.FIT_CENTER);
                images[2].setImageBitmap(thumbnail);
            }

        }
        setTitle("Edit image");
    }

    private void viewTransformation(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = view.getX() - event.getRawX();
                yCoOrdinate = view.getY() - event.getRawY();

                start.set(event.getX(), event.getY());
                isOutSide = false;
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
                isZoomAndRotate = false;
                if (mode == DRAG) {
                    float x = event.getX();
                    float y = event.getY();
                }
            case MotionEvent.ACTION_OUTSIDE:
                isOutSide = true;
                mode = NONE;
                lastEvent = null;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false;
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    }
                    if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist1 = spacing(event);
                        if (newDist1 > 10f) {
                            float scale = newDist1 / oldDist * view.getScaleX();
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                        }
                        if (lastEvent != null) {
                            newRot = rotation(event);
                            view.setRotation((float) (view.getRotation() + (newRot - d)));
                        }
                    }
                }
                break;
        }
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (int) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
