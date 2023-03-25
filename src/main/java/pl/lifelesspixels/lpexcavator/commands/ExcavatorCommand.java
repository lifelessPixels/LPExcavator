package pl.lifelesspixels.lpexcavator.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.lifelesspixels.lpexcavator.gui.ExcavatorGUI;
import pl.lifelesspixels.lputilities.commands.CommandUtils;

public class ExcavatorCommand implements CommandExecutor {

    private final static String EXCAVATOR_PERMISSION = "lpexcavator.command.excavator";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(!sender.hasPermission(EXCAVATOR_PERMISSION)) {
            CommandUtils.sendNoPermissionsMessage(sender, alias);
            return true;
        }

        if(!(sender instanceof Player)) {
            CommandUtils.sendOnlyAsPlayer(sender, alias);
            return true;
        }

        if(args.length == 0) {
            ExcavatorGUI excavatorGUI = new ExcavatorGUI();
            excavatorGUI.openFor((Player)(sender));
            return true;
        }

        CommandUtils.sendUsage(sender, alias, "");
        return true;
    }

}
