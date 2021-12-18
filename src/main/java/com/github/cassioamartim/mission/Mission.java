package com.github.cassioamartim.mission;

import com.github.cassioamartim.Spoly;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Mission {

    BOSS_TROPHY("Troféu do Boss", Arrays.asList("",
            "§fConsiga o troféu matando um boss, e traga-o",
            "§fPara mim, e receberá uma recompensa!",
            "")),
    EXPERIENCE("Experiência", Arrays.asList("",
            "§fConsiga a meta de §b500 xp's§f e venha até mim",
            "§fpara receber uma recompensa!",
            ""));

    private static final Map<String, Mission> MISSION_MAP = new HashMap<>();

    static {
        for (Mission mission : values())
            MISSION_MAP.put(mission.getName().toLowerCase(), mission);
    }

    String name;
    List<String> lore;

    public static Mission getByName(@NonNull String name) {
        return MISSION_MAP.get(name.toLowerCase());
    }

    public void reward(Player player) {
        boolean success = false;
        player.closeInventory();

        switch (this) {
            case BOSS_TROPHY:
                if (!player.getInventory().contains(Material.DIAMOND)) {
                    player.sendMessage("§cVocê precisa ter o §eTroféu do Boss§c para concluir está missão!");
                    return;
                }
                success = true;

                player.getInventory().remove(Material.DIAMOND);
                break;
            case EXPERIENCE:
                if (player.getLevel() < 500) {
                    player.sendMessage("§cVocê precisa ter §e500 EXP's§c para concluir está missão!");
                    return;
                }
                success = true;

                player.setLevel(0);
                break;
        }

        if (success) {
            player.sendMessage("§aA missão §e" + name + "§a foi completada!");
            player.addAttachment(Spoly.getPlugin(Spoly.class), "mission." + name.toLowerCase(), true);
        }
    }

    public boolean isAlreadyCompleted(Player player) {
        return player.hasPermission("mission." + name.toLowerCase());
    }
}
