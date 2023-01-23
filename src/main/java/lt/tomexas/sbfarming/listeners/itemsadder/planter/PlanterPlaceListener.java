package lt.tomexas.sbfarming.listeners.itemsadder.planter;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import lt.tomexas.sbfarming.Main;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlanterPlaceListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onPlanterPlace(CustomBlockPlaceEvent event) {
        if (!event.getNamespacedID().equalsIgnoreCase("blocks:planter-dirt")) return;
        Island island = SuperiorSkyblockAPI.getIslandAt(event.getBlock().getLocation());
        if (island == null) return;

        Location location = event.getBlock().getLocation();
        main.getUtils().addPlanterToHashMap(island, location);
    }
}
