package lt.tomexas.sbfarming.tasks;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import lt.tomexas.sbfarming.Main;
import lt.tomexas.sbfarming.info.SprinklerInfo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SprinklerTask extends BukkitRunnable {

    private final Main main = Main.getInstance();

    @Override
    public void run() {
        for (List<SprinklerInfo> infoList : main.getSprinklerInfoHashMap().values()) {
            for (SprinklerInfo info : infoList) {
                Location location = info.getLocation();
                Block block = location.getBlock();
                CustomFurniture.remove(info.getEntity(), false);
                CustomFurniture sprinkler;
                if (!block.getRelative(BlockFace.DOWN).getType().equals(Material.WATER)) {
                    sprinkler = CustomFurniture.spawn("blocks:wooden-sprinkler-0", block);
                    info.setWater(false);
                } else {
                    sprinkler = CustomFurniture.spawn("blocks:wooden-sprinkler-5", block);
                    info.setWater(true);
                    List<CustomBlock> planterList = getPlantersInRadiusOfLocation(location, 3);
                    if (!planterList.isEmpty()) {
                        Random rand = new Random();
                        CustomBlock randomPlanter = planterList.get(rand.nextInt(planterList.size()));
                        CustomBlock.place("blocks:planter-moist-farmland", randomPlanter.getBlock().getLocation());
                    }
                }
                info.setEntity(sprinkler.getArmorstand());
                info.setUuid(sprinkler.getArmorstand().getUniqueId().toString());
            }
        }
    }

    private List<CustomBlock> getPlantersInRadiusOfLocation(Location loc, int radius) {
        int
                minX = loc.getBlockX() - radius,
                minY = loc.getBlockY() - radius,
                minZ = loc.getBlockZ() - radius,
                maxX = loc.getBlockX() + radius,
                maxY = loc.getBlockY() + radius,
                maxZ = loc.getBlockZ() + radius;

        List<CustomBlock> blockList = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    CustomBlock planter = CustomBlock.byAlreadyPlaced(loc.getWorld().getBlockAt(x, y, z));
                    if (planter == null) continue;
                    if (planter.getId().equals("planter-farmland")) {
                        blockList.add(planter);
                    }
                }
            }
        }

        return blockList;
    }
}
