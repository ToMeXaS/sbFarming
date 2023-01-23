package lt.tomexas.sbfarming.listeners.bukkit.item;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnListener implements Listener {

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Material material = event.getEntity().getItemStack().getType();
        if (!material.equals(Material.CARROT)
                && !material.equals(Material.BEETROOT)
                && !material.equals(Material.POTATO)
                && !material.equals(Material.WHEAT)
                && !material.equals(Material.POISONOUS_POTATO)
                && !material.equals(Material.WHEAT_SEEDS)
                && !material.equals(Material.BEETROOT_SEEDS)) return;

        event.setCancelled(true);
    }
}
