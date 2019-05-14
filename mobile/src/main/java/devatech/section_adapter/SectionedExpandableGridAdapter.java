package devatech.section_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import devatech.kims.Baseconfig;
import devatech.kims.R;
import devatech.multithread.Lock;
import devatech.multithread.ThreadStart;
import devatech.new_task.PdfViewActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by lenovo on 2/23/2016.
 */
public class SectionedExpandableGridAdapter extends RecyclerView.Adapter<SectionedExpandableGridAdapter.ViewHolder> {

    //data array
    private ArrayList<Object> mDataArrayList;

    //context
    private final Context mContext;

    //listeners
    private final ItemClickListener mItemClickListener;
    private final SectionStateChangeListener mSectionStateChangeListener;

    private ViewHolder viewHolders;

    //view type
    private static final int VIEW_TYPE_SECTION = R.layout.layout_section;
    //private static final int VIEW_TYPE_ITEM = R.layout.layout_item; //TODO : change this
    private static final int VIEW_TYPE_ITEM = R.layout.card_view_list1;

    public SectionedExpandableGridAdapter(Context context, ArrayList<Object> dataArrayList,
                                          final GridLayoutManager gridLayoutManager, ItemClickListener itemClickListener,
                                          SectionStateChangeListener sectionStateChangeListener) {

        mContext = context;
        mItemClickListener = itemClickListener;
        mSectionStateChangeListener = sectionStateChangeListener;
        mDataArrayList = dataArrayList;


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return isSection(position) ? gridLayoutManager.getSpanCount() : 1;
                // return (3 - position % 3);

            }
        });


    }

    private boolean isSection(int position) {
        return mDataArrayList.get(position) instanceof Section;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        switch (holder.viewType) {


            case VIEW_TYPE_ITEM:

               /* final Item item = (Item) mDataArrayList.get(position);
                holder.itemTextView.setText(item.getName());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(item);
                    }
                });*/


                final Item item = (Item) mDataArrayList.get(position);

                /********
                 *
                 * @POnnusamy
                 * Date: 24.2.2017
                 * Description: Write External storage apf viewer application
                 *
                 * *********/

               /* try {
                    shipPDFViewer(mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/
               /* File pdfimage1 = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_Image");

                if (!pdfimage1.exists())
                {
                    pdfimage1.mkdirs();
                }*/

                //Check Is offline Pdf is avialble

                File filechecks = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf");

                if (filechecks.exists()) {
                    holder.pdf_available.setImageResource(R.drawable.downloaded);
                } else {
                    holder.pdf_available.setImageResource(R.drawable.notdownload);
                }

                //==========================================

                if (Baseconfig.CheckNW(mContext)) {
                    /*******
                     * @Ponnusamy
                     * Date: 13.2.2017
                     * Description: if is online
                     * *******/

                    String URL = item.getImg();




                    File pdfimage = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".png");
                    if(pdfimage.exists()) //if check is available sd card
                    {
                        String filename = String.valueOf(item.getId()) + ".png";
                        String path = Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/"+filename;
                        Picasso.with(mContext).load(new File(path)).into(holder.imgview);

                    }else {
                        Picasso.with(mContext)
                                .load(item.getImg())
                                .placeholder(R.drawable.no_image)
                                .error(R.drawable.no_image)
                                // .centerCrop()
                                .into(holder.imgview);

                    }
                } else {
                    /*******
                     * @Ponnusamy
                     * Date: 13.2.2017
                     * Description: if is offline load pdf image in offline
                     * *******/

                    String filename = String.valueOf(item.getId()) + ".png";
                    String path = Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/"+filename;

                    File fl=new File(path);

                    if(fl.exists())
                    {
                        Picasso.with(mContext).load(new File(path)).into(holder.imgview);
                    }
                    else
                    {
                        Picasso.with(mContext).load(R.drawable.no_image).into(holder.imgview);
                    }

                }

                /******
                 * @Ponnusamy
                 * Date: 13.2.2017
                 * Description : if pdf is downloaded write image in sd
                 * ******/
                File pdfimage = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_Image/" + String.valueOf(item.getId()) + ".png");
                File pdffilecheck = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf");

                if (pdffilecheck.exists()) {
                    if(!pdfimage.exists())
                    {
                        Uri uri = null;
                        try {

                            uri = Uri.fromFile(pdffilecheck);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //   Baseconfig.generateImageFromPdf(uri, holder.view.getContext(), String.valueOf(item.getId()));
                    }

                }



                holder.itemTextView.setText(item.getName());
                holder.Series.setText(item.getSeries());

                holder.cardvw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //   mItemClickListener.itemClicked(item);
                        //  Toast.makeText(view.getContext(), "Item: " + item.getName() + " clicked" + item.getPDF_URL(), Toast.LENGTH_SHORT).show();


                        File file = new File(Environment.getExternalStorageDirectory().toString().toString() + "/.KIMS/_PDF/");
                        if (!file.exists()) {
                            file.mkdir();

                            File filecheck = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf");

                            if (filecheck.exists()) {
                                // Toast.makeText(view.getContext(), "Already  Download ...", Toast.LENGTH_SHORT).show();
                                // ((Activity) view.getContext()).finish();
                               /* Intent intent4 = new Intent(view.getContext(), PdfViewActivity.class);
                                intent4.putExtra("Id", item.getId());
                                intent4.putExtra("FilePath", filecheck.toString());
                                intent4.putExtra("FileName", item.getName());
                                intent4.putExtra("Title", item.getName() + " - " + item.getSeries());
                                view.getContext().startActivity(intent4);*/

                                /*Intent intent = new Intent(view.getContext(), MyPdfViewerActivity.class);
                                intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf");
                               view.getContext().startActivity(intent);*/

                                openPDF(filecheck,view.getContext());
                            } else {
//                                DownloadFileFromURLTask downloadFileFromURLTask = new DownloadFileFromURLTask(String.valueOf(item.getId()), item.getPDF_URL(), holder, Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf", item);
//                                downloadFileFromURLTask.execute();

                                /******
                                 *
                                 * @Ponnusamy
                                 * Date : 9.2.2017
                                 * Description: MultiThread Downloader
                                 *
                                 * *********/
                                if (Baseconfig.isConnected(mContext)) {
                                    try {
                                        Lock lock = new Lock();
                                        new ThreadStart(item.getPDF_URL(), holder, Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf", item, lock).start();

                                        /*******
                                         *Description: Download PDF Image
                                         * @Author: Ponnusamy
                                         * Date: 24.2.2017
                                         * ******/
                                        // TODO: 2/24/2017
                                        DownloadImageSDcard downloadImageSDcard=new DownloadImageSDcard();
                                        downloadImageSDcard.execute(item.getImg(),String.valueOf(item.getId()));

                                        while (lock.getNumberofCount() > 0)
                                            synchronized (lock) {
                                                lock.wait();
                                            }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("Information")
                                            .setContentText("No Internet Connection Available\nGet connected with internet to download this magazines")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                    sweetAlertDialog.dismiss();
/*
                                                    ((Activity)mContext).finish();
                                                    Intent intent = new Intent(mContext, Task_Navigation.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    mContext.startActivity(intent);
                                                    Baseconfig.setLocale(Baseconfig.Language, mContext, null);*/
                                                }
                                            })
                                            .show();
                                }
                            }


                        } else {


                            File filecheck = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf");

                            if (filecheck.exists()) {
                                // Toast.makeText(view.getContext(), "Already  Download ...", Toast.LENGTH_SHORT).show();
                                //   ((Activity) view.getContext()).finish();
                               /* Intent intent4 = new Intent(view.getContext(), PdfViewActivity.class);
                                intent4.putExtra("Id", item.getId());
                                intent4.putExtra("FilePath", filecheck.toString());
                                intent4.putExtra("FileName", item.getName());
                                intent4.putExtra("Title", item.getName() + " - " + item.getSeries());
                                view.getContext().startActivity(intent4);*/
                                /*Intent intent = new Intent(view.getContext(), MyPdfViewerActivity.class);
                                intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf");
                                view.getContext().startActivity(intent);*/
                                openPDF(filecheck,view.getContext());

                            } else {
                                /******
                                 *
                                 * @Ponnusamy
                                 * Date : 9.2.2017
                                 * Description: MultiThread Downloader
                                 *
                                 * *********/
//                                DownloadFileFromURLTask downloadFileFromURLTask = new DownloadFileFromURLTask(String.valueOf(item.getId()), item.getPDF_URL(), holder, Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf", item);
//                                downloadFileFromURLTask.execute();
                                if (Baseconfig.isConnected(mContext)) {
                                    try {
                                        Lock lock = new Lock();
                                        new ThreadStart(item.getPDF_URL(), holder, Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf", item, lock).start();

                                        /*******
                                         *Description: Download PDF Image
                                         * @Author: Ponnusamy
                                         * Date: 24.2.2017
                                         * ******/
                                        // TODO: 2/24/2017
                                        DownloadImageSDcard downloadImageSDcard=new DownloadImageSDcard();
                                        downloadImageSDcard.execute(item.getImg(),String.valueOf(item.getId()));
                                        while (lock.getNumberofCount() > 0)
                                            synchronized (lock) {
                                                lock.wait();
                                            }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("Information")
                                            .setContentText("No Internet Connection Available\nGet connected with internet to download this magazines")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                    sweetAlertDialog.dismiss();
/*
                                                    ((Activity)mContext).finish();
                                                    Intent intent = new Intent(mContext, Task_Navigation.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    mContext.startActivity(intent);
                                                    Baseconfig.setLocale(Baseconfig.Language, mContext, null);*/
                                                }
                                            })
                                            .show();
                                }

                            }
                        }


                    }
                });

                /*String imageurl = item.getImg();

                Picasso.with(holder.imgview.getContext())
                        .load(imageurl)
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        // .centerCrop()
                        .into(holder.imgview);*/

                break;


            case VIEW_TYPE_SECTION:
                final Section section = (Section) mDataArrayList.get(position);
                holder.sectionTextView.setText(section.getName());
                holder.sectionTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(section);
                    }
                });
                holder.sectionToggleButton.setChecked(section.isExpanded);
                holder.sectionToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mSectionStateChangeListener.onSectionStateChanged(section, isChecked);
                    }
                });
                break;

        }

    }

    void openPDF(File file,Context context)
    {

       /* PackageManager pm = context.getPackageManager();
        boolean isInstalled = isPackageInstalled("pdf.reader", pm);
        if(isInstalled)
        {*/
        try {
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(context, "PDF Viewer is not found...", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.pdfviewer"));
            context.startActivity(i);

/*
                Uri packageURI = Uri.parse("pdf.reader");
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        packageURI);

                // Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // intent.setFlags(Intent.ACTION_PACKAGE_REPLACED);

                // intent.setAction(Settings. ACTION_APPLICATION_SETTINGS);

                intent.setDataAndType(
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/.KIMS/_PDF/PDFViewApp/pdfviewver.apk")),
                        "application/vnd.android.package-archive");

                // Not open this Below Line Bcuz...
                // //intent.setClass(this, Project02Activity.class); // This Line Call
                // Activity Recursively its dangerous.

                context.startActivity(intent);*/
        }
        //}
      /*  else
        {
                Uri packageURI = Uri.parse("pdf.reader");
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        packageURI);

                // Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // intent.setFlags(Intent.ACTION_PACKAGE_REPLACED);

                // intent.setAction(Settings. ACTION_APPLICATION_SETTINGS);

                intent.setDataAndType(
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/.KIMS/_PDF/PDFViewApp/pdfviewver.apk")),
                        "application/vnd.android.package-archive");

                // Not open this Below Line Bcuz...
                // //intent.setClass(this, Project02Activity.class); // This Line Call
                // Activity Recursively its dangerous.

                context.startActivity(intent);




        }
*/


    }

    private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    private void shipPDFViewer(Context context) throws IOException {

        // Check if the database exists before copying
        boolean initialiseDatabase = (new File(Environment.getExternalStorageDirectory()+"/.KIMS/_PDF/PDFViewApp/pdfviewver.apk")).exists();

        if (initialiseDatabase == false) {
            Log.i("Processing...", "Copying Database");
            // Open the .db file in your assets directory
            InputStream is = context.getAssets().open("pdfviewer.apk");
            File dir=new File(Environment.getExternalStorageDirectory()+"/.KIMS/_PDF/PDFViewApp");
            if(!dir.exists()) {
                dir.mkdirs();
            }

            //Storagename
            File file=new File(Environment.getExternalStorageDirectory()+"/.KIMS/_PDF/PDFViewApp/pdfviewver.apk");

            // Copy the database into the destination
            OutputStream os = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            int sizeOfInputStram = is.available();
            long total = 0;
            while ((length = is.read(buffer)) > 0) {
                total += length;

                os.write(buffer, 0, length);

            }
            os.flush();

            os.close();
            is.close();


        }



    }
    @Override
    public int getItemCount() {
        return mDataArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSection(position))
            return VIEW_TYPE_SECTION;
        else return VIEW_TYPE_ITEM;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        public View view;
        int viewType;

        //for section
        TextView sectionTextView;
        ToggleButton sectionToggleButton;


        //for item
        TextView itemTextView, Series;
        CardView cardvw;
        ImageView imgview;
        public ProgressBar downloadProgress;
        public TextView progresstext;

        public ImageView pdf_available;


        public ViewHolder(View view, int viewType) {

            super(view);

            /*this.viewType = viewType;
            this.view = view;

            if (viewType == VIEW_TYPE_ITEM) {
                itemTextView = (TextView) view.findViewById(R.id.text_item);
            } else {
                sectionTextView = (TextView) view.findViewById(R.id.text_section);
                sectionToggleButton = (ToggleButton) view.findViewById(R.id.toggle_button_section);
            }
*/


            this.viewType = viewType;
            this.view = view;

            if (viewType == VIEW_TYPE_ITEM) {
                itemTextView = (TextView) view.findViewById(R.id.textView4);
                cardvw = (CardView) view.findViewById(R.id.card_view);
                imgview = (ImageView) view.findViewById(R.id.img);
                Series = (TextView) view.findViewById(R.id.textView5);
                downloadProgress = (ProgressBar) view.findViewById(R.id.download_progress);
                progresstext = (TextView) view.findViewById(R.id.textView8);
                pdf_available = (ImageView) view.findViewById(R.id.pdf_available);

            } else {
                sectionTextView = (TextView) view.findViewById(R.id.text_section);
                sectionToggleButton = (ToggleButton) view.findViewById(R.id.toggle_button_section);
            }

        }


    }


}

class DownloadFileFromURLTask extends
        AsyncTask<String, String, String> {
    String fileid = "";
    String Pdf_url = "";
    SectionedExpandableGridAdapter.ViewHolder viewHolder;
    Item item;
    String Destinationpath = "";

    @Override
    protected void onPreExecute() {
        // super.onPreExecute();

        viewHolder.downloadProgress.setVisibility(View.VISIBLE);
        viewHolder.progresstext.setVisibility(View.VISIBLE);


    }

    /**
     * Downloading file in background thread
     */

    @Override
    protected String doInBackground(String... f_url) {
        Log.i(TAG, "do in background");


        int count;
        //Code to update the UI
        try {
            URL url = new URL(Pdf_url);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            File file = new File(Destinationpath);
            // Output stream to write file
            OutputStream output = new FileOutputStream(file);

            byte data[] = new byte[104857600];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }


        return null;
    }

    /**
     * Updating progress bar
     */
    protected void onProgressUpdate(final String... progress) {
        // setting progress percentage
        viewHolder.downloadProgress.setProgress(Integer.parseInt(progress[0]));
        viewHolder.downloadProgress.setProgress(Integer.parseInt(progress[0]));
        viewHolder.progresstext.setText(progress[0] + "%");
        viewHolder.progresstext.setText(progress[0] + "%");


    }

    /**
     * After completing background task Dismiss the progress dialog
     **/

    @Override
    protected void onPostExecute(String file_url) {
        Log.i(TAG, "on post excute!");

        // dismiss progressbars after the file was downloaded

        viewHolder.downloadProgress.setVisibility(View.GONE);
        viewHolder.downloadProgress.setVisibility(View.GONE);

        Toast.makeText(viewHolder.view.getContext(), "Download Sucess...", Toast.LENGTH_SHORT).show();

        ((Activity) viewHolder.view.getContext()).finish();
        Intent intent4 = new Intent(viewHolder.view.getContext(), PdfViewActivity.class);
        intent4.putExtra("Id", item.getId());
        intent4.putExtra("FilePath", Destinationpath.toString());
        intent4.putExtra("FileName", item.getName());
        intent4.putExtra("Title", item.getName() + " - " + item.getSeries());
        viewHolder.view.getContext().startActivity(intent4);


    }

    public DownloadFileFromURLTask(String id, String pdf_url, SectionedExpandableGridAdapter.ViewHolder holder, String Destination_path, Item item) {

        this.fileid = id;
        this.Pdf_url = pdf_url;
        this.viewHolder = holder;
        this.Destinationpath = Destination_path;
        this.item = item;

    }

}


class DownloadImageSDcard extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {
        try {
            //set the download URL, a url that points to a file on the internet
            //this is the file to be downloaded
            URL url = new URL(params[0]);
            String filename=params[1];

            //create the new connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //set up some things on the connection
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //and connect!
            urlConnection.connect();

            //set the path where we want to save the file
            //in this case, going to save it on the root directory of the
            //sd card.
            File SDCardRoot =new File(Environment.getExternalStorageDirectory().toString().toString() + "/.KIMS/_PDF/");
            //create a new file, specifying the path, and the filename
            //which we want to save the file as.
            File file = new File(SDCardRoot,filename+".png");

            //this will be used to write the downloaded data into the file we created
            FileOutputStream fileOutput = new FileOutputStream(file);

            //this will be used in reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file
            int totalSize = urlConnection.getContentLength();
            //variable to store total downloaded bytes
            int downloadedSize = 0;

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0; //used to store a temporary size of the buffer

            //now, read through the input buffer and write the contents to the file
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                //add the data in the buffer to the file in the file output stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                //add up the size so we know how much is downloaded
                downloadedSize += bufferLength;
                //this is where you would do something to report the prgress, like this maybe
                //updateProgress(downloadedSize, totalSize);

            }
            //close the output stream when done
            fileOutput.close();

            //catch some possible errors...
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // see http://androidsnippets.com/download-an-http-file-to-sdcard-with-progress-notification
        return null;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }



}
