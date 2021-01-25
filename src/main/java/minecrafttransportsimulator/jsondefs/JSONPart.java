package minecrafttransportsimulator.jsondefs;

import java.util.List;

import minecrafttransportsimulator.baseclasses.Point3d;
import minecrafttransportsimulator.jsondefs.JSONVehicle.VehicleCollisionBox;
import minecrafttransportsimulator.jsondefs.JSONVehicle.VehicleConnection;
import minecrafttransportsimulator.jsondefs.JSONVehicle.VehicleDoor;
import minecrafttransportsimulator.jsondefs.JSONVehicle.VehiclePart;
import minecrafttransportsimulator.jsondefs.JSONVehicle.VehicleRendering;
import minecrafttransportsimulator.packloading.JSONParser.JSONRequired;

public class JSONPart extends AJSONMultiModelProvider<JSONPart.JSONPartGeneral>{
	@JSONRequired(dependentField="type", dependentValues={"engine"}, subField="general")
    public JSONPartEngine engine;
	@JSONRequired(dependentField="type", dependentValues={"ground"}, subField="general")
    public JSONPartGroundDevice ground;
	@JSONRequired(dependentField="type", dependentValues={"propeller"}, subField="general")
    public JSONPartPropeller propeller;
	@JSONRequired(dependentField="type", dependentValues={"seat"}, subField="general")
    public JSONPartSeat seat;
	@JSONRequired(dependentField="type", dependentValues={"gun"}, subField="general")
    public JSONPartGun gun;
	@JSONRequired(dependentField="type", dependentValues={"bullet"}, subField="general")
    public JSONPartBullet bullet;
	@JSONRequired(dependentField="type", dependentValues={"interactable"}, subField="general")
    public JSONPartInteractable interactable;
	@JSONRequired(dependentField="type", dependentValues={"effector"}, subField="general")
    public JSONPartEffector effector;
	@JSONRequired(dependentField="type", dependentValues={"generic"}, subField="general")
    public JSONPartGeneric generic;
    public List<VehiclePart> subParts;
    public List<VehicleCollisionBox> collision;
    public List<VehicleDoor> doors;
    public List<VehicleConnection> connections;
    //TODO make this regular rendering when parts are all converted.
    public VehicleRendering rendering;
    
    @Deprecated
    public PartCustom custom;
    @Deprecated
    public PartWheel wheel;
    @Deprecated
    public PartPontoon pontoon;
    @Deprecated
    public PartSkid skid;
    @Deprecated
    public PartTread tread;

    public class JSONPartGeneral extends AJSONMultiModelProvider<JSONPart.JSONPartGeneral>.General{
    	@JSONRequired
    	public String type;
    	public String customType;
    	public boolean disableMirroring;
    	public boolean useVehicleTexture;
    }
    
    public class JSONPartEngine{
		@JSONDescription("")
    	public boolean isAutomatic;
		
		//FIXME add description
    	public boolean isSteamPowered;
		
		@JSONDescription("")
    	public boolean flamesOnStartup;
		
		@JSONDescription("")
    	public boolean isCrankingNotPitched;
		
		@JSONDescription("")
    	public int starterPower;
		
		@JSONDescription("")
    	public int shiftSpeed;
		
		@JSONDescription("")
    	public int clutchTime;
		
		@JSONDescription("")
    	public int revResistance;
		
		@JSONDescription("")
    	public int maxRPM;
		
		@JSONDescription("")
    	public float fuelConsumption;
		
		@JSONDescription("")
    	public float jetPowerFactor;
		
		@JSONDescription("")
    	public float bypassRatio;
		
		@JSONDescription("")
    	public float propellerRatio;
		
    	@JSONRequired
    	@JSONDescription("")
		public float[] gearRatios;
		
		@JSONDescription("")
		public int[] upShiftRPM;
		
		@JSONDescription("")
		public int[] downShiftRPM;
		
		@JSONRequired
    	@JSONDescription("")
		public String fuelType;
		
		@JSONDescription("")
		public float superchargerFuelConsumption;
		
		@JSONDescription("")
		public float superchargerEfficiency;
		
		@JSONDescription("")
		public EngineSound customSoundset[];
		
		public class EngineSound{
			@JSONRequired
			@JSONDescription("")
			public String soundName;
			
			@JSONDescription("")
			public float pitchIdle;
			
			@JSONDescription("")
			public float pitchMax;
			
			@JSONDescription("")
			public float pitchLength;
			
			@JSONDescription("")
			public float volumeIdle;
			
			@JSONDescription("")
			public float volumeMax;
			
			@JSONDescription("")
			public float volumeLength;
			
			@JSONDescription("")
			public int pitchCenter;
			
			@JSONDescription("")
			public int volumeCenter;
			
			@JSONDescription("")
			public boolean pitchAdvanced;
			
			@JSONDescription("")
			public boolean volumeAdvanced;
		}
    }
    
    public class JSONPartGroundDevice{
    	public boolean isWheel;
    	public boolean isTread;
    	public boolean canFloat;
    	@Deprecated
		public boolean canGoFlat;
    	public float width;
    	public float height;
    	public float flatHeight;
    	public float motiveFriction;
        public float lateralFriction;
        public float extraCollisionBoxOffset;
        public float spacing;
    }
    
    public class JSONPartPropeller{
    	public boolean isDynamicPitch;
    	public boolean isRotor;
    	public short pitch;
    	public int diameter;
    	public int startingHealth;
    }
    
    public class JSONPartSeat{
    	public boolean standing;
    	public float widthScale;
    	public float heightScale;
    }
    
    public class JSONPartGun{
    	public boolean autoReload;
    	public boolean isSemiAuto;
    	public boolean yawIsInternal;
    	public boolean pitchIsInternal;
    	public int capacity;
    	public int fireDelay;
    	public int reloadTime;
    	public int windupTime;
    	public int muzzleVelocity;
    	public List<Point3d> muzzlePositions;
    	public float minPitch;
    	public float maxPitch;
    	public float minYaw;
    	public float maxYaw;
    	public float diameter;
    	public float length;
    	public boolean fireSolo;
    	public boolean resetPosition;
    	public float defaultPitch;
    	public float defaultYaw;
        public List<JSONParticleObject> particleObjects;
        
        public boolean handHeld;
        @JSONRequired(dependentField="handHeld", dependentValues={"true"})
    	public Point3d handHeldNormalOffset;
        @JSONRequired(dependentField="handHeld", dependentValues={"true"})
    	public Point3d handHeldAimedOffset;
    }
    
    public class JSONPartBullet{
    	@Deprecated
    	public String type;
    	@JSONRequired
    	public List<String> types;
    	public int quantity;
    	public float diameter;
    	public float blastStrength;
    	public float armorPenetration;
    	public int burnTime;
    	public int accelerationTime;
    	public int maxVelocity;
    	public float maxOffAxis;
    	public float turnFactor;
    	public float angleOfAttack;
    	public float proximityFuze;
    	public int airBurstDelay;
    	public List<JSONParticleObject> particleObjects;
    }
    
    public class JSONPartInteractable{
    	@JSONRequired
    	public String interactionType;
    	public boolean feedsVehicles;
    	public int inventoryUnits;
    }
    
    public class JSONPartEffector{
    	@JSONRequired
    	public String type;
    	public int blocksWide;
    }
    
    public class JSONPartGeneric{
    	public float width;
    	public float height;
    }
    
    @Deprecated
    public class PartCustom{
    	public float width;
    	public float height;
    }
    @Deprecated
    public class PartWheel{
    	public float diameter;
        public float motiveFriction;
        public float lateralFriction;
    }
    @Deprecated
    public class PartSkid{
    	public float width;
    	public float lateralFriction;
    }
    @Deprecated
    public class PartPontoon{
    	public float width;
    	public float lateralFriction;
        public float extraCollisionBoxOffset;
    }
    @Deprecated
    public class PartTread{
    	public float width;
    	public float motiveFriction;
        public float lateralFriction;
        public float extraCollisionBoxOffset;
        public float spacing;
    }
}
