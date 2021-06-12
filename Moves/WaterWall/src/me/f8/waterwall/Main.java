package me.f8.waterwall;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.WaterAbility;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.TempBlock;

public class Main extends WaterAbility implements AddonAbility{
	
	private int speed;
	private int range;
	private long cooldown;
	private double damage;	
	private Location location;
	private Location origin;
	private Vector direction;
	private int H;
	private Boolean stop = false;
	
	public Main(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
		
		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		
		
		setFields();
		
		
		start();
		
		
		bPlayer.addCooldown(this);
	}
		
	private void setFields() {
		
		this.origin = player.getLocation().clone().add(0, 1, 0);
		
		
		this.location = origin.clone();
		
		
		this.direction = player.getLocation().getDirection();
		
		this.speed = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Water.WaterWall.Speed");
		this.range = ProjectKorra.plugin.getConfig().getInt("ExtraAbilities.Water.WaterWall.Range");
		this.cooldown = ProjectKorra.plugin.getConfig().getLong("ExtraAbilities.Water.WaterWall.Cooldown");
		this.damage = ProjectKorra.plugin.getConfig().getDouble("ExtraAbilities.Water.WaterWall.Damage");
	}		


	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return cooldown;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "WaterWall";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void progress() {
		// TODO Auto-generated method stub
		
		if (player.isDead() || !player.isOnline()) {
			remove();
			return;
		}
		
		
		if (origin.distance(location) > range) {
			remove();
			return;
		}
		
		while (stop = false) {
			location.add(direction.multiply(speed));
		}
		
		
		if ((location.getBlock().getType() == Material.WATER )){
			Location tempLoc = location.clone();
				while (H<10) {
					Block x = tempLoc.clone().add(0,H,0).getBlock();
					new TempBlock (x , Material.WATER, GeneralMethods.getWaterData(0));
					H++;
				}
				
				
				}
			if (H==10) {
				stop = true;
				H = 0;

			}

		
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 1)) {
			
			if ((entity instanceof LivingEntity) && entity.getUniqueId() != player.getUniqueId()) {
				DamageHandler.damageEntity(entity, damage, this);
				remove();
				return;
			}
		}
	}
		

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "F8";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	@Override
	public void load() {
		
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilityListener(), ProjectKorra.plugin);
		FileConfiguration config = ProjectKorra.plugin.getConfig();
		config.addDefault("ExtraAbilities.Water.WaterWall.Range", Integer.valueOf(20));
		config.addDefault("ExtraAbilities.Water.WaterWall.Cooldown", Integer.valueOf(1000));
		config.addDefault("ExtraAbilities.Water.WaterWall.Damage", Double.valueOf(2.0D));
		config.addDefault("ExtraAbilities.Water.WaterWall.Speed", Double.valueOf(1));
		config.options().copyDefaults(true);
		ProjectKorra.plugin.saveConfig();
		ProjectKorra.plugin.getLogger().info(String.format("%s %s, developed by %s, has been loaded.", getName(), getVersion(), getAuthor()));
		
		
		ProjectKorra.log.info("Successfully enabled " + getName() + " by " + getAuthor());
	}
		// TODO Auto-generated method stub
		
	

	@Override
	public void stop() {
		
		ProjectKorra.log.info("Successfully disabled " + getName() + " by " + getAuthor());
		
		
		super.remove();
	}
		// TODO Auto-generated method stub
		
	}


	