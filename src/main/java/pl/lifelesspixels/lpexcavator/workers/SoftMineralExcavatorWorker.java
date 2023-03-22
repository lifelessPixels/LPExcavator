package pl.lifelesspixels.lpexcavator.workers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.lifelesspixels.lpexcavator.LPExcavator;
import pl.lifelesspixels.lpexcavator.data.ExcavatorPlayerStates;
import pl.lifelesspixels.lpexcavator.data.ShovelExcavatorMode;
import pl.lifelesspixels.lpexcavator.utils.BlockFinder;
import pl.lifelesspixels.lpexcavator.utils.ToolTraits;

import java.util.Comparator;
import java.util.List;

public class SoftMineralExcavatorWorker implements ExcavatorWorker {

    private final Material expectedMaterial;

    public SoftMineralExcavatorWorker(Material expectedMaterial) {
        this.expectedMaterial = expectedMaterial;
    }

    @Override
    public int Excavate(int toolDurability, Player player, Block startingBlock) {
        // get state
        BlockFinder finder = LPExcavator.getInstance().getDefaultBlockFinder();
        ExcavatorPlayerStates states = LPExcavator.getInstance().getPlayerStates();

        // check mode
        if(states.getPlayerShovelMode(player) == ShovelExcavatorMode.Disabled)
            return 0;

        // check material type
        if(startingBlock.getType() != expectedMaterial)
            return 0;

        // check used tool
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if(!ToolTraits.isShovel(itemInHand))
            return 0;
        int shovelTickDelay = ToolTraits.getShovelTickDelay(itemInHand);

        // collect all matching material blocks
        List<Block> materialBlocks = finder.getAdjacentBlocksOfSameType(startingBlock);

        // if blocks are affected by gravity, sort them by Y coordinate to avoid collapsing
        if(expectedMaterial.hasGravity())
            materialBlocks.sort((first, second) -> second.getY() - first.getY());
        // NOTE: gravity block might fall down
        else materialBlocks.remove(startingBlock);

        // if there is only one block (starting one), skip everything
        if(materialBlocks.size() == 0)
            return 0;

        // if tool is not durable enough for this operation, break just the amount that the tool can
        int brokenBlocks = materialBlocks.size();
        if(toolDurability != -1 && materialBlocks.size() > toolDurability) {
            materialBlocks = materialBlocks.subList(0, toolDurability);
            brokenBlocks = toolDurability;
        }

        // otherwise, collect all matching leaves blocks
        SoftMineralExcavatorWorkerEffect effect =
                new SoftMineralExcavatorWorkerEffect(expectedMaterial, materialBlocks, player, shovelTickDelay, itemInHand);
        effect.run();
        return brokenBlocks;
    }

}
