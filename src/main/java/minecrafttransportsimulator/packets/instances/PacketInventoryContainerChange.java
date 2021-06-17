package minecrafttransportsimulator.packets.instances;

import io.netty.buffer.ByteBuf;
import minecrafttransportsimulator.entities.instances.EntityInventoryContainer;
import minecrafttransportsimulator.mcinterface.WrapperNBT;
import minecrafttransportsimulator.mcinterface.WrapperWorld;
import minecrafttransportsimulator.packets.components.APacketEntity;
import net.minecraft.item.ItemStack;

/**Packet sent to inventory containers to update the inventory in them.
 * 
 * @author don_bruce
 */
public class PacketInventoryContainerChange extends APacketEntity<EntityInventoryContainer>{
	private final int index;
	private final ItemStack stackToChangeTo;
	
	public PacketInventoryContainerChange(EntityInventoryContainer inventory, int index, ItemStack stackToChangeTo){
		super(inventory);
		this.index = index;
		this.stackToChangeTo = stackToChangeTo;
	}
	
	public PacketInventoryContainerChange(ByteBuf buf){
		super(buf);
		this.index = buf.readInt();
		this.stackToChangeTo = new ItemStack(readDataFromBuffer(buf).tag);
	}
	
	@Override
	public void writeToBuffer(ByteBuf buf){
		super.writeToBuffer(buf);
		buf.writeInt(index);
		WrapperNBT stackData = new WrapperNBT();
		stackToChangeTo.writeToNBT(stackData.tag);
		writeDataToBuffer(stackData, buf);
	}
	
	@Override
	public boolean handle(WrapperWorld world, EntityInventoryContainer inventory){
		inventory.setStack(stackToChangeTo, index);
		return true;
	}
}
