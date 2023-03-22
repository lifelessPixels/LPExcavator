package pl.lifelesspixels.lpexcavator.workers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.lifelesspixels.lpexcavator.LPExcavator;
import pl.lifelesspixels.lpexcavator.data.ExcavatorPlayerStates;
import pl.lifelesspixels.lpexcavator.data.PickaxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.utils.BlockFinder;
import pl.lifelesspixels.lpexcavator.utils.ToolTraits;

import java.util.List;

public class OreExcavatorWorker implements ExcavatorWorker {

    private final Material expectedMaterial;
    private final boolean isStoneVariant;

    public OreExcavatorWorker(Material expectedMaterial) {
        this.expectedMaterial = expectedMaterial;
        this.isStoneVariant = false;
    }

    public OreExcavatorWorker(Material expectedMaterial, boolean isStoneVariant) {
        this.expectedMaterial = expectedMaterial;
        this.isStoneVariant = isStoneVariant;
    }

    @Override
    public int Excavate(int toolDurability, Player player, Block startingBlock) {
        // get state
        BlockFinder finder = LPExcavator.getInstance().getDefaultBlockFinder();
        ExcavatorPlayerStates states = LPExcavator.getInstance().getPlayerStates();

        // check mode
        if(states.getPlayerPickaxeMode(player) == PickaxeExcavatorMode.Disabled)
            return 0;
        if(states.getPlayerPickaxeMode(player) == PickaxeExcavatorMode.OnlyOres && isStoneVariant)
            return 0;

        // check material type
        if(startingBlock.getType() != expectedMaterial)
            return 0;

        // check used tool
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if(!ToolTraits.isPickaxe(itemInHand))
            return 0;
        int pickaxeTickDelay = ToolTraits.getPickaxeTickDelay(itemInHand);

        // collect all matching ore blocks
        List<Block> oreBlocks = finder.getAdjacentBlocksOfSameType(startingBlock);
        oreBlocks.remove(startingBlock);

        // if there is only one block (starting one), skip everything
        if(oreBlocks.size() == 0)
            return 0;

        // if tool is not durable enough for this operation, break just the amount that the tool can
        int brokenBlocks = oreBlocks.size();
        if(toolDurability != -1 && oreBlocks.size() > toolDurability) {
            oreBlocks = oreBlocks.subList(0, toolDurability);
            brokenBlocks = toolDurability;
        }

        // otherwise, collect all matching leaves blocks
        OreExcavatorWorkerEffect effect =
                new OreExcavatorWorkerEffect(expectedMaterial, oreBlocks, player, pickaxeTickDelay, itemInHand);
        effect.run();
        return brokenBlocks;
    }

}
