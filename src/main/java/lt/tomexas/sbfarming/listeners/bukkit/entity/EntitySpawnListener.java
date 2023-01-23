package lt.tomexas.sbfarming.listeners.bukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!event.getEntityType().equals(EntityType.PHANTOM)) return;
        event.setCancelled(true);
    }
}
