package pl.lifelesspixels.lpexcavator.workers;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.HashSet;

public class Workers {

    private static final HashMap<Material, ExcavatorWorker> workersRegistry = new HashMap<>();

    static {
        // simple wood types
        {
            HashSet<Material> oakWoodLeaves = new HashSet<>();
            oakWoodLeaves.add(Material.OAK_LEAVES);
            oakWoodLeaves.add(Material.AZALEA_LEAVES);
            oakWoodLeaves.add(Material.FLOWERING_AZALEA_LEAVES);
            workersRegistry.put(Material.OAK_LOG, new TreeExcavatorWorker(Material.OAK_LOG, oakWoodLeaves));

            workersRegistry.put(Material.BIRCH_LOG, new TreeExcavatorWorker(Material.BIRCH_LOG, Material.BIRCH_LEAVES));
            workersRegistry.put(Material.SPRUCE_LOG, new TreeExcavatorWorker(Material.SPRUCE_LOG, Material.SPRUCE_LEAVES));
            workersRegistry.put(Material.JUNGLE_LOG, new TreeExcavatorWorker(Material.JUNGLE_LOG, Material.JUNGLE_LEAVES));
            workersRegistry.put(Material.ACACIA_LOG, new TreeExcavatorWorker(Material.ACACIA_LOG, Material.ACACIA_LEAVES));
            workersRegistry.put(Material.DARK_OAK_LOG, new TreeExcavatorWorker(Material.DARK_OAK_LOG, Material.DARK_OAK_LEAVES));
            workersRegistry.put(Material.MANGROVE_LOG, new TreeExcavatorWorker(Material.MANGROVE_LOG, Material.MANGROVE_LEAVES));
            workersRegistry.put(Material.CRIMSON_STEM, new TreeExcavatorWorker(Material.CRIMSON_STEM, Material.NETHER_WART_BLOCK));
            workersRegistry.put(Material.WARPED_STEM, new TreeExcavatorWorker(Material.WARPED_STEM, Material.WARPED_WART_BLOCK));
        }

        // stripped wood types
        {
            workersRegistry.put(Material.STRIPPED_OAK_LOG, new TreeExcavatorWorker(Material.STRIPPED_OAK_LOG));
            workersRegistry.put(Material.STRIPPED_BIRCH_LOG, new TreeExcavatorWorker(Material.STRIPPED_BIRCH_LOG));
            workersRegistry.put(Material.STRIPPED_JUNGLE_LOG, new TreeExcavatorWorker(Material.STRIPPED_JUNGLE_LOG));
            workersRegistry.put(Material.STRIPPED_SPRUCE_LOG, new TreeExcavatorWorker(Material.STRIPPED_SPRUCE_LOG));
            workersRegistry.put(Material.STRIPPED_ACACIA_LOG, new TreeExcavatorWorker(Material.STRIPPED_ACACIA_LOG));
            workersRegistry.put(Material.STRIPPED_DARK_OAK_LOG, new TreeExcavatorWorker(Material.STRIPPED_DARK_OAK_LOG));
            workersRegistry.put(Material.STRIPPED_MANGROVE_LOG, new TreeExcavatorWorker(Material.STRIPPED_MANGROVE_LOG));
            workersRegistry.put(Material.STRIPPED_CRIMSON_STEM, new TreeExcavatorWorker(Material.STRIPPED_CRIMSON_STEM));
            workersRegistry.put(Material.STRIPPED_WARPED_STEM, new TreeExcavatorWorker(Material.STRIPPED_WARPED_STEM));
        }

        // ore types
        {
            workersRegistry.put(Material.COAL_ORE, new OreExcavatorWorker(Material.COAL_ORE));
            workersRegistry.put(Material.COPPER_ORE, new OreExcavatorWorker(Material.COPPER_ORE));
            workersRegistry.put(Material.LAPIS_ORE, new OreExcavatorWorker(Material.LAPIS_ORE));
            workersRegistry.put(Material.IRON_ORE, new OreExcavatorWorker(Material.IRON_ORE));
            workersRegistry.put(Material.REDSTONE_ORE, new OreExcavatorWorker(Material.REDSTONE_ORE));
            workersRegistry.put(Material.DIAMOND_ORE, new OreExcavatorWorker(Material.DIAMOND_ORE));
            workersRegistry.put(Material.GOLD_ORE, new OreExcavatorWorker(Material.GOLD_ORE));
            workersRegistry.put(Material.EMERALD_ORE, new OreExcavatorWorker(Material.EMERALD_ORE));
            workersRegistry.put(Material.DEEPSLATE_COAL_ORE, new OreExcavatorWorker(Material.DEEPSLATE_COAL_ORE));
            workersRegistry.put(Material.DEEPSLATE_COPPER_ORE, new OreExcavatorWorker(Material.DEEPSLATE_COPPER_ORE));
            workersRegistry.put(Material.DEEPSLATE_LAPIS_ORE, new OreExcavatorWorker(Material.DEEPSLATE_LAPIS_ORE));
            workersRegistry.put(Material.DEEPSLATE_IRON_ORE, new OreExcavatorWorker(Material.DEEPSLATE_IRON_ORE));
            workersRegistry.put(Material.DEEPSLATE_REDSTONE_ORE, new OreExcavatorWorker(Material.DEEPSLATE_REDSTONE_ORE));
            workersRegistry.put(Material.DEEPSLATE_DIAMOND_ORE, new OreExcavatorWorker(Material.DEEPSLATE_DIAMOND_ORE));
            workersRegistry.put(Material.DEEPSLATE_GOLD_ORE, new OreExcavatorWorker(Material.DEEPSLATE_GOLD_ORE));
            workersRegistry.put(Material.DEEPSLATE_EMERALD_ORE, new OreExcavatorWorker(Material.DEEPSLATE_EMERALD_ORE));
            workersRegistry.put(Material.NETHER_QUARTZ_ORE, new OreExcavatorWorker(Material.NETHER_QUARTZ_ORE));
            workersRegistry.put(Material.NETHER_GOLD_ORE, new OreExcavatorWorker(Material.NETHER_GOLD_ORE));
            workersRegistry.put(Material.ANCIENT_DEBRIS, new OreExcavatorWorker(Material.ANCIENT_DEBRIS));
        }

        // alternate stone variants types
        {
            workersRegistry.put(Material.GRANITE, new OreExcavatorWorker(Material.GRANITE, true));
            workersRegistry.put(Material.DIORITE, new OreExcavatorWorker(Material.DIORITE, true));
            workersRegistry.put(Material.ANDESITE, new OreExcavatorWorker(Material.ANDESITE, true));
            workersRegistry.put(Material.MOSSY_COBBLESTONE, new OreExcavatorWorker(Material.MOSSY_COBBLESTONE, true));
            workersRegistry.put(Material.TUFF, new OreExcavatorWorker(Material.TUFF, true));
        }

        // soft minerals types
        {
             workersRegistry.put(Material.GRAVEL, new SoftMineralExcavatorWorker(Material.GRAVEL));
             workersRegistry.put(Material.CLAY, new SoftMineralExcavatorWorker(Material.CLAY));
             workersRegistry.put(Material.MUD, new SoftMineralExcavatorWorker(Material.MUD));
        }

    }

    public static ExcavatorWorker getWorker(Block block) {
        return getWorker(block.getType());
    }

    public static ExcavatorWorker getWorker(Material material) {
        return workersRegistry.get(material);
    }

}
