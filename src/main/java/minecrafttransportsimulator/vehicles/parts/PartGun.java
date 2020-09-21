package minecrafttransportsimulator.vehicles.parts;

import mcinterface.InterfaceAudio;
import mcinterface.InterfaceNetwork;
import mcinterface.InterfaceRender;
import mcinterface.WrapperInventory;
import mcinterface.WrapperNBT;
import mcinterface.WrapperPlayer;
import minecrafttransportsimulator.baseclasses.Damage;
import minecrafttransportsimulator.baseclasses.Point3d;
import minecrafttransportsimulator.dataclasses.MTSRegistry;
import minecrafttransportsimulator.items.components.AItemBase;
import minecrafttransportsimulator.items.instances.ItemPart;
import minecrafttransportsimulator.jsondefs.JSONPart;
import minecrafttransportsimulator.jsondefs.JSONVehicle.VehiclePart;
import minecrafttransportsimulator.packets.instances.PacketVehiclePartGun;
import minecrafttransportsimulator.rendering.components.IVehiclePartFXProvider;
import minecrafttransportsimulator.rendering.instances.ParticleBullet;
import minecrafttransportsimulator.sound.SoundInstance;
import minecrafttransportsimulator.vehicles.main.EntityVehicleF_Physics;

public class PartGun extends APart implements IVehiclePartFXProvider{	
	//Stored variables used to determine bullet firing behavior.
	public int shotsFired;
	public int bulletsLeft;
	public Point3d currentOrientation;
	public Point3d prevOrientation;
	public double vehiclePitchContribution;
	private JSONPart loadedBulletDefinition;
	
	//These variables are used during firing and will be reset on entity loading.
	public boolean firing;
	public int temp;
	public int cooldownTimeRemaining;
	public int reloadTimeRemaining;
	private WrapperPlayer lastController;
	private byte gunNumber;
	private long lastTimeFired;
	private long timeToFire;
	
	private final double anglePerTickSpeed;
		
	public PartGun(EntityVehicleF_Physics vehicle, VehiclePart packVehicleDef, JSONPart definition, WrapperNBT data, APart parentPart){
		super(vehicle, packVehicleDef, definition, data, parentPart);
		this.shotsFired = data.getInteger("shotsFired");
		this.bulletsLeft = data.getInteger("bulletsLeft");
		this.currentOrientation = data.getPoint3d("currentOrientation");
		this.prevOrientation = currentOrientation.copy();
		String loadedBulletPack = data.getString("loadedBulletPack");
		String loadedBulletName = data.getString("loadedBulletName");
		if(!loadedBulletPack.isEmpty()){
			this.loadedBulletDefinition = (JSONPart) MTSRegistry.packItemMap.get(loadedBulletPack).get(loadedBulletName).definition;
		}
		//If we didn't load the bullet due to pack changes, set the current bullet count to 0.
		//This prevents pack changes from locking guns.
		if(loadedBulletDefinition == null){
			bulletsLeft = 0;
		}
		this.anglePerTickSpeed = (50/definition.gun.diameter + 1/definition.gun.length);
		
		//Get the gun number based on how many guns the vehicle has.
		gunNumber = 1;
		for(APart part : vehicle.parts){
			if(part instanceof PartGun){
				++gunNumber;
			}
		}
		for(APart part : vehicle.partsFromNBT){
			if(part instanceof PartGun){
				++gunNumber;
			}
		}
	}
	
	@Override
	public boolean interact(WrapperPlayer player){
		//Check to see if we have any bullets in our hands.
		//If so, try to re-load this gun with them.
		AItemBase heldItem = player.getHeldItem();
		if(heldItem instanceof ItemPart){
			if(tryToReload((ItemPart) heldItem)){
				player.removeItem(heldItem, null);
			}
		}
		return true;
    }
	
	@Override
	public void attack(Damage damage){
		//Add shots fired when damaged.  If the damage is explosive, add more damage and jam the gun.
		if(!damage.isExplosion){
			shotsFired += (int) (damage.amount*2F);
			//If the source is flammable, add temp to the gun.
			if(damage.isFire){
				temp += damage.amount;
			}
		}else{
			shotsFired += (int) (damage.amount*10F);
			reloadTimeRemaining = definition.gun.reloadTime;
		}
	}
	
	@Override
	public void update(){
		super.update();
		prevOrientation.setTo(currentOrientation);
		
		//Get the current player controller for this gun.
		//We check for child parts first, parent parts second, and finally vehicle controller seats.
		WrapperPlayer playerController = getCurrentController();
		
		//Adjust aim to face direction controller is facing.
		//Aim speed depends on gun size, with smaller and shorter guns moving quicker.
		//Pitch and yaw only depend on where the player is looking, and where the gun is pointed.
		//This allows for guns to be mounted anywhere on a vehicle and at any angle.
		//If the controller is null, and we are firing, set us to not do so.
		//We ain't coding sentrys here.... yet.
		if(playerController != null){
			//Aadjust yaw.  We need to normalize the delta here as yaw can go past -180 to 180.
			double deltaYaw = playerController.getYaw() - (currentOrientation.y + totalRotation.y + vehicle.angles.y);
			while(deltaYaw > 180){
				deltaYaw -= 360;
			}
			while(deltaYaw < -180){
				deltaYaw += 360;
			}
			//System.out.format("Current:%f Player:%f Delta:%f Total:%f Min:%f, max:%f\n", (currentOrientation.y + totalRotation.y + vehicle.angles.y), playerController.getYaw(), deltaYaw, totalRotation.y, definition.gun.minYaw, definition.gun.maxYaw);
			if(deltaYaw < 0){
				currentOrientation.y += Math.max(-anglePerTickSpeed, deltaYaw); 
			}else if(deltaYaw > 0){
				currentOrientation.y += Math.min(anglePerTickSpeed, deltaYaw);
			}
			//Apply yaw clamps.
			//If yaw is from -180 to 180, we are a gun that can spin around on its mount.
			//We need to do special logic for this type of gun.
			if(definition.gun.minYaw == -180  && definition.gun.maxYaw == 180){
				if(currentOrientation.y > 180 ){
					currentOrientation.y -= 360;
					prevOrientation.y -= 360;
				}else if(currentOrientation.y < -180){
					currentOrientation.y += 360;
					prevOrientation.y += 360;
				}
			}else{
				if(currentOrientation.y > definition.gun.maxYaw){
					currentOrientation.y = definition.gun.maxYaw;
				}
				if(currentOrientation.y < definition.gun.minYaw){
					currentOrientation.y = definition.gun.minYaw;
				}
			}
			
			//Adjust pitch.
			//For pitch, we need to find the relative angle of the player to the vehicle's 0-pitch plane.
			//When the player rotates their head, they don't do so relative to the pitch of the vehicle the gun is on, 
			//so a yaw change can result in a pitch change.
			vehiclePitchContribution = vehicle.angles.x*Math.cos(Math.toRadians(vehicle.angles.y - currentOrientation.y));
			double deltaPitch = playerController.getPitch() - (currentOrientation.x + totalRotation.x + vehiclePitchContribution);
			if(deltaPitch < 0){
				currentOrientation.x += Math.max(-anglePerTickSpeed, deltaPitch); 
			}else if(deltaPitch > 0){
				currentOrientation.x += Math.min(anglePerTickSpeed, deltaPitch);
			}
			//Apply pitch clamps.
			if(currentOrientation.x < -definition.gun.maxPitch){
				currentOrientation.x = -definition.gun.maxPitch;
			}
			if(currentOrientation.x > -definition.gun.minPitch){
				currentOrientation.x = -definition.gun.minPitch;
			}
			
		}else{
			firing = false;
		}
		
		//Decrement cooldown time blocking gun from firing, if we have any.
		if(cooldownTimeRemaining > 0){
			--cooldownTimeRemaining;
		}
		
		//If we are reloading, decrement the reloading timer.
		//This timer is also used for jams, which are essentially reloads without getting more bullets.
		//In either case, we can't fire until the reload is done.
		if(reloadTimeRemaining > 0){
			--reloadTimeRemaining;
			return;
		}
		
		//If this gun is being told to fire, and we have bullets, and are not reloading, fire.
		//Don't spawn bullets on the server, as they will cause lots of lag and network traffic.
		//Instead, spawn them on the clients, and then send back hit data to the server.
		//This is backwards from what usually happens, and can possibly be hacked, but it's FAR
		//easier on MC to leave clients to handle lots of bullets than the server and network systems.
		//We still need to run the gun code on the server, however, as we need to mess with inventory.
		if(firing && bulletsLeft > 0 && reloadTimeRemaining == 0 && cooldownTimeRemaining == 0){
			//We would fire a bullet here, but that's for the SFXSystem to handle, not the update loop.
			//Make sure to add-on an offset to our firing point to allow for multi-gun units.
			long millisecondCamOffset = (long) (definition.gun.fireDelay*(1000D/20D)*(gunNumber - 1D)/vehicle.totalGuns);
			cooldownTimeRemaining = definition.gun.fireDelay;
			timeToFire = System.currentTimeMillis() + millisecondCamOffset;
			lastController = playerController;
			if(!vehicle.world.isClient()){
				//Only remove bullets from the server.  We remove them from the client when they spawn.
				--bulletsLeft;
			}
		}
		
		//If we are out of bullets, and aren't cooling down from firing, re-load ourselves from any vehicle inventories.
		//This only works if the gun is set to auto-reload.
		//While the reload method checks for reload time, we check here to save on code processing.
		//No sense in looking for bullets if we can't load them anyways.
		if(bulletsLeft == 0 && definition.gun.autoReload && reloadTimeRemaining == 0){
			//Iterate through all the inventory slots in crates to try to find matching ammo.
			for(APart part : vehicle.parts){
				if(part instanceof PartInteractable){
					WrapperInventory inventory = ((PartInteractable) part).inventory;
					if(inventory != null && part.definition.interactable.feedsVehicles){
						for(byte i=0; i<inventory.getSize(); ++i){
							AItemBase item = inventory.getItemInSlot(i);
							if(item instanceof ItemPart){
								if(tryToReload((ItemPart) item)){
									//Bullet is right type, and we can fit it.  Remove from crate and add to the gun.
									//Return here to ensure we don't set the loadedBullet to blank since we found bullets.
									inventory.decrement(i);
									return;
								}
							}
						}
					}
				}
			}
			//If we are on the server, we need to set the bullet as null to allow for new bullets to be loaded.
			//If we are on the client, we need to leave this here, as the client might still be processing bullet firing.
			//IF we set it null during that time, the bullet would be fired and not know what it was.
			if(!vehicle.world.isClient()){
				loadedBulletDefinition = null;
			}
		}
	}
	
	/**
	 * Attempts to reload the gun with the passed-in part.  Returns true if the part is a bullet
	 * and was loaded, false if not.  Responsible for packet callbacks and playing sounds.
	 */
	public boolean tryToReload(ItemPart part){
		if(part.definition.bullet != null){
			if(reloadTimeRemaining == 0){
				//Only fill bullets if we match the bullet already in the gun, or if our diameter matches.
				if((loadedBulletDefinition == null && part.definition.bullet.diameter == definition.gun.diameter) || loadedBulletDefinition.equals(part.definition)){
					//Make sure we don't over-fill the gun.
					if(part.definition.bullet.quantity + bulletsLeft <= definition.gun.capacity){
						loadedBulletDefinition = part.definition;
						bulletsLeft += part.definition.bullet.quantity;
						reloadTimeRemaining = definition.gun.reloadTime;
						if(vehicle.world.isClient()){
							InterfaceAudio.playQuickSound(new SoundInstance(this, definition.packID + ":" + definition.systemName + "_reloading"));
						}else{
							InterfaceNetwork.sendToClientsTracking(new PacketVehiclePartGun(this, loadedBulletDefinition.packID, loadedBulletDefinition.systemName), vehicle);
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Helper method to get the current controller of this gun.
	 */
	public WrapperPlayer getCurrentController(){
		//Check our parent part, if we have one.
		if(parentPart instanceof PartSeat){
			return (WrapperPlayer) vehicle.locationRiderMap.get(parentPart.placementOffset);
		}
		
		//Check any child parts.
		for(APart childPart : childParts){
			if(childPart instanceof PartSeat){
				return (WrapperPlayer) vehicle.locationRiderMap.get(childPart.placementOffset);
			}
		}
		
		//Not parent or child.  Get main vehicle controller.
		for(APart vehiclePart : vehicle.parts){
			if(vehiclePart instanceof PartSeat){
				if(vehiclePart.vehicleDefinition.isController){
					return (WrapperPlayer) vehicle.locationRiderMap.get(vehiclePart.placementOffset);
				}
			}
		}
		
		//No controller found.
		return null;
	}
	
	@Override
	public WrapperNBT getData(){
		WrapperNBT data = super.getData();
		data.setInteger("shotsFired", shotsFired);
		data.setInteger("bulletsLeft", bulletsLeft);
		data.setPoint3d("currentOrientation", currentOrientation);
		if(loadedBulletDefinition != null){
			data.setString("loadedBulletPack", loadedBulletDefinition.packID);
			data.setString("loadedBulletName", loadedBulletDefinition.systemName);
		}
		return data;
	}
	
	@Override
	public float getWidth(){
		return 0.75F;
	}

	@Override
	public float getHeight(){
		return 0.75F;
	}

	@Override
	public Point3d getActionRotation(float partialTicks){
		Point3d rotation = currentOrientation.copy().subtract(prevOrientation).multiply((double)partialTicks).add(currentOrientation);
		//Don't return pitch if we are a turret.
		if(definition.gun.isTurret){
			rotation.x = 0;
		}
		return rotation;
	}
		
	@Override
	public void spawnParticles(){
		if(timeToFire != lastTimeFired && System.currentTimeMillis() >= timeToFire && bulletsLeft > 0){
			//Fire a bullet by spawning it with the appropriate muzzle velocity and angle.
			//Angle is based on the current gun orientation, plus a slight fudge-factor.
			//This is based on the barrel length and shell size.
			Point3d gunFactoredAngles = currentOrientation.copy().add((Math.random() - 0.5F)*(10*definition.gun.diameter/(definition.gun.length*1000)), (Math.random() - 0.5F)*(10*definition.gun.diameter/(definition.gun.length*1000)), 0D);
			gunFactoredAngles.add(totalRotation).add(vehiclePitchContribution, vehicle.angles.y, 0D);
			
			//Set initial velocity to the vehicle's velocity, plus the gun muzzle velocity at the specified orientation.
			Point3d bulletVelocity = vehicle.motion.copy().multiply(vehicle.SPEED_FACTOR).add(new Point3d(0D, 0D, definition.gun.muzzleVelocity/20D/10D).rotateFine(gunFactoredAngles));
			
			//Get the bullet's initial position.  This is based off the gun orientation and barrel length.
			Point3d bulletPosition = new Point3d(0D, 0D, definition.gun.length).rotateFine(gunFactoredAngles).add(worldPos);
	        
			//Add the bullet as a particle.
			InterfaceRender.spawnParticle(new ParticleBullet(vehicle.world, bulletPosition, bulletVelocity, loadedBulletDefinition, lastController, vehicle));
			InterfaceAudio.playQuickSound(new SoundInstance(this, definition.packID + ":" + definition.systemName + "_firing"));
			lastTimeFired = timeToFire;
			
			//Remove a bullet from the count.
			--bulletsLeft;
		}
	}
}