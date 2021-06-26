package net.minehorizon.upgradeablepick.handler.ut;

import net.minehorizon.upgradeablepick.UpgradeablePick;
import net.minehorizon.upgradeablepick.handler.enums.Items;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {
    private final UpgradeablePick upgradeablePick;
    public ItemUtil(UpgradeablePick upgradeablePick) {
        this.upgradeablePick = upgradeablePick;
    }

    public ItemStack createUpgradeablePickaxe() {
        ItemStack itemStack = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("upgradeablePickaxe.name")));
        itemMeta.setLore(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getStringList("upgradeablePickaxe.lore")));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public boolean isUpgradeablePick(ItemStack itemStack) {
        if(itemStack.getType().name().contains("PICK")) {
            if(itemStack.hasItemMeta()) {
                if(itemStack.getItemMeta().hasLore()) {
                    return itemStack.getItemMeta().getLore().equals(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getStringList("upgradeablePickaxe.lore")));
                }
            }
        }
        return false;
    }

    public boolean is(ItemStack itemStack, Items items) {
        return itemStack.isSimilar(createMenuItem(items));
    }

    //////////////////////////////////////////////////////////////////////////// Upgrade GUI ////////////////////////////////////////////////////////////////////////////

    public ItemStack duplicatePickaxe(ItemStack itemStack) {
        return new ItemStack(itemStack);
    }

    public ItemStack createMenuItem(Items items) {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(upgradeablePick.getConfig().getString("upgradeGUI.items." + items.getConfigIdentifier() + ".material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("upgradeGUI.items." + items.getConfigIdentifier() + ".name")));
        itemMeta.setLore(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getStringList("upgradeGUI.items." + items.getConfigIdentifier() + ".lore")));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public int getSlot(Items items) {
        return upgradeablePick.getConfig().getInt("upgradeGUI.items." + items.getConfigIdentifier() + ".slot");
    }



    //////////////////////////////////////////////////////////////////////////// Other ////////////////////////////////////////////////////////////////////////////

    public ItemStack createPane() {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(upgradeablePick.getConfig().getString("upgradeGUI.fillerBlock")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack createEnchantmentItem(Items items, int level) {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(upgradeablePick.getConfig().getString("upgradeGUI.items." + items.getConfigIdentifier() + ".material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("upgradeGUI.items." + items.getConfigIdentifier() + ".name")));
        List<String> lore = new ArrayList<>();
        for(String s : upgradeablePick.getConfig().getStringList("upgradeGUI.items." + items.getConfigIdentifier() + ".lore")) {
            lore.add(s.replace("{cost}", String.valueOf(getCost(items,level))));
        }
        itemMeta.setLore(upgradeablePick.getUtility().color(lore));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack createBlockedItem(Items items) {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(upgradeablePick.getConfig().getString("upgradeGUI.blockedItem")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("upgradeGUI.items." + items.getConfigIdentifier() + ".altName")));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public int getEnchantmentLevel(ItemStack itemStack, Enchantment enchantment) {
        if(itemStack.containsEnchantment(enchantment)) {
            return itemStack.getEnchantmentLevel(enchantment);
        }

        return 0;
    }

    public int getCost(Items items, int level) {
        return upgradeablePick.getConfig().getInt("tracks." + items.getConfigIdentifier() + "." + level);
    }

    //////////////////////////////////////////////////////////////////////// Explosive Pickaxe ////////////////////////////////////////////////////////////////////////

    public ItemStack createExplosivePickaxe() {
        ItemStack itemStack = new ItemStack(Material.matchMaterial(upgradeablePick.getConfig().getString("explosivePick.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getString("explosivePick.name")));
        itemMeta.setLore(upgradeablePick.getUtility().color(upgradeablePick.getConfig().getStringList("explosivePick.lore")));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public boolean isExplosivePickaxe(ItemStack itemStack) {
        return itemStack.isSimilar(createExplosivePickaxe());
    }

}
