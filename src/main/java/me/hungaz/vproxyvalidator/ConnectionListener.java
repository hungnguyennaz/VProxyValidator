package me.hungaz.vproxyvalidator;

import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.event.ClientConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.List;

public class ConnectionListener implements Listener {
    private final VProxyValidatorBungeePlugin plugin;
    public ConnectionListener(VProxyValidatorBungeePlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onClientConnect(ClientConnectEvent event) {
        ListenerInfo listenerInfo = event.getListener();
        if (listenerInfo.isProxyProtocol()) {
            InetSocketAddress inet = (InetSocketAddress) event.getSocketAddress();
            String ip = inet.getAddress().getHostAddress();
            if (!isAllowedIP(ip)) {
                event.setCancelled(true);
                plugin.getLogger().info("§cĐã chặn kết nối từ IP " + ip + " vì nó không thuộc hệ thống của VietProtect.");
            }
        }
    }
    private boolean isAllowedIP(String ipAddress) {
        List<String> allowedIPs = plugin.getAllowedIPs();
        for (String allowedIP : allowedIPs) {
            if (IPUtils.ipMatchesCIDR(ipAddress, allowedIP)) {
                return true;
            }
        }
        return false;
    }
}
