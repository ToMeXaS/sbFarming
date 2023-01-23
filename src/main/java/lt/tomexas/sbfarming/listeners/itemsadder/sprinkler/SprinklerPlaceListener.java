package lt.tomexas.sbfarming.listeners.itemsadder.sprinkler;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.FurniturePlaceSuccessEvent;
import lt.tomexas.sbfarming.Main;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class SprinklerPlaceListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onSprinklerPlace(PlayerInteractEvent event) {
        if (event.getHand() == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getClickedBlock() == null) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        CustomStack stack = CustomStack.byItemStack(event.getItem());
        if (stack == null) return;
        if (!stack.getNamespacedID().equals("farming-blocks:wooden-sprinkler")) return;
        CustomFurniture entity = CustomFurniture.spawn("blocks:wooden-sprinkler-0", event.getClickedBlock().getRelative(BlockFace.UP));
        Island island = SuperiorSkyblockAPI.getIslandAt(entity.getArmorstand().getLocation());
        if (island == null) return;

        Location location = entity.getArmorstand().getLocation();
        main.getUtils().addSprinklerToHashMap(island, location, entity.getArmorstand());
        event.getItem().setAmount(event.getItem().getAmount()-1);
    }
}
