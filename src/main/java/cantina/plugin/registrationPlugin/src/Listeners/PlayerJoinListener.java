package cantina.plugin.registrationPlugin.src.Listeners;

import cantina.plugin.registrationPlugin.src.Database.Database;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.xml.crypto.Data;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Database.connect();

        Player player = event.getPlayer();

        if (Database.isUserRegistered(player.getName()) && Database.isUserLoggedIn(player.getName())) {
            player.sendMessage(ChatColor.GREEN + "You are already logged in!");
            Database.close();
        } else {
            player.setGameMode(GameMode.SPECTATOR);
            player.setWalkSpeed(0);

            if(Database.isUserRegistered(player.getName())) {
                player.sendMessage(ChatColor.GREEN + "Login using /login password");
            } else{
                player.sendMessage(ChatColor.AQUA + "Register using /register <password> <repeat password>");
                player.setGameMode(GameMode.SURVIVAL);
                player.setWalkSpeed(0);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.SPECTATOR && !Database.isUserLoggedIn(player.getName())) {
            event.setCancelled(true);
        }
    }
}
