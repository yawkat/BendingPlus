package com.etriacraft.bendingplus.APIs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AirGliderAPI {
	
	public static ItemStack createGlider() {
		ItemStack is = new ItemStack(Material.WOOD_PICKAXE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.GOLD + "Air Glider");
		is.setItemMeta(im);
		return is;
	}
	
	public static boolean isAirGlider(ItemStack is) {
		ItemMeta im = is.getItemMeta();
		if (is.getType() == Material.WOOD_PICKAXE && im.getDisplayName() != null && im.getDisplayName().equals(ChatColor.GOLD + "Air Glider")) {
			return true;
		}
		return false;
	}

}
