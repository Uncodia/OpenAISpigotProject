package com.uncodia.openaispigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpenAISpigot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().broadcast("OpenAISpigot is enabled!", "openaispigot.broadcast");
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().getPlugin("OpenAISpigot").getDataFolder().mkdirs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
