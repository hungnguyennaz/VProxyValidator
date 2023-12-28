package me.hungaz.vproxyvalidator;

public class IPUtils {

    public static boolean ipMatchesCIDR(String ipAddress, String cidr) {
        String[] parts = cidr.split("/");
        String cidrIp = parts[0];
        int cidrPrefix = Integer.parseInt(parts[1]);

        String[] ipParts = ipAddress.split("\\.");
        int ip = (Integer.parseInt(ipParts[0]) << 24)
                | (Integer.parseInt(ipParts[1]) << 16)
                | (Integer.parseInt(ipParts[2]) << 8)
                | Integer.parseInt(ipParts[3]);

        int subnetMask = -1 << (32 - cidrPrefix);
        int cidrNetwork = IPUtils.ipToInteger(cidrIp) & subnetMask;

        return (ip & subnetMask) == cidrNetwork;
    }

    private static int ipToInteger(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        return (Integer.parseInt(parts[0]) << 24)
                | (Integer.parseInt(parts[1]) << 16)
                | (Integer.parseInt(parts[2]) << 8)
                | Integer.parseInt(parts[3]);
    }
}
