package com.dev.mohamed.partyphotos.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dev.mohamed.partyphotos.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moham on 7/15/2018.
 */

public class PartyManageAdapter  extends RecyclerView.Adapter<PartyManageAdapter.PartyManageAdapterViewHolder>{

    List<Uri> imagesList;
    Context context;
    @NonNull
    @Override
    public PartyManageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.party_manage_item,parent,false);

        return new PartyManageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartyManageAdapterViewHolder holder, int position) {

        Glide.with(context).load(imagesList.get(position)).into(holder.mPhoto);
    }

    @Override
    public int getItemCount() {
        if (imagesList!=null)
        return imagesList.size();
        else return 0;
    }

    public void updateListImages(List<Uri> imagesList)
    {
        this.imagesList=imagesList;
        notifyDataSetChanged();
    }

    class PartyManageAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.img_photo)ImageView mPhoto;
        @BindView(R.id.img_delete)ImageView mDelete;
        public PartyManageAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();

            switch (view.getId())
            {
                case R.id.img_delete:
                    imagesList.remove(pos);
                    notifyDataSetChanged();
            }

        }
    }
}
