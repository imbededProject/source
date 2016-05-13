package com.example.user.arlamtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class AlarmReceiver extends BroadcastReceiver {

    private int YOURAPP_NOTI_ID;



    private static Ringtone r;


    private Net net;

    @Override
    public void onReceive(Context context, Intent intent){
        //showNotification(context, R.drawable.abc_ic_go_search_api_mtrl_alpha,"알람","움직여");

        net = new Net();
        net.execute();

        Uri notifi = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(context,notifi);

        r.play();



        try {
            Intent i = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_ONE_SHOT);
            pi.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }


    }

    public static void stopRingtone(){
        if ( r == null)
            return;
        r.stop();
    }



    public class Net extends AsyncTask<String,Void,String>
    {
        protected Net()
        {}

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0)
        {
            try {
                String link="http://192.168.43.245/on.php";



                //String link = "http://52.68.141.174/php/test.php";

                URL url = new URL(link);
                url.openStream();

            }
            catch(Exception e){
                return new String("Exception:" + e.getMessage());
            }



            return null;
        }

        protected void onPostExecute(String str)
        {

        }



    }

}
