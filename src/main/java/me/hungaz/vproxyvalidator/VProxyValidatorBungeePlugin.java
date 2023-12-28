package me.hungaz.vproxyvalidator;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VProxyValidatorBungeePlugin extends Plugin {
    private List<String> allowedIPs;
    @Override
    public void onEnable() {
        fetchIPWhitelist();
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ConnectionListener(this));
    }
    private void fetchIPWhitelist() {
        allowedIPs = new ArrayList<>();
        try {
            URL url = new URL("https://raw.githubusercontent.com/hungnguyennaz/VietProtect-IPs/main/v4.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                allowedIPs.add(line.trim());
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> getAllowedIPs() {
        return allowedIPs;
    }
}
