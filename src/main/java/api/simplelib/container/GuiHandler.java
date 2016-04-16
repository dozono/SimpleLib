package api.simplelib.container;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.List;

/**
 * @author ci010
 */
@ModGuiHandler
public class GuiHandler implements IGuiHandler
{
	private static List<IContainerProvider> providers = Lists.newArrayList();

	public static int addContainerProvider(IContainerProvider provider)
	{
		if (providers.contains(provider))
			return providers.indexOf(provider);
		providers.add(provider);
		return providers.size() - 1;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return providers.get(ID).getContainer(player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return providers.get(ID).getGuiContainer(player, world, x, y, z);
	}
}
