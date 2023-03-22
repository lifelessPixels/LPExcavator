package pl.lifelesspixels.lpexcavator.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ToolTraits {

    public static boolean isAxe(ItemStack item) {
        return isAxe(item.getType());
    }

    public static boolean isAxe(Material material) {
        return material == Material.WOODEN_AXE || material == Material.STONE_AXE ||
                material == Material.IRON_AXE || material == Material.GOLDEN_AXE ||
                material == Material.DIAMOND_AXE || material == Material.NETHERITE_AXE;
    }

    public static int getAxeTickDelay(ItemStack item) {
        return getAxeTickDelay(item.getType());
    }

    public static int getAxeTickDelay(Material material) {
        return switch(material) {
            case WOODEN_AXE -> 5;
            case STONE_AXE -> 4;
            case IRON_AXE -> 3;
            case GOLDEN_AXE -> 2;
            case DIAMOND_AXE, NETHERITE_AXE -> 1;
            default -> throw new IllegalArgumentException("invalid axe material");
        };
    }

    public static boolean isPickaxe(ItemStack item) {
        return isPickaxe(item.getType());
    }

    public static boolean isPickaxe(Material material) {
        return material == Material.WOODEN_PICKAXE || material == Material.STONE_PICKAXE ||
                material == Material.IRON_PICKAXE || material == Material.GOLDEN_PICKAXE ||
                material == Material.DIAMOND_PICKAXE || material == Material.NETHERITE_PICKAXE;
    }

    public static int getPickaxeTickDelay(ItemStack item) {
        return getPickaxeTickDelay(item.getType());
    }

    public static int getPickaxeTickDelay(Material material) {
        return switch(material) {
            case WOODEN_PICKAXE -> 5;
            case STONE_PICKAXE -> 4;
            case IRON_PICKAXE -> 3;
            case GOLDEN_PICKAXE -> 2;
            case DIAMOND_PICKAXE, NETHERITE_PICKAXE -> 1;
            default -> throw new IllegalArgumentException("invalid pickaxe material");
        };
    }

    public static boolean isShovel(ItemStack item) {
        return isShovel(item.getType());
    }

    public static boolean isShovel(Material material) {
        return material == Material.WOODEN_SHOVEL || material == Material.STONE_SHOVEL ||
                material == Material.IRON_SHOVEL || material == Material.GOLDEN_SHOVEL ||
                material == Material.DIAMOND_SHOVEL || material == Material.NETHERITE_SHOVEL;
    }

    public static int getShovelTickDelay(ItemStack item) {
        return getShovelTickDelay(item.getType());
    }

    public static int getShovelTickDelay(Material material) {
        return switch(material) {
            case WOODEN_SHOVEL -> 5;
            case STONE_SHOVEL -> 4;
            case IRON_SHOVEL -> 3;
            case GOLDEN_SHOVEL -> 2;
            case DIAMOND_SHOVEL, NETHERITE_SHOVEL -> 1;
            default -> throw new IllegalArgumentException("invalid shovel material");
        };
    }

}
