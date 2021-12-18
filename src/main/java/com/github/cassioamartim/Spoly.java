package com.github.cassioamartim;

import com.github.cassioamartim.mission.MissionCommand;
import com.github.cassioamartim.mission.MissionInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Spoly extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MissionInventory(), this);
        getServer().getCommandMap().register("missoes", new MissionCommand());

        System.out.println("[Mission-Plugin] Started!");
    }

    @Override
    public void onDisable() {
        System.out.println("[Mission-Plugin] Finished!");
    }
}
