package com.jedk1.jedcore.ability.earthbending;

import com.jedk1.jedcore.JedCore;
import com.jedk1.jedcore.configuration.JedCoreConfig;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.MetalAbility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Material.*;

public class MagnetShield extends MetalAbility implements AddonAbility {

	private static final List<Material> METAL_LIST = new ArrayList<Material>() {{
		addAll(Arrays.asList(IRON_INGOT, IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS,
				IRON_BLOCK, IRON_AXE, IRON_PICKAXE, IRON_SWORD, IRON_HOE, IRON_SHOVEL, IRON_DOOR, IRON_NUGGET, IRON_BARS,
				IRON_HORSE_ARMOR, IRON_TRAPDOOR, HEAVY_WEIGHTED_PRESSURE_PLATE, GOLD_INGOT, GOLDEN_HELMET, GOLDEN_CHESTPLATE,
				GOLDEN_LEGGINGS, GOLDEN_BOOTS, GOLD_BLOCK, GOLD_NUGGET, GOLDEN_AXE, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD,
				GOLDEN_HOE, GOLDEN_HORSE_ARMOR, LIGHT_WEIGHTED_PRESSURE_PLATE, CLOCK, COMPASS));
		int serverVersion = GeneralMethods.getMCVersion();
		if (serverVersion >= 1170) {
			add(Material.getMaterial("RAW_GOLD_BLOCK"));
			add(Material.getMaterial("RAW_IRON_BLOCK"));
			add(Material.getMaterial("RAW_IRON"));
			add(Material.getMaterial("RAW_GOLD"));
		}
	}};

	public MagnetShield(Player player) {
		super(player);
		
		if (!bPlayer.canBendIgnoreCooldowns(this) || !bPlayer.canMetalbend()) {
			return;
		}
		
		if (hasAbility(player, MagnetShield.class)) {
			getAbility(player, MagnetShield.class).remove();
			return;
		}

		start();
	}

	@Override
	public void progress() {
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		if (!bPlayer.canBendIgnoreBindsCooldowns(this)) {
			remove();
			return;
		}
		if (!player.isSneaking()) {
			remove();
			return;
		}

		for (Entity e : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 4)) {
			if (e instanceof Item) {
				Item i = (Item) e;

				if (METAL_LIST.contains(i.getItemStack().getType())) {
					Vector direction = GeneralMethods.getDirection(player.getLocation(), i.getLocation()).multiply(0.1);
					i.setVelocity(direction);
				}
			}

			else if (e instanceof FallingBlock) {
				FallingBlock fb = (FallingBlock) e;

				if (METAL_LIST.contains(fb.getBlockData().getMaterial())) {
					Vector direction = GeneralMethods.getDirection(player.getLocation(), fb.getLocation()).multiply(0.1);
					fb.setVelocity(direction);
					fb.setDropItem(false);
				}
			}
		}
	}
	
	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return player.getLocation();
	}

	@Override
	public String getName() {
		return "MagnetShield";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public String getAuthor() {
		return JedCore.dev;
	}

	@Override
	public String getVersion() {
		return JedCore.version;
	}

	@Override
	public String getDescription() {
		ConfigurationSection config = JedCoreConfig.getConfig(this.player);
		return "* JedCore Addon *\n" + config.getString("Abilities.Earth.MagnetShield.Description");
	}

	@Override
	public void load() {}

	@Override
	public void stop() {}

	@Override
	public boolean isEnabled() {
		ConfigurationSection config = JedCoreConfig.getConfig(this.player);
		return config.getBoolean("Abilities.Earth.MagnetShield.Enabled");
	}

	public static List<Material> getMetal() {
		return METAL_LIST;
	}
}
