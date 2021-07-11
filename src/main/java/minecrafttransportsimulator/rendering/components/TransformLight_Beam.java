package minecrafttransportsimulator.rendering.components;

import java.awt.Color;

import minecrafttransportsimulator.entities.components.AEntityC_Definable;
import minecrafttransportsimulator.jsondefs.JSONLight;
import minecrafttransportsimulator.mcinterface.InterfaceRender;
import minecrafttransportsimulator.systems.ConfigSystem;

/**This class represents a light object of a model.  Inputs are the name of the name model
* and the name of the light.
*
* @author don_bruce
*/
public class TransformLight_Beam<AnimationEntity extends AEntityC_Definable<?>> extends ATransformLight<AnimationEntity>{
	private final Color color;
	
	public TransformLight_Beam(JSONLight definition){
		super(definition);
		color = Color.decode(definition.color);
	}

	@Override
	public double applyTransform(AnimationEntity entity, boolean blendingEnabled, float partialTicks, double offset){
		super.applyTransform(entity, blendingEnabled, partialTicks, offset);
		float beamBrightness = Math.min((1 - entity.world.getLightBrightness(entity.position, false))*lightLevel, 1);
		if(beamBrightness > 0){
			InterfaceRender.bindTexture("mts:textures/rendering/lightbeam.png");
			if(ConfigSystem.configObject.clientRendering.beamsBright.value){
				InterfaceRender.setLightingState(false);
				InterfaceRender.setBlendBright(true);
			}
			InterfaceRender.setColorState(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F, beamBrightness);
		}
		return 0;
	}
	
	@Override
	public void doPostRenderLogic(AnimationEntity entity, boolean blendingEnabled, float partialTicks){
		if(lightLevel > 0){
			if(ConfigSystem.configObject.clientRendering.beamsBright.value){
				InterfaceRender.setBlendBright(false);
				InterfaceRender.setLightingState(true);
			}
			InterfaceRender.setColorState(1.0F, 1.0F, 1.0F, 1.0F);
			InterfaceRender.recallTexture();
		}
	}
}
