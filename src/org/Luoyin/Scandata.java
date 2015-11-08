package org.Luoyin;

/**
 * Created by jjboom on 2015/10/10.
 */
public class Scandata {
    public  String BSSID ;
    public  String SSID ;
    public String location;
    public Scandata(String BSSID,String SSID,String location)
    {
        this.BSSID=BSSID;
        this.SSID=SSID;
        this.location=location;
    }

}
