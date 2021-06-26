package net.minehorizon.upgradeablepick.handler.enums;

import org.bukkit.enchantments.Enchantment;

public enum Items {
    NULL(false, false),
    OWN_PICKAXE(false, false),
    EFFICIENCY_CRYSTAL(true, "efficiencyCrystal", true, Enchantment.DIG_SPEED),
    FORTUNE_CRYSTAL(true,"fortuneCrystal", true, Enchantment.LOOT_BONUS_BLOCKS),
    MATERIAL_UPGRADE(true, "matToken", false);

    boolean hasConfigIdentifier;
    String configIdentifier;
    boolean hasLinkedEnchant;
    Enchantment linkedEnchant;

    Items(boolean hasConfigIdentifier, boolean hasLinkedEnchant) {
        this.hasConfigIdentifier = hasConfigIdentifier;
        this.hasLinkedEnchant = hasLinkedEnchant;
    }

    Items(boolean hasConfigIdentifier, String configIdentifier, boolean hasLinkedEnchant) {
        this.hasConfigIdentifier = hasConfigIdentifier;
        this.configIdentifier = configIdentifier;
        this.hasLinkedEnchant = hasLinkedEnchant;
    }

    Items(boolean hasConfigIdentifier, String configIdentifier, boolean hasLinkedEnchant, Enchantment linkedEnchant) {
        this.hasConfigIdentifier = hasConfigIdentifier;
        this.configIdentifier = configIdentifier;
        this.hasLinkedEnchant = hasLinkedEnchant;
        this.linkedEnchant = linkedEnchant;
    }

    public boolean hasConfigIdentifier() {
        return hasConfigIdentifier;
    }

    public String getConfigIdentifier() {
        return configIdentifier;
    }

    public boolean hasLinkedEnchant() {
        return hasLinkedEnchant;
    }

    public Enchantment getLinkedEnchant() {
        return linkedEnchant;
    }
}
