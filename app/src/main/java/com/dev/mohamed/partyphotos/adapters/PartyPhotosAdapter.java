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

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moham on 6/28/2018.
 */

public class PartyPhotosAdapter extends RecyclerView.Adapter<PartyPhotosAdapter.PartyphotosAdapterViewHolder>{



    Context context;
    ArrayList<String>listOfPhotos;
    public static final String PARTY_LIST_KEY="partyListKey";
    public static final  String PARTY_PHOTO_COUNT_KEY="photoCount";
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




        Glide.with(context).load(listOfPhotos.get(position)).into(holder.photo);

    }

    public void updateListOfPhotos(ArrayList<String>listOfPhotos)
    {
        this.listOfPhotos=listOfPhotos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
       if (listOfPhotos!=null)
           return listOfPhotos.size();
       else
           return 0;
    }

    class PartyphotosAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.img_photo) ImageView photo;

        public PartyphotosAdapterViewHolder(View itemView) {
            super(itemView);
           // photo=itemView.findViewById(R.id.img_photo);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context, SingleImageShowActivity.class);
            intent.putExtra(PARTY_PHOTO_COUNT_KEY,getAdapterPosition());
            intent.putExtra(PARTY_LIST_KEY,listOfPhotos);
            context.startActivity(intent);
        }
    }
}
