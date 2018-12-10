package groups.kma.editapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import groups.kma.editapp.R;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private CallbackInterface mCallback;
    private ArrayList<Integer> listMenu;
    private Context context;
    public static final int PICK_IMAGE = 1;
    public interface CallbackInterface{
        void onHandleSelection(int position);
    }
    public GridAdapter(Context context,ArrayList<Integer> listMenu){
        this.context=context;
        this.listMenu=listMenu;
        try{
            mCallback = (CallbackInterface) context;
        }catch(ClassCastException ex){
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }
    }

    @NonNull
    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.two_grid,parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder holder, final int position) {
        holder.img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.img.setImageResource(listMenu.get(position));
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallback != null){
                    mCallback.onHandleSelection(position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.img);
        }
    }
}
