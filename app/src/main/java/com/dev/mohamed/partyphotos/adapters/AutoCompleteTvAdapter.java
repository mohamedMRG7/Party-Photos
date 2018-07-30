package com.dev.mohamed.partyphotos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.dev.mohamed.partyphotos.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by moham on 6/25/2018.
 */

public class AutoCompleteTvAdapter extends ArrayAdapter {

    String [] names={"mohamed","mahmod","mosa","ahmed","a5dr"};

    List<String>names1;
    List<String>temp;
    ArrayList sugg=new ArrayList();
    public AutoCompleteTvAdapter(@NonNull Context context ,List<String> list) {
        super(context, android.R.layout.simple_list_item_1,list);
        names1=list;
        temp=new ArrayList<>(list);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null)
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_item,parent,false);

        TextView textView=convertView.findViewById(R.id.tv_name);

        textView.setText(names1.get(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return names1.size();
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
                for (String x : temp)
                {

                    if (x.toLowerCase().startsWith(charSequence.toString())) {
                        sugg.add(x);

                    }
                }


            FilterResults results=new FilterResults();
            results.values=sugg;
            results.count=sugg.size();

            return results;
            }else return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            ArrayList<String> list=(ArrayList<String>)filterResults.values;

            if(filterResults.count>0) {
                clear();
                for (String c : list) {

                    add(c);
                    notifyDataSetChanged();
                }
            }else  {clear(); notifyDataSetChanged();}
        }
    };

    @Nullable
    @Override
    public Object getItem(int position) {
        return names1.get(position);
    }
}
