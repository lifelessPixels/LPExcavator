package pl.lifelesspixels.lpexcavator.data;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ExcavatorPlayerStates {

    private static final AxeExcavatorMode DEFAULT_AXE_EXCAVATOR_MODE = AxeExcavatorMode.Disabled;
    private static final PickaxeExcavatorMode DEFAULT_PICKAXE_EXCAVATOR_MODE = PickaxeExcavatorMode.Disabled;
    private static final ShovelExcavatorMode DEFAULT_SHOVEL_EXCAVATOR_MODE = ShovelExcavatorMode.Disabled;
    private final HashMap<Player, AxeExcavatorMode> axeExcavatorMode = new HashMap<>();
    private final HashMap<Player, PickaxeExcavatorMode> pickaxeExcavatorMode = new HashMap<>();
    private final HashMap<Player, ShovelExcavatorMode> shovelExcavatorMode = new HashMap<>();

    public void setPlayerAxeMode(Player player, AxeExcavatorMode mode) {
        axeExcavatorMode.put(player, mode);
    }

    public AxeExcavatorMode getPlayerAxeMode(Player player) {
        if(!axeExcavatorMode.containsKey(player))
            axeExcavatorMode.put(player, DEFAULT_AXE_EXCAVATOR_MODE);
        return axeExcavatorMode.get(player);
    }

    public void setPlayerPickaxeMode(Player player, PickaxeExcavatorMode mode) {
        pickaxeExcavatorMode.put(player, mode);
    }

    public PickaxeExcavatorMode getPlayerPickaxeMode(Player player) {
        if(!pickaxeExcavatorMode.containsKey(player))
            pickaxeExcavatorMode.put(player, DEFAULT_PICKAXE_EXCAVATOR_MODE);
        return pickaxeExcavatorMode.get(player);
    }

    public void setPlayerShovelMode(Player player, ShovelExcavatorMode mode) {
        shovelExcavatorMode.put(player, mode);
    }

    public ShovelExcavatorMode getPlayerShovelMode(Player player) {
        if(!shovelExcavatorMode.containsKey(player))
            shovelExcavatorMode.put(player, DEFAULT_SHOVEL_EXCAVATOR_MODE);
        return shovelExcavatorMode.get(player);
    }

    public void removePlayerStates(Player player) {
        axeExcavatorMode.remove(player);
        pickaxeExcavatorMode.remove(player);
        shovelExcavatorMode.remove(player);
    }

}
