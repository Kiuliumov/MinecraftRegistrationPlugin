package cantina.plugin.registrationPlugin.src.Commands;


import cantina.plugin.registrationPlugin.src.Database.Database;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return false;
        }

        Player player = (Player) sender;
        Database.connect();

        if (Database.isUserLoggedIn(player.getName())) {
            player.sendMessage(ChatColor.YELLOW + "You are already logged in.");
            Database.close();
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Usage: /login <password>");
            Database.close();
            return false;
        }

        String password = args[0];
        String playerIp = player.getAddress().getAddress().getHostAddress();

        if (!Database.isUserRegistered(player.getName())) {
            player.sendMessage(ChatColor.RED + "You are not registered! Use /register <password> <repeat password> first.");
            Database.close();
            return false;
        }

        boolean isPasswordCorrect = Database.verifyUser(player.getName(), password);
        if (!isPasswordCorrect) {
            player.sendMessage(ChatColor.RED + "Incorrect password. Try again.");
            Database.close();
            return false;
        }

        if (Database.isIpExpired(player.getName(), playerIp)) {
            Database.updateUserIp(player.getName(), playerIp);
        }

        Database.setUserLoggedIn(player.getName(), true);
        player.sendMessage(ChatColor.GREEN + "You have successfully logged in!");
        player.setGameMode(GameMode.SURVIVAL);
        player.setWalkSpeed(0.2f);
        Database.close();
        return true;
    }
}