package cantina.plugin.registrationPlugin.src.Commands;

import cantina.plugin.registrationPlugin.src.Database.Database;
import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Database.connect();

        if (sender instanceof Player) {

            if (args.length != 2) {
                player.sendMessage("Usage: /register <password> <repeat password>");
                return false;
            }
            if (Database.isUserRegistered(player.getName())) {
                player.sendMessage(ChatColor.RED + "You are already registered!");
                return false;
            }

            String password = args[0];
            String repeatPassword = args[1];

            if (!password.equals(repeatPassword)) {
                player.sendMessage(ChatColor.RED + "Passwords do not match!");
                return false;
            }

            String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

            Pattern pattern = Pattern.compile(passwordRegex);

            Matcher matcher = pattern.matcher(password);

            if (!matcher.matches()) {
                player.sendMessage(ChatColor.RED + "Your password must be at least 8 characters long, include at least one uppercase letter, and at least one number.");
                return false;
            }


            String ip = Objects.requireNonNull(player.getAddress()).getAddress().getHostAddress();
            boolean isRegistered = Database.registerUser(player.getName(), password, ip);

            if (isRegistered) {
                player.sendMessage(ChatColor.GREEN + "You have successfully registered!");
                player.setGameMode(GameMode.SURVIVAL);
                player.setWalkSpeed(0.2f);
            } else {
                player.sendMessage(ChatColor.RED + "Registration failed. Please try again.");
            }
            Database.close();
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            Database.close();
            return false;
        }
        return true;
    }
}

