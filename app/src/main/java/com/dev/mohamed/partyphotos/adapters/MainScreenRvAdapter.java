package com.dev.mohamed.partyphotos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.R;
import com.dev.mohamed.partyphotos.data.PartyData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by moham on 6/25/2018.
 */

public class MainScreenRvAdapter extends RecyclerView.Adapter<MainScreenRvAdapter.AdapterViewHolder>{




    Context context;
    ArrayList<PartyData> data;
    OnPartyClick onPartyClick;

    public MainScreenRvAdapter(OnPartyClick onPartyClick) {
        this.onPartyClick = onPartyClick;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);


        View view=inflater.inflate(R.layout.photo_fram,parent,false);


        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        if (data.get(position).getPartyMainPhotoLink()!=null)
        Glide.with(context).load(data.get(position).getPartyMainPhotoLink())
                .apply(new RequestOptions().circleCrop()).into(holder.frame);

        else
            Glide.with(context).load(data.get(position).getListOfPhotosLinks().get(0))
                .apply(new RequestOptions().circleCrop()).into(holder.frame);
    }

    public void updatePartiesList(ArrayList<PartyData> data)
    {
        this.data=data;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
         if (data==null)
             return 0;
         else return data.size();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       @BindView(R.id.o3) ImageView frame;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            PartyData partyData=data.get(getAdapterPosition());
            onPartyClick.partyClick(partyData,frame);
        }
    }


    public interface OnPartyClick
    {
        void partyClick(PartyData data,ImageView avatar);
    }
}
