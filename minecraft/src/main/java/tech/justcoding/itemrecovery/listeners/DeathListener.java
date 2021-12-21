package tech.justcoding.itemrecovery.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import tech.justcoding.itemrecovery.Main;
import tech.justcoding.itemrecovery.RecoveryDeath;

import java.util.HashMap;

public class DeathListener implements Listener {
    public static HashMap<Player, RecoveryDeath> playerDeaths = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        RecoveryDeath rdeath = new RecoveryDeath(event);
        playerDeaths.put(event.getEntity(), rdeath);
        event.getEntity().sendMessage(Main.getPrefix() + "Bist du durch einen Bug gestorben?");
        event.getEntity().sendMessage(Main.getPrefix() + "Benutze den Command " + ChatColor.BLUE + "/recovery" +
                ChatColor.GRAY + ", um deine Items wiederherstellen zu lassen.");
    }
}
