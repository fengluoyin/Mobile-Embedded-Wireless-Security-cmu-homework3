package org.Luoyin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private WifiAdmin mWifiAdmin;
    private String mScanResult;
    static final int UPDATE_INTERVAL = 1200000;
    private Timer timer = new Timer();
    int counter = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "My Service created", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onCreate");
        List<Scandata> listResultofINI=new ArrayList<>();
        listResultofINI.add(new Scandata("04:bd:88:2b:4d:93", "CMU-SECURE","INI Quiet Study Area 205"));
        listResultofINI.add(new Scandata("04:bd:88:2b:4d:92", "CMU-SECURE","INI Quiet Study Area 205"));
        listResultofINI.add(new Scandata("04:bd:88:2b:4c:43", "CMU-SECURE","INI Project Room 203"));
        listResultofINI.add(new Scandata("04:bd:88:37:00:b3", "CMU-SECURE","INI 2nd floor kitchen"));
        listResultofINI.add(new Scandata("04:bd:88:2b:4d:d3", "CMU-SECURE","INI Electrical Room 218"));
        listResultofINI.add(new Scandata("04:bd:88:36:ff:d3", "CMU-SECURE","INI 2nd floor Printer"));
        listResultofINI.add(new Scandata("04:bd:88:37:00:13", "CMU-SECURE","INI Interview Room 201/202"));
        listResultofINI.add(new Scandata("04:bd:88:37:00:03", "CMU-SECURE","INI Open Study Area I (East)"));
        listResultofINI.add(new Scandata("04:bd:88:37:00:53", "CMU-SECURE","INI Open Study Area II (West)"));



        mWifiAdmin = new WifiAdmin(MyService.this, listResultofINI);


    }
    public void getScanResult() {
        mScanResult = mWifiAdmin.getScanResult();

        if(mScanResult!=null) {
            //he is nearby

            SendEmailTask emailTask = new SendEmailTask(
                    "flyfollowing@gmail.com",
                    "fly19940510",
                    "flyfollowing@gmail.com");
            emailTask.setEmailRecipient("448501751@qq.com");
            emailTask.setEmailSubject("Awesome HW3 Stuff!!");
            emailTask.setEmailBody("This email was sent from an NEXUS 5, without user interaction.\nSent to your address to be awesome..."+"\n\n"+mScanResult);
            Thread emailThread = new Thread(emailTask);
            emailThread.start();
        }
        else {
            //he is not nearby

        }
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "My Service Stoped", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onDestroy");

    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "My Service Start", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onStart");
        getScanResult();
        /*while(true) {
            try {
                Thread.sleep(1200000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getScanResult();

        }*/

          /*  new Handler().postDelayed(() -> {
                getScanResult();
                //execute the task
            }, 60000);*/


        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {
                Log.d("MyService", String.valueOf(++counter));
                getScanResult();
            }
        }, 0, UPDATE_INTERVAL);
    }
}
