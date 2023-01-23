package lt.tomexas.sbfarming.listeners.itemsadder.player;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJumpListener implements Listener {

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        Block block = event.getTo().getBlock();
        CustomBlock to = CustomBlock.byAlreadyPlaced(block);
        if (to == null) return;

        if (to.getId().equalsIgnoreCase("planter-moist-farmland"))
            CustomBlock.place("blocks:planter-dirt", block.getLocation());
        else if (to.getId().equalsIgnoreCase("planter-farmland"))
            CustomBlock.place("blocks:planter-dirt", block.getLocation());
    }
}
