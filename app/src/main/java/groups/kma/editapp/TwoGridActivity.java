package groups.kma.editapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import groups.kma.editapp.adapter.GridAdapter;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class TwoGridActivity extends AppCompatActivity implements GridAdapter.CallbackInterface{
    RecyclerView rvMenu;
    ArrayList<Integer> listItem;
    GridAdapter adapter;
    ArrayList<String> stringArrayList;
    LinearLayout main_layout;

    public static final int PICK_IMAGE = 1;
    public static final int REQUEST_IMAGE = 200;
    ImageView imgLeft,imgRight;
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
        setContentView(R.layout.activity_two_grid);
        addControls();
        addEvents();
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
        imgLeft= this.<ImageView>findViewById(R.id.img1);
        imgRight= this.<ImageView>findViewById(R.id.img2);
        addImage();
    }
    private void addEvents() {
        final float b1=imgLeft.getY();
        final float b2=imgRight.getY();
        imgLeft.setOnTouchListener(new View.OnTouchListener() {
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
                            showPictureDialog("left");
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Set default", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            FrameLayout frameLayout=
                                                    (FrameLayout) findViewById(R.id.frame1);
                                            float x=frameLayout.getWidth();
                                            float y=imgLeft.getWidth();
                                            imgLeft.setRotation(0);
                                            imgLeft.setX((x-y)/2);
                                            imgLeft.setY(b1);
                                            imgLeft.setScaleX(1);
                                            imgLeft.setScaleY(1);
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
        imgRight.setOnTouchListener(new View.OnTouchListener() {
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
                            showPictureDialog("right");
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(main_layout, "Set default", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            FrameLayout frameLayout=
                                                    (FrameLayout) findViewById(R.id.frame2);
                                            float x=frameLayout.getWidth();
                                            float y=imgRight.getWidth();
                                            imgRight.setRotation(0);
                                            imgRight.setX((x-y)/2);
                                            imgRight.setY(b2);
                                            imgRight.setScaleX(1);
                                            imgRight.setScaleY(1);
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
        mnuShapes.add(R.drawable.select2);
        mnuShapes.add(R.drawable.vertical2grid);
        return mnuShapes;
    }

    @Override
    public void onHandleSelection(int position) {
        LinearLayout layout= (LinearLayout)findViewById(R.id.layoutSet);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        switch (position){
            case 0:
                layout.setOrientation(LinearLayout.HORIZONTAL);
                lp.setMargins(0,0,2,0);
                lp1.setMargins(2,0,0,0);
                imgLeft.setLayoutParams(lp);
                imgRight.setLayoutParams(lp1);
                break;
            case 1:
                layout.setOrientation(LinearLayout.VERTICAL);
                lp.setMargins(0,0,0,2);
                lp1.setMargins(0,2,0,0);
                imgLeft.setLayoutParams(lp);
                imgRight.setLayoutParams(lp1);
                break;
        }
    }

    private void addImage() {
        stringArrayList = getIntent().getStringArrayListExtra("SELECTED_LETTER");
        assert stringArrayList != null;
        if (stringArrayList.size() > 0) {
            for (int i = 0; i < stringArrayList.size(); i++) {
                if (i < stringArrayList.size() - 1) {
                    Log.i("REP", "" + stringArrayList.get(i).toString());
                    Bitmap myBitmap = BitmapFactory.decodeFile(stringArrayList.get(i));
                    imgLeft.setAdjustViewBounds(true);
                    imgLeft.setImageBitmap(myBitmap);
                    imgLeft.setScaleY(2);
                    imgLeft.setScaleX(2);
                }else {
                    Log.i("REP", "" + stringArrayList.get(i).toString());
                    Bitmap myBitmap = BitmapFactory.decodeFile(stringArrayList.get(i));
                    imgRight.setAdjustViewBounds(true);
                    imgRight.setImageBitmap(myBitmap);
                    imgRight.setScaleY(2);
                    imgRight.setScaleX(2);
                }
            }
        }
    }

    private void showPictureDialog(String str) {
        if (str.equalsIgnoreCase("left"))
        {
            view = str;
        }
        if (str.equalsIgnoreCase("right"))
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
                    if (view.equalsIgnoreCase("left")){

                        imgLeft.setBackgroundResource(android.R.color.transparent);
                        imgLeft.setImageBitmap(bitmap);
                    }
                    if (view.equalsIgnoreCase("right")){

                        imgRight.setBackgroundResource(android.R.color.transparent);
                        imgRight.setImageBitmap(bitmap);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == REQUEST_IMAGE &&resultCode == Activity.RESULT_OK) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            if (view.equalsIgnoreCase("left")){
                imgLeft.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgLeft.setImageBitmap(thumbnail);
            }
            if (view.equalsIgnoreCase("right")){
                imgRight.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgRight.setImageBitmap(thumbnail);
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
