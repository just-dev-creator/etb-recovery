package tech.justcoding.itemrecovery.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.justcoding.itemrecovery.Main;
import tech.justcoding.itemrecovery.RecoveryDeath;
import tech.justcoding.itemrecovery.listeners.DeathListener;

import java.sql.SQLException;
import java.util.UUID;

public class RecoveryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getNoPlayer());
            return false;
        }
        Player player = (Player) sender;
        if (!DeathListener.playerDeaths.containsKey(player)) {
            player.sendMessage(Main.getErrorPrefix() + "Du hattet in der letzten Zeit keinen Tod, für den du einen" +
                    " Erstattungsantrag erstellen kannst.");
            return false;
        }
        RecoveryDeath recoveryDeath = DeathListener.playerDeaths.get(player);
        player.sendMessage(Main.getPrefix() + "Wir laden deinen Tod nun in die Datenbank hoch.");
        try {
            UUID uuid = Main.supabase.createRequest(recoveryDeath);
            TextComponent part1 = new TextComponent(Main.getPrefix() + "Bitte mache weitere Angaben unter ");
            TextComponent part2 = new TextComponent(ChatColor.BLUE + "recovery.justcoding.tech/request/" +
                    uuid.toString());
            part2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                    "https://recovery.justcoding.tech/request/" + uuid.toString()));
            player.spigot().sendMessage(part1, part2);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage(Main.getErrorPrefix() + "Es ist ein SQL-Fehler aufgetreten. Bitte kontaktiere" +
                    " einen Admin, um die weitere Vorgehensweise zu besprechen.");
            return false;
        }
    }
}
