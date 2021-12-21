package tech.justcoding.itemrecovery.utils;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import tech.justcoding.itemrecovery.RecoveryDeath;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class Supabase {
    private final Connection connection;

    public Supabase(String jdbcURL) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(jdbcURL);
    }

    public void putUser(RecoveryDeath death) throws SQLException {
        Bukkit.getLogger().info("INSERT INTO reclaims (uuid, ping, posx, posy, posz, items)\n" +
                "VALUES ('" + death.getPlayerUUID().toString() + "', " + death.getPlayerPing() + ", " +
                death.getLocationX() + ", " + death.getLocationY() + ", " + death.getLocationZ() + ", '" +
                "{\"hello\":\"abc\"}" + "');");
        Statement st = connection.createStatement();
        st.executeUpdate("INSERT INTO reclaims (uuid, ping, posx, posy, posz, items, requestid)\n" +
                "VALUES ('" + death.getPlayerUUID().toString() + "', " + death.getPlayerPing() + ", " +
                death.getLocationX() + ", " + death.getLocationY() + ", " + death.getLocationZ() + ", '" +
                "{\"hello\":\"abc\"}" + "', '" + UUID.randomUUID() + "');");
        st.close();
    }

    public UUID createRequest(RecoveryDeath death) throws SQLException {
        UUID uuid = UUID.randomUUID();
        Statement st = connection.createStatement();
        st.executeUpdate("INSERT INTO reclaims (uuid, ping, posx, posy, posz, items, requestid)\n" +
                "VALUES ('" + death.getPlayerUUID().toString() + "', " + death.getPlayerPing() + ", " +
                death.getLocationX() + ", " + death.getLocationY() + ", " + death.getLocationZ() + ", '" +
                itemListToJSON(death.getDeathInventory()) + "', '" + uuid.toString() + "');");
        st.close();
        return uuid;
    }

    public static String itemListToJSON(List<ItemStack> itemList) {
        JsonObject jsonObject = new JsonObject();
        Bukkit.getLogger().info(String.valueOf(itemList.size()));
        for (ItemStack itemStack : itemList) {
            JsonObject innerObject = new JsonObject();
            innerObject.addProperty("amount", itemStack.getAmount());
            Bukkit.getLogger().info(String.valueOf(itemStack.getAmount()));
            if (!itemStack.getEnchantments().isEmpty()) {
                JsonObject enchantments = new JsonObject();
                for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
                    enchantments.addProperty(enchantment.getKey().getKey(), itemStack.getEnchantmentLevel(enchantment));
                }
                innerObject.add("enchantments", enchantments);
            }
            if (((itemStack.getItemMeta() == null) ? 0 : (short) ((Damageable) itemStack.getItemMeta()).getDamage()) != 0) {
                innerObject.addProperty("damage", (short) ((Damageable) itemStack.getItemMeta()).getDamage());
            }
            Bukkit.getLogger().info(String.valueOf(innerObject.toString()));
            jsonObject.add(itemStack.getType().name(), innerObject);
        }
        return jsonObject.toString();
    }
}
