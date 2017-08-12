package galaxyspace.core.handler;

import galaxyspace.GalaxySpace;
import galaxyspace.systems.SolarSystem.SolarSystemPlanets;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.event.client.CelestialBodyRenderEvent;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;


public class GSColorRingClient
{
	
    public static final ResourceLocation saturnRingTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/saturn_rings.png");
    public static final ResourceLocation uranusRingTexture = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/uranus_rings.png");
    
    private static final ResourceLocation barnardaRingTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnardaRings.png");
    private static final ResourceLocation barnardaRing2Texture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/barnardaRings2.png");
    private static final ResourceLocation acrdisk = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/acrdisk.png");
	
    final Minecraft minecraft = FMLClientHandler.instance().getClient();
    public Random rand;

   
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRingRender(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent)
    {
 	
    	if(renderEvent.celestialBody.getRingColorR() == 1.0F
    			&& renderEvent.celestialBody.getRingColorG() == 0.0F
    			&& renderEvent.celestialBody.getRingColorB() == 0.0F) 
    	{
    		this.RingRender(renderEvent, renderEvent.celestialBody, 0.7F, 0.0F, 0.0F);
    	}
    	
    	
    	if(renderEvent.celestialBody.getRingColorG() == 1.0F
    			&& renderEvent.celestialBody.getRingColorB() == 0.0F
    			&& renderEvent.celestialBody.getRingColorR() == 0.0F) {
            this.RingRender(renderEvent, renderEvent.celestialBody, 0.0F, 0.7F, 0.0F);
        }

    	if(renderEvent.celestialBody.getRingColorR() == 0.0F
    			&& renderEvent.celestialBody.getRingColorG() == 0.0F
    			&& renderEvent.celestialBody.getRingColorB() == 0.0F) {
            this.RingRenderNull(renderEvent, renderEvent.celestialBody);
        }
    	
    	if(renderEvent.celestialBody.equals(GalacticraftCore.planetOverworld)) {
            this.RingRender(renderEvent, renderEvent.celestialBody, 0.0F, 0.7F, 0.0F);
        }
    }
    
    public void RingRenderNull(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent, CelestialBody aroundBody)
    {
    	renderEvent.setCanceled(true);
    }

    public void RingRender(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent, CelestialBody aroundBody, float par1, float par2, float par3)
    {
    	Vector3f mapPos = renderEvent.parentOffset;
        
        float xOffset = (float) mapPos.x;
        float yOffset = (float) mapPos.y;
        
           
        	if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiCelestialSelection)
        		GL11.glColor4f(par1, par2, par3, 0.5F);
           	else
        		GL11.glColor4f(0.3F, 0.1F, 0.1F, 1.0F);
            renderEvent.setCanceled(true);
            GL11.glBegin(GL11.GL_LINE_LOOP);
            
            final float theta = (float) (2 * Math.PI / 90);
            final float cos = (float) Math.cos(theta);
            final float sin = (float) Math.sin(theta);

            float min = 72.0F;
            float max = 78.0F;

            if(aroundBody instanceof Planet) {
                min = 72.F;
                max = 78.F;
             } else if(aroundBody instanceof Moon) {
                max = 1 / 1.5F;
                min = 1 / 1.9F;     
             }
            
            float x = max * renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
            float y = 0;

            float temp;
            for (int i = 0; i < 90; i++)
            {
               
                GL11.glVertex2f(x+xOffset, y+yOffset);
               
                temp = x;
                x = cos * x - sin * y;
                y = sin * temp + cos * y;

            }
            
            GL11.glEnd();
            GL11.glBegin(GL11.GL_LINE_LOOP);

            x = min * renderEvent.celestialBody.getRelativeDistanceFromCenter().scaledDistance;
            y = 0;

            for (int i = 0; i < 90; i++)
            {
                GL11.glVertex2f(x+xOffset, y+yOffset);

                temp = x;
                x = cos * x - sin * y;
                y = sin * temp + cos * y;
            }
            GL11.glEnd();
            GL11.glColor4f(par1, par2, par3, 0.1F);
            GL11.glBegin(GL11.GL_QUADS);
            x = min * renderEvent.celestialBody.getRelativeDistanceFromCenter().scaledDistance;
            y = 0;
            float x2 = max * renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
            float y2 = 0;
            
            for (int i = 0; i < 90; i++)
            {
            	
                GL11.glVertex2f(x2+xOffset, y2+yOffset);
                GL11.glVertex2f(x+xOffset, y+yOffset);

                temp = x;
                x = cos * x - sin * y;
                y = sin * temp + cos * y;
                temp = x2;
                x2 = cos * x2 - sin * y2;
                y2 = sin * temp + cos * y2;

                GL11.glVertex2f(x+xOffset, y+yOffset);
                GL11.glVertex2f(x2+xOffset, y2+yOffset);
            }
            GL11.glEnd();
            
       
	}

      
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onBodyRender(CelestialBodyRenderEvent.Pre renderEvent)
    {
        if (renderEvent.celestialBody.equals(SolarSystemPlanets.planetKuiperBelt))
        {
        	
            GL11.glRotatef(Sys.getTime() / 10.0F % 360, 0, 0, 1);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPlanetPost(CelestialBodyRenderEvent.Post event)
    {
    	
        if (this.minecraft.currentScreen instanceof GuiCelestialSelection)
        {
        	if (event.celestialBody.getTierRequirement() == -2)
        	{
        		this.minecraft.renderEngine.bindTexture(this.acrdisk);
        		float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 3.0F;
        		size = size * event.celestialBody.getRelativeSize();
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-7.3F * size, -3.0F * size, 15.0F * size, 7.5F * size, 0, 0, 30, 7, false, false, 30, 7);
        	}
        	if (event.celestialBody == SolarSystemPlanets.planetSaturn)
        	{
                this.minecraft.renderEngine.bindTexture(saturnRingTexture);
                float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 6.0F;
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-7.5F * size, -1.75F * size, 15.0F * size, 3.5F * size, 0, 0, 30, 7, false, false, 30, 7);
        	}
        	else if (event.celestialBody == SolarSystemPlanets.planetUranus)
        	{
                this.minecraft.renderEngine.bindTexture(uranusRingTexture);
                float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 6.0F;
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-1.75F * size, -7.0F * size, 3.5F * size, 14.0F * size, 0, 0, 28, 7, false, false, 28, 7);
        	}
        	/*else if (event.celestialBody == BarnardsPlanets.planetBarnardaF)
        	{
        		GL11.glEnable(GL11.GL_BLEND);
                this.minecraft.renderEngine.bindTexture(barnardaRingTexture);
                float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 6.0F;
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-11.0F * size, -1.75F * size, 22.0F * size, 3.5F * size, 0, 0, 32, 7, false, false, 32, 7);
        	}
        	else if (event.celestialBody == BarnardsPlanets.planetBarnardaG)
        	{
        		GL11.glEnable(GL11.GL_BLEND);
                this.minecraft.renderEngine.bindTexture(barnardaRing2Texture);
                float size = GuiCelestialSelection.getWidthForCelestialBodyStatic(event.celestialBody) / 6.0F;
                ((GuiCelestialSelection)this.minecraft.currentScreen).drawTexturedModalRect(-11.4F * size, -1.75F * size, 23.4F * size, 3.5F * size, 0, 0, 32, 7, false, false, 32, 7);
        	}*/
        	
        }
    }    
  }


