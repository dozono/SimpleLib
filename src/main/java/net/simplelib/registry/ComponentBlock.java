package net.simplelib.registry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.simplelib.RegistryHelper;

/**
 * @author CI010
 */
public class ComponentBlock extends MinecraftComponent<Block>
{
	public ComponentBlock(Block wrap)
	{
		super(wrap);
	}

	@Override
	public Block setUnlocalizedName(String name)
	{
		return this.getComponent().setUnlocalizedName(name);
	}

	@Override
	public String getUnlocalizedName()
	{
		return this.getComponent().getUnlocalizedName();
	}

	@Override
	public Block setCreativeTab(CreativeTabs tab)
	{
		return this.getComponent().setCreativeTab(tab);
	}

	@Override
	public Block register(String name)
	{
		return GameRegistry.registerBlock(this.getComponent(), name);
	}

	@Override
	public Block registerOre(String name)
	{
		OreDictionary.registerOre(name, this.getComponent());
		return this.getComponent();
	}

	@Override
	public Block registerModel(String name)
	{
		Item item = Item.getItemFromBlock(this.getComponent());
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,
				0, new ModelResourceLocation(RegistryHelper.INSTANCE.currentMod() +
						":" + name, "inventory"));
		ModelBakery.addVariantName(item, RegistryHelper.INSTANCE.currentMod() + ":" + name);
		return this.getComponent();
	}
}