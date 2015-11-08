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
    // ����WifiManager����
    private WifiManager mWifiManager;
    private boolean flag=false;
    // ����WifiInfo����
    private WifiInfo mWifiInfo;


    /**
     * ���췽��
     */
    public WifiAdmin(Context context,  List<Scandata> listResultofINI) {
        this.listResultofHam = listResultofHam;
        this.listResultofINI = listResultofINI;
        mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }


    /**
     * ɨ���ܱ�����
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
     * �õ�ɨ����
     */
    public String getScanResult() {
        // ÿ�ε��ɨ��֮ǰ�����һ�ε�ɨ����
        if (mStringBuffer != null) {
            mStringBuffer = new StringBuffer();
        }
        // ��ʼɨ������
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