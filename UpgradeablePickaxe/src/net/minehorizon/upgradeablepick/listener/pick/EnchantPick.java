package net.minehorizon.upgradeablepick.listener.pick;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public class EnchantPick implements Listener {
    private final UpgradeablePick upgradeablePick;
    public EnchantPick(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        if(!event.isCancelled()) {
            if(upgradeablePick.getItemUtill().isUpgradeablePick(event.getItem())) {
                event.getEnchanter().sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("cannotEnchantUP")));
                event.setCancelled(true);
            }
        }
    }
}
