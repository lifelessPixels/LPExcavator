package pl.lifelesspixels.lpexcavator.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.lifelesspixels.lpchestgui.data.ChestGUIClickActionBuilder;
import pl.lifelesspixels.lpchestgui.data.ChestGUIClickActionResult;
import pl.lifelesspixels.lpchestgui.gui.ChestGUI;
import pl.lifelesspixels.lpexcavator.LPExcavator;
import pl.lifelesspixels.lpexcavator.data.AxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.data.ExcavatorPlayerStates;
import pl.lifelesspixels.lpexcavator.data.PickaxeExcavatorMode;
import pl.lifelesspixels.lpexcavator.data.ShovelExcavatorMode;

import java.util.ArrayList;
import java.util.Objects;

public class ExcavatorGUI extends ChestGUI {

    private final static ItemStack AXE_ITEM;
    private final static ItemStack PICKAXE_ITEM;
    private final static ItemStack SHOVEL_ITEM;

    static {
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to change axe excavator mode");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        AXE_ITEM = item;

        item = new ItemStack(Material.DIAMOND_PICKAXE);
        meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to change pickaxe excavator mode");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        PICKAXE_ITEM = item;

        item = new ItemStack(Material.DIAMOND_SHOVEL);
        meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to change shovel excavator mode");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        SHOVEL_ITEM = item;
    }

    public ExcavatorGUI() {
        super(4, "Excavator settings");;

        // sett actions
        ExcavatorPlayerStates states = LPExcavator.getInstance().getPlayerStates();
        setAction(11,
                new ChestGUIClickActionBuilder().withLeftClickCallback(context -> {
                    Player player = context.getPlayer();
                    states.setPlayerAxeMode(player, states.getPlayerAxeMode(player).cycle());
                    displayAxeIndicatorFor(player, states);
                    return ChestGUIClickActionResult.RemainOpen;
                }).build(),
                AXE_ITEM,
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP
        );
        setAction(13,
                new ChestGUIClickActionBuilder().withLeftClickCallback(context -> {
                    Player player = context.getPlayer();
                    states.setPlayerPickaxeMode(player, states.getPlayerPickaxeMode(player).cycle());
                    displayPickaxeIndicatorFor(player, states);
                    return ChestGUIClickActionResult.RemainOpen;
                }).build(),
                PICKAXE_ITEM,
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP
        );
        setAction(15,
                new ChestGUIClickActionBuilder().withLeftClickCallback(context -> {
                    Player player = context.getPlayer();
                    states.setPlayerShovelMode(player, states.getPlayerShovelMode(player).cycle());
                    displayShovelIndicatorFor(player, states);
                    return ChestGUIClickActionResult.RemainOpen;
                }).build(),
                SHOVEL_ITEM,
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP
        );
    }

    @Override
    public void onOpen(Player player) {
        displayIndicatorsFor(player);
    }

    private void displayIndicatorsFor(Player player) {
        ExcavatorPlayerStates states = LPExcavator.getInstance().getPlayerStates();
        displayAxeIndicatorFor(player, states);
        displayPickaxeIndicatorFor(player, states);
        displayShovelIndicatorFor(player, states);
    }

    private void displayAxeIndicatorFor(Player player, ExcavatorPlayerStates states) {
        resetSlot(20);

        Material material;
        AxeExcavatorMode mode = states.getPlayerAxeMode(player);
        switch (mode) {
            case Disabled -> material = Material.BARRIER;
            case OnlySameDirection -> material = Material.OAK_LOG;
            case AllSame -> material = Material.OAK_WOOD;
            default -> throw new IllegalStateException("invalid axe excavator mode");
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Axe excavator mode: ");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + mode.readableDescription());
        lore.add("");
        lore.add(ChatColor.WHITE + "Click the axe above to change mode");
        meta.setLore(lore);
        item.setItemMeta(meta);
        setDummyItem(20, item);
    }

    private void displayPickaxeIndicatorFor(Player player, ExcavatorPlayerStates states) {
        resetSlot(22);

        Material material;
        PickaxeExcavatorMode mode = states.getPlayerPickaxeMode(player);
        switch (mode) {
            case Disabled -> material = Material.BARRIER;
            case OnlyOres -> material = Material.DIAMOND_ORE;
            case OresAndAlternateStones -> material = Material.ANDESITE;
            default -> throw new IllegalStateException("invalid pickaxe excavator mode");
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Pickaxe excavator mode: ");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + mode.readableDescription());
        lore.add("");
        lore.add(ChatColor.WHITE + "Click the pickaxe above to change mode");
        meta.setLore(lore);
        item.setItemMeta(meta);
        setDummyItem(22, item);
    }

    private void displayShovelIndicatorFor(Player player, ExcavatorPlayerStates states) {
        resetSlot(24);

        Material material;
        ShovelExcavatorMode mode = states.getPlayerShovelMode(player);
        switch (mode) {
            case Disabled -> material = Material.BARRIER;
            case Enabled -> material = Material.GRAVEL;
            default -> throw new IllegalStateException("invalid shovel excavator mode");
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Shovel excavator mode: ");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + mode.readableDescription());
        lore.add("");
        lore.add(ChatColor.WHITE + "Click the shovel above to change mode");
        meta.setLore(lore);
        item.setItemMeta(meta);
        setDummyItem(24, item);
    }


}
