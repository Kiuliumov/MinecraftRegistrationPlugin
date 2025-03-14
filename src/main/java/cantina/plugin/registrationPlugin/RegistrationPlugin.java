package cantina.plugin.registrationPlugin;

import cantina.plugin.registrationPlugin.src.Commands.InfoCommand;
import cantina.plugin.registrationPlugin.src.Commands.LoginCommand;
import cantina.plugin.registrationPlugin.src.Commands.RegisterCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import cantina.plugin.registrationPlugin.src.Listeners.PlayerJoinListener;
public final class RegistrationPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        String pluginVersion = getPluginMeta().getVersion();
        String message = String.format("RegistrationPlugin by Kiuliumov\nVersion: %s\nEnabled!", pluginVersion);
        Bukkit.broadcastMessage(message);

        this.getCommand("info").setExecutor((CommandExecutor) new InfoCommand(this));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        this.getCommand("register").setExecutor((CommandExecutor) new RegisterCommand());
        this.getCommand("login").setExecutor((CommandExecutor) new LoginCommand());

    }

    @Override
    public void onDisable() {
        String pluginVersion = getPluginMeta().getVersion();
        String message = String.format("Thank you for using RegistrationPlugin!");
        Bukkit.broadcastMessage(message);
    }
}
