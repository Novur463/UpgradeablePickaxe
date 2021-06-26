package net.minehorizon.upgradeablepick.commands;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import net.minehorizon.upgradeablepick.handler.PickHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GivePick implements CommandExecutor {
    private UpgradeablePick upgradeablePick;
    public GivePick(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;

            if(!player.hasPermission("upgradeablepickaxe.give")) {
                upgradeablePick.getUtility().sendNoPermission(player);
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("givePickInvalidFormat")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("invalidPlayer")));
                return true;
            }

            if(!upgradeablePick.getUtility().isInventoryFull(target)) {
                PickHandler pickHandler = new PickHandler(upgradeablePick,target);
                pickHandler.givePick();
                target.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("receivedUpgradeablePickaxe")));
                player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("gaveUpgradeablePickaxe").replace("{target}", target.getName())));
                return true;
            }
            target.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("inventoryFull")));
            player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("targetPlayerInventoryFull").replace("{target}", target.getName())));
            return true;
        }

        else if(sender instanceof ConsoleCommandSender) {

            if(args.length == 0) {
                sender.sendMessage("Invalid format! /givepick <player>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                sender.sendMessage("Invalid player!");
                return true;
            }

            if(!upgradeablePick.getUtility().isInventoryFull(target)) {
                PickHandler pickHandler = new PickHandler(upgradeablePick,target);
                pickHandler.givePick();
                target.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("receivedUpgradeablePickaxe")));
                sender.sendMessage("Upgradeable pickaxe given to player: " + target.getName());
                return true;
            }

            sender.sendMessage("Target player inventory is full!");
            return true;
        }
        return true;
    }
}
