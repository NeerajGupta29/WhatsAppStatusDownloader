package status.downloader.whatsappstatusdownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import status.downloader.whatsappstatusdownloader.ui.main.Fragment1;
import status.downloader.whatsappstatusdownloader.ui.main.Fragment2;

public class Main2Activity extends AppCompatActivity {
    public int position,total=0;
    VideoView videoView;
    boolean flag=false;
    MediaController mediaController;
    int flag2;
    LinearLayout linearLayout;
    PhotoView photoView;
    private View decorView;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;
    BitmapDrawable drawable;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        createPersonalisedAd();
        photoView=findViewById(R.id.photoView);
        videoView=findViewById(R.id.videoView);
        linearLayout=findViewById(R.id.bottomBar);

        position = intent.getIntExtra("key", -1);
        total=intent.getIntExtra("total",-1);
        flag2=intent.getIntExtra("flag",-1);
         Show();
        decorView=getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

    }
    public void Download (View view)
    {

        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/WhatsApp Saved Status");
        directory.mkdir();
        File file;
        if(!flag)
            file=new File(directory,System.currentTimeMillis()+".jpg");
        else
            file=new File(directory,System.currentTimeMillis()+".mp4");

        try(InputStream in=new FileInputStream(Fragment1.list[position])){

            try (OutputStream out =new FileOutputStream(file)){
                byte[] buf=new byte[1024];
                int len;
                while ((len=in.read(buf))>0)
                    out.write(buf,0,len);

                Toast.makeText(this,"Status download in WhatsApp Saved Status Folder",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }




    }
    public void Share(View view) {


        videoView.pause();
            try {

                String videoPath = Fragment1.list[position].getPath();
                Uri uri=Uri.parse(videoPath);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("video/mp4");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share Video To:"));




            }
            catch (Exception e){
                Toast.makeText(this,String.valueOf(e),Toast.LENGTH_SHORT).show();
            }
        }

    public void Back(View view){
        if(position>0) {
            position = position - 1;
        }
            else
                {
                    position=total-1;
                }
            Show();
        }


    public void Next(View view){
        if(position<total-1)
        {
            position=position+1;
        }
        else
        { position=0;
        }
        Show();
    }

    public void Show(){
        if(flag2==0)
        {  if(Fragment1.list[position].getName().endsWith(".mp4"))
        {
            photoView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            flag = true;
            mediaController = new MediaController(this);

            videoView.setVideoURI(Uri.parse(Fragment1.list[position].getPath()));

            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            videoView.start();




        }
        else {

            videoView.setVisibility(View.GONE);

            photoView.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(Fragment1.list[position])
                    .into(photoView);
        }
        }

        else
        {
            linearLayout.setVisibility(View.GONE);
            if(Fragment2.list4.get(position).getName().endsWith(".mp4"))
            {   photoView.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                if(mediaController==null)
                    mediaController=new MediaController(this);

                videoView.setVideoURI(Uri.parse(Fragment2.list4.get(position).getPath()));

                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.start();

            }
            else {
                videoView.setVisibility(View.GONE);
                photoView.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(Fragment2.list4.get(position))
                        .into(photoView);

            }

        }
    }

    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }
    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    private void createPersonalisedAd()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        createInterstitialAd(adRequest);
    }
    private void createInterstitialAd(AdRequest adRequest){
        InterstitialAd.load(this,"ca-app-pub-8646118031997878/3166836501", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("ad", "onAdLoaded");
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.





                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("ad", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd != null)
        {
            mInterstitialAd.show(this);
        }

        super.onBackPressed();


    }
}
