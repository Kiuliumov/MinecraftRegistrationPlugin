package cantina.plugin.registrationPlugin.src.Commands;

import cantina.plugin.registrationPlugin.RegistrationPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;

public class InfoCommand implements CommandExecutor {

    private RegistrationPlugin plugin;
    public InfoCommand(RegistrationPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            String pluginName = plugin.getDescription().getName();
            String pluginVersion = plugin.getDescription().getVersion();

            player.sendMessage("§6==== Plugin Info ====");
            player.sendMessage("§7Name: " + pluginName);
            player.sendMessage("§7Version: " + pluginVersion);
            player.sendMessage("§6====================");
        } else {
            sender.sendMessage("This command can only be used by a player.");
        }

        return true;

    }
}