package base.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public enum IP {
    INSTANCE;

    public String getLocalIp() {
        if( LOCAL_IP_DEFAULT.equals(localIp) ) {
            try {
                List<String>ips = new ArrayList<String>();
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iface = interfaces.nextElement();
                    // filters out 127.0.0.1 and inactive interfaces
                    if (iface.isLoopback() || !iface.isUp()) {
                        continue;
                    }

                    Enumeration<InetAddress> addresses = iface.getInetAddresses();
                    while(addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        String ip = addr.getHostAddress();
                        if( !ip.contains(":")
                                && !ip.startsWith("127.")
                                && !ip.startsWith("224.")
                                && !ip.startsWith("255.") ) {
                            ips.add(ip);
                        }
                    }
                }

                // try and get 10.
                for (String ip : ips) {
                    if( ip.startsWith("10.") ) {
                        localIp = ip;
                        break;
                    }
                }

                // nothing set, try and get the first ip address
                if( LOCAL_IP_DEFAULT.equals(localIp) && ips.size()>0 ) {
                    localIp = ips.get(0);
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        return localIp;
    }

    public InetAddress getLocalAddress() {
        if(localAddress==null) {
            try {
                localAddress = InetAddress.getByName(getLocalIp());
            } catch (UnknownHostException excep) {
                localAddress = InetAddress.getLoopbackAddress();
            }
        }
        return localAddress;
    }

    public static InetAddress getLocalAddress(String ip) {
        if(ip==null) {
            throw new IllegalArgumentException("Null value for ip");
        }
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException excep) {
            address = InetAddress.getLoopbackAddress();
        }
        return address;
    }

    public void overideLocalIp(String myIp) {
        localIp = myIp;
        localAddress = null;
    }

    private static final String LOCAL_IP_DEFAULT = "127.0.0.1";

    private String localIp = LOCAL_IP_DEFAULT;

    private InetAddress localAddress;

}
