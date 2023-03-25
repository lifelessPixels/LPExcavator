package pl.lifelesspixels.lpexcavator;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.lifelesspixels.lpexcavator.commands.ExcavatorCommand;
import pl.lifelesspixels.lpexcavator.data.AxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.data.ExcavatorPlayerStates;
import pl.lifelesspixels.lpexcavator.data.PickaxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.events.BlockEventsListener;
import pl.lifelesspixels.lpexcavator.events.ItemEventsListener;
import pl.lifelesspixels.lpexcavator.events.PlayerConnectionEventsListener;
import pl.lifelesspixels.lpexcavator.utils.BlockFinder;

import java.util.Objects;

public class LPExcavator extends JavaPlugin implements Listener {

    private static LPExcavator instance;

    private ExcavatorPlayerStates playerStates = new ExcavatorPlayerStates();
    private BlockFinder blockFinder = new BlockFinder(128);

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // register handlers
        getServer().getPluginManager().registerEvents(new BlockEventsListener(), this);
        getServer().getPluginManager().registerEvents(new ItemEventsListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerConnectionEventsListener(), this);

        // make sure the state is consistent after reload and choose safe option of disabled excavator
        for(Player player : getServer().getOnlinePlayers()) {
            playerStates.setPlayerAxeMode(player, AxeExcavatorMode.Disabled);
            playerStates.setPlayerPickaxeMode(player, PickaxeExcavatorMode.Disabled);
        }

        // register command handlers
        Objects.requireNonNull(getCommand("excavator")).setExecutor(new ExcavatorCommand());
    }

    public ExcavatorPlayerStates getPlayerStates() {
        return playerStates;
    }

    public BlockFinder getDefaultBlockFinder() {
        return blockFinder;
    }

    public static LPExcavator getInstance() {
        return instance;
    }

}
