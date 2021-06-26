package net.minehorizon.upgradeablepick.handler;

import me.bukkit.mTokens.Inkzzz.API.MySQLTokensAPI;
import net.minehorizon.upgradeablepick.UpgradeablePick;
import net.minehorizon.upgradeablepick.handler.enums.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PickHandler {
    private final UpgradeablePick upgradeablePick;
    private Player player;
    private Inventory inventory;

    public PickHandler(UpgradeablePick upgradeablePick, Player player) {
        this.upgradeablePick = upgradeablePick;
        this.player = player;

        this.inventory = Bukkit.createInventory(null,upgradeablePick.getConfig().getInt("upgradeGUI.size"), "Upgradeable GUI");
    }

    public void refreshInventory() {
        PickHandler pickHandler = new PickHandler(upgradeablePick,player);
        pickHandler.openUpgradeGUI(player.getInventory().getItemInMainHand());
    }

    public void givePick() {
        player.getInventory().addItem(upgradeablePick.getItemUtill().createUpgradeablePickaxe());
    }

    public void giveExplosive() { player.getInventory().addItem(upgradeablePick.getItemUtill().createExplosivePickaxe()); }

    public void openUpgradeGUI(ItemStack itemStack) {

        for(Items items : Items.values()) {

            if(items == Items.OWN_PICKAXE) {
                inventory.setItem(upgradeablePick.getConfig().getInt("upgradeGUI.items.ownPick.slot"), upgradeablePick.getItemUtill().duplicatePickaxe(player.getInventory().getItemInMainHand()));
            }

            if(items.hasConfigIdentifier()) {

                if(items.hasLinkedEnchant()) {

                    int level = getEnchantmentLevel(itemStack, items.getLinkedEnchant());
                    if(level == upgradeablePick.getConfig().getInt("enchantment.max." + items.getConfigIdentifier())) {
                        inventory.setItem(upgradeablePick.getItemUtill().getSlot(items), upgradeablePick.getItemUtill().createBlockedItem(items));
                    } else {
                        inventory.setItem(upgradeablePick.getItemUtill().getSlot(items), upgradeablePick.getItemUtill().createMenuItem(items));
                    }

                }

                else {
                    if(items == Items.MATERIAL_UPGRADE) {
                        if(!canUpgrade(itemStack)) {
                            inventory.setItem(upgradeablePick.getItemUtill().getSlot(items), upgradeablePick.getItemUtill().createBlockedItem(items));
                        } else {
                            inventory.setItem(upgradeablePick.getItemUtill().getSlot(items), upgradeablePick.getItemUtill().createMenuItem(items));
                        }
                    }

                }


            }
        }

        fill();

        player.openInventory(inventory);
    }

    private void fill() {
        for(int i = 0; i < inventory.getSize(); i++) {
            if(inventory.getItem(i) == null || (inventory.getItem(i).getType() == Material.AIR)) {
                inventory.setItem(i,upgradeablePick.getItemUtill().createPane());
            }
        }
    }


    public void purchase(Items items, ItemStack itemStack) {
        if(items.hasLinkedEnchant()) {

            int currentLevel = getEnchantmentLevel(itemStack,items.getLinkedEnchant());

            if((currentLevel + 1) > upgradeablePick.getConfig().getInt("enchantment.max." + items.getConfigIdentifier())) {
                player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("cannotUpgradeEnchantment")));
            } else {

                if(canAffordEnchantment(items, itemStack)) {
                    upgradeablePick.getMySQLTokensAPI().takeTokens(player, getCost(items,currentLevel+1));

                    removeEnchantment(itemStack,items.getLinkedEnchant());
                    itemStack.addUnsafeEnchantment(items.getLinkedEnchant(),currentLevel + 1);
                } else {
                    player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("cannotAfford").replace("{cost}", String.valueOf(getCost(items,currentLevel+1)))));
                }
            }
        }

        else if(items == Items.MATERIAL_UPGRADE) {
            if(canUpgrade(itemStack)) {
                if(!canAfford(player,getMATCost(getNextMaterial(itemStack)))) {
                    player.sendMessage(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("cannotAffordMAT").replace("{cost}", String.valueOf(getMATCost(getNextMaterial(itemStack))))));
                } else {
                    upgradeablePick.getMySQLTokensAPI().takeTokens(player, getMATCost(getNextMaterial(itemStack)));
                    Material material = getNextMaterial(itemStack);
                    itemStack.setType(material);
                }
            }
        } else {
            inventory.setItem(upgradeablePick.getConfig().getInt("upgradeGUI.items.matToken.slot"), upgradeablePick.getItemUtill().createBlockedItem(items));
        }
    }

    private boolean canAffordEnchantment(Items items, ItemStack itemStack) {
        int enchantmentLevel = upgradeablePick.getItemUtill().getEnchantmentLevel(itemStack, items.getLinkedEnchant());
        int cost = upgradeablePick.getConfig().getInt("tracks." + items.getConfigIdentifier() + "." + (enchantmentLevel+1));

        if((upgradeablePick.getMySQLTokensAPI().getTokens(player) - cost) >= 0) {
            return true;
        }
        return false;
    }

    public int getCost(Items items, int level) {
        return upgradeablePick.getConfig().getInt("tracks." + items.getConfigIdentifier() + "." + level);
    }

    private boolean canUpgrade(ItemStack itemStack) {
        return itemStack.getType().name().contains("PICK") && itemStack.getType() != Material.DIAMOND_PICKAXE;
    }

    private int getEnchantmentLevel(ItemStack itemStack, Enchantment enchantment) {
        if(itemStack.containsEnchantment(enchantment)) {
            return itemStack.getEnchantmentLevel(enchantment);
        }

        return 0;
    }

    private void removeEnchantment(ItemStack itemStack, Enchantment enchantment) {
        if(itemStack.containsEnchantment(enchantment)) {
            itemStack.removeEnchantment(enchantment);
        }
    }

    private boolean canAfford(Player player, int cost) {
        return (upgradeablePick.getMySQLTokensAPI().getTokens(player) - cost) >= 0;
    }

    private Material getNextMaterial(ItemStack itemStack) {
        Material newMaterialType = null;

        switch(itemStack.getType()) {
            case WOODEN_PICKAXE:
                newMaterialType = Material.STONE_PICKAXE;
                break;
            case STONE_PICKAXE:
                newMaterialType = Material.IRON_PICKAXE;
                break;
            case IRON_PICKAXE:
                newMaterialType = Material.DIAMOND_PICKAXE;
                break;
        }

        if(newMaterialType != Material.AIR) {
            return newMaterialType;
        }

        return Material.AIR;
    }

    private int getMATCost(Material material) {
        switch(material) {
            case STONE_PICKAXE:
                return upgradeablePick.getConfig().getInt("tracks.matToken.1");
            case IRON_PICKAXE:
                return upgradeablePick.getConfig().getInt("tracks.matToken.2");
            case DIAMOND_PICKAXE:
                return upgradeablePick.getConfig().getInt("tracks.matToken.3");
        }
        return 0;
    }
}
