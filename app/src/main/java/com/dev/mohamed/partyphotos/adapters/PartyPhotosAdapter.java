package com.dev.mohamed.partyphotos.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.R;
import com.dev.mohamed.partyphotos.SingleImageShowActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moham on 6/28/2018.
 */

public class PartyPhotosAdapter extends RecyclerView.Adapter<PartyPhotosAdapter.PartyphotosAdapterViewHolder>{



    Context context;
    @NonNull
    @Override
    public PartyphotosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.party_photos_item,parent,false);

        return new PartyphotosAdapterViewHolder(view);
    }

    ConstraintSet set=new ConstraintSet();
    @Override
    public void onBindViewHolder(@NonNull PartyphotosAdapterViewHolder holder, int position) {

        Random random=new Random();


        Glide.with(context).load(MostViewdRvAdapter.images[position]).into(holder.photo);
        int widthRatio=16;
        int highRatio=14;
        String ratio =String.format("%d:%d", widthRatio,highRatio);
        set.clone(holder.mConstraintLayout);
        set.setDimensionRatio(holder.photo.getId(), ratio);
        set.applyTo(holder.mConstraintLayout);
    }

    @Override
    public int getItemCount() {
        return MostViewdRvAdapter.images.length;
    }

    class PartyphotosAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.img_photo) ImageView photo;
        ConstraintLayout mConstraintLayout;
        public PartyphotosAdapterViewHolder(View itemView) {
            super(itemView);
           // photo=itemView.findViewById(R.id.img_photo);
            ButterKnife.bind(this,itemView);
            mConstraintLayout=itemView.findViewById(R.id.parentContsraint);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            context.startActivity(new Intent(context, SingleImageShowActivity.class));
        }
    }
}
