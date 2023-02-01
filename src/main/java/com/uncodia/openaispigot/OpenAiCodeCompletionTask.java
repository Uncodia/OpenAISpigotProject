package com.uncodia.openaispigot;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OpenAiCodeCompletionTask extends BukkitRunnable {
    String prompt = "";
    String textResponseOutput = null;
    Player player;
    Villager closestVillager;

    public OpenAiCodeCompletionTask(String prompt, Player player, Villager closestVillager) {
        this.prompt = prompt;
        this.player = player;
        this.closestVillager = closestVillager;
    }


    @Override
    public void run() {
        try {
            this.textResponseOutput = UnirestUtil.openAICompletions(prompt);
            player.getServer().broadcastMessage(ChatColor.AQUA + closestVillager.getName() + ": " + this.textResponseOutput);
            String textResponseOutput = this.textResponseOutput;
            new BukkitRunnable() {
                @Override
                public void run() {
                    //create a text above the villager without holographic displays
                    ArmorStand armorStand = player.getWorld().spawn(closestVillager.getLocation().add(0, 1, 0), ArmorStand.class);
                    armorStand.setGravity(false);
                    armorStand.setVisible(false);
                    //make textResponseOutput 10world per line
                    String[] words = textResponseOutput.split(" ");
                    //show 7 world by a time for 1.5 second each time with bukkit runnable in a loop
                    int textResponseOutputLength = words.length;
                    int i = 0;
                    for (int j = 0; j < textResponseOutputLength; j += 5) {
                        int finalJ = j;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                String text = "";
                                for (int k = finalJ; k < finalJ + 5; k++) {
                                    if (k < textResponseOutputLength) {
                                        text = text + words[k] + " ";
                                    }
                                }
                                armorStand.setCustomName(text);
                                armorStand.setCustomNameVisible(true);
                            }
                        }.runTaskLater(player.getServer().getPluginManager().getPlugin("OpenAISpigot"), 30 * i);
                        i++;
                    }
                    //make the villager stand still
                    closestVillager.setAI(false);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            armorStand.remove();
                            closestVillager.setAI(true);
                        }
                    }.runTaskLater(player.getServer().getPluginManager().getPlugin("OpenAISpigot"), 30 * i);
                }
            }.runTask(player.getServer().getPluginManager().getPlugin("OpenAISpigot"));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
