package net.minehorizon.upgradeablepick.listener;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import net.minehorizon.upgradeablepick.handler.PickHandler;
import net.minehorizon.upgradeablepick.handler.enums.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Click implements Listener {
    private final UpgradeablePick upgradeablePick;
    public Click(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase("Upgradeable GUI")) {
            ItemStack itemStack = event.getCurrentItem();
            Player player = (Player)event.getWhoClicked();

            if(event.getSlot() == 13) event.setCancelled(true);

            if(upgradeablePick.getItemStackMap().containsValue(itemStack)) {

                if(itemStack == null) return;

                Items items = getItemFromString(itemStack);
                ItemStack inHand = player.getInventory().getItemInMainHand();

                PickHandler pickHandler = new PickHandler(upgradeablePick,player);
                pickHandler.purchase(items, inHand);
                pickHandler.refreshInventory();

                event.setCancelled(true);
            } else {
                event.setCancelled(true);
            }
        }
    }

    private Items getItemFromString(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        String name = itemMeta.getDisplayName();

        if(name.contains("Efficiency")) {
            return Items.EFFICIENCY_CRYSTAL;
        } else if(name.contains("Fortune")) {
            return Items.FORTUNE_CRYSTAL;
        } else if(name.contains("Material Upgrade Token")) {
            return Items.MATERIAL_UPGRADE;
        }

        return null;
    }
}
