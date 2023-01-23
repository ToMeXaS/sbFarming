package lt.tomexas.sbfarming.listeners.itemsadder.seedling;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import lt.tomexas.sbfarming.Main;
import lt.tomexas.sbfarming.info.PlanterInfo;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class SeedlingBreakListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onSeedlingBreak(FurnitureBreakEvent event) {
        if (!event.getNamespacedID().contains("plants:")) return;

        Island island = SuperiorSkyblockAPI.getIslandAt(event.getBukkitEntity().getLocation());
        if (island == null) return;
        Location location = event.getBukkitEntity().getLocation().subtract(0.5,1.03125,0.5);
        location.setPitch(0.0F);
        location.setYaw(0.0F);
        World world = event.getBukkitEntity().getWorld();
        CustomFurniture plant = CustomFurniture.byAlreadySpawned(event.getBukkitEntity());
        List<PlanterInfo> infoList = main.getPlanterInfoHashMap().get(island);
        Player player = event.getPlayer();
        for (PlanterInfo info : infoList) {
            if (info.getLocation().equals(location)) {
                if (info.isPlantFullyGrown()) {
                    world.dropItemNaturally(location.add(0, 1, 0), main.getUtils().getSeedProduce(plant).getItemStack());
                    if (main.getUtils().percentChance(0.10)) {
                        world.dropItemNaturally(location.add(0, 1, 0), main.getUtils().getSeedFurniture(plant).getItemStack());
                    }
                } else {
                    world.dropItemNaturally(location.add(0, 1, 0), main.getUtils().getSeedFurniture(plant).getItemStack());
                }
                info.setPlantId("");
                info.setPlantUUID("");
                info.setPlantEntity(null);
                info.setPlantFullyGrown(false);
                info.setGrowthState(0);
            }
        }
        player.playSound(player.getLocation(), Sound.BLOCK_CROP_BREAK, 0.3F, 1F);
    }
}
