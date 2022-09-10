package me.masmc05.nobed;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Pattern;

public final class Main extends JavaPlugin implements Listener {
    private static final Pattern bed = Pattern.compile("BED");
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        var item = e.getItem();
        if (block == null || item == null || !bed.matcher(item.getType().name()).find()) return;
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(e.getPlayer());
        Location loc = new Location(new BukkitWorld(block.getWorld()), block.getX(), block.getY(), block.getZ());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        if (!query.testBuild(loc, localPlayer, Flags.BUILD)) e.setCancelled(true);
    }
}
