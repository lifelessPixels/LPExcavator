package pl.lifelesspixels.lpexcavator.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.lifelesspixels.lpexcavator.LPExcavator;
import pl.lifelesspixels.lpexcavator.data.ExcavatorPlayerStates;

public class PlayerConnectionEventsListener implements Listener {

    private final ExcavatorPlayerStates playerStates = LPExcavator.getInstance().getPlayerStates();

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        playerStates.removePlayerStates(event.getPlayer());
    }

}
