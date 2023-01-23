package lt.tomexas.sbfarming.listeners.bukkit.player;

import dev.lone.itemsadder.api.Events.FurniturePlaceSuccessEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (!event.getClickedBlock().getType().equals(Material.GRASS_BLOCK)
                && !event.getClickedBlock().getType().equals(Material.DIRT)
                && !event.getClickedBlock().getType().equals(Material.DIRT_PATH)) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_HOE)) return;

        Player player = event.getPlayer();
        event.setCancelled(true);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&4&lᴇʀʀᴏʀ &8&l┃ &cThis functionality is disabled on this server, try out the brand new farming revamp system we've implemented."
        ));
    }
}
