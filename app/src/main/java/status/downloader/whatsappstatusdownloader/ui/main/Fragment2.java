package status.downloader.whatsappstatusdownloader.ui.main;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import status.downloader.whatsappstatusdownloader.R;


public class Fragment2 extends Fragment {

    public static ArrayList<File>list4;
    private RecyclerView rv;

    public Fragment2() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View fragment1View = inflater.inflate(R.layout.fragment2, container, false);
        rv= fragment1View.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(manager);
        return fragment1View;
    }

    @Override
    public void onResume() {
        super.onResume();
        int i=0;
        list4=new ArrayList<>();
        try {
        File root = new File(Environment.getExternalStorageDirectory().toString() + "/WhatsApp Saved Status");
        assert root != null;
        File[] list2 = root.listFiles();
        while(i< Objects.requireNonNull(list2).length){

            list4.add(list2[i]);
            i++;
        }
            Collections.sort(list4,Collections.<File>reverseOrder());
        MyAdapter2 myAdapter = new MyAdapter2(getContext(), list4);
            rv.setAdapter(myAdapter);


    } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
