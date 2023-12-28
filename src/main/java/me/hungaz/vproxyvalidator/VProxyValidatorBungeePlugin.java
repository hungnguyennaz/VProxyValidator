package me.hungaz.vproxyvalidator;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VProxyValidatorBungeePlugin extends Plugin {
    private List<String> allowedIPs;
    @Override
    public void onEnable() {
        if (isProxyProtocolEnabled()) {
            if (fetchIPWhitelist()) {
                getLogger().info("§aPlugin đã khởi động, server đã được bảo vệ§r");
                getProxy().getScheduler().schedule(this, this::fetchIPWhitelist, 5, 5, TimeUnit.MINUTES);
            } else {
                getLogger().severe("§cCó lỗi khi setup plugin. Hãy liên hệ VietProtect thông qua §eDiscord https://dsc.gg/vietprotect§c để được hỗ trợ§r");
            }
            ProxyServer.getInstance().getPluginManager().registerListener(this, new ConnectionListener(this));
        } else {
            getLogger().severe("§cBạn cần phải bật §eproxy-protocol trong config.yml§c để sử dụng VietProtect Firewall§r");
            getLogger().severe("§cHãy tạo ticket kèm với dòng logs này để được hỗ trợ");
            getLogger().severe("§cServer sẽ được tắt để đảm bảo an toàn");
            getLogger().severe("§eSupport Server: https://dsc.gg/vietprotect");
            ProxyServer.getInstance().stop();
        }
    }
    private boolean isProxyProtocolEnabled() {
        for (ListenerInfo listener : ProxyServer.getInstance().getConfig().getListeners()) {
            if (listener.isProxyProtocol()) {
                return true;
            }
        }
        return false;
    }
    private boolean fetchIPWhitelist() {
        allowedIPs = new ArrayList<>();
        try {
            URL url = new URL("https://raw.githubusercontent.com/hungnguyennaz/VietProtect-IPs/main/v4.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                allowedIPs.add(line.trim());
            }
            reader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<String> getAllowedIPs() {
        return allowedIPs;
    }
}
