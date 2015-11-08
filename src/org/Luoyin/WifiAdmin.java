package org.Luoyin;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiAdmin {
    private final static String TAG = "WifiAdmin";
    private StringBuffer mStringBuffer = new StringBuffer();
    private List<ScanResult> listResult;
    private List<Scandata> listResultofHam;
    private List<Scandata> listResultofINI;
    private ScanResult mScanResult;
    private int max=0;
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    private boolean flag=false;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;


    /**
     * 构造方法
     */
    public WifiAdmin(Context context,  List<Scandata> listResultofINI) {
        this.listResultofHam = listResultofHam;
        this.listResultofINI = listResultofINI;
        mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }


    /**
     * 扫描周边网络
     */
    public void scan() {
        mWifiManager.startScan();
        listResult = mWifiManager.getScanResults();
        if (listResult != null) {
            Log.i(TAG, "check the result");
        } else {
            Log.i(TAG, "there is no network around");
        }
    }

    /**
     * 得到扫描结果
     */
    public String getScanResult() {
        // 每次点击扫描之前清空上一次的扫描结果
        if (mStringBuffer != null) {
            mStringBuffer = new StringBuffer();
        }
        // 开始扫描网络
        scan();
        listResult = mWifiManager.getScanResults();
                                        if (listResult != null) {
                                            for (int i = 0; i < listResult.size(); i++) {
                                                mScanResult = listResult.get(i);
                                                if(mScanResult.level>listResult.get(max).level)
                                                    max=i;
                                                for (int t = 0; t < listResultofINI.size(); t++) {

                                                    if ((mScanResult.BSSID.compareTo(listResultofINI.get(t).BSSID) == 0) && (mScanResult.SSID.compareTo(listResultofINI.get(t).SSID) == 0)) {

                                                        Log.i(TAG, "Be careful THE IS AROUND THE BUILDING!!!!! ");
                                                        flag=true;
                                                        mStringBuffer = mStringBuffer.append("NO.").append(i + 1)
                                                                .append(" :").append(mScanResult.SSID).append("->")
                                                                .append(mScanResult.BSSID).append("->")
                                                                .append(mScanResult.capabilities).append("->")
                                                                .append(mScanResult.frequency).append("->")
                                                                .append(mScanResult.level).append("->")
                                                                .append(mScanResult.describeContents()).append("\n\n");
                                                    }
                                                }
                                            }
                                            if (flag==true) {
                                                mStringBuffer.append("\n\n").append("he is around"+listResultofINI.get(max).location);

                                        }
                                        }

            return mStringBuffer.toString();

    }
}