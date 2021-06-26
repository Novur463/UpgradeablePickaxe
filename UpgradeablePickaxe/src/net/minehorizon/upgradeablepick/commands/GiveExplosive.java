package net.minehorizon.upgradeablepick.commands;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import net.minehorizon.upgradeablepick.handler.PickHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GiveExplosive implements CommandExecutor {
    private final UpgradeablePick upgradeablePick;
    public GiveExplosive(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player)sender;

            if(!player.hasPermission("explosivepick.give")) {
                upgradeablePick.getUtility().sendNoPermission(player);
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("exInvalidFormat")));
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("invalidPlayer")));
                return true;
            }

            if(!upgradeablePick.getUtility().isInventoryFull(target)) {
                PickHandler pickHandler = new PickHandler(upgradeablePick,target);
                pickHandler.giveExplosive();
                target.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("receivedExplosive")));
                if(!target.getName().equalsIgnoreCase(player.getName())) {
                    player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("gaveExplosive").replace("{target}", target.getName())));
                }
                return true;
            }
            target.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("inventoryFull")));
            if(!target.getName().equalsIgnoreCase(player.getName())) {
                player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("targetPlayerInventoryFull").replace("{target}", target.getName())));
            }
            return true;
        }

        else if(sender instanceof ConsoleCommandSender) {

            if(args.length == 0) {
                sender.sendMessage("Incorrect format! /giveexplosive <player>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) {
                sender.sendMessage("Player " + args[0] + " could not be found!");
                return true;
            }

            if(upgradeablePick.getUtility().isInventoryFull(target)) {
                sender.sendMessage("Inventory of player " + target.getName() + " was full, so could not be given an explosive pickaxe.");
                return true;
            }

            PickHandler pickHandler = new PickHandler(upgradeablePick,target);
            pickHandler.giveExplosive();
            target.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("receivedExplosive")));
            sender.sendMessage("Explosive pickaxe given to player " + target.getName() + " successfully");
            return true;
        }
        return true;
    }
}
