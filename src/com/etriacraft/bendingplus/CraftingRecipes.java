package com.etriacraft.bendingplus;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;

import com.etriacraft.bendingplus.APIs.GrapplingHookAPI;
import com.etriacraft.bendingplus.APIs.AirGliderAPI;

public class CraftingRecipes {
	
	static BendingPlus plugin;
	
	public CraftingRecipes(BendingPlus plugin) {
		CraftingRecipes.plugin = plugin;
	}

	public static void registerRecipes() {
		// Grappling Hooks
		plugin.getServer().addRecipe(ironHookRecipe);
		plugin.getServer().addRecipe(goldHookRecipe);
		plugin.getServer().addRecipe(airGliderRecipe);
	}
	
	static ShapedRecipe ironHookRecipe = new ShapedRecipe(GrapplingHookAPI.createHook(25))
		.shape(" **", " &*", "   ")
		.setIngredient('*', Material.IRON_INGOT)
		.setIngredient('&', Material.FISHING_ROD);
	
	static ShapedRecipe goldHookRecipe = new ShapedRecipe(GrapplingHookAPI.createHook(50))
		.shape (" **", " &*", "   ")
		.setIngredient('*', Material.GOLD_INGOT)
		.setIngredient('&', Material.FISHING_ROD);
	
	static ShapedRecipe airGliderRecipe = new ShapedRecipe (AirGliderAPI.createGlider())
		.shape("wwp", " sw", "s w")
		.setIngredient('w', Material.WOOL)
		.setIngredient('p', Material.WOOD_PICKAXE)
		.setIngredient('s', Material.STICK);
}
