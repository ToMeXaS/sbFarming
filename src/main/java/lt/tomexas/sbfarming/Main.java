package lt.tomexas.sbfarming;

import com.bgsoftware.superiorskyblock.api.island.Island;
import lt.tomexas.sbfarming.listeners.bukkit.block.BlockBreakListener;
import lt.tomexas.sbfarming.listeners.bukkit.entity.EntitySpawnListener;
import lt.tomexas.sbfarming.listeners.bukkit.item.ItemSpawnListener;
import lt.tomexas.sbfarming.listeners.bukkit.player.PlayerInteractListener;
import lt.tomexas.sbfarming.listeners.itemsadder.planter.PlanterBreakListener;
import lt.tomexas.sbfarming.listeners.itemsadder.planter.PlanterInteractListener;
import lt.tomexas.sbfarming.listeners.itemsadder.planter.PlanterPlaceListener;
import lt.tomexas.sbfarming.listeners.itemsadder.seedling.SeedlingBreakListener;
import lt.tomexas.sbfarming.listeners.itemsadder.sprinkler.SprinklerBreakListener;
import lt.tomexas.sbfarming.listeners.itemsadder.sprinkler.SprinklerPlaceListener;
import lt.tomexas.sbfarming.info.PlanterInfo;
import lt.tomexas.sbfarming.info.SprinklerInfo;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;
    private final PluginManager pluginManager = getServer().getPluginManager();

    private Dictionary dictionary;
    private Utils utils;

    private final HashMap<Island, List<PlanterInfo>> planterInfoHashMap = new HashMap<>();
    private final HashMap<Island, List<SprinklerInfo>> sprinklerInfoHashMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        dictionary = new Dictionary();
        utils = new Utils();

        utils.retrievePlanterInfo();
        utils.retrieveSprinklerInfo();

        //Bukkit-------------------------------------------------------------------//
        pluginManager.registerEvents(new BlockBreakListener(), this);

        pluginManager.registerEvents(new EntitySpawnListener(), this);

        pluginManager.registerEvents(new ItemSpawnListener(), this);

        pluginManager.registerEvents(new PlayerInteractListener(), this);
        //-------------------------------------------------------------------------//

        //ItemsAdder--------------------------------------------------------------//
        pluginManager.registerEvents(new PlanterBreakListener(), this);
        pluginManager.registerEvents(new PlanterPlaceListener(), this);
        pluginManager.registerEvents(new PlanterInteractListener(), this);

        pluginManager.registerEvents(new SprinklerPlaceListener(), this);
        pluginManager.registerEvents(new SprinklerBreakListener(), this);

        //pluginManager.registerEvents(new PlayerJumpListener(), this);

        pluginManager.registerEvents(new SeedlingBreakListener(), this);
        //-------------------------------------------------------------------------//
    }

    @Override
    public void onDisable() {
        utils.savePlanterInfo();
        utils.saveSprinklerInfo();
    }

    public static Main getInstance() {
        return instance;
    }

    public Utils getUtils() {
        return utils;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public HashMap<Island, List<PlanterInfo>> getPlanterInfoHashMap() {
        return planterInfoHashMap;
    }

    public HashMap<Island, List<SprinklerInfo>> getSprinklerInfoHashMap() {
        return sprinklerInfoHashMap;
    }
}
