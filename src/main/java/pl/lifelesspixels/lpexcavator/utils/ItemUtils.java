package pl.lifelesspixels.lpexcavator.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class ItemUtils {

    public static int getDurabilityOfItemInHand(Player player) {
        return getDurabilityOfItem(player.getInventory().getItemInMainHand());
    }

    public static int getDurabilityOfItem(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        // if the item is not damageable, it has infinite durability
        if(!(itemMeta instanceof Damageable))
            return -1;

        // otherwise tool is damageable and we can calculate its durability
        Damageable damageable = (Damageable)(itemMeta);
        return getMaxDurabilityOfItem(item) - damageable.getDamage();
    }

    public static int getMaxDurabilityOfItemInHand(Player player) {
        return getMaxDurabilityOfItem(player.getInventory().getItemInMainHand());
    }

    public static int getMaxDurabilityOfItem(ItemStack item) {
        return item.getType().getMaxDurability();
    }

    public static void damageItemInHand(Player player, int damageCount) {
        if(damageItem(player.getInventory().getItemInMainHand(), damageCount))
            breakItemInHand(player);
    }

    public static boolean damageItem(ItemStack item, int damageCount) {
        int durability = getDurabilityOfItem(item);
        if(durability == -1)
            return false;

        if(durability < damageCount)
            return true;

        // otherwise damage the item
        Damageable damageable = (Damageable)(item.getItemMeta());
        Objects.requireNonNull(damageable).setDamage(damageable.getDamage() + damageCount);
        item.setItemMeta(damageable);
        return false;
    }

    public static void breakItemInHand(Player player) {
        player.getInventory().clear(player.getInventory().getHeldItemSlot());
        player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
    }

}
