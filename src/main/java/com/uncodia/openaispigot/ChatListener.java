package com.uncodia.openaispigot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();


        new BukkitRunnable() {
            @Override
            public void run() {
                double closestDistance = Double.MAX_VALUE;
                Villager closestVillager = null;
                if(player.getNearbyEntities(10, 10, 10).size() == 0) {
                    player.getServer().broadcastMessage(ChatColor.RED + "There are no villagers nearby!");
                    return;
                }
                for (Entity entity : player.getNearbyEntities(10, 10, 10)) {
                    if (entity.getType() != EntityType.VILLAGER) {
                        continue;
                    }
                    double distance = entity.getLocation().distance(player.getLocation());
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestVillager = (Villager) entity;
                    }
                }
                if(closestVillager==null){
                    player.getServer().broadcastMessage(ChatColor.RED + "There are no villagers nearby!");
                    return;
                }

                if (closestVillager != null) {
                    closestVillager.shakeHead();
                    Vector lookDirection = player.getEyeLocation().toVector().subtract(closestVillager.getEyeLocation().toVector());
                    closestVillager.setAI(false);
                    closestVillager.teleport(closestVillager.getLocation().setDirection(lookDirection));
                    closestVillager.setAI(true);
                    String prompt = "You are a village in minecraft. Your name is "+closestVillager.getName()+". The name of player you are talking to is "+player.getName()+
                            ". Your location is "+closestVillager.getLocation().toString()+". Your type is "+closestVillager.getVillagerType()+
                            ". Your age is "+closestVillager.getAge()+". the seed that you are in is "+ closestVillager.getSeed()+". Respond to the following sentence as a villager in minecraft."+event.getMessage();
                    new OpenAiCodeCompletionTask(prompt,player,closestVillager).runTaskAsynchronously(event.getPlayer().getServer().getPluginManager().getPlugin("OpenAISpigot"));
                }
            }
        }.runTask(event.getPlayer().getServer().getPluginManager().getPlugin("OpenAISpigot"));

    }
}
