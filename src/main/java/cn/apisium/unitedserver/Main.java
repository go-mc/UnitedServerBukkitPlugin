package cn.apisium.unitedserver;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements PluginMessageListener, Listener {
    private final Charset CHARSET = Charset.forName("UTF-8");
    private final HashSet<Player> playerList = new HashSet<>();

    @Override
    public void onEnable() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "UnitedServer:switch");
        getServer().getMessenger().registerIncomingPluginChannel(this, "UnitedServer:brand", this);

        getServer().getPluginManager().registerEvents(this, this);
        final PluginCommand p = getServer().getPluginCommand("unitedserver");
        // noinspection ConstantConditions
        p.setExecutor(this);
        p.setTabCompleter(this);
    }

    public HashSet<Player> getInstalledPlayers() { return playerList; }
    public boolean isPlayerInstalled(Player player) { return playerList.contains(player); }

    @SuppressWarnings("WeakerAccess")
    public void switchServer(@Nonnull Player player, @Nonnull String url) {
        switchServer(player, url.getBytes(CHARSET));
    }

    @SuppressWarnings("WeakerAccess")
    public void switchServer(@Nonnull Player player, @Nonnull byte[] bytes) {
        player.sendPluginMessage(this, "UnitedServer:switch", bytes);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("unitedserver.use")) {
            sender.sendMessage("¡ìcYou don't have permission to do this!");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("¡ìcPlease use: ¡ìf/unitedserver ¡ìa<server> <player>");
            return true;
        }
        final Player p = getServer().getPlayer(args[1]);
        if (p == null) {
            sender.sendMessage("¡ìcNo such a player: ¡ìe" + args[1]);
            return true;
        }
        this.switchServer(p, args[0]);
        sender.sendMessage("¡ìaSuccess!");
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return sender.hasPermission("unitedserver") && args.length == 1
            ? getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList())
            : null;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("UnitedServer:brand")) return;
        playerList.add(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        playerList.remove(e.getPlayer());
    }
}
