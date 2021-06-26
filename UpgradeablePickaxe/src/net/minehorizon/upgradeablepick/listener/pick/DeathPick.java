package net.minehorizon.upgradeablepick.listener.pick;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeathPick implements Listener {
    private final UpgradeablePick upgradeablePick;
    private Map<String, List<ItemStack>> itemStackMap;
    public DeathPick(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;

        itemStackMap = new HashMap<>();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        ItemStack[] contents = player.getInventory().getContents();

        for(ItemStack itemStack : contents) {

            if(itemStack == null || itemStack.getType() == Material.AIR) return;

            if(upgradeablePick.getItemUtill().isUpgradeablePick(itemStack)) {
                if(event.getDrops().contains(itemStack)) {
                    addToList(player.getName(), itemStack);
                    event.getDrops().remove(itemStack);
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if(itemStackMap.containsKey(event.getPlayer().getName())) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(upgradeablePick, new BukkitRunnable() {
                @Override
                public void run() {
                    for(ItemStack itemStack : itemStackMap.get(event.getPlayer().getName())) {
                        event.getPlayer().getInventory().addItem(itemStack);
                    }
                    itemStackMap.remove(event.getPlayer().getName(), itemStackMap.get(event.getPlayer().getName()));
                }
            }, 5L);
        }
    }

    private void addToList(String key, ItemStack value) {
        itemStackMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }
}
