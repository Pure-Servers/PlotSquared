/*
 * Copyright (c) IntellectualCrafters - 2014.
 * You are not allowed to distribute and/or monetize any of our intellectual property.
 * IntellectualCrafters is not affiliated with Mojang AB. Minecraft is a trademark of Mojang AB.
 *
 * >> File = PlotAPI.java
 * >> Generated by: Citymonstret at 2014-08-09 01:44
 */

package com.intellectualcrafters.plot.api;

import com.intellectualcrafters.plot.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.intellectualcrafters.plot.PlotMain;
import com.intellectualcrafters.plot.commands.MainCommand;
import com.intellectualcrafters.plot.commands.SubCommand;

import java.util.ArrayList;
import java.util.Set;

/**
 * The plotMain api class.
 * @author Citymonstret
 */
@SuppressWarnings({"unused", "javadoc"})
public class PlotAPI {

	//To perform bukkit operations correctly.
	private JavaPlugin plugin;
	//To access plotMain stuffz.
	private PlotMain plotMain;
	//Reference
	public static final String ADMIN_PERMISSION = "plots.admin";
	
	
	/**
	 * Constructor. Insert any Plugin.
	 * @param plugin
	 */
	public PlotAPI(JavaPlugin plugin) {
		this.plugin = plugin;
		this.plotMain = JavaPlugin.getPlugin(PlotMain.class);
	}
	
	/**
	 * Send a message to a player.
	 * @param player
	 * @param c (Caption)
	 */
	public void sendMessage(Player player, C c){
        PlayerFunctions.sendMessage(player, c);
	}
	
	/**
	 * Send a message to a player.
	 * @param player
	 * @param string
	 */
	public void sendMessage(Player player, String string) {
		PlayerFunctions.sendMessage(player, string);
	}
	
	/**
	 * Send a message to the console.
	 * @param msg
	 */
	public void sendConsoleMessage(String msg) {
		PlotMain.sendConsoleSenderMessage(msg);
	}

	/**
	 * Send a message to the console
	 * @param c (Caption)
	 */
	public void sendConsoleMessage(C c) {
		sendConsoleMessage(c.s());
	}
	/**
	 * Register a flag for use in plots
	 * @param flag
	 */
	public void registerFlag(Flag flag) {
	    PlotMain.registerFlag(flag);
	}
	/**
	 * get all the currently registered flags
	 * @return array of Flag[]
	 */
	public Flag[] getRegisteredFlags() {
	    return PlotMain.getFlags().toArray(new Flag[0]);
	}
    /**
     * Get a plot based on the ID
     * @param id
     * @return plot, null if ID is wrong
     */
    public Plot getPlot(World world, int x, int z) {
        return PlotHelper.getPlot(world, new PlotId(x, z));
    }

	/**
	 * Get a plot based on the location
	 * @param l
	 * @return plot if found, otherwise it creates a temporary plot-
	 */
	public Plot getPlot(Location l) {
		return PlotHelper.getCurrentPlot(l);
	}
	
	/**
	 * Get a plot based on the player location
	 * @param player
	 * @return plot if found, otherwise it creates a temporary plot
	 */
	public Plot getPlot(Player player) {
		return this.getPlot(player.getLocation());
	}
	
	/**
	 * Check whether or not a player has a plot
	 * @param player
	 * @return true if player has a plot, false if not.
	 */
	public boolean hasPlot(World world, Player player) {
		return getPlots(world, player, true) != null && getPlots(world, player, true).length > 0;
	}

    /**
     * Get all plots for the player
     * @param plr to search for
     * @param just_owner should we just search for owner? Or with rights?
     */
    public Plot[] getPlots(World world, Player plr, boolean just_owner) {
       ArrayList<Plot> pPlots = new ArrayList<>();
       for(Plot plot : PlotMain.getPlots(world).values()) {
           if(just_owner) {
               if(plot.owner != null && plot.owner == plr.getUniqueId()) {
                   pPlots.add(plot);
               }
           } else {
               if(plot.hasRights(plr)) {
                   pPlots.add(plot);
               }
           }
       }
       return (Plot[]) pPlots.toArray();
    }
    /**
     * Get all plots for the world
     * @param World to get plots of
     * @return Plot[] - array of plot objects in world
     */
    public Plot[] getPlots(World world) {
        return PlotMain.getWorldPlots(world);
    }
    /**
     * Get all plot worlds
     * @return World[] - array of plot worlds
     */
    public String[] getPlotWorlds() {
        return PlotMain.getPlotWorlds();
    }
    /**
     * Get if plot world
     * @param world (to check if plot world)
     * @return boolean (if plot world or not)
     */
    public boolean isPlotWorld(World world) {
        return PlotMain.isPlotWorld(world);
    }
    /**
     * Get the settings for a world (settings bundled in PlotWorld class)
     * @param world (to get settings of)
     * @return PlotWorld class for ther world
     * ! will return null if not a plot world
     */
    public PlotWorld getWorldSettings(World world) {
        return PlotMain.getWorldSettings(world);
    }

    /**
     * Get plot locations
     * @param p
     * @return [0] = bottomLc, [1] = topLoc, [2] = home
     */
    public Location[] getLocations(Plot p) {
        World world = Bukkit.getWorld(p.world);
        return new Location[] {
            PlotHelper.getPlotBottomLoc(world, p.id),
            PlotHelper.getPlotTopLoc(world, p.id),
            PlotHelper.getPlotHome(world, p.id)
        };
    }

    /**
     * Get home location
     * @param p
     * @return plot bottom location
     */
    public Location getHomeLocation(Plot p) {
        return PlotHelper.getPlotHome(p.getWorld(), p.id);
    }

    /**
     * Get Bottom Location
     * @param p
     * @return plot bottom location
     */
    public Location getBottomLocation(Plot p){
        World world = Bukkit.getWorld(p.world);
        return PlotHelper.getPlotBottomLoc(world, p.id);
    }

    /**
     * Get Top Location
     * @param p
     * @return plot top location
     */
    public Location getTopLocation(Plot p){
        World world = Bukkit.getWorld(p.world);
        return PlotHelper.getPlotTopLoc(world, p.id);
    }

	/**
	 * Check whether or not a player is in a plot
	 * @param player
	 * @return true if the player is in a plot, false if not-
	 */
	public boolean isInPlot(Player player) {
		return PlayerFunctions.isInPlot(player);
	}
	
	/**
	 * Register a subcommand
	 * @param c
	 */
	public void registerCommand(SubCommand c) {
		MainCommand.subCommands.add(c);
	}
	
	/**
	 * Get the plotMain class
	 * @return PlotMain Class
	 */
	public PlotMain getPlotMain() {
		return this.plotMain;
	}
	
	/**
	 * Get the inserted plugin
	 * @return Plugin.
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private JavaPlugin getPlugin() {
		return this.plugin;
	}

    /**
     * Get the player plot count
     * @param player
     * @return
     */
    public int getPlayerPlotCount(World world, Player player) {
        return PlayerFunctions.getPlayerPlotCount(world, player);
    }

    /**
     * Get a players plots
     * @param player
     * @return a set containing the players plots
     */
    public Set<Plot> getPlayerPlots(World world, Player player) {
        return PlayerFunctions.getPlayerPlots(world, player);
    }

    /**
     * Get the allowed plot count for a player
     * @param player
     * @return the number of allowed plots
     */
    public int getAllowedPlots(Player player) {
        return PlayerFunctions.getAllowedPlots(player);
    }
}
