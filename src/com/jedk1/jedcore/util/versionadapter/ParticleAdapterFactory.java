package com.jedk1.jedcore.util.versionadapter;

import org.bukkit.Bukkit;

public class ParticleAdapterFactory {

    private ParticleAdapter adapter;

    public ParticleAdapterFactory() {
        String version = Bukkit.getServer().getClass().getPackage().getName();
        Bukkit.getLogger().info("[JedCore] Checking Bukkit version for ParticleAdapter: " + version);

        if (version.equals("org.bukkit.craftbukkit")) {
            Bukkit.getLogger().info("[JedCore] Using 1.20.5+ ParticleAdapter");
            adapter = new ParticleAdapter_1_20_5();
        } else {
            Bukkit.getLogger().info("[JedCore] Using 1.20.4- ParticleAdapter");
            adapter = new ParticleAdapter_1_20_4();
        }
    }

    public ParticleAdapter getAdapter() {
        return adapter;
    }
}