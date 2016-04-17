package api.simplelib.minecraft;

import api.simplelib.Context;
import api.simplelib.Var;
import api.simplelib.VarSync;
import api.simplelib.common.Nullable;
import api.simplelib.network.ModNetwork;
import api.simplelib.utils.ITagSerializable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.simplelib.interactive.process.VarInteger;
import net.simplelib.network.NBTWindowsMessage;

import java.util.List;

/**
 * @author ci010
 */
public class ContainerWrap extends Container
{
	private ImmutableList<VarSync> syncs;

	public ContainerWrap load(List<VarSync> syncs)
	{
		this.syncs = ImmutableList.copyOf(syncs);
//		for (VarSync sync : this.syncs)
//			sync.addListener(this);
		return this;
	}

	public ContainerWrap load(InventoryPlayer player)
	{
		int index;
		for (index = 0; index < 9; ++index)
			this.addSlotToContainer(new Slot(player, index, 8 + index * 18, 142));
		for (index = 0; index < 3; ++index)
			for (int offset = 0; offset < 9; ++offset)
				this.addSlotToContainer(new Slot(player, offset + index * 9 + 9, 8 + offset * 18, 84 + index * 18));
		return this;
	}

	public ContainerWrap loadSlots(List<Slot> slots)
	{
		for (Slot slot : slots)
			this.addSlotToContainer(slot);
		return this;
	}

	public List<EntityPlayerMP> getPlayers()
	{
		List<EntityPlayerMP> players = Lists.newArrayList();
		for (Object o : this.crafters)
			if (o instanceof EntityPlayerMP)
				players.add((EntityPlayerMP) o);
		return players;
	}

	@Override
	public void onCraftGuiOpened(ICrafting iCrafting)
	{
		if (iCrafting instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) iCrafting;
			for (int num = 0; num < syncs.size(); ++num)
				ModNetwork.instance().sendTo(new NBTWindowsMessage(this.windowId, num, syncs.get(num)),
						playerMP);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value)
	{}

	@SideOnly(Side.CLIENT)
	public void updateSync(int id, NBTTagCompound tag)
	{
		syncs.get(id).readFromNBT(tag);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		super.onCraftMatrixChanged(inventoryIn);
		//TODO think about this...
		//this basically is a callback function...
		//called when an inventory changed and this function is expected to update and sync the data to other players.
		//The inventory seems like always be the temporally inventory.
//		this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	}

	@Override
	protected void finalize() throws Throwable
	{
//		super.finalize();
//		for (VarSync sync : this.syncs)
//			sync.removeListener(this);
	}

	//	@Override
	public void onInventoryChange(IInventory inventoryIn)
	{
		onCraftMatrixChanged(inventoryIn);
	}

	//	@Override
	public void onChange(Var var, @Nullable Context context)
	{
		if (syncs.contains(var))
		{
			int i = syncs.indexOf(var);
			for (EntityPlayerMP playerMP : getPlayers())
			{
				ModNetwork.instance().sendTo(new NBTWindowsMessage(this.windowId, i, (ITagSerializable) var),
						playerMP);
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return false;
	}
}