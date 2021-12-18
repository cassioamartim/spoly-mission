package com.github.cassioamartim.mission;

import lombok.val;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class MissionInventory implements Listener {

    @EventHandler
    public void click(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase("Missões")) {
            val player = (Player) event.getWhoClicked();

            val item = event.getCurrentItem();
            if (item == null || item.getType().equals(Material.AIR) || item.getType().equals(Material.STAINED_GLASS_PANE)) {
                event.setCancelled(true);
                return;
            }

            val mission = Mission.getByName(item.getItemMeta().getDisplayName().substring(2));
            if (mission == null) return;

            mission.reward(player);
        }
    }

    @EventHandler
    public void interact(PlayerInteractEntityEvent event) {
        event.setCancelled(true);

        if (event.getRightClicked() instanceof Villager) {
            val villager = (Villager) event.getRightClicked();

            if (villager.getCustomName().equalsIgnoreCase("§eMissões"))
                make(event.getPlayer());
        }
    }

    public void make(Player player) {
        val inv = Bukkit.createInventory(null, 36, "Missões");
        var index = 0;

        for (Mission mission : Mission.values()) {
            inv.setItem(index, missionItem(player, mission));
            index++;
        }

        player.openInventory(inv);
    }

    public ItemStack missionItem(Player player, Mission mission) {
        return mission.isAlreadyCompleted(player) ?
                makeItem(Material.STAINED_GLASS_PANE, "§c" + mission.getName(), 1, 14, Collections.singletonList("§7Missão concluida.")) :
                makeItem(Material.STORAGE_MINECART, "§e" + mission.getName(), 1, 0, mission.getLore());
    }

    public ItemStack makeItem(Material material, String displayName, int amount, int id, List<String> lore) {
        val stack = new ItemStack(material, amount, (short) id);
        val meta = stack.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }
}
