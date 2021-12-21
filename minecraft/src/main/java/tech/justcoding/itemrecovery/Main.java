package tech.justcoding.itemrecovery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tech.justcoding.itemrecovery.commands.RecoveryCommand;
import tech.justcoding.itemrecovery.listeners.DeathListener;
import tech.justcoding.itemrecovery.utils.Config;
import tech.justcoding.itemrecovery.utils.Supabase;

import java.sql.SQLException;

public final class Main extends JavaPlugin {

    public static Supabase supabase;

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerListeners();
        registerCommands();
        Config.registerConfiguration();
        try {
            supabase = new Supabase(Config.getString("supabasePostgresJDBC", "jdbc:postgresql://db.xxx.supabase.co:5432/postgres?user=postgres&password=xxx"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new DeathListener(), this);
    }

    private void registerCommands() {
        getCommand("recovery").setExecutor(new RecoveryCommand());
    }

    public static String getErrorPrefix() {
        return ChatColor.DARK_GRAY + "┃ " + ChatColor.DARK_RED + "ERROR" + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY;
    }

    public static String getServerPrefix() {
        return ChatColor.DARK_GRAY + "┃ " + ChatColor.BLUE + "Server" + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY;
    }

    public static String getPrefix() {
        return getServerPrefix();
    }

    public static String getPrefix(String name) {
        return ChatColor.DARK_GRAY + "┃ " + ChatColor.BLUE + name + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY;
    }

    public static String getNoPermission() {
        return getErrorPrefix() + "Du hast keine Rechte für diesen Befehl";
    }

    public static String getNoPlayer() {
        return getErrorPrefix() + "Du musst für diese Aktion ein Spieler sein";
    }

}
