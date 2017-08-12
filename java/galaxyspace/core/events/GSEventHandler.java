package galaxyspace.core.events;

import galaxyspace.core.coremod.gui.screen.GSGuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.tick.KeyHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GSEventHandler {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onGuiOpenEvent(GuiOpenEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		
		if (event.getGui() instanceof GuiCelestialSelection)
		{
			if(mc.gameSettings.isKeyDown(KeyHandlerClient.galaxyMap)) event.setGui(new GSGuiCelestialSelection(true, null, true));   			
			else event.setGui(new GSGuiCelestialSelection(false, null, false));
		}
	 }
}
