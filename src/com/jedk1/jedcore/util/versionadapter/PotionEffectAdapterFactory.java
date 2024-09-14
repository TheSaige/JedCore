package com.jedk1.jedcore.util.versionadapter;

import org.bukkit.Bukkit;

public class PotionEffectAdapterFactory {

    private PotionEffectAdapter adapter;

    public PotionEffectAdapterFactory() {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        Bukkit.getLogger().info("[JedCore] Checking Bukkit version for PotionEffectAdapter: " + version);

        if (version.equals("org.bukkit.craftbukkit")) {
            Bukkit.getLogger().info("[JedCore] Using 1.20.5+ PotionEffectAdapter");
            adapter = new PotionEffectAdapter_1_20_5();
        } else {
            Bukkit.getLogger().info("[JedCore] Using 1.20.4- PotionEffectAdapter");
            adapter = new PotionEffectAdapter_1_20_4();
        }
    }

    public PotionEffectAdapter getAdapter() {
        return adapter;
    }
}
