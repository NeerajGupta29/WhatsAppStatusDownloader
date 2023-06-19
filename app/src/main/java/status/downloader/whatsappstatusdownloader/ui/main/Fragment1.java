package status.downloader.whatsappstatusdownloader.ui.main;


import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.Objects;

import status.downloader.whatsappstatusdownloader.R;

public class Fragment1 extends androidx.fragment.app.Fragment {

    public static File[] list;
    private RecyclerView rv;

    public Fragment1() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment1View = inflater.inflate(R.layout.fragment1, container, false);
        rv= fragment1View.findViewById(R.id.recyclerView);
        rv.setHasFixedSize(true);
        LinearLayoutManager manager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(manager);
        manager.findFirstVisibleItemPosition();
        try {
            File root = new File(Environment.getExternalStorageDirectory().toString() + '/' + "WhatsApp" + '/' + "Media" + '/' + ".Statuses");
            list = root.listFiles();
            if (Objects.requireNonNull(list).length > 0) {
                MyAdapter myAdapter = new MyAdapter(getContext(), list);
                rv.setAdapter(myAdapter);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment1View;


    }


}
