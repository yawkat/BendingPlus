package com.etriacraft.bendingplus;

import org.bukkit.plugin.java.JavaPlugin;
import com.etriacraft.bendingplus.listeners.GrapplingListener;

public class BendingPlus extends JavaPlugin {

	public final GrapplingListener glistener = new GrapplingListener(this);
	public static BendingPlus plugin;
	
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(glistener, this);
		new CraftingRecipes(this);
		
		CraftingRecipes.registerRecipes();
	}
	
	public void onDisable() {
		
	}
}
