package lt.tomexas.sbfarming.listeners.itemsadder.planter;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.CustomBlockInteractEvent;
import lt.tomexas.sbfarming.Main;
import lt.tomexas.sbfarming.info.PlanterInfo;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlanterInteractListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onPlanterRightClickWithHoe(CustomBlockInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getBlockFace().equals(BlockFace.UP)) return;
        if (!event.getNamespacedID().equalsIgnoreCase("blocks:planter-dirt")) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STONE_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.IRON_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_HOE)
                && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_HOE)) return;



        CustomBlock planterFarmland = CustomBlock.getInstance("planter-farmland");
        planterFarmland.place(event.getBlockClicked().getLocation());
        setPlanterToTilled(event.getBlockClicked().getLocation());

        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_HOE_TILL, 0.3F, 1F);

        /*ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        Damageable damageable = (Damageable) meta;
        damageable.setDamage(damageable.getDamage() + 1);
        item.setItemMeta(damageable);*/
    }

    @EventHandler
    public void onPlanterRightClickWithSeed(CustomBlockInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getBlockFace().equals(BlockFace.UP)) return;
        if (!event.getNamespacedID().equalsIgnoreCase("blocks:planter-moist-farmland")) return;

        CustomStack heldItem = CustomStack.byItemStack(event.getPlayer().getInventory().getItemInMainHand());
        Island island = SuperiorSkyblockAPI.getIslandAt(event.getBlockClicked().getLocation());
        if (heldItem == null) return;
        CustomStack seed = main.getUtils().getSeedStack(heldItem);
        if (seed == null) return;
        if (island == null) return;

        Player player = event.getPlayer();

        String[] id = seed.getId().split("-");
        CustomFurniture plant = CustomFurniture.spawn(id[0] + "-1", event.getBlockClicked().getRelative(BlockFace.UP));
        setPlanterPlantId(event.getBlockClicked().getLocation(), id[0] + "-1");
        setPlanterEntity(event.getBlockClicked().getLocation(), plant.getArmorstand());
        player.playSound(player.getLocation(), Sound.ITEM_CROP_PLANT, 0.3F, 1F);
        Bukkit.broadcastMessage("SPAWNED");

    }

    @EventHandler
    public void onPlanterRightClickWithWater(CustomBlockInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getBlockFace().equals(BlockFace.UP)) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.WATER_BUCKET)) return;
        if (!event.getNamespacedID().equalsIgnoreCase("blocks:planter-farmland")) return;

        CustomBlock planterFarmland = CustomBlock.getInstance("planter-moist-farmland");
        planterFarmland.place(event.getBlockClicked().getLocation());
        setPlanterToWatered(event.getBlockClicked().getLocation());
        event.setCancelled(true);

        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_BUCKET_EMPTY, 0.3F, 1F);

        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.getPlayer().getInventory().getItemInMainHand().setType(Material.BUCKET);
    }

    private void setPlanterToTilled(Location location) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        for (PlanterInfo info : main.getPlanterInfoHashMap().get(island)) {
            if (info.getLocation().equals(location)) {
                info.setTilled(true);
            }
        }
    }

    private void setPlanterToWatered(Location location) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        for (PlanterInfo info : main.getPlanterInfoHashMap().get(island)) {
            if (info.getLocation().equals(location)) {
                info.setWatered(true);
            }
        }
    }

    private void setPlanterEntity(Location location, Entity entity) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        for (PlanterInfo info : main.getPlanterInfoHashMap().get(island)) {
            if (info.getLocation().equals(location)) {
                info.setPlantEntity(entity);
                info.setPlantUUID(entity.getUniqueId().toString());
            }
        }
    }

    private void setPlanterPlantId(Location location, String plantId) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        for (PlanterInfo info : main.getPlanterInfoHashMap().get(island)) {
            if (info.getLocation().equals(location)) {
                info.setPlantId(plantId);
            }
        }
    }
}
