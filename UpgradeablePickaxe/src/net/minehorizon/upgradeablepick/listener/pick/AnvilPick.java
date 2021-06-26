package net.minehorizon.upgradeablepick.listener.pick;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilPick implements Listener {
    private final UpgradeablePick upgradeablePick;
    public AnvilPick(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getInventory() instanceof AnvilInventory) {
            ItemStack clickedItem = event.getCurrentItem();

            if(clickedItem == null || (clickedItem.getType() == Material.AIR)) return;

            if(upgradeablePick.getItemUtill().isUpgradeablePick(clickedItem)) {
                event.setCancelled(true);
                event.getWhoClicked().sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("cannotAnvilUP")));
            }
        }

        else if(event.getInventory() instanceof GrindstoneInventory) {
            ItemStack clickedItem = event.getCurrentItem();

            if(clickedItem == null || (clickedItem.getType() == Material.AIR)) return;

            if(upgradeablePick.getItemUtill().isUpgradeablePick(clickedItem)) {
                event.setCancelled(true);
                event.getWhoClicked().sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("cannotGrindstoneUP")));
            }
        }
    }
}
