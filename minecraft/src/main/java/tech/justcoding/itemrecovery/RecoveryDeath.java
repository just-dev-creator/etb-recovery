package tech.justcoding.itemrecovery;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RecoveryDeath {
    private final Date deathTime;
    private final ArrayList<ItemStack> deathInventory;
    private final int playerPing;
    private final String playerIP;
    private final int locationX;
    private final int locationY;
    private final int locationZ;
    private final UUID playerUUID;
    private final float xpPoints;
    private final int xpLevel;

    public RecoveryDeath(Player player) {
        this.deathTime = new Date();
        this.playerUUID = player.getUniqueId();
        this.xpPoints = player.getExp();
        this.xpLevel = player.getLevel();

        this.deathInventory = new ArrayList<>();
        this.deathInventory.addAll(List.of(player.getInventory().getArmorContents()));
        this.deathInventory.addAll(List.of(player.getInventory().getExtraContents()));
        this.deathInventory.addAll(List.of(player.getInventory().getContents()));

        this.playerPing = player.getPing();
        this.playerIP = player.getAddress().getHostName();
        Location location = player.getLocation();
        this.locationX = location.getBlockX();
        this.locationY = location.getBlockY();
        this.locationZ = location.getBlockZ();
    }

    public RecoveryDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        this.deathTime = new Date();
        this.playerUUID = player.getUniqueId();
        this.playerPing = player.getPing();
        this.playerIP = player.getAddress().getHostName();
        this.deathInventory = (ArrayList<ItemStack>) event.getDrops();
        this.locationX = player.getLocation().getBlockX();
        this.locationY = player.getLocation().getBlockY();
        this.locationZ = player.getLocation().getBlockZ();
        this.xpPoints = player.getExp();
        this.xpLevel = player.getLevel();
    }

    public Date getDeathTime() {
        return deathTime;
    }

    public ArrayList<ItemStack> getDeathInventory() {
        return deathInventory;
    }

    public int getPlayerPing() {
        return playerPing;
    }

    public String getPlayerIP() {
        return playerIP;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public int getLocationZ() {
        return locationZ;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public float getXpPoints() {
        return xpPoints;
    }

    public int getXpLevel() {
        return xpLevel;
    }
}
