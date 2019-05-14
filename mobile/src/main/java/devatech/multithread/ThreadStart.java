package devatech.multithread;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import devatech.section_adapter.Item;
import devatech.section_adapter.SectionedExpandableGridAdapter;

/**
 * Created by Ponnusamy M on 2/9/2017.
 */

public class ThreadStart extends  Thread {
    String Url;
    SectionedExpandableGridAdapter.ViewHolder viewHolder;
    String Destination;
    Item item;
    Lock lock;

    public ThreadStart(String Url,SectionedExpandableGridAdapter.ViewHolder viewHolder, String Destination, Item item,Lock lock) {
        this.Url=Url;
        this.viewHolder=viewHolder;
        this.Destination=Destination;
        this.item=item;
        this.lock=lock;
    }

    @Override
    public void run() {
       // super.run();
        try {

            lock.addNewThread();

            new Download(Url, viewHolder, Destination, item).download();

            lock.removeThread();
            synchronized (lock) {
                lock.notify();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "ThreadStart", e.toString());
        }
    }
}
