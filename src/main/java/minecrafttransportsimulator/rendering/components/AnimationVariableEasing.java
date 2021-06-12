package minecrafttransportsimulator.rendering.components;

import minecrafttransportsimulator.mcinterface.InterfaceCore;
import minecrafttransportsimulator.jsondefs.JSONAnimationDefinition;


/**Class designed for calculating the easing equations and returning the interpolated values
 * This is used for interpolating animation values with non-linear equations
 *
 * @author TurboDefender
 */
public class AnimationVariableEasing {

	public double getEasingValue(JSONAnimationDefinition animation, long timeMoved, boolean isReverse) {
		
		double time = timeMoved/(double)(animation.duration*50);
		
		//Check if the animation is playing in reverse
		if (isReverse) {
			//Check if reverseEasing is not omitted
			if (animation.reverseEasing != null) {
				switch(animation.reverseEasing) {
					case LINEAR: {
						return linear(animation, timeMoved);
					}
					
					case EASEINQUAD: {
						return easeInQuad(animation, timeMoved);
					}
					
					case EASEOUTQUAD: {
						return easeOutQuad(animation, timeMoved);
					}
					
					case EASEINOUTQUAD: {
						return easeInOutQuad(animation, timeMoved);
					}
					
					case EASEINCUBIC: {
						return easeInCubic(animation, timeMoved);
					}
					
					case EASEOUTCUBIC: {
						return easeOutCubic(animation, timeMoved);
					}
					
					case EASEINOUTCUBIC: {
						return easeInOutCubic(animation, timeMoved);
					}
					
					case EASEINQUART: {
						return easeInQuart(animation, timeMoved);
					}
					
					case EASEOUTQUART: {
						return easeOutQuart(animation, timeMoved);
					}
					
					case EASEINOUTQUART: {
						return easeInOutQuart(animation, timeMoved);
					}
					
					case EASEINQUINT: {
						return easeInQuint(animation, timeMoved);
					}
					
					case EASEOUTQUINT: {
						return easeOutQuint(animation, timeMoved);
					}
					
					case EASEINOUTQUINT: {
						return easeInOutQuint(animation, timeMoved);
					}
					
					case EASEINCIRC: {
						return easeInCirc(animation, timeMoved);
					}
					
					case EASEOUTCIRC: {
						return easeOutCirc(animation, timeMoved);
					}
					
					case EASEINOUTCIRC: {
						return easeInOutCirc(animation, timeMoved);
					}
					
					case EASEINBACK: {
						return easeInBack(animation, timeMoved);
					}
					
					case EASEOUTBACK: {
						return easeOutBack(animation, timeMoved);
					}
					
					case EASEINOUTBACK: {
						return easeInOutBack(animation, timeMoved);
					}
					
					case EASEINELASTIC: {
						return easeInElastic(animation, timeMoved);
					}
					
					case EASEOUTELASTIC: {
						return easeOutElastic(animation, timeMoved);
					}
					
					case EASEINOUTELASTIC: {
						return easeInOutElastic(animation, timeMoved);
					}
					
					case EASEINBOUNCE: {
						return easeInBounce(animation, time);
					}
					
					case EASEOUTBOUNCE: {
						return easeOutBounce(animation, time);
					}
					
					case EASEINOUTBOUNCE: {
						return easeInOutBounce(animation, time);
					}
					
					
					default: {
						InterfaceCore.logError("Easing type " + animation.reverseEasing + " does not exist. Defaulting to linea.");
						return linear(animation, timeMoved);
					}
				}
			//If it is, then check if forwardsEasing isn't omitted
			//We can use it's value to apply an easing type to the reverse animation
			} else if (animation.forwardsEasing != null) {
				switch(animation.forwardsEasing) {
					case LINEAR: {
						return linear(animation, timeMoved);
					}
					
					case EASEINQUAD: {
						return easeInQuad(animation, timeMoved);
					}
					
					case EASEOUTQUAD: {
						return easeOutQuad(animation, timeMoved);
					}
					
					case EASEINOUTQUAD: {
						return easeInOutQuad(animation, timeMoved);
					}
					
					case EASEINCUBIC: {
						return easeInCubic(animation, timeMoved);
					}
					
					case EASEOUTCUBIC: {
						return easeOutCubic(animation, timeMoved);
					}
					
					case EASEINOUTCUBIC: {
						return easeInOutCubic(animation, timeMoved);
					}
					
					case EASEINQUART: {
						return easeInQuart(animation, timeMoved);
					}
					
					case EASEOUTQUART: {
						return easeOutQuart(animation, timeMoved);
					}
					
					case EASEINOUTQUART: {
						return easeInOutQuart(animation, timeMoved);
					}
					
					case EASEINQUINT: {
						return easeInQuint(animation, timeMoved);
					}
					
					case EASEOUTQUINT: {
						return easeOutQuint(animation, timeMoved);
					}
					
					case EASEINOUTQUINT: {
						return easeInOutQuint(animation, timeMoved);
					}
					
					case EASEINCIRC: {
						return easeInCirc(animation, timeMoved);
					}
					
					case EASEOUTCIRC: {
						return easeOutCirc(animation, timeMoved);
					}
					
					case EASEINOUTCIRC: {
						return easeInOutCirc(animation, timeMoved);
					}
					
					case EASEINBACK: {
						return easeInBack(animation, timeMoved);
					}
					
					case EASEOUTBACK: {
						return easeOutBack(animation, timeMoved);
					}
					
					case EASEINOUTBACK: {
						return easeInOutBack(animation, timeMoved);
					}
					
					case EASEINELASTIC: {
						return easeInElastic(animation, timeMoved);
					}
					
					case EASEOUTELASTIC: {
						return easeOutElastic(animation, timeMoved);
					}
					
					case EASEINOUTELASTIC: {
						return easeInOutElastic(animation, timeMoved);
					}
					
					case EASEINBOUNCE: {
						return easeInBounce(animation, time);
					}
					
					case EASEOUTBOUNCE: {
						return easeOutBounce(animation, time);
					}
					
					case EASEINOUTBOUNCE: {
						return easeInOutBounce(animation, time);
					}
					
					default: {
						InterfaceCore.logError("Easing type " + animation.forwardsEasing + " does not exist. Defaulting to linear.");
						return linear(animation, timeMoved);
					}
				}
			//If both are omitted, then apply linear easing
			} else {
				return linear(animation, timeMoved);
			}
		//If animation is playing forwards
		} else {
			//Check if forwardsEasing isn't omitted
			if (animation.forwardsEasing != null) {
				switch(animation.forwardsEasing) {
					case LINEAR: {
						return linear(animation, timeMoved);
					}
					
					case EASEINQUAD: {
						return easeInQuad(animation, timeMoved);
					}
					
					case EASEOUTQUAD: {
						return easeOutQuad(animation, timeMoved);
					}
					
					case EASEINOUTQUAD: {
						return easeInOutQuad(animation, timeMoved);
					}
					
					case EASEINCUBIC: {
						return easeInCubic(animation, timeMoved);
					}
					
					case EASEOUTCUBIC: {
						return easeOutCubic(animation, timeMoved);
					}
					
					case EASEINOUTCUBIC: {
						return easeInOutCubic(animation, timeMoved);
					}
					
					case EASEINQUART: {
						return easeInQuart(animation, timeMoved);
					}
					
					case EASEOUTQUART: {
						return easeOutQuart(animation, timeMoved);
					}
					
					case EASEINOUTQUART: {
						return easeInOutQuart(animation, timeMoved);
					}
					
					case EASEINQUINT: {
						return easeInQuint(animation, timeMoved);
					}
					
					case EASEOUTQUINT: {
						return easeOutQuint(animation, timeMoved);
					}
					
					case EASEINOUTQUINT: {
						return easeInOutQuint(animation, timeMoved);
					}
					
					case EASEINCIRC: {
						return easeInCirc(animation, timeMoved);
					}
					
					case EASEOUTCIRC: {
						return easeOutCirc(animation, timeMoved);
					}
					
					case EASEINOUTCIRC: {
						return easeInOutCirc(animation, timeMoved);
					}
					
					case EASEINBACK: {
						return easeInBack(animation, timeMoved);
					}
					
					case EASEOUTBACK: {
						return easeOutBack(animation, timeMoved);
					}
					
					case EASEINOUTBACK: {
						return easeInOutBack(animation, timeMoved);
					}
					
					case EASEINELASTIC: {
						return easeInElastic(animation, timeMoved);
					}
					
					case EASEOUTELASTIC: {
						return easeOutElastic(animation, timeMoved);
					}
					
					case EASEINOUTELASTIC: {
						return easeInOutElastic(animation, timeMoved);
					}
					
					case EASEINBOUNCE: {
						return easeInBounce(animation, time);
					}
					
					case EASEOUTBOUNCE: {
						return easeOutBounce(animation, time);
					}
					
					case EASEINOUTBOUNCE: {
						return easeInOutBounce(animation, time);
					}
					
					default: {
						InterfaceCore.logError("Easing type " + animation.forwardsEasing + " does not exist. Defaulting to linear.");
						return linear(animation, timeMoved);
					}
				}
			//If it is, then apply linear easing
			} else {
				return linear(animation, timeMoved);
			}
		}
	}
	
	/* Below this are methods that calculate the graph of each easing curve
	 * It returns a value according to the value provided to timeElapsed
	 */
	
	public double linear(JSONAnimationDefinition animation, long timeElapsed) {
		
		return (timeElapsed/(double)(animation.duration*50));
		
	}

	public double easeInQuad(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double value;
		
		value = time * time;

		return value;
	}
	
	public double easeOutQuad(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double value;
		
		value = time * (2 - time);
		
		return value;
	}
	
	public double easeInOutQuad(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		if (time < 0.5) {
			
			double value = 2 * time * time;
			
			return value;
			
		} else {
			
			double value = -1 + (4 - 2 * time) * time;

			return value;
			
		}
	}
	
	public double easeInCubic(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = time * time * time;
		
		return value;
	}
	
	public double easeOutCubic(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = --time * time * time + 1;
		
		return value;
	}
	
	public double easeInOutCubic(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		if (time < 0.5) {
			
			double value = 4 * time * time * time;
			
			return value;

		} else {
			
			double value = (time - 1) * (2 * time - 2) * (2 * time - 2) + 1;
			
			return value;
			
		}
		
	}

	public double easeInQuart(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = time * time * time * time;
		
		return value;
	}
	
	public double easeOutQuart(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = 1 - (--time) * time * time * time;
		
		return value;
	}
	
	public double easeInOutQuart(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		if (time < 0.5) {
			
			double value = 8 * time * time * time * time;
			
			return value;

		} else {
			
			double value = 1 - 8 * (--time) * time * time * time;
			
			return value;
			
		}
	}
	
	public double easeInQuint(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = time * time * time * time * time;
		
		return value;
	}
	
	public double easeOutQuint(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = 1 + (--time) * time * time * time * time;
		
		return value;
	}
	
	public double easeInOutQuint(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		if (time < 0.5) {
			
			double value = 16 * time * time * time * time * time;
			
			return value;

		} else {
			
			double value = 1 + 16 * (--time) * time * time * time * time;
			
			return value;
			
		}
	}
	
	public double easeInExpo(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = time == 0 ? 0 : Math.pow(2, 10 * time - 10);
		
		return value;
	}
	
	public double easeOutExpo(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = time == 1 ? 1 : 1 - Math.pow(2, -10 * time);
		
		return value;
	}
	
	public double easeInOutExpo(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		if (time == 0) {
			
			return 0;
			
		} else if (time == 1) {
			
			return 1;
			
		} else if (time < 0.5) {
			
			double value = Math.pow(2, 20 * time - 10) / 2;
			
			return value;
			
		} else {
			
			double value = (2 - Math.pow(2, -20 * time + 10)) / 2;
			
			return value;
			
		}
	}

	public double easeInCirc(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = 1 - Math.sqrt(1 - Math.pow(time, 2));
		
		return value;
	}
	
	public double easeOutCirc(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		double value = Math.sqrt(1 - Math.pow(time - 1, 2));
		
		return value;
	}
	
	public double easeInOutCirc(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		
		if (time < 0.5) {
			
			double value = (1 - Math.sqrt(1 - Math.pow(2 * time, 2))) / 2;
			
			return value;
			
		} else {
			
			double value = (Math.sqrt(1 - Math.pow(-2 * time + 2, 2)) + 1) / 2;
			
			return value;
			
		}
	}
	
	public double easeInBack(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double c1 = 1.70518;
		double c3 = c1 + 1;
		
		double value = c3 * time * time * time - c1 * time * time;
		
		return value;
	}
	
	public double easeOutBack(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double c1 = 1.70518;
		double c3 = c1 + 1;
		
		double value = 1 + c3 * Math.pow(time - 1, 3) + c1 * Math.pow(time - 1, 2);
		
		return value;
	}
	
	public double easeInOutBack(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double c1 = 1.70518;
		double c2 = c1 * 1.525;
		
		if (time < 0.5) {
			
			double value = (Math.pow(2 * time, 2) * ((c2 + 1) * 2 * time - c2)) / 2;
			
			return value;
			
		} else {
			
			double value = (Math.pow(2 * time - 2, 2) * ((c2 + 1) * (time * 2 - 2) + c2) + 2) / 2;
			
			return value;
			
		}
	}

	public double easeInElastic(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double c4 = (2 * Math.PI) / 3;
		
		if (time == 0) {
			
			return 0;
			
		} else if (time == 1) {
			
			return 1;
			
		} else {
			
			double value = -Math.pow(2, 10 * time - 10) * Math.sin((time * 10 - 10.75) * c4);
			
			return value;
		}
	}
	
	public double easeOutElastic(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double c4 = (2 * Math.PI) / 3;
		
		if (time == 0) {
			
			return 0;
			
		} else if (time == 1) {
			
			return 1;
			
		} else {
			
			double value = Math.pow(2, -10 * time) * Math.sin((time * 10 - 0.75) * c4) + 1;
			
			return value;
		}
	}
	
	public double easeInOutElastic(JSONAnimationDefinition animation, long timeElapsed) {
		long duration = animation.duration*50;
		double time = timeElapsed/(double)duration;
		double c5 = (2 * Math.PI) / 4.5;
		
		if (time == 0) {
		
			return 0;
		
		} else if (time == 1) {
			
			return 1;
			
		} else if (time < 0.5 ){
			
			double value = -(Math.pow(2, 20 * time - 10) * Math.sin((20 * time - 11.125) * c5)) /2;
			
			return value;
			
		} else {
			
			double value = (Math.pow(2, -20 * time + 10) * Math.sin((20 * time - 11.125) * c5)) / 2 + 1;
			
			return value;
		}
	}

	public double easeOutBounce(JSONAnimationDefinition animation, double time) {
		double n1 = 7.5625;
		double d1 = 2.75;
		
		if (time < 1 / d1) {
			
			double value = n1 * time * time;
			
			return value;
			
		} else if (time < 2 / d1) {
			
		    double value = n1 * (time -= 1.5 / d1) * time + 0.75;
		    System.out.format("Value: %f | Time: %f\n", value, time);
		    
			return value;
			
		} else if (time < 2.5 / d1) {
			
		    double value = n1 * (time -= 2.25 / d1) * time + 0.9375;
		    System.out.format("Value: %f | Time: %f\n", value, time);
		    
			return value;
			
		} else {
			
		    double value = n1 * (time -= 2.625 / d1) * time + 0.984375;
		    System.out.format("Value: %f | Time: %f\n", value, time);
		    
			return value;
		}
	}
	
	public double easeInBounce(JSONAnimationDefinition animation, double time) {
		
		double value = 1 - easeOutBounce(animation, 1 - time);
		
		return value;
	}
	
	public double easeInOutBounce(JSONAnimationDefinition animation, double time) {
		if (time < 0.5) {
			
			double value = (1 - easeOutBounce(animation, 1 - 2 * time)) / 2;
			
			return value;
			
		} else {
			
			double value = (1 + easeInBounce(animation, 2 * time - 1)) / 2;
			
			return value;
		}
	}
}
