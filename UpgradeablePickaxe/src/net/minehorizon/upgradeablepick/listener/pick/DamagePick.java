package net.minehorizon.upgradeablepick.listener.pick;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class DamagePick implements Listener {
    private final UpgradeablePick upgradeablePick;
    public DamagePick(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    @EventHandler
    public void onDamage(PlayerItemDamageEvent event) {
        if(upgradeablePick.getItemUtill().isUpgradeablePick(event.getItem()) || upgradeablePick.getItemUtill().isExplosivePickaxe(event.getItem())) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }
}
