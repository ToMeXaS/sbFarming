package lt.tomexas.sbfarming.listeners.itemsadder.sprinkler;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import lt.tomexas.sbfarming.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SprinklerBreakListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onSprinklerBreak(FurnitureBreakEvent event) {
        if (!event.getNamespacedID().equalsIgnoreCase("blocks:wooden-sprinkler-0") && !event.getNamespacedID().equalsIgnoreCase("blocks:wooden-sprinkler-5")) return;
        Island island = SuperiorSkyblockAPI.getIslandAt(event.getBukkitEntity().getLocation());
        if (island == null) return;

        //CustomStack item = CustomStack.getInstance("wooden-sprinkler");
        //event.getBukkitEntity().getWorld().dropItemNaturally(event.getBukkitEntity().getLocation(), item.getItemStack());
        main.getUtils().removeSprinklerFromHashMap(island, event.getBukkitEntity().getLocation());
    }
}
