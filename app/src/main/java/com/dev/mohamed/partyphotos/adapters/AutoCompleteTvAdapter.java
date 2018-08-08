package com.dev.mohamed.partyphotos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dev.mohamed.partyphotos.R;
import com.dev.mohamed.partyphotos.data.PartyData;

import java.util.ArrayList;

/**
 * Created by moham on 6/25/2018.
 */

public class AutoCompleteTvAdapter extends ArrayAdapter {



    ArrayList<PartyData> partyData;
    ArrayList<PartyData> temp;
    ArrayList<PartyData> sugg=new ArrayList<>();
    public AutoCompleteTvAdapter(@NonNull Context context ,ArrayList<PartyData> list) {
        super(context, android.R.layout.simple_list_item_1,list);
        partyData =list;
        temp=new ArrayList<>(list);

    }

    public void updateData(ArrayList<PartyData> data)
    {
        partyData =data;
        temp=new ArrayList<>(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null)
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_item,parent,false);

        TextView textView=convertView.findViewById(R.id.tv_name);
        ImageView avatar=convertView.findViewById(R.id.o3);
        textView.setText(partyData.get(position).getPartyName());

        if (partyData.get(position).getPartyMainPhotoLink()!=null)
            Glide.with(getContext()).load(partyData.get(position).getPartyMainPhotoLink())
                    .apply(new RequestOptions().circleCrop()).into(avatar);

        else
            Glide.with(getContext()).load(partyData.get(position).getListOfPhotosLinks().get(0))
                    .apply(new RequestOptions().circleCrop()).into(avatar);

        return convertView;
    }

    @Override
    public int getCount() {
        return partyData.size();
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence!=null)
            {

                sugg.clear();
                for (PartyData party : temp)
                {
                        if (party.getPartyName().toLowerCase().startsWith(charSequence.toString()))
                            sugg.add(party);


                }


            FilterResults results=new FilterResults();
            results.values=sugg;
            results.count=sugg.size();

            return results;
            }else return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            ArrayList<PartyData> list=(ArrayList<PartyData>)filterResults.values;

            if(filterResults.count>0) {
                clear();
                for (PartyData c : list) {

                    add(c);
                    notifyDataSetChanged();
                }
            }else  {clear(); notifyDataSetChanged();}
        }
    };

    @Nullable
    @Override
    public PartyData getItem(int position) {
        return partyData.get(position);
    }
}
