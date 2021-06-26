package net.minehorizon.upgradeablepick.listener;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import net.minehorizon.upgradeablepick.handler.PickHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RightClick implements Listener {
    private final UpgradeablePick upgradeablePick;
    public RightClick(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getPlayer().isSneaking()) {

                ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
                Player player = event.getPlayer();

                if(itemStack.getType() == Material.AIR) return;

                if(upgradeablePick.getItemUtill().isUpgradeablePick(itemStack)) {
                    PickHandler pickHandler = new PickHandler(upgradeablePick,player);
                    pickHandler.openUpgradeGUI(itemStack);
                }
            }
        }
    }
}
