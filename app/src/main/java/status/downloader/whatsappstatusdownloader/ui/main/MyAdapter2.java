package status.downloader.whatsappstatusdownloader.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import status.downloader.whatsappstatusdownloader.Main2Activity;
import status.downloader.whatsappstatusdownloader.R;


public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder>  {
    private  Context context;
    private ArrayList<File> list;



    MyAdapter2(Context context, ArrayList<File> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_2,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int  position) {

       if(list.get(position).getName().endsWith(".mp4"))
        holder.imageView3.setVisibility(View.VISIBLE);
       else{
           holder.imageView3.setVisibility(View.GONE);
       }


       Glide.with(context)
                .load(list.get(position))
                .override(240,240).centerCrop()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
            public void onClick(View v) {

                  Intent intent = new Intent(context, Main2Activity.class);
                  intent.putExtra("key", holder.getAdapterPosition());
                  intent.putExtra("total",getItemCount());
                  intent.putExtra("flag",1);
                  context.startActivity(intent);

            }
        });
        holder.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String videoPath = list.get(position).getPath();
                    Uri uri=Uri.parse(videoPath);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("video/mp4");
                    intent.putExtra(Intent.EXTRA_STREAM,uri);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(Intent.createChooser(intent, "Share Video To:"));




                }
                catch (Exception e){
                    Toast.makeText(context,String.valueOf(e),Toast.LENGTH_SHORT).show();
                }
            }
        });

       holder.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {

                final AlertDialog.Builder deleteDialog=new AlertDialog.Builder(context);
                deleteDialog.setTitle("Delete");
                deleteDialog.setMessage("Do You really want to delete it ?");
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new File(list.get(position).getPath()).delete();
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,list.size());
                        Toast.makeText(context,"File Delete",Toast.LENGTH_SHORT).show();

                    }
                });
                deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                deleteDialog.show();
            }
        });






    }

    @Override
    public int getItemCount() {
        return list.size();
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
