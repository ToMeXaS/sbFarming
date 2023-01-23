package lt.tomexas.sbfarming.listeners.itemsadder.planter;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.CustomBlockBreakEvent;
import dev.lone.itemsadder.api.ItemsAdder;
import lt.tomexas.sbfarming.Main;
import lt.tomexas.sbfarming.info.PlanterInfo;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PlanterBreakListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onDirtPlanterBreak(CustomBlockBreakEvent event) {
        if (!event.getNamespacedID().equalsIgnoreCase("blocks:planter-dirt")) return;
        Island island = SuperiorSkyblockAPI.getIslandAt(event.getBlock().getLocation());
        if (island == null) return;

        Location location = event.getBlock().getLocation();
        main.getUtils().removePlanterFromHashMap(island, location);
    }

    @EventHandler
    public void onFarmlandPlanterBreak(CustomBlockBreakEvent event) {
        if (!event.getNamespacedID().equalsIgnoreCase("blocks:planter-moist-farmland") && !event.getNamespacedID().equalsIgnoreCase("blocks:planter-farmland")) return;
        Island island = SuperiorSkyblockAPI.getIslandAt(event.getBlock().getLocation());
        if (island == null) return;

        World world = event.getBlock().getWorld();
        Location location = event.getBlock().getLocation();
        clearPlant(island, location);

        main.getUtils().removePlanterFromHashMap(island, location);
        world.dropItemNaturally(location, CustomStack.getInstance("blocks:planter-dirt").getItemStack());
    }

    private void clearPlant(Island island, Location location) {
        for (PlanterInfo info : main.getPlanterInfoHashMap().get(island)) {
            if (info.getLocation().equals(location)) {
                CustomFurniture plant = CustomFurniture.byAlreadySpawned(info.getPlantEntity());
                World world = location.getWorld();
                if (plant != null) {
                    if (isPlantInList(plant.getId())) {
                        plant.remove(false);
                        if (info.isPlantFullyGrown()) {
                            world.dropItemNaturally(location.add(0, 1, 0), main.getUtils().getSeedProduce(plant).getItemStack());
                            if (main.getUtils().percentChance(0.10)) {
                                world.dropItemNaturally(location.add(0, 1, 0), main.getUtils().getSeedFurniture(plant).getItemStack());
                            }
                        } else {
                            world.dropItemNaturally(location.add(0, 1, 0), main.getUtils().getSeedFurniture(plant).getItemStack());
                        }

                        info.setPlantEntity(null);
                        info.setPlantId("");
                        info.setPlantUUID("");
                        info.setPlantFullyGrown(false);
                        info.setGrowthState(0);
                    }
                }
            }
        }
    }

    private boolean isPlantInList(String plantId) {
        List<CustomStack> plants = ItemsAdder.getAllItems("plants");
        for (CustomStack plant : plants) {
            if (plant.getId().equalsIgnoreCase(plantId)) {
                return true;
            }
        }
        return false;
    }
}
