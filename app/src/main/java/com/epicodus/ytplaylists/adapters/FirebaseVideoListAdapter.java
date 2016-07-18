package com.epicodus.ytplaylists.adapters;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.epicodus.ytplaylists.models.VideoObj;
import com.epicodus.ytplaylists.util.ItemTouchHelperAdapter;
import com.epicodus.ytplaylists.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Guest on 7/18/16.
 */
public class FirebaseVideoListAdapter extends FirebaseRecyclerAdapter<VideoObj, FirebaseVideoViewHolder> implements ItemTouchHelperAdapter {

    private final DatabaseReference mRef;
    private final OnStartDragListener mOnStartDragListener;
    private final Context mContext;

    public FirebaseVideoListAdapter(Class<VideoObj> modelClass, int modelLayout,
                                         Class<FirebaseVideoViewHolder> viewHolderClass,
                                         Query ref, OnStartDragListener onStartDragListener, Context context) {

        super(modelClass, modelLayout, viewHolderClass, ref);


        mRef = ref.getRef();

        mOnStartDragListener = onStartDragListener;
        mContext = context;

    }

    @Override
    protected void populateViewHolder(final FirebaseVideoViewHolder viewHolder, VideoObj model, int position) {


        viewHolder.bindVideo(model);
        viewHolder.mThumbnailImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }
}