package org.example.Helpers;

import java.net.InetAddress;
import java.net.DatagramSocket;


public class ServerHelper {

    static String getLocalIpAddress(){
        String ip_address = null;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip_address = socket.getLocalAddress().getHostAddress();
        }catch(Exception e){

        }
        return ip_address;
    }    

}
