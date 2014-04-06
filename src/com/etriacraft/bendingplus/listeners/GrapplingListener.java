package com.etriacraft.bendingplus.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

import tools.BendingType;
import tools.Tools;

import com.etriacraft.bendingplus.BendingPlus;
import com.etriacraft.bendingplus.APIs.GrapplingHookAPI;
import com.etriacraft.bendingplus.events.PlayerGrappleEvent;

public class GrapplingListener implements Listener {
	
	static BendingPlus plugin;
	
	public GrapplingListener(BendingPlus plugin) {
		this.plugin = plugin;
	}
	
	public HashMap<Integer, Integer> noFallEntities = new HashMap<Integer, Integer>();
	public HashMap<String, Integer> noGrapplePlayers = new HashMap<String, Integer>();
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onGrapple(PlayerGrappleEvent event) {
		if (!Tools.isBender(event.getPlayer().getName(), BendingType.ChiBlocker) && !Tools.isBender(event.getPlayer().getName(), BendingType.Earth)) {
			event.setCancelled(true);
		}
		if (event.isCancelled())
			return;
		
		final Player player = event.getPlayer();
		
		event.getHookItem().setDurability((short) - 10);
		if (noGrapplePlayers.containsKey(player.getName())) {
			return;
		}
		
		Entity e = event.getPulledEntity();
		Location loc = event.getPullLocation();
		
		if (player.equals(e)) { // Pulling themselve sto location
			if (player.getLocation().distance(loc) < 3) // Too close
				pullPlayerSlightly(player, loc);
			else
				pullEntityToLocation(player, loc);
		} else {
			pullEntityToLocation(e, loc);
		}
		
		if (GrapplingHookAPI.addUse(player, event.getHookItem())) 
			GrapplingHookAPI.playGrappleSound(player.getLocation());
		GrapplingHookAPI.addPlayerCooldown(player, 100);
	}
	@EventHandler (priority = EventPriority.HIGHEST) 
	public void fishEvent(PlayerFishEvent event) {
		Player player = event.getPlayer();
		
		if (GrapplingHookAPI.isGrapplingHook(player.getItemInHand())) {
			if (event.getState() == PlayerFishEvent.State.IN_GROUND) {
				Location loc = event.getHook().getLocation();
				for (Entity ent: event.getHook().getNearbyEntities(1.5, 1, 1.5)) {
					if (ent instanceof Item) {
						PlayerGrappleEvent e = new PlayerGrappleEvent(player, ent, player.getLocation());
						plugin.getServer().getPluginManager().callEvent(e);
						return;
					}
				}
				PlayerGrappleEvent e = new PlayerGrappleEvent(player, player, loc);
				plugin.getServer().getPluginManager().callEvent(e);
			}
		} 
		
	}
	
	private void pullPlayerSlightly(Player p, Location loc) {
		if (loc.getY() > p.getLocation().getY()) {
			p.setVelocity(new Vector(0, 0.25, 0));
			return;
		}
		
		Location playerLoc = p.getLocation();
		
		Vector vector = loc.toVector().subtract(playerLoc.toVector());
		p.setVelocity(vector);
	}
	
	private void pullEntityToLocation(final Entity e, Location loc) {
		Location entityLoc = e.getLocation();
		
		entityLoc.setY(entityLoc.getY() + 0.5);
		e.teleport(entityLoc);
		
		double g = -0.08;
		double d = loc.distance(entityLoc);
		double t = d;
		double v_x = (1.0+0.07*t) * (loc.getX()-entityLoc.getX())/t;
		double v_y = (1.0+0.03*t) * (loc.getY()-entityLoc.getY())/t -0.5*g*t;
		double v_z = (1.0+0.07*t) * (loc.getZ()-entityLoc.getZ())/t;

		Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}

}
