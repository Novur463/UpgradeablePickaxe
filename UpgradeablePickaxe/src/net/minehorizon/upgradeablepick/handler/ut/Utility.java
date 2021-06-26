package net.minehorizon.upgradeablepick.handler.ut;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    private final UpgradeablePick upgradeablePick;
    public Utility(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    public void sendNoPermission(Player player) {
        player.sendMessage(color(upgradeablePick.getConfig().getString("noPermission")));
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

    public List<String> color(List<String> stringList) {
        List<String> colorList = new ArrayList<>();
        for(String s : stringList) {
            colorList.add(color(s));
        }
        return colorList;
    }

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch(NumberFormatException ex) {
            return false;
        }
    }
}
