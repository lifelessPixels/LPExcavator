package pl.lifelesspixels.lpexcavator.workers;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface ExcavatorWorker {

    public int Excavate(int toolDurability, Player player, Block startingBlock);

}
