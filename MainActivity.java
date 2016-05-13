package com.example.user.arlamtest;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends ActionBarActivity implements DatePicker.OnDateChangedListener , TimePicker.OnTimeChangedListener{

    private AlarmManager mManager;

    private GregorianCalendar mCalendar;

    private DatePicker mDate;

    private TimePicker mTime;

    private NotificationManager mNotification;

    private static PowerManager.WakeLock screenWakeLock;

    private boolean stat = false;


    private boolean reset = false;
    private boolean lighton = false;
    private boolean lightoff = false;
    private boolean viboff = false;
    private Net net;


    String d1;
    String t1;
    String d2;
    String t2;

    protected void onCreate(Bundle savedInstanceState) {
        acquireWakeLock(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);


        //mNotification = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        mManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        mCalendar = new GregorianCalendar();
        Log.i("HelloAlarmActivity",mCalendar.getTime().toString());

        Button btn_set = (Button)findViewById(R.id.btn_set);
        Button btn_reset = (Button)findViewById(R.id.btn_reset);
        Button btn_lighton = (Button)findViewById(R.id.btn_lighton);
        Button btn_lightoff = (Button)findViewById(R.id.btn_lightoff);
        Button btn_viboff = (Button)findViewById(R.id.btn_viboff);
        Button btn_stat = (Button)findViewById(R.id.btn_stat);

        btn_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat = true;
                net = new Net();
                net.execute();



            }
        });

        btn_viboff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viboff = true;
                net = new Net();
                net.execute();
            }
        });


        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAlarm();
                releaseWakeLock();
            }
        });

        btn_lighton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lighton = true;

                net = new Net();
                net.execute();


            }
        });

        btn_lightoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightoff = true;

                net = new Net();
                net.execute();
            }
        });

        mDate = (DatePicker)findViewById(R.id.date_picker);
        mDate.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH),this);
        mTime = (TimePicker)findViewById(R.id.time_picker);
        mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        mTime.setOnTimeChangedListener(this);

    }

    private void setAlarm()
    {
        long nowTime = System.currentTimeMillis();
        if(  nowTime >= mCalendar.getTimeInMillis() ){

            Toast.makeText(getApplicationContext(),"입력한 날짜는 현재 날짜보다 이전입니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent i = new Intent(getApplicationContext(),AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);
        mManager.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),0, sender);
        Log.i("HelloAlarmActivity",mCalendar.getTime().toString());
    }

    private void resetAlarm()
    {
        reset = true;
        AlarmReceiver.stopRingtone();
        mManager.cancel(pendingIntent());
        net = new Net();
        net.execute();



    }

    private PendingIntent pendingIntent(){

        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
        return pi;
    }

    private void acquireWakeLock(Context context){

        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        screenWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, context.getClass().getName());

        if(screenWakeLock != null){
            screenWakeLock.acquire();
        }
    }

    private void releaseWakeLock(){

        if(screenWakeLock != null){
            screenWakeLock.release();
            screenWakeLock = null;
        }
    }

    //일자 설정 클래스의 상태변화 리스너
    public void onDateChanged (DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mCalendar.set (year, monthOfYear, dayOfMonth, mTime.getCurrentHour(), mTime.getCurrentMinute());
        Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
    }
    //시각 설정 클래스의 상태변화 리스너
    public void onTimeChanged (TimePicker view, int hourOfDay, int minute) {
        mCalendar.set (mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), hourOfDay, minute);
        Log.i("HelloAlarmActivity",mCalendar.getTime().toString());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class Net extends AsyncTask<String,Void,String>
    {
        protected Net()
        {}

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0)
        {
            try {
                String link="";

                if( reset )
                {
                    link = "http://192.168.43.245/vib.php";
                    reset = false;
                }
                else if(stat)
                {
                    link = "http://192.168.43.245/stat.php";
                }
                else if (lighton)
                {

                    link = "http://192.168.43.245/on.php";
                    lighton = false;
                }
                else if (lightoff)
                {
                    link = "http://192.168.43.245/off.php";

                    lightoff = false;
                }
                else if ( viboff)
                {
                    link = "http://192.168.43.245/viboff.php";
                    viboff = false;
                }


                //String link = "http://52.68.141.174/php/test.php";

                URL url = new URL(link);
                url.openStream();

            }
            catch(Exception e){
                return new String("Exception:" + e.getMessage());
            }

            if(stat)
                Parsing();


            return null;
        }

        protected void onPostExecute(String str)
        {
            if(stat) {
                Intent stati = new Intent(getApplicationContext(), StatActivity.class);
                stati.putExtra("d1", d1);
                stati.putExtra("t1", t1);
                stati.putExtra("d2", d2);
                stati.putExtra("t2", t2);
                stat = false;
                startActivity(stati);

            }
        }

        public void Parsing()
        {
            String link = "http://192.168.43.245/stat.xml";

            try
            {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                URL Server = new URL(link);
                InputStream is = Server.openStream();
                xpp.setInput(is, "UTF-8");

                String tag;
                int tagId=0;

                int eventType = xpp.getEventType();

                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tag = xpp.getName();

                            if(tag.equals("d1"))
                                tagId = 1;
                            else if ( tag.equals("t1"))
                                tagId = 2;
                            else if (tag.equals("d2") )
                                tagId =3;
                            else if ( tag.equals("t2"))
                                tagId =4;
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                        case XmlPullParser.TEXT:
                            if(tagId ==1)
                                d1 = xpp.getText();
                            else if (tagId==2)
                                t1 = xpp.getText();
                            else if (tagId == 3)
                                d2 = xpp.getText();
                            else if (tagId ==4)
                                t2 = xpp.getText();

                            tagId = 0;
                            break;
                    }

                    eventType = xpp.next();
                }

            }
            catch(XmlPullParserException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }


    }

}
