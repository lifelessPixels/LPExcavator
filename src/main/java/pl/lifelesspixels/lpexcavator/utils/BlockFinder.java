package pl.lifelesspixels.lpexcavator.utils;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;

import java.util.*;
import java.util.function.Predicate;

public class BlockFinder {

    private final int maxBlockCount;

    public BlockFinder(int maxBlockCount) {
        this.maxBlockCount = maxBlockCount;
    }

    public List<Block> getAdjacentBlocksOfSameTypeAndOrientation(Block startingBlock) {
        return getAdjacentBlocksOfSameTypeAndOrientation(startingBlock, true);
    }

    public List<Block> getAdjacentBlocksOfSameTypeAndOrientation(Block startingBlock, boolean allowsGoingDown) {
        boolean usesOrientation;
        Axis expectedAxis;
        Material expectedMaterial = startingBlock.getType();

        // check for orientation
        BlockData startingBlockData = startingBlock.getBlockData();
        if(startingBlockData instanceof Orientable) {
            Orientable orientableBlockData = (Orientable)(startingBlockData);
            usesOrientation = true;
            expectedAxis = orientableBlockData.getAxis();
        } else {
            usesOrientation = false;
            expectedAxis = null;
        }

        // create predicate and return list
        return getAdjacentBlocksUsingPredicate(startingBlock, block -> {
            BlockData checkedBlockData = block.getBlockData();
            boolean isOrientationOkay = true;
            if(usesOrientation) {
                 if(!(checkedBlockData instanceof Orientable)) {
                     isOrientationOkay = false;
                 } else {
                     Orientable orientable = (Orientable) (checkedBlockData);
                     isOrientationOkay = orientable.getAxis() == expectedAxis;
                 }
            }

            return block.getType() == expectedMaterial && isOrientationOkay;
        }, allowsGoingDown);
    }

    public List<Block> getAdjacentBlocksOfSameType(Block startingBlock) {
        return getAdjacentBlocksOfSameType(startingBlock, true);
    }

    public List<Block> getAdjacentBlocksOfSameType(Block startingBlock, boolean allowsGoingDown) {
        Material expectedMaterial = startingBlock.getType();
        return getAdjacentBlocksUsingPredicate(
                startingBlock, block -> block.getType() == expectedMaterial, allowsGoingDown);
    }

    public List<Block> getAdjacentBlocksUsingPredicate(Block startingBlock, Predicate<Block> predicate) {
        return getAdjacentBlocksUsingPredicate(startingBlock, predicate, true);
    }

    public List<Block> getAdjacentBlocksUsingPredicate(Block startingBlock, Predicate<Block> predicate, boolean allowsGoingDown) {
        int processedBlocks = 0;
        List<Block> collectedBlocks = new ArrayList<>();

        Queue<Block> blocksToProcess = new ArrayDeque<>();
        blocksToProcess.add(startingBlock);

        // get block's world
        World world = startingBlock.getWorld();

        // process all blocks
        while(!blocksToProcess.isEmpty() && processedBlocks <= maxBlockCount) {
            Block block = blocksToProcess.remove();
            collectedBlocks.add(block);
            processedBlocks++;

            // get block's coords
            int blockX = block.getX();
            int blockY = block.getY();
            int blockZ = block.getZ();

            // test all neighbours
            for (int x = blockX - 1; x <= blockX + 1; x++) {
                for (int z = blockZ - 1; z <= blockZ + 1; z++) {
                    for (int y = blockY - (allowsGoingDown ? 1 : 0); y <= blockY + 1; y++) {
                        if (blockX == x && blockY == y && blockZ == z)
                            continue;

                        Block neighbour = world.getBlockAt(x, y, z);
                        if (!predicate.test(neighbour))
                            continue;

                        if (!collectedBlocks.contains(neighbour) && !blocksToProcess.contains(neighbour))
                            blocksToProcess.add(neighbour);
                    }
                }
            }
        }

        return collectedBlocks;
    }

    public List<Block> getBlocksOfTypeInRadius(Material expectedMaterial, Block startingBlock, int radius) {
        HashSet<Material> materials = new HashSet<>();
        materials.add(expectedMaterial);
        return getBlocksOfTypesInRadius(materials, startingBlock, radius);
    }

    public List<Block> getBlocksOfTypesInRadius(Set<Material> expectedMaterials, Block startingBlock, int radius) {
        List<Block> collectedBlocks = new ArrayList<>();

        // get needed values
        int blockX = startingBlock.getX();
        int blockY = startingBlock.getY();
        int blockZ = startingBlock.getZ();
        World world = startingBlock.getWorld();

        // iterate
        for(int x = blockX - radius; x <= blockX + radius; x++) {
            for(int y = blockY - radius; y <= blockY + radius; y++) {
                for(int z = blockZ - radius; z <= blockZ + radius; z++) {
                    Block block = world.getBlockAt(x, y, z);

                    if(expectedMaterials.contains(block.getType()))
                        collectedBlocks.add(block);
                }
            }
        }

        // return result
        return collectedBlocks;
    }

}
