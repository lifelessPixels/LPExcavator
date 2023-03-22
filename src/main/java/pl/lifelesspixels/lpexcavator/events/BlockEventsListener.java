package pl.lifelesspixels.lpexcavator.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.lifelesspixels.lpexcavator.utils.ItemUtils;
import pl.lifelesspixels.lpexcavator.workers.ExcavatorWorker;
import pl.lifelesspixels.lpexcavator.workers.Workers;

public class BlockEventsListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // check if we have worker for this block type
        ExcavatorWorker worker = Workers.getWorker(event.getBlock());
        if(worker == null)
            return;

        // check if preferred tool is used
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        if (!event.getBlock().isPreferredTool(itemInHand))
            return;

        // do the excavation
        int toolDurability = ItemUtils.getDurabilityOfItemInHand(event.getPlayer());
        int brokenBlocks = worker.Excavate(toolDurability, event.getPlayer(), event.getBlock());

        // damage the tool
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE)
            ItemUtils.damageItemInHand(event.getPlayer(), brokenBlocks);
    }

}
