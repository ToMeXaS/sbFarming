package lt.tomexas.sbfarming.tasks;

import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import lt.tomexas.sbfarming.Main;
import lt.tomexas.sbfarming.info.PlanterInfo;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GrowthTask extends BukkitRunnable {

    private final Main main = Main.getInstance();

    @Override
    public void run() {
        if (main.getUtils().parseTime(Bukkit.getWorld("SuperiorWorld").getTime()).equals("07:00")) {
            for (Map.Entry<Island, List<PlanterInfo>> map : main.getPlanterInfoHashMap().entrySet()) {
                for (PlanterInfo info : map.getValue()) {
                    if (info.getPlantId().isEmpty()) continue;
                    CustomBlock planter = CustomBlock.byAlreadyPlaced(info.getLocation().getBlock());
                    CustomFurniture plant = CustomFurniture.byAlreadySpawned(info.getPlantEntity());

                    if (plant == null) continue;
                    if (!plant.getNamespace().contains("plants")) continue;
                    if (!planter.getId().equals("planter-moist-farmland")) continue;

                    String[] id = plant.getId().split("-");
                    int state = Integer.parseInt(id[1]);
                    state++;

                    if (getPlantList(id[0]).size() == state) {
                        Bukkit.broadcastMessage("FULLY GROWN");
                        info.setPlantFullyGrown(true);
                    } else if (getPlantList(id[0]).size() < state) continue;

                    plant.remove(false);
                    plant = CustomFurniture.spawn(id[0] + "-" + state, planter.getBlock().getRelative(BlockFace.UP));
                    CustomBlock.place("planter-farmland", planter.getBlock().getLocation());

                    info.setWatered(false);
                    info.setGrowthState(state);
                    info.setPlantEntity(plant.getArmorstand());
                    info.setPlantUUID(plant.getArmorstand().getUniqueId().toString());
                    info.setPlantId(plant.getId());
                }
            }
        }
    }

    private List<CustomStack> getPlantList(String name) {
        List<CustomStack> plants = ItemsAdder.getAllItems("plants");
        List<CustomStack> plantsList = new ArrayList<>();
        for (CustomStack stack : plants) {
            if (stack.getId().contains(name) && !stack.getId().contains("gold")) {
                plantsList.add(stack);
            }
        }
        return plantsList;
    }
}
