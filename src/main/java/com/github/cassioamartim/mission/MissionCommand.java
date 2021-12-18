package com.github.cassioamartim.mission;

import lombok.val;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

public class MissionCommand extends Command {

    public MissionCommand() {
        super("missoes", "", "", Collections.singletonList("setmissoes"));
    }

    @Override
    public boolean execute(CommandSender sender, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVocê precisa estar dentro do jogo para isto.");
            return true;
        }
        val player = (Player) sender;

        if (!player.hasPermission("mission.set")) {
            player.sendMessage("§cVocê não tem permissão para executar este comando!");
            return true;
        }

        spawnEntity(player.getLocation());
        player.sendMessage("§aEntidade de Missões spawnada!");
        return false;
    }

    public void spawnEntity(Location location) {
        val villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);

        villager.setCanPickupItems(false);
        villager.setAdult();
        villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9999999, 255));
        villager.setHealth(20);
        villager.setCustomName("§eMissões");
        villager.setProfession(Villager.Profession.PRIEST);
    }
}
