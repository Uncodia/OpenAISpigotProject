package com.uncodia.openaispigot;


import org.bukkit.ChatColor;

import org.bukkit.entity.ArmorStand;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import org.bukkit.scheduler.BukkitRunnable;


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
            //bukkit runnable for showing text above villagers
            new BukkitRunnable() {
                @Override
                public void run() {
                    //create a text above the villager without holographic displays
                    ArmorStand armorStand = player.getWorld().spawn(closestVillager.getLocation().add(0, 0.3, 0), ArmorStand.class);
                    armorStand.setGravity(false);
                    armorStand.setVisible(false);
                    //make textResponseOutput sentences seperated by . ? ! and make each sentence
                    //and show them one by one with bukkit runnable in a loop with 3second delay each sentence
                    String[] sentences = textResponseOutput.split("(?<=[.?!])\\s*");
                    for (int i = 0; i < sentences.length; i++) {
                        String sentence = sentences[i];
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                armorStand.setCustomName(sentence);
                                armorStand.setCustomNameVisible(true);
                            }
                        }.runTaskLater(player.getServer().getPluginManager().getPlugin("OpenAISpigot"), 20 * 3 * i);
                    }
                    //bukkit runnable to remove the text after showed all texts and make the villager ai true again
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            armorStand.remove();
                            closestVillager.setAI(true);
                        }
                    }.runTaskLater(player.getServer().getPluginManager().getPlugin("OpenAISpigot"), 20 * 3 * sentences.length);
                }


                            //delay between each sentence based on number of character in sentence

                    //bukkit runnable to remove the text after showed all texts and make the villager ai true again




            }.runTask(player.getServer().getPluginManager().getPlugin("OpenAISpigot"));
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
