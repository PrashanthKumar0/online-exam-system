package org.example.Helpers;

import java.net.InetAddress;
import java.net.DatagramSocket;


public class ServerHelper {

    public static String getLocalIpAddress(){
        String ip_address = null;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip_address = socket.getLocalAddress().getHostAddress();
        }catch(Exception e){

        }
        if(ip_address == null) return "127.0.0.1";
        
        return ip_address;
    }    

}
