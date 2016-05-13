package com.example.user.arlamtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;



public class StatActivity extends ActionBarActivity {

    String d1;
    String t1;
    String d2;
    String t2;
    TextView textd1;
    TextView textt1;
    TextView textd2;
    TextView textt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        Intent i = getIntent();
        d1 = i.getExtras().getString("d1");
        t1 = i.getExtras().getString("t1");
        d2 = i.getExtras().getString("d2");
        t2 = i.getExtras().getString("t2");
        textd1 = (TextView)findViewById(R.id.textd1);
        textd1.setText(d1);
        textd2 = (TextView)findViewById(R.id.textd2);
        textd2.setText(d2);
        textt1 = (TextView)findViewById(R.id.textt1);
        textt1.setText(t1);
        textt2 = (TextView)findViewById(R.id.textt2);
        textt2.setText(t2);
        /*Net net = new Net();
        net.execute();*/



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stat, menu);
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
                String link="192.168.43.245/stat.php";



                //String link = "http://52.68.141.174/php/test.php";

                URL url = new URL(link);
                url.openStream();

            }
            catch(Exception e){
                return new String("Exception:" + e.getMessage());
            }

            Parsing();


            return null;
        }

        protected void onPostExecute(String str)
        {


            Toast.makeText(getApplicationContext(),d1,Toast.LENGTH_SHORT).show();
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
                int tagId =0;

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
