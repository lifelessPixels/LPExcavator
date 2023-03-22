package pl.lifelesspixels.lpexcavator.workers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.lifelesspixels.lpexcavator.LPExcavator;
import pl.lifelesspixels.lpexcavator.data.AxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.data.ExcavatorPlayerStates;
import pl.lifelesspixels.lpexcavator.utils.BlockFinder;
import pl.lifelesspixels.lpexcavator.utils.ToolTraits;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeExcavatorWorker implements ExcavatorWorker {

    private static final int DEFAULT_LEAVES_RADIUS = 3;

    private final Material woodMaterial;
    private final Set<Material> leavesMaterials;

    public TreeExcavatorWorker(Material woodMaterial) {
        this.woodMaterial = woodMaterial;
        this.leavesMaterials = new HashSet<>();
    }

    public TreeExcavatorWorker(Material woodMaterial, Material leavesMaterial) {
        this.woodMaterial = woodMaterial;

        HashSet<Material> leavesMaterials = new HashSet<>();
        leavesMaterials.add(leavesMaterial);
        this.leavesMaterials = leavesMaterials;
    }

    public TreeExcavatorWorker(Material woodMaterial, Set<Material> leavesMaterials) {
        this.woodMaterial = woodMaterial;
        this.leavesMaterials = leavesMaterials;
    }

    @Override
    public int Excavate(int toolDurability, Player player, Block startingBlock) {
        // get state
        BlockFinder finder = LPExcavator.getInstance().getDefaultBlockFinder();
        ExcavatorPlayerStates states = LPExcavator.getInstance().getPlayerStates();
        AxeExcavatorMode playerMode = states.getPlayerAxeMode(player);

        // check mode
        if(playerMode == AxeExcavatorMode.Disabled)
            return 0;

        // check wood type
        if(startingBlock.getType() != woodMaterial)
            return 0;

        // check used tool
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if(!ToolTraits.isAxe(itemInHand))
            return 0;
        int axeTickDelay = ToolTraits.getAxeTickDelay(itemInHand);

        // collect all matching wood blocks
        List<Block> woodBlocks = (playerMode == AxeExcavatorMode.AllSame) ?
                finder.getAdjacentBlocksOfSameType(startingBlock, false) :
                finder.getAdjacentBlocksOfSameTypeAndOrientation(startingBlock, false);

        // if there is only one block (starting one, skip everything)
        if(woodBlocks.size() == 1)
            return 0;

        // if tool is not durable enough for this operation, break just the amount that the tool can (without leaves)
        if(toolDurability != -1 && woodBlocks.size() > toolDurability) {
            TreeExcavatorWorkerEffect effect = new TreeExcavatorWorkerEffect(
                    woodMaterial, leavesMaterials, woodBlocks.subList(0, toolDurability),
                            new ArrayList<>(), player, axeTickDelay, itemInHand);
            effect.run();
            return toolDurability;
        }

        // otherwise, collect all matching leaves blocks
        HashSet<Block> leavesBlocksSet = new HashSet<>();
        for(Block woodBlock : woodBlocks)
            leavesBlocksSet.addAll(finder.getBlocksOfTypesInRadius(leavesMaterials, woodBlock, DEFAULT_LEAVES_RADIUS));
        List<Block> leavesBlocks = new ArrayList<>(leavesBlocksSet);

        // remove starting block from the list
        woodBlocks.remove(startingBlock);

        // start an excavation worker effect
        TreeExcavatorWorkerEffect effect = new TreeExcavatorWorkerEffect(
                woodMaterial, leavesMaterials, woodBlocks, leavesBlocks, player, axeTickDelay, itemInHand);
        effect.run();

        // return number of destroyed blocks
        return woodBlocks.size();
    }
}
