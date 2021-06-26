package net.minehorizon.upgradeablepick;

import me.bukkit.mTokens.Inkzzz.API.MySQLTokensAPI;
import me.bukkit.mTokens.Inkzzz.Tokens;
import net.minehorizon.upgradeablepick.commands.GiveExplosive;
import net.minehorizon.upgradeablepick.commands.GivePick;
import net.minehorizon.upgradeablepick.handler.enums.Items;
import net.minehorizon.upgradeablepick.handler.ut.ItemUtil;
import net.minehorizon.upgradeablepick.handler.ut.Utility;
import net.minehorizon.upgradeablepick.listener.Click;
import net.minehorizon.upgradeablepick.listener.RightClick;
import net.minehorizon.upgradeablepick.listener.pick.AnvilPick;
import net.minehorizon.upgradeablepick.listener.pick.DamagePick;
import net.minehorizon.upgradeablepick.listener.pick.DeathPick;
import net.minehorizon.upgradeablepick.listener.pick.EnchantPick;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class UpgradeablePick extends JavaPlugin {
    private UpgradeablePick instance;
    private Utility utility;
    private ItemUtil itemUtill;
    private MySQLTokensAPI mySQLTokensAPI;
    private Map<String, ItemStack> itemStackMap;

    @Override
    public void onEnable() {

        if(!pointsAPIEnabled()) {
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().warning("Disabling due to no MySQL-Tokens dependency!");
        }

        instance = this;

        utility = new Utility(instance);
        itemUtill = new ItemUtil(instance);
        itemStackMap = new HashMap<>();
        mySQLTokensAPI = Tokens.getInstance().getAPI();

        getServer().getPluginManager().registerEvents(new RightClick(instance), this);
        getServer().getPluginManager().registerEvents(new Click(instance), this);

        getServer().getPluginManager().registerEvents(new AnvilPick(instance), this);
        getServer().getPluginManager().registerEvents(new DamagePick(instance), this);
        getServer().getPluginManager().registerEvents(new DeathPick(instance), this);
        getServer().getPluginManager().registerEvents(new EnchantPick(instance), this);

        registerCommands("giveupgradeable", new GivePick(instance));
        registerCommands("giveexplosive", new GiveExplosive(instance));

        populateList();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private boolean pointsAPIEnabled() {
        return Bukkit.getPluginManager().getPlugin("MySQL-Tokens").isEnabled();
    }

    private void populateList() {
        for(Items items : Items.values()) {
            if(items.hasConfigIdentifier()) {
                itemStackMap.put(items.getConfigIdentifier(), itemUtill.createMenuItem(items));
            }
        }
    }

    private void registerCommands(String command, CommandExecutor executor) {
        PluginCommand pluginCommand = getCommand(command);
        if(pluginCommand == null) {
            return;
        }
        pluginCommand.setExecutor(executor);
    }

    public MySQLTokensAPI getMySQLTokensAPI() {
        return mySQLTokensAPI;
    }

    public Utility getUtility() {
        return utility;
    }

    public ItemUtil getItemUtill() {
        return itemUtill;
    }

    public Map<String, ItemStack> getItemStackMap() {
        return itemStackMap;
    }
}
