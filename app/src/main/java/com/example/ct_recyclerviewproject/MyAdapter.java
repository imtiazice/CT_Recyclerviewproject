package com.example.ct_recyclerviewproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class
MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<com.example.ct_recyclerviewproject.Model.List> moviesList;
    Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView maxTemp, minTemp, date, desc;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            maxTemp = view.findViewById(R.id.maxTemp);
            minTemp = view.findViewById(R.id.minTemp);
            date = view.findViewById(R.id.date);
            desc = view.findViewById(R.id.desc);
            icon = view.findViewById(R.id.icon);
        }
    }


    public MyAdapter(Context c, List<com.example.ct_recyclerviewproject.Model.List> moviesList) {
        this.c = c;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType ==0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_for_today, parent, false);
        }else{
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_for_forteen_days, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        com.example.ct_recyclerviewproject.Model.List movie = moviesList.get(position);
        holder.maxTemp.setText(String.format("%.0f", Double.parseDouble(movie.getTemp().getMax().toString())) + (char) 0x00B0);
        holder.minTemp.setText(String.format("%.0f", Double.parseDouble(movie.getTemp().getMin().toString())) + (char) 0x00B0);
        holder.date.setText(getDateFromString( movie.getDt().toString() ));
        holder.desc.setText(movie.getWeather().get(0).getMain());
        String imageUrl = "http://www.openweathermap.org/img/w/"+movie.getWeather().get(0).getIcon()+".png";
        Glide.with(c).load(imageUrl).into(holder.icon);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static String getDateFromString(String seconds)
    {


        long sec = Long.parseLong(seconds);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sec*1000);

        Calendar calendar1 = Calendar.getInstance();
        Date today = calendar1.getTime();
        calendar1.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar1.getTime();

        String today_Name = formatter.format(today);
        String tomorrow_Name = formatter.format(tomorrow);
        String day_Name = formatter.format(calendar.getTime());

        if(day_Name.equalsIgnoreCase(today_Name))
            return "Today";
        else if (day_Name.equalsIgnoreCase(tomorrow_Name))
            return "Tomorrow";
        else
            return  day_Name;
    }

}





