package pl.lifelesspixels.lpexcavator.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.lifelesspixels.lpexcavator.LPExcavator;
import pl.lifelesspixels.lpexcavator.data.AxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.data.ExcavatorPlayerStates;
import pl.lifelesspixels.lpexcavator.data.PickaxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.data.ShovelExcavatorMode;
import pl.lifelesspixels.lpexcavator.utils.ToolTraits;

public class ItemEventsListener implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if(!event.getPlayer().isSneaking() || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK))
            return;

        Material itemType = event.getMaterial();
        if(!ToolTraits.isAxe(itemType) && !ToolTraits.isPickaxe(itemType) && !ToolTraits.isShovel(itemType))
            return;

        Player player = event.getPlayer();
        ExcavatorPlayerStates states = LPExcavator.getInstance().getPlayerStates();

        if(ToolTraits.isAxe(itemType)) {
            AxeExcavatorMode newMode = states.getPlayerAxeMode(player).cycle();
            states.setPlayerAxeMode(player, newMode);
            player.sendMessage(ChatColor.GREEN + "Axe excavator mode" + ChatColor.RESET + ": " + newMode.readableDescription());
        } else if (ToolTraits.isPickaxe(itemType)) {
            PickaxeExcavatorMode newMode = states.getPlayerPickaxeMode(player).cycle();
            states.setPlayerPickaxeMode(player, newMode);
            player.sendMessage(ChatColor.GREEN + "Pickaxe excavator mode" + ChatColor.RESET + ": " + newMode.readableDescription());
        } else {
            ShovelExcavatorMode newMode = states.getPlayerShovelMode(player).cycle();
            states.setPlayerShovelMode(player, newMode);
            player.sendMessage(ChatColor.GREEN + "Shovel excavator mode" + ChatColor.RESET + ": " + newMode.readableDescription());
        }

        // NOTE: prevent default action (i.e. stripping the log)
        event.setCancelled(true);
    }

}
