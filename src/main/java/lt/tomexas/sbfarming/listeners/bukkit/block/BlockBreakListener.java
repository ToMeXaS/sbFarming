package lt.tomexas.sbfarming.listeners.bukkit.block;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import lt.tomexas.sbfarming.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class BlockBreakListener implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.GRASS) && !event.getBlock().getType().equals(Material.TALL_GRASS)) return;

        event.setDropItems(false);
        if (main.getUtils().percentChance(0.15)) {
            Location location = event.getBlock().getLocation();
            World world = location.getWorld();
            if (world == null) return;
            world.dropItemNaturally(location, getRandomSeed());
        }
    }

    private ItemStack getRandomSeed() {
        List<CustomStack> seedList = ItemsAdder.getAllItems("seeds");
        Random rand = new Random();
        int amount = rand.nextInt((3) + 1);
        ItemStack item = seedList.get(rand.nextInt(seedList.size())).getItemStack();
        item.setAmount(amount);
        return item;
    }
}
