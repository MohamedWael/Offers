package com.sunmediaeg.offers.utilities;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by moham on 1/22/2017.
 * <p>
 * this class is formed form the content described in :
 * https://www.codeproject.com/Articles/1112730/Android-Download-Manager-Tutorial-How-to-Download
 * <p>
 * And edited by mohamed wael
 * imohamedwael@Outlook.com
 */

public class DownloadUtil {

    private static DownloadUtil instance;

    private DownloadManager downloadManager;
    private Context mContext;
    private Cursor cursor;
    private DownloadListener downloadListener;
    private DownloadCompleteListener downloadCompleteListener;

    public static String NOTHING_DOWNLOADED = "NOTHING_DOWNLOADED";


    public static DownloadUtil getInstance(Context mContext, DownloadCompleteListener downloadCompleteListener) {
        if (instance == null) {
            instance = new DownloadUtil(mContext, downloadCompleteListener);
        }
        return instance;
    }

    public static DownloadUtil getInstance(Context mContext) {
        if (instance == null) {
            instance = new DownloadUtil(mContext);
        }
        return instance;
    }

    private DownloadUtil(Context mContext) {
        this.mContext = mContext;
        downloadListener = new DownloadListener() {
            boolean dwonloaded = false;

            @Override
            public void setDownloaded(boolean b) {
                dwonloaded = b;
            }

            @Override
            public boolean isDownloaded() {
                return dwonloaded;
            }
        };
    }

    private DownloadUtil(Context mContext, DownloadCompleteListener downloadCompleteListener) {
        this.mContext = mContext;
        this.downloadCompleteListener = downloadCompleteListener;
        downloadListener = new DownloadListener() {
            boolean dwonloaded = false;

            @Override
            public void setDownloaded(boolean b) {
                dwonloaded = b;
            }

            @Override
            public boolean isDownloaded() {
                return dwonloaded;
            }
        };
    }

    public long download(Uri uri, String fileName, String description) {

        long downloadID;

        // Create request for android download manager

        downloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle(fileName);

        //Setting description of request
        request.setDescription(description);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Set the local destination for the downloaded file to a path
        //within the application's external files directory
        request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, fileName);
//request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "");
        //Enqueue download and save into referenceId
        downloadID = downloadManager.enqueue(request);


        return downloadID;
    }

    /**
     * @param downloadID
     * @return a string to be parsed using Uri.parse to get the uri of the downloaded file
     */
    public Uri checkDownloadStatus(long downloadID) {
        String uri = NOTHING_DOWNLOADED;
        DownloadManager.Query downloadQuery = new DownloadManager.Query();
        //set the query filter to our previously Enqueued download
        downloadQuery.setFilterById(downloadID);

        //Query the download manager about downloads that have been requested.
        cursor = downloadManager.query(downloadQuery);
        getProgress(downloadID);
        if (cursor.moveToFirst()) {
            uri = downloadStatus(cursor, downloadID);
        }

//        return downloadManager.getUriForDownloadedFile(downloadID);
        return Uri.parse(uri);
    }

    private String downloadStatus(Cursor cursor, long refrenceDownloadId) {

        //column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        String filename = cursor.getString(filenameIndex);

//        String downloadFileLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//        if (downloadFileLocalUri != null) {
//            File mFile = new File(Uri.parse(downloadFileLocalUri).getPath());
//            String downloadedFilename = mFile.getName();
////            downloadFilePath = mFile.getAbsolutePath();
//        }
        Logger.d("getDownloadedFileURI", "" + getDownloadedFileURI(cursor));
        String uri = getDownloadedFileURI(cursor);
        if (uri != null) {
            File mFile = new File(Uri.parse(getDownloadedFileURI(cursor)).getPath());
            String downloadedFilename = mFile.getName();
        }


        String statusText = "";
        String reasonText = "";

        switch (status) {
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch (reason) {
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch (reason) {
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                reasonText = "Filename:\n" + filename;
                break;
        }

        if (notifyDownloadComplete(refrenceDownloadId))
            return getDownloadedFileURI(cursor);
        else return NOTHING_DOWNLOADED;
    }


    public boolean notifyDownloadComplete(final long downloadedFileId) {
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                //check if the broadcast message is for our enqueued download
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if (referenceId == downloadedFileId) {
                    Toast toast = Toast.makeText(mContext, "Download Complete", Toast.LENGTH_LONG);
                    toast.show();
                    downloadCompleteListener.onDownloadComplete(downloadManager.getUriForDownloadedFile(downloadedFileId));
                    downloadListener.setDownloaded(true);
                }
            }
        };
        mContext.registerReceiver(downloadReceiver, filter);
        return downloadListener.isDownloaded();
    }


    private String getDownloadedFileURI(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
    }

    private void getProgress(long downloadId) {

        final DownloadManager.Query q = new DownloadManager.Query();
        q.setFilterById(downloadId);
        new Thread(new Runnable() {
            boolean downloading = true;

            @Override
            public void run() {
                while (downloading) {
                    Cursor cursor = downloadManager.query(q);
                    cursor.moveToFirst();

                    int downloadedBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int size = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }

                    double progress = ((double) downloadedBytes / (double) size) * 100d;
                    if (size > -1)
                        downloadCompleteListener.getProgress(progress, downloadedBytes, size);
                    cursor.close();
                }
            }
        }).start();


    }

    private interface DownloadListener {

        void setDownloaded(boolean b);

        boolean isDownloaded();
    }

    public interface DownloadCompleteListener {
        void getProgress(double progress, long downloadedBytes, long sizeInBytes);

        void onDownloadComplete(Uri fileLocalUri);
    }
}