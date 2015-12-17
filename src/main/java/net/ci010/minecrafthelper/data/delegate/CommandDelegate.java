package net.ci010.minecrafthelper.data.delegate;

import net.ci010.minecrafthelper.abstracts.RegistryDelegate;
import net.ci010.minecrafthelper.annotation.type.ASMDelegate;
import net.ci010.minecrafthelper.annotation.type.ModCommand;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;

/**
 * @author ci010
 */
@ASMDelegate
public class CommandDelegate extends RegistryDelegate<ModCommand>
{
	@Mod.EventHandler
	public void onServerStart(FMLServerStartedEvent event)
	{
		try
		{
			((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand((CommandBase) this
					.getAnnotatedClass().newInstance());
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}
