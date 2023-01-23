package lt.tomexas.sbfarming.info;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class SprinklerInfo {

    private Location location;
    private Entity entity;
    private String uuid;
    private boolean water;
    private int taskId;

    public SprinklerInfo(Location location, Entity entity, String uuid, boolean water, int taskId) {
        this.location = location;
        this.entity = entity;
        this.uuid = uuid;
        this.water = water;
        this.taskId = taskId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean hasWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
