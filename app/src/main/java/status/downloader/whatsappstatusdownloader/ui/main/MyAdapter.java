package status.downloader.whatsappstatusdownloader.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.io.File;


import status.downloader.whatsappstatusdownloader.Main2Activity;
import status.downloader.whatsappstatusdownloader.R;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private  Context context;
    private File [] list;



    MyAdapter(Context context, File[] list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_1,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int  position) {


        if(list[position].getName().endsWith(".mp4"))
            holder.imageView3.setVisibility(View.VISIBLE);
        else{
            holder.imageView3.setVisibility(View.GONE);
        }

       Glide.with(context)
                .load(list[position])
                .override(240,240).centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
            public void onClick(View v) {


                  Intent intent = new Intent(context, Main2Activity.class);
                  intent.putExtra("key", holder.getAdapterPosition());
                  intent.putExtra("total",getItemCount());
                  intent.putExtra("flag",0);
                  context.startActivity(intent);

            }
        });



            }




    @Override
    public int getItemCount() {
        return list.length;
    }
    static class  MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView,imageView3,imageView4,imageView5;
        MyViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            imageView3=itemView.findViewById(R.id.imageView3);
            imageView4=itemView.findViewById(R.id.delete);
            imageView5=itemView.findViewById(R.id.share);
        }
    }
}
