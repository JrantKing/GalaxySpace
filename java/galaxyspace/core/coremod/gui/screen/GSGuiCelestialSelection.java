package galaxyspace.core.coremod.gui.screen;

import galaxyspace.GalaxySpace;
import galaxyspace.core.api.IAdvancedSpace;

import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.event.client.CelestialBodyRenderEvent;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.WorldProviderSpace;
import micdoodle8.mods.galacticraft.api.recipe.SpaceStationRecipe;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.api.world.ISolarLevel;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.google.common.collect.Maps;

public class GSGuiCelestialSelection extends GuiCelestialSelection {

	public List<CelestialBody> possibleBodiesGS;
	private int posYSS = 132 + 41;
	private int timer;	
	private int traveltime;
	private boolean check = false;
	private Random rand;
	
	protected int zoomPos = 0;
	protected int zoomPos1 = 0;
	
	protected int canCreateOffset = 0;
	
	public static ResourceLocation guiBG = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialselection.png");
	public static ResourceLocation guiMain2 = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialselection2.png");
		
	public GSGuiCelestialSelection(boolean mapMode, List<CelestialBody> possibleBodies, boolean canCreateStations) {
		super(mapMode, possibleBodies, canCreateStations);	
		this.possibleBodiesGS = possibleBodies;		
	}
	
	@Override
    public void initGui()
    {
        super.initGui();        
    }

	@Override
	public void setBlackBackground()
    {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(this.guiBG);
        
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0.0D, height, -90.0D).tex(0, 1).endVertex();
        worldRenderer.pos(width, height, -90.0D).tex(1, 1).endVertex();
        worldRenderer.pos(width, 0.0D, -90.0D).tex(1, 0).endVertex();
        worldRenderer.pos(0.0D, 0.0D, -90.0D).tex(0, 0).endVertex();
        tessellator.draw();
        
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
	@Override
	public void drawCircles()
    {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glLineWidth(3);
        int count = 0;

        final float theta = (float) (2 * Math.PI / 90);
        final float cos = (float) Math.cos(theta);
        final float sin = (float) Math.sin(theta);

        for (Planet planet : GalaxyRegistry.getRegisteredPlanets().values())
        {
            if (planet.getParentSolarSystem() != null)
            {
                Vector3f systemOffset = this.getCelestialBodyPosition(planet.getParentSolarSystem().getMainStar());

                float x = this.getScale(planet);
                float y = 0;

                float alpha = 1.0F;

                if ((this.selectedBody instanceof IChildBody && ((IChildBody) this.selectedBody).getParentPlanet() != planet) || (this.selectedBody instanceof Planet && this.selectedBody != planet && this.isZoomed()))
                {
                    if (this.lastSelectedBody == null && !(this.selectedBody instanceof IChildBody) && !(this.selectedBody instanceof Satellite))
                    {
                        alpha = 1.0F - Math.min(this.ticksSinceSelection / 25.0F, 1.0F);
                    }
                    else
                    {
                        alpha = 0.0F;
                    }
                }

                if (alpha != 0)
                {
                    switch (count % 2)
                    {
                    case 0:
        				GL11.glColor4f(planet.getRingColorR() / 1.4F, planet.getRingColorG() / 1.4F, planet.getRingColorB() / 1.4F, alpha / 1.4F);
        				break;
        			case 1:
        				GL11.glColor4f(planet.getRingColorR(), planet.getRingColorG(), planet.getRingColorB(), alpha);
        				break;
                    }

                    CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre(planet, systemOffset);
                    MinecraftForge.EVENT_BUS.post(preEvent);

                    if (!preEvent.isCanceled())
                    {
                        GL11.glTranslatef(systemOffset.x, systemOffset.y, systemOffset.z);

                        GL11.glBegin(GL11.GL_LINE_LOOP);

                        float temp;
                        for (int i = 0; i < 90; i++)
                        {
                            GL11.glVertex2f(x, y);

                            temp = x;
                            x = cos * x - sin * y;
                            y = sin * temp + cos * y;
                        }

                        GL11.glEnd();

                        GL11.glTranslatef(-systemOffset.x, -systemOffset.y, -systemOffset.z);

                        count++;
                    }

                    CelestialBodyRenderEvent.CelestialRingRenderEvent.Post postEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Post(planet);
                    MinecraftForge.EVENT_BUS.post(postEvent);
                }
            }
        }

        count = 0;

        if (this.selectedBody != null)
        {
            Vector3f planetPos = this.getCelestialBodyPosition(this.selectedBody);

            if (this.selectedBody instanceof IChildBody)
            {
                planetPos = this.getCelestialBodyPosition(((IChildBody) this.selectedBody).getParentPlanet());
            }
            else if (this.selectedBody instanceof Satellite)
            {
                planetPos = this.getCelestialBodyPosition(((Satellite) this.selectedBody).getParentPlanet());
            }

            GL11.glTranslatef(planetPos.x, planetPos.y, 0);

            for (Moon moon : GalaxyRegistry.getRegisteredMoons().values())
            {
                if ((moon.getParentPlanet() == this.selectedBody && this.selectionState != EnumSelection.SELECTED) || moon == this.selectedBody || getSiblings(this.selectedBody).contains(moon))
                {
                    if (this.drawCircle(moon, count, sin, cos))
                    {
                        count++;
                    }
                }
            }

            for (Satellite satellite : GalaxyRegistry.getRegisteredSatellites().values())
            {
                if (this.possibleBodies != null && this.possibleBodies.contains(satellite))
                {
                    if ((satellite.getParentPlanet() == this.selectedBody && this.selectionState != EnumSelection.SELECTED) && this.ticksSinceSelection > 24 || satellite == this.selectedBody || this.lastSelectedBody instanceof IChildBody)
                    {
                        if (this.drawCircle(satellite, count, sin, cos))
                        {
                            count++;
                        }
                    }
                }
            }
        }

        GL11.glLineWidth(1);
    }
	
	private boolean drawCircle(CelestialBody body, int count, float sin, float cos)
    {
        float x = this.getScale(body);
        float y = 0;

        float alpha = 1;

        if (this.isZoomed())
        {
            alpha = this.selectedBody instanceof IChildBody ? 1.0F : Math.min(Math.max((this.ticksSinceSelection - 30) / 15.0F, 0.0F), 1.0F);

            if (this.lastSelectedBody instanceof Moon && body instanceof Moon)
            {
                if (GalaxyRegistry.getMoonsForPlanet(((Moon) this.lastSelectedBody).getParentPlanet()).contains(body))
                {
                    alpha = 1.0F;
                }
            }
            else if (this.lastSelectedBody instanceof Satellite && body instanceof Satellite)
            {
                if (GalaxyRegistry.getSatellitesForCelestialBody(((Satellite) this.lastSelectedBody).getParentPlanet()).contains(body))
                {
                    alpha = 1.0F;
                }
            }
        }

        if (alpha != 0)
        {
            switch (count % 2)
            {
            case 0:
                GL11.glColor4f(body.getRingColorR() / 1.4F, body.getRingColorG() / 1.4F, body.getRingColorB() / 1.4F, alpha);
                break;
            case 1:
                GL11.glColor4f(body.getRingColorR(), body.getRingColorG(), body.getRingColorB(), alpha);
                break;
            }

            CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre(body, new Vector3f(0.0F, 0.0F, 0.0F));
            MinecraftForge.EVENT_BUS.post(preEvent);

            if (!preEvent.isCanceled())
            {
                GL11.glBegin(GL11.GL_LINE_LOOP);

                float temp;
                for (int i = 0; i < 90; i++)
                {
                    GL11.glVertex2f(x, y);

                    temp = x;
                    x = cos * x - sin * y;
                    y = sin * temp + cos * y;
                }

                GL11.glEnd();
                return true;
            }

            CelestialBodyRenderEvent.CelestialRingRenderEvent.Post postEvent = new CelestialBodyRenderEvent.CelestialRingRenderEvent.Post(body);
            MinecraftForge.EVENT_BUS.post(postEvent);
        }

        return false;
    }

	
	@Override
	public HashMap<CelestialBody, Matrix4f> drawCelestialBodies(Matrix4f worldMatrix)
    {
        GL11.glColor3f(1, 1, 1);
        FloatBuffer fb = BufferUtils.createFloatBuffer(16 * Float.SIZE);
        HashMap<CelestialBody, Matrix4f> matrixMap = Maps.newHashMap();

        for (SolarSystem solarSystem : GalaxyRegistry.getRegisteredSolarSystems().values())
        {
            Star star = solarSystem.getMainStar();

            if (star != null && star.getBodyIcon() != null)
            {
                GL11.glPushMatrix();
                Matrix4f worldMatrix0 = new Matrix4f(worldMatrix);

                Matrix4f.translate(this.getCelestialBodyPosition(star), worldMatrix0, worldMatrix0);

                Matrix4f worldMatrix1 = new Matrix4f();
                Matrix4f.rotate((float) Math.toRadians(45), new Vector3f(0, 0, 1), worldMatrix1, worldMatrix1);
                Matrix4f.rotate((float) Math.toRadians(-55), new Vector3f(1, 0, 0), worldMatrix1, worldMatrix1);
                worldMatrix1 = Matrix4f.mul(worldMatrix0, worldMatrix1, worldMatrix1);

                fb.rewind();
                worldMatrix1.store(fb);
                fb.flip();
                GL11.glMultMatrix(fb);

                float alpha = 1.0F;

                if (this.selectedBody != null && this.selectedBody != star && this.isZoomed())
                {
                    alpha = 1.0F - Math.min(this.ticksSinceSelection / 25.0F, 1.0F);
                }

                if (this.selectedBody != null && this.isZoomed())
                {
                    if (star != this.selectedBody)
                    {
                        alpha = 1.0F - Math.min(this.ticksSinceSelection / 25.0F, 1.0F);

                        if (!(this.lastSelectedBody instanceof Star) && this.lastSelectedBody != null)
                        {
                            alpha = 0.0F;
                        }
                    }
                }

                if (alpha != 0)
                {
                    CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre(star, star.getBodyIcon(), 8);
                    MinecraftForge.EVENT_BUS.post(preEvent);

                    GL11.glColor4f(1, 1, 1, alpha);
                    if (preEvent.celestialBodyTexture != null)
                    {
                        this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                    }

                    if (!preEvent.isCanceled())
                    {
                        int size = getWidthForCelestialBodyStatic(star);
                        if (star == this.selectedBody && this.selectionState == EnumSelection.SELECTED)
                        {
                            size /= 2;
                            size *= 3;
                        }
                        this.drawTexturedModalRect(-size / 2, -size / 2, size, size, 0, 0, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                        matrixMap.put(star, worldMatrix1);
                    }

                    CelestialBodyRenderEvent.Post postEvent = new CelestialBodyRenderEvent.Post(star);
                    MinecraftForge.EVENT_BUS.post(postEvent);
                }

                fb.clear();
                GL11.glPopMatrix();
            }
        }

        for (Planet planet : GalaxyRegistry.getRegisteredPlanets().values())
        {
            if (planet.getBodyIcon() != null)
            {
                GL11.glPushMatrix();
                Matrix4f worldMatrix0 = new Matrix4f(worldMatrix);

                Matrix4f.translate(this.getCelestialBodyPosition(planet), worldMatrix0, worldMatrix0);

                Matrix4f worldMatrix1 = new Matrix4f();
                Matrix4f.rotate((float) Math.toRadians(45), new Vector3f(0, 0, 1), worldMatrix1, worldMatrix1);
                Matrix4f.rotate((float) Math.toRadians(-55), new Vector3f(1, 0, 0), worldMatrix1, worldMatrix1);
                worldMatrix1 = Matrix4f.mul(worldMatrix0, worldMatrix1, worldMatrix1);

                fb.rewind();
                worldMatrix1.store(fb);
                fb.flip();
                GL11.glMultMatrix(fb);

                float alpha = 1.0F;

                if ((this.selectedBody instanceof IChildBody && ((IChildBody) this.selectedBody).getParentPlanet() != planet) || (this.selectedBody instanceof Planet && this.selectedBody != planet && this.isZoomed()))
                {
                    if (this.lastSelectedBody == null && !(this.selectedBody instanceof IChildBody))
                    {
                        alpha = 1.0F - Math.min(this.ticksSinceSelection / 25.0F, 1.0F);
                    }
                    else
                    {
                        alpha = 0.0F;
                    }
                }

                if (alpha != 0)
                {
                    CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre(planet, planet.getBodyIcon(), 12);
                    MinecraftForge.EVENT_BUS.post(preEvent);

                    GL11.glColor4f(1, 1, 1, alpha);
                    if (preEvent.celestialBodyTexture != null)
                    {
                        this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                    }

                    if (!preEvent.isCanceled())
                    {
                        int size = getWidthForCelestialBodyStatic(planet);
                        this.drawTexturedModalRect(-size / 2, -size / 2, size, size, 0, 0, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                        matrixMap.put(planet, worldMatrix1);
                    }

                    CelestialBodyRenderEvent.Post postEvent = new CelestialBodyRenderEvent.Post(planet);
                    MinecraftForge.EVENT_BUS.post(postEvent);
                }

                fb.clear();
                GL11.glPopMatrix();
            }
        }

        if (this.selectedBody != null)
        {
            Matrix4f worldMatrix0 = new Matrix4f(worldMatrix);

            for (Moon moon : GalaxyRegistry.getRegisteredMoons().values())
            {
                if ((moon == this.selectedBody || (moon.getParentPlanet() == this.selectedBody && this.selectionState != EnumSelection.SELECTED)) && (this.ticksSinceSelection > 35 || this.selectedBody == moon || (this.lastSelectedBody instanceof Moon && GalaxyRegistry.getMoonsForPlanet(((Moon) this.lastSelectedBody).getParentPlanet()).contains(moon))) || getSiblings(this.selectedBody).contains(moon))
                {
                    GL11.glPushMatrix();
                    Matrix4f worldMatrix1 = new Matrix4f(worldMatrix0);
                    Matrix4f.translate(this.getCelestialBodyPosition(moon), worldMatrix1, worldMatrix1);

                    Matrix4f worldMatrix2 = new Matrix4f();
                    Matrix4f.rotate((float) Math.toRadians(45), new Vector3f(0, 0, 1), worldMatrix2, worldMatrix2);
                    Matrix4f.rotate((float) Math.toRadians(-55), new Vector3f(1, 0, 0), worldMatrix2, worldMatrix2);
                    Matrix4f.scale(new Vector3f(0.25F, 0.25F, 1.0F), worldMatrix2, worldMatrix2);
                    worldMatrix2 = Matrix4f.mul(worldMatrix1, worldMatrix2, worldMatrix2);

                    fb.rewind();
                    worldMatrix2.store(fb);
                    fb.flip();
                    GL11.glMultMatrix(fb);

                    CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre(moon, moon.getBodyIcon(), 8);
                    MinecraftForge.EVENT_BUS.post(preEvent);

                    GL11.glColor4f(1, 1, 1, 1);
                    if (preEvent.celestialBodyTexture != null)
                    {
                        this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);
                    }

                    if (!preEvent.isCanceled())
                    {
                        int size = getWidthForCelestialBodyStatic(moon);
                        this.drawTexturedModalRect(-size / 2, -size / 2, size, size, 0, 0, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                        matrixMap.put(moon, worldMatrix1);
                    }

                    CelestialBodyRenderEvent.Post postEvent = new CelestialBodyRenderEvent.Post(moon);
                    MinecraftForge.EVENT_BUS.post(postEvent);
                    fb.clear();
                    GL11.glPopMatrix();
                }
            }
        }

        if (this.selectedBody != null)
        {
            Matrix4f worldMatrix0 = new Matrix4f(worldMatrix);

            for (Satellite satellite : GalaxyRegistry.getRegisteredSatellites().values())
            {
                if (this.possibleBodies != null && this.possibleBodies.contains(satellite))
                {
                    if ((satellite == this.selectedBody || (satellite.getParentPlanet() == this.selectedBody && this.selectionState != EnumSelection.SELECTED)) && (this.ticksSinceSelection > 35 || this.selectedBody == satellite || (this.lastSelectedBody instanceof Satellite && GalaxyRegistry.getSatellitesForCelestialBody(((Satellite) this.lastSelectedBody).getParentPlanet()).contains(satellite))))
                    {
                        GL11.glPushMatrix();
                        Matrix4f worldMatrix1 = new Matrix4f(worldMatrix0);
                        Matrix4f.translate(this.getCelestialBodyPosition(satellite), worldMatrix1, worldMatrix1);

                        Matrix4f worldMatrix2 = new Matrix4f();
                        Matrix4f.rotate((float) Math.toRadians(45), new Vector3f(0, 0, 1), worldMatrix2, worldMatrix2);
                        Matrix4f.rotate((float) Math.toRadians(-55), new Vector3f(1, 0, 0), worldMatrix2, worldMatrix2);
                        Matrix4f.scale(new Vector3f(0.25F, 0.25F, 1.0F), worldMatrix2, worldMatrix2);
                        worldMatrix2 = Matrix4f.mul(worldMatrix1, worldMatrix2, worldMatrix2);

                        fb.rewind();
                        worldMatrix2.store(fb);
                        fb.flip();
                        GL11.glMultMatrix(fb);

                        CelestialBodyRenderEvent.Pre preEvent = new CelestialBodyRenderEvent.Pre(satellite, satellite.getBodyIcon(), 8);
                        MinecraftForge.EVENT_BUS.post(preEvent);

                        GL11.glColor4f(1, 1, 1, 1);
                        this.mc.renderEngine.bindTexture(preEvent.celestialBodyTexture);

                        if (!preEvent.isCanceled())
                        {
                            int size = getWidthForCelestialBodyStatic(satellite);
                            this.drawTexturedModalRect(-size / 2, -size / 2, size, size, 0, 0, preEvent.textureSize, preEvent.textureSize, false, false, preEvent.textureSize, preEvent.textureSize);
                            matrixMap.put(satellite, worldMatrix1);
                        }

                        CelestialBodyRenderEvent.Post postEvent = new CelestialBodyRenderEvent.Post(satellite);
                        MinecraftForge.EVENT_BUS.post(postEvent);
                        fb.clear();
                        GL11.glPopMatrix();
                    }
                }
            }
        }

        return matrixMap;
    }
	public static int getWidthForCelestialBodyStatic(CelestialBody celestialBody)
    {
		int size = (int) celestialBody.getRelativeSize();
    	if(size > 6) size = 6;  
    	else if(size <= 0) size = 1;
    	
        if (Minecraft.getMinecraft().currentScreen instanceof GuiCelestialSelection &&
                (celestialBody != ((GSGuiCelestialSelection) Minecraft.getMinecraft().currentScreen).selectedBody ||
                        ((GSGuiCelestialSelection) Minecraft.getMinecraft().currentScreen).selectionState != EnumSelection.SELECTED))
        {
            return celestialBody instanceof Star ? 8 * size : celestialBody instanceof Planet ? 4 * size : celestialBody instanceof IChildBody ? 4 * size : 2;
        }

        return celestialBody instanceof Star ? 12 * size : celestialBody instanceof Planet ? 6 * size : celestialBody instanceof IChildBody ? 6 * size : 2;
    }
	
	@Override
	public void drawButtons(int mousePosX, int mousePosY)
	{
		this.zLevel = 0.0F;
		boolean handledSliderPos = false;

		{
			String str;
			// Catalog:	            
			this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);	            
			GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);						
	        this.drawTexturedModalRect(GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 74, 11, 0, 392, 148, 22, false, false);
	        str = GCCoreUtil.translate("gui.message.catalog.name").toUpperCase();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 40 - fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 1, ColorUtil.to32BitColor(255, 255, 255, 255));

	        int scale = (int) Math.min(95, this.ticksSinceMenuOpen * 12.0F);

	        // Parent frame:
	        GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	        this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	        this.drawTexturedModalRect(GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE - 95 + scale, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 12, 95, 41, 0, 436, 95, 41, false, false);
	        str = this.isZoomed() ? this.selectedBody.getLocalizedName() : this.getParentName();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 9 - 95 + scale, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 34, ColorUtil.to32BitColor(255, 255, 255, 255));
	        GL11.glColor4f(1, 1, 0, 1);
	        this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);

	        // Grandparent frame:
	        this.drawTexturedModalRect(GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 2 - 95 + scale, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 14, 93, 17, 95, 436, 93, 17, false, false);
	        str = this.isZoomed() ? this.getParentName() : this.getGrandparentName();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 7 - 95 + scale, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 16, ColorUtil.to32BitColor(255, 120, 120, 120));
	        GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);

	        List<CelestialBody> children = this.getChildren(this.isZoomed() ? this.selectedBody : this.selectedParent);

	        for (int i = 0; i < children.size(); i++)
	        {
	        	CelestialBody child = children.get(i);
	            int xOffset = 0;

	            if (child.equals(this.selectedBody))
	            {
	                xOffset += 4;
	            }

	            scale = (int) Math.min(95.0F, Math.max(0.0F, (this.ticksSinceMenuOpen * 25.0F) - 95 * i));

	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                if (child.getReachable())
	                {
	                	if(child.equals(this.selectedBody)) GL11.glColor4f(0.0F, 1.0F, 1.0F, scale / 95.0F); //TODO 123
	                    else GL11.glColor4f(0.0F, 0.6F, 0.0F, scale / 95.0F);
	                }
	                else if(child.getTierRequirement() == -1)
	                {
	                	GL11.glColor4f(0.0F, 0.0F, 0.0F, scale / 95.0F);
	                }
	                else
	                {
	                    GL11.glColor4f(0.6F, 0.0F, 0.0F, scale / 95.0F);
	                }
	                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 3 + xOffset, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 51 + i * 14, 86, 10, 0, 489, 86, 10, false, false);
	                GL11.glColor4f(0.0F, 0.6F, 1.0F, scale / 95.0F);
	                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 2 + xOffset, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 50 + i * 14, 93, 12, 95, 464, 93, 12, false, false);

	                if (scale > 0)
	                {
	                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	                    str = child.getLocalizedName();
	                    int color = 14737632;
	                    this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 7 + xOffset, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 52 + i * 14, color);
	                }
	            }

	            if (this.mapMode)
	            {
	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                GL11.glColor4f(1.0F, 0.0F, 0.0F, 1);
	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 74, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 74, 11, 0, 392, 148, 22, true, false);
	                str = GCCoreUtil.translate("gui.message.exit.name").toUpperCase();
	                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	                this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 40 - fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 1, ColorUtil.to32BitColor(255, 255, 255, 255));
	            }

	            if (this.selectedBody != null)
	            {
	                // Right-hand bar (basic selectionState info)
	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
	                GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);

	                if (this.selectedBody instanceof Satellite)
	                {
	                    Satellite selectedSatellite = (Satellite) this.selectedBody;
	                    int stationListSize = this.spaceStationMap.get(getSatelliteParentID(selectedSatellite)).size();

	                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
	                    int max = Math.min((this.height / 2) / 14, stationListSize);
	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 95, 53, this.selectedStationOwner.length() == 0 ? 95 : 0, 186, 95, 53, false, false);
	                    if (this.spaceStationListOffset <= 0)
	                    {
	                        GL11.glColor4f(0.65F, 0.65F, 0.65F, 1);
	                    }
	                    else
	                    {
	                        GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                    }
	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 85, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 45, 61, 4, 0, 239, 61, 4, false, false);
	                    if (max + spaceStationListOffset >= stationListSize)
	                    {
	                        GL11.glColor4f(0.65F, 0.65F, 0.65F, 1);
	                    }
	                    else
	                    {
	                        GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                    }
	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 85, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 49 + max * 14, 61, 4, 0, 239, 61, 4, false, true);
	                    GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);

	                    if (this.spaceStationMap.get(getSatelliteParentID(selectedSatellite)).get(this.selectedStationOwner) == null)
	                    {
	                        str = GCCoreUtil.translate("gui.message.select_ss.name");
	                        this.drawSplitString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 47, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 20, 91, ColorUtil.to32BitColor(255, 255, 255, 255), false, false);
	                    }
	                    else
	                    {
	                        str = GCCoreUtil.translate("gui.message.ss_owner.name");
	                        this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 85, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 18, ColorUtil.to32BitColor(255, 255, 255, 255));
	                        str = this.selectedStationOwner;
	                        this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 47 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 30, ColorUtil.to32BitColor(255, 255, 255, 255));
	                    }

	                    Iterator<Map.Entry<String, StationDataGUI>> it = this.spaceStationMap.get(getSatelliteParentID(selectedSatellite)).entrySet().iterator();
	                    int i = 0;
	                    int j = 0;
	                    while (it.hasNext() && i < max)
	                    {
	                        Map.Entry<String, StationDataGUI> e = it.next();

	                        if (j >= this.spaceStationListOffset)
	                        {
	                            this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                            GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                            int xOffset = 0;

	                            if (e.getKey().equalsIgnoreCase(this.selectedStationOwner))
	                            {
	                                xOffset -= 5;
	                            }

	                            this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95 + xOffset, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 50 + i * 14, 93, 12, 95, 464, 93, 12, true, false);
	                            str = "";
	                            String str0 = e.getValue().getStationName();
	                            int point = 0;
	                            while (this.fontRendererObj.getStringWidth(str) < 80 && point < str0.length())
	                            {
	                                str = str + str0.substring(point, point + 1);
	                                point++;
	                            }
	                            if (this.fontRendererObj.getStringWidth(str) >= 80)
	                            {
	                                str = str.substring(0, str.length() - 3);
	                                str = str + "...";
	                            }
	                            this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 88 + xOffset, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 52 + i * 14, ColorUtil.to32BitColor(255, 255, 255, 255));
	                            i++;
	                        }
	                        j++;
	                    }
	                }
	                else
	                {
	                	this.mc.renderEngine.bindTexture(this.guiMain2);
	                	GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                	
	                	int sliderPos = this.zoomPos;
	                	int texPos = this.zoomPos1;
	                	if(sliderPos != 133) 
	                	{
	                		sliderPos = Math.min(this.ticksSinceSelection * 4, 133);
	                		this.zoomPos = sliderPos;
	                	}
	                	if(texPos != 125)
	                	{
	                		texPos = Math.min(this.ticksSinceSelection * 4 - 10, 125);
	                		this.zoomPos1 = texPos;
	                	}
		                int menuTopLeft = GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 52;
		                int posX = width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - sliderPos;
		                int posX2 = (int) (width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 18);
		                this.drawTexturedModalRect(posX, menuTopLeft + 12, 133, 237, 0, 0 + 79, 266, 433, true, false);
	                    
		                
		                //TODO: Celestial Body - Info
		                str = GCCoreUtil.translate("gui.message.generalinformation");
		                this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 66, ColorUtil.to32BitColor(255, 150, 200, 255));
	                    
		            	str = GCCoreUtil.translate("gui.message.physicalparameters");
                    	this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 66 + 40, ColorUtil.to32BitColor(255, 150, 200, 255));

                    	str = GCCoreUtil.translate("gui.message.atmosphericparameters");
                    	this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 66 + 82, ColorUtil.to32BitColor(255, 150, 200, 255));

                    	if(this.selectedBody instanceof Star || this.selectedBody.getTierRequirement() == -1)	str = GCCoreUtil.translate("gui.message.type") + " " + GCCoreUtil.translate("gui.message.star");
	                    if(this.selectedBody instanceof Planet && this.selectedBody.getTierRequirement() != -1)	str = GCCoreUtil.translate("gui.message.type") + " " + GCCoreUtil.translate("gui.message.planet");
	                    if(this.selectedBody instanceof Moon)	str = GCCoreUtil.translate("gui.message.type") + " " + GCCoreUtil.translate("gui.message.moon");
	                    if(this.selectedBody instanceof Satellite)	str = GCCoreUtil.translate("gui.message.type") + " " + GCCoreUtil.translate("gui.message.satellite");
	                    if(this.selectedBody.getTierRequirement() == -2)	str = GCCoreUtil.translate("gui.message.type") + " " + GCCoreUtil.translate("gui.info.blackhole.name");
                    	
	                    this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 10, ColorUtil.to32BitColor(255, 255, 255, 255));
	                  
	                    WorldProvider dim = WorldUtil.getProviderForDimensionClient(this.selectedBody.getDimensionID());
	                    
	                    str = GCCoreUtil.translate("gui.message.class") + " " + this.classPlanet(this.selectedBody, dim);
                    	this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 19, ColorUtil.to32BitColor(255, 255, 255, 255));
                   	
                    	str = GCCoreUtil.translate("gui.message.gravity") + " " + GCCoreUtil.translate("gui.message.unknown");	
                    	if(this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld) str = GCCoreUtil.translate("gui.message.gravity") + " " + ((IGalacticraftWorldProvider)dim).getGravity();
	                    this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 36 + 12, ColorUtil.to32BitColor(255, 255, 255, 255));
	                  	   
	                    str = GCCoreUtil.translate("gui.message.daylength") + " " + GCCoreUtil.translate("gui.message.unknown");	
	                    if(this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld) str = GCCoreUtil.translate("gui.message.daylength") + " " + ((WorldProviderSpace)dim).getDayLength() / 1000 + ":" + ((WorldProviderSpace)dim).getDayLength() % 1000 + "h";
	                    this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 36 + 11 * 2, ColorUtil.to32BitColor(255, 255, 255, 255));
	                
	                    str = GCCoreUtil.translate("gui.message.temperature") + " " + GCCoreUtil.translate("gui.message.unknown");	
                        if(this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld) str = GCCoreUtil.translate("gui.message.temperature") + " " + ((IGalacticraftWorldProvider)dim).getThermalLevelModifier() + " T/l";	                        		
                        this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 76 + 14, ColorUtil.to32BitColor(255, 255, 255, 255));
                   		
                        str = GCCoreUtil.translate("gui.message.solarenergy") + " " + GCCoreUtil.translate("gui.message.unknown");	
	                    if(dim instanceof ISolarLevel) str = GCCoreUtil.translate("gui.message.solarenergy") + " "  + (Math.round(((ISolarLevel)dim).getSolarEnergyMultiplier() * 1000)) / 1000F + " S/l";	
	                    if(this.selectedBody == GalacticraftCore.planetOverworld) str = GCCoreUtil.translate("gui.message.solarenergy") + " 1.0 S/l";
	                    this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 76 + 12*2, ColorUtil.to32BitColor(255, 255, 255, 255));
		                   
	                    str = GCCoreUtil.translate("gui.message.windspeed") + " " + GCCoreUtil.translate("gui.message.unknown");	
	                    if(dim instanceof IGalacticraftWorldProvider) str = GCCoreUtil.translate("gui.message.windspeed") + " "  + ((IGalacticraftWorldProvider)dim).getWindLevel() + " W/l";	
	                    if(this.selectedBody == GalacticraftCore.planetOverworld) str = GCCoreUtil.translate("gui.message.windspeed") + " 1.0 W/l";
	                    this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 74 + 12 * 3, ColorUtil.to32BitColor(255, 255, 255, 255));
	                   
	                    str = GCCoreUtil.translate("gui.message.windenergy") + " " + GCCoreUtil.translate("gui.message.unknown");	
                        if(this.selectedBody.getReachable() && this.selectedBody != GalacticraftCore.planetOverworld && dim instanceof IAdvancedSpace) str = GCCoreUtil.translate("gui.message.windenergy") + " " + (Math.round(((IAdvancedSpace)dim).getSolarWindMultiplier() * 1000)) / 1000F;	
                        this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 75 + 12 * 4, ColorUtil.to32BitColor(255, 255, 255, 255));
                   		
	                    str = GCCoreUtil.translate("gui.message.breathableatmo") + " " + GCCoreUtil.translate("gui.message.unknown");	
                        if(this.selectedBody.getReachable()) str = GCCoreUtil.translate("gui.message.breathableatmo") + " " + this.localeBoolean(this.selectedBody.atmosphere.isBreathable());	
                        this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 73 + 12 * 5, ColorUtil.to32BitColor(255, 255, 255, 255));
                   		
                        str = GCCoreUtil.translate("gui.message.atmopressure") + " " + GCCoreUtil.translate("gui.message.unknown");	
                        if(dim instanceof IAdvancedSpace) str = GCCoreUtil.translate("gui.message.atmopressure") + " " + ((IAdvancedSpace)dim).AtmosphericPressure();	
                        this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 72 + 12 * 6, ColorUtil.to32BitColor(255, 255, 255, 255));
                   	
                        str = GCCoreUtil.translate("gui.message.solarradiation") + " " + GCCoreUtil.translate("gui.message.unknown");	
                        if(dim instanceof IAdvancedSpace) str = GCCoreUtil.translate("gui.message.solarradiation") + " " + ((IAdvancedSpace)dim).SolarRadiation();	
                        this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 71 + 12 * 7, ColorUtil.to32BitColor(255, 255, 255, 255));
                   	
 	                   
                    	if(!(this.selectedBody instanceof Moon))
	                    {
	                    	if(this.selectedBody instanceof Star) str = GCCoreUtil.translate("gui.message.planets") + " "  + this.getChildren(this.selectedParent).size();
	                   		else str = GCCoreUtil.translate("gui.message.moons") + " "  + this.getChildren(this.selectedBody).size();	
	                    	this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - texPos, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 67 + 76 + 10 * 14, ColorUtil.to32BitColor(255, 255, 255, 255));
	                    }
	                }

	                if (this.canCreateSpaceStation(this.selectedBody) && (!(this.selectedBody instanceof Satellite)))
	                {
	                    GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
	                    int canCreateLength = Math.max(0, this.drawSplitString(GCCoreUtil.translate("gui.message.can_create_space_station.name"), 0, 0, 91, 0, true, true) - 2);
	                    canCreateOffset = canCreateLength * this.fontRendererObj.FONT_HEIGHT;

	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 134, 93, 4, 159, 102, 93, 4, false, false);
	                    for (int barY = 0; barY < canCreateLength; ++barY)
	                    {
	                        this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 138 + barY * this.fontRendererObj.FONT_HEIGHT, 93, this.fontRendererObj.FONT_HEIGHT, 159, 106, 93, this.fontRendererObj.FONT_HEIGHT, false, false);
	                    }
	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 138 + canCreateOffset, 93, 43, 159, 106, 93, 43, false, false);
	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 79, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 129, 61, 4, 0, 170, 61, 4, false, false);

	                    SpaceStationRecipe recipe = WorldUtil.getSpaceStationRecipe(this.selectedBody.getDimensionID());
	                    if (recipe != null)
	                    {
	                        GL11.glColor4f(0.0F, 1.0F, 0.1F, 1);
	                        boolean validInputMaterials = true;

	                        int i = 0;
	                        for (Map.Entry<Object, Integer> e : recipe.getInput().entrySet())
	                        {
	                            Object next = e.getKey();
	                            int xPos = (int) (width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95 + i * 93 / (double) recipe.getInput().size() + 5);
	                            int yPos = GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 154 + canCreateOffset;

	                            if (next instanceof ItemStack)
	                            {
	                                int amount = getAmountInInventory((ItemStack) next);
	                                RenderHelper.enableGUIStandardItemLighting();
	                                ItemStack toRender = ((ItemStack) next).copy();
	                                this.itemRender.renderItemAndEffectIntoGUI(toRender, xPos, yPos);
	                                this.itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, toRender, xPos, yPos, null);
	                                RenderHelper.disableStandardItemLighting();
	                                GL11.glEnable(GL11.GL_BLEND);

	                                if (mousePosX >= xPos && mousePosX <= xPos + 16 && mousePosY >= yPos && mousePosY <= yPos + 16)
	                                {
	                                    GL11.glDepthMask(true);
	                                    GL11.glEnable(GL11.GL_DEPTH_TEST);
	                                    GL11.glPushMatrix();
	                                    GL11.glTranslatef(0, 0, 300);
	                                    int k = this.fontRendererObj.getStringWidth(((ItemStack) next).getDisplayName());
	                                    int j2 = mousePosX - k / 2;
	                                    int k2 = mousePosY - 12;
	                                    int i1 = 8;

	                                    if (j2 + k > this.width)
	                                    {
	                                        j2 -= (j2 - this.width + k);
	                                    }

	                                    if (k2 + i1 + 6 > this.height)
	                                    {
	                                        k2 = this.height - i1 - 6;
	                                    }

	                                    int j1 = ColorUtil.to32BitColor(190, 0, 153, 255);
	                                    this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
	                                    this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
	                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
	                                    this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
	                                    this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
	                                    int k1 = ColorUtil.to32BitColor(170, 0, 153, 255);
	                                    int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
	                                    this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
	                                    this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
	                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
	                                    this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

	                                    this.fontRendererObj.drawString(((ItemStack) next).getDisplayName(), j2, k2, ColorUtil.to32BitColor(255, 255, 255, 255));

	                                    GL11.glPopMatrix();
	                                }

	                                str = "" + e.getValue();
	                                boolean valid = amount >= e.getValue();
	                                if (!valid && validInputMaterials)
	                                {
	                                    validInputMaterials = false;
	                                }
	                                int color = valid | this.mc.player.capabilities.isCreativeMode ? ColorUtil.to32BitColor(255, 0, 255, 0) : ColorUtil.to32BitColor(255, 255, 0, 0);
	                                this.fontRendererObj.drawString(str, xPos + 8 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 170 + canCreateOffset, color);
	                            }
	                            else if (next instanceof Collection)
	                            {
	                                Collection<ItemStack> items = (Collection<ItemStack>) next;

	                                int amount = 0;

	                                for (ItemStack stack : items)
	                                {
	                                    amount += getAmountInInventory(stack);
	                                }

	                                RenderHelper.enableGUIStandardItemLighting();

	                                Iterator<ItemStack> it = items.iterator();
	                                int count = 0;
	                                int toRenderIndex = (this.ticksSinceMenuOpen / 20) % items.size();
	                                ItemStack toRender = null;
	                                while (it.hasNext())
	                                {
	                                    ItemStack stack = it.next();
	                                    if (count == toRenderIndex)
	                                    {
	                                        toRender = stack;
	                                        break;
	                                    }
	                                    count++;
	                                }

	                                if (toRender == null)
	                                {
	                                    continue;
	                                }

	                                this.itemRender.renderItemAndEffectIntoGUI(toRender, xPos, yPos);
	                                this.itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, toRender, xPos, yPos, null);
	                                RenderHelper.disableStandardItemLighting();
	                                GL11.glEnable(GL11.GL_BLEND);

	                                if (mousePosX >= xPos && mousePosX <= xPos + 16 && mousePosY >= yPos && mousePosY <= yPos + 16)
	                                {
	                                    GL11.glDepthMask(true);
	                                    GL11.glEnable(GL11.GL_DEPTH_TEST);
	                                    GL11.glPushMatrix();
	                                    GL11.glTranslatef(0, 0, 300);
	                                    int k = this.fontRendererObj.getStringWidth(toRender.getDisplayName());
	                                    int j2 = mousePosX - k / 2;
	                                    int k2 = mousePosY - 12;
	                                    int i1 = 8;

	                                    if (j2 + k > this.width)
	                                    {
	                                        j2 -= (j2 - this.width + k);
	                                    }

	                                    if (k2 + i1 + 6 > this.height)
	                                    {
	                                        k2 = this.height - i1 - 6;
	                                    }

	                                    int j1 = ColorUtil.to32BitColor(190, 0, 153, 255);
	                                    this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
	                                    this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
	                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
	                                    this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
	                                    this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
	                                    int k1 = ColorUtil.to32BitColor(170, 0, 153, 255);
	                                    int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
	                                    this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
	                                    this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
	                                    this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
	                                    this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

	                                    this.fontRendererObj.drawString(toRender.getDisplayName(), j2, k2, ColorUtil.to32BitColor(255, 255, 255, 255));

	                                    GL11.glPopMatrix();
	                                }

	                                str = "" + e.getValue();
	                                boolean valid = amount >= e.getValue();
	                                if (!valid && validInputMaterials)
	                                {
	                                    validInputMaterials = false;
	                                }
	                                int color = valid | this.mc.player.capabilities.isCreativeMode ? ColorUtil.to32BitColor(255, 0, 255, 0) : ColorUtil.to32BitColor(255, 255, 0, 0);
	                                this.fontRendererObj.drawString(str, xPos + 8 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 170 + canCreateOffset, color);
	                            }

	                            i++;
	                        }

	                        if (validInputMaterials || this.mc.player.capabilities.isCreativeMode)
	                        {
	                            GL11.glColor4f(0.0F, 1.0F, 0.1F, 1);
	                        }
	                        else
	                        {
	                            GL11.glColor4f(1.0F, 0.0F, 0.0F, 1);
	                        }

	                        this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);

	                        if (!this.mapMode)
	                        {
	                            if (mousePosX >= width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95 && mousePosX <= width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE && mousePosY >= GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 182 + canCreateOffset && mousePosY <= GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 182 + 12 + canCreateOffset)
	                            {
	                                this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 182 + canCreateOffset, 93, 12, 0, 174, 93, 12, false, false);
	                            }
	                        }

	                        this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 95, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 182 + canCreateOffset, 93, 12, 0, 174, 93, 12, false, false);

	                        int color = (int) ((Math.sin(this.ticksSinceMenuOpen / 5.0) * 0.5 + 0.5) * 255);
	                        this.drawSplitString(GCCoreUtil.translate("gui.message.can_create_space_station.name"), width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 48, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 137, 91, ColorUtil.to32BitColor(255, color, 255, color), true, false);

	                        if (!mapMode)
	                        {
	                            this.drawSplitString(GCCoreUtil.translate("gui.message.create_ss.name").toUpperCase(), width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 48, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 185 + canCreateOffset, 91, ColorUtil.to32BitColor(255, 255, 255, 255), false, false);
	                        }
	                    }
	                    else
	                    {
	                        this.drawSplitString(GCCoreUtil.translate("gui.message.cannot_create_space_station.name"), width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 48, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 138, 91, ColorUtil.to32BitColor(255, 255, 255, 255), true, false);
	                    }
	                }

	                // Catalog overlay
	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F - Math.min(0.3F, this.ticksSinceSelection / 50.0F));
	                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 74, 11, 0, 392, 148, 22, false, false);
	                str = GCCoreUtil.translate("gui.message.catalog.name").toUpperCase();
	                this.fontRendererObj.drawString(str, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 40 - fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 1, ColorUtil.to32BitColor(255, 255, 255, 255));

	                // Top bar title:
	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                if (this.selectedBody instanceof Satellite)
	                {
	                    if (this.selectedStationOwner.length() == 0 || !this.selectedStationOwner.equalsIgnoreCase(this.mc.player.getGameProfile().getName()))
	                    {
	                        GL11.glColor4f(1.0F, 0.0F, 0.0F, 1);
	                    }
	                    else
	                    {
	                        GL11.glColor4f(0.0F, 1.0F, 0.0F, 1);
	                    }
	                    this.drawTexturedModalRect(width / 2 - 47, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 94, 11, 0, 414, 188, 22, false, false);
	                }
	                else
	                {
	                    this.drawTexturedModalRect(width / 2 - 47, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 94, 11, 0, 414, 188, 22, false, false);
	                }
	                if (this.selectedBody.getTierRequirement() >= 0 && (!(this.selectedBody instanceof Satellite)))
	                {
	                    boolean canReach;
	                    if (!this.selectedBody.getReachable() || (this.possibleBodies != null && !this.possibleBodies.contains(this.selectedBody)))
	                    {
	                        canReach = false;
	                        GL11.glColor4f(1.0F, 0.0F, 0.0F, 1);
	                    }
	                    else
	                    {
	                        canReach = true;
	                        GL11.glColor4f(0.0F, 1.0F, 0.0F, 1);
	                    }
	                    this.drawTexturedModalRect(width / 2 - 30, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 11, 30, 11, 0, 414, 60, 22, false, false);
	                    this.drawTexturedModalRect(width / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 11, 30, 11, 128, 414, 60, 22, false, false);
	                    str = GCCoreUtil.translateWithFormat("gui.message.tier.name", this.selectedBody.getTierRequirement() == 0 ? "?" : this.selectedBody.getTierRequirement());
	                    this.fontRendererObj.drawString(str, width / 2 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 13, canReach ? ColorUtil.to32BitColor(255, 140, 140, 140) : ColorUtil.to32BitColor(255, 255, 100, 100));
	                }
	                else if (this.selectedBody.getTierRequirement() == -2 && (this.selectedBody instanceof Star))
	                {
	                	GL11.glColor4f(1.0F, 1.0F, 0.0F, 1); 
	                	this.drawTexturedModalRect(width / 2 - 40, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 11, 40, 11, 0, 414, 90, 22, false, false);
		                this.drawTexturedModalRect(width / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 11, 60, 11, 121, 414, 100, 22, false, false);
		                str = GCCoreUtil.translate("gui.info.blackhole.name");
		                this.fontRendererObj.drawString(str, width / 2 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 13, ColorUtil.to32BitColor(255, 255, 255, 0));
		                
	                }
	                str = this.selectedBody.getLocalizedName();

	                if (this.selectedBody instanceof Satellite)
	                {
	                    str = GCCoreUtil.translate("gui.message.rename.name").toUpperCase();
	                }

	                this.fontRendererObj.drawString(str, width / 2 - this.fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 2, ColorUtil.to32BitColor(255, 255, 255, 255));

	                // Catalog wedge:
	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                this.drawTexturedModalRect(GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 4, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 83, 12, 0, 477, 83, 12, false, false);

	                if (!this.mapMode)
	                {
	                    if (!this.selectedBody.getReachable() || (this.possibleBodies != null && !this.possibleBodies.contains(this.selectedBody)) || (this.selectedBody instanceof Satellite && this.selectedStationOwner.equals("")))
	                    {
	                        GL11.glColor4f(1.0F, 0.0F, 0.0F, 1);
	                    }
	                    else
	                    {
	                        GL11.glColor4f(0.0F, 1.0F, 0.0F, 1);
	                    }

	                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 74, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE, 74, 11, 0, 392, 148, 22, true, false);
	                    str = GCCoreUtil.translate("gui.message.launch.name").toUpperCase();
	                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	                    this.fontRendererObj.drawString(str, width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 40 - fontRendererObj.getStringWidth(str) / 2, GuiCelestialSelection.BORDER_SIZE + GuiCelestialSelection.BORDER_EDGE_SIZE + 2, ColorUtil.to32BitColor(255, 255, 255, 255));
	                }

	                if (this.selectionState == EnumSelection.SELECTED && !(this.selectedBody instanceof Satellite))
	                {
	                    handledSliderPos = true;

	                    int sliderPos = this.zoomTooltipPos;
	                    if (zoomTooltipPos != 38)
	                    {
	                        sliderPos = Math.min(this.ticksSinceSelection * 2, 38);
	                        this.zoomTooltipPos = sliderPos;
	                    }

	                    GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
	                    this.drawTexturedModalRect(width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 182, height - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - sliderPos, 83, 38, 512 - 166, 512 - 76, 166, 76, true, false);

	                    boolean flag0 = GalaxyRegistry.getSatellitesForCelestialBody(this.selectedBody).size() > 0;
	                    boolean flag1 = this.selectedBody instanceof Planet && GalaxyRegistry.getMoonsForPlanet((Planet) this.selectedBody).size() > 0;
	                    if (flag0 && flag1)
	                    {
	                        this.drawSplitString(GCCoreUtil.translate("gui.message.click_again.0.name"), width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 182 + 41, height - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE + 2 - sliderPos, 79, ColorUtil.to32BitColor(255, 150, 150, 150), false, false);
	                    }
	                    else if (!flag0 && flag1)
	                    {
	                        this.drawSplitString(GCCoreUtil.translate("gui.message.click_again.1.name"), width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 182 + 41, height - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE + 6 - sliderPos, 79, ColorUtil.to32BitColor(255, 150, 150, 150), false, false);
	                    }
	                    else if (flag0)
	                    {
	                        this.drawSplitString(GCCoreUtil.translate("gui.message.click_again.2.name"), width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 182 + 41, height - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE + 6 - sliderPos, 79, ColorUtil.to32BitColor(255, 150, 150, 150), false, false);
	                    }
	                    else
	                    {
	                        this.drawSplitString(GCCoreUtil.translate("gui.message.click_again.3.name"), width - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE - 182 + 41, height - GuiCelestialSelection.BORDER_SIZE - GuiCelestialSelection.BORDER_EDGE_SIZE + 11 - sliderPos, 79, ColorUtil.to32BitColor(255, 150, 150, 150), false, false);
	                    }

	                }

	                if (this.selectedBody instanceof Satellite && renamingSpaceStation)
	                {
	                    this.drawDefaultBackground();
	                    GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	                    this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain1);
	                    this.drawTexturedModalRect(width / 2 - 90, this.height / 2 - 38, 179, 67, 159, 0, 179, 67, false, false);
	                    this.drawTexturedModalRect(width / 2 - 90 + 4, this.height / 2 - 38 + 2, 171, 10, 159, 92, 171, 10, false, false);
	                    this.drawTexturedModalRect(width / 2 - 90 + 8, this.height / 2 - 38 + 18, 161, 13, 159, 67, 161, 13, false, false);
	                    this.drawTexturedModalRect(width / 2 - 90 + 17, this.height / 2 - 38 + 59, 72, 12, 159, 80, 72, 12, true, false);
	                    this.drawTexturedModalRect(width / 2, this.height / 2 - 38 + 59, 72, 12, 159, 80, 72, 12, false, false);
	                    str = GCCoreUtil.translate("gui.message.assign_name.name");
	                    this.fontRendererObj.drawString(str, width / 2 - this.fontRendererObj.getStringWidth(str) / 2, this.height / 2 - 35, ColorUtil.to32BitColor(255, 255, 255, 255));
	                    str = GCCoreUtil.translate("gui.message.apply.name");
	                    this.fontRendererObj.drawString(str, width / 2 - this.fontRendererObj.getStringWidth(str) / 2 - 36, this.height / 2 + 23, ColorUtil.to32BitColor(255, 255, 255, 255));
	                    str = GCCoreUtil.translate("gui.message.cancel.name");
	                    this.fontRendererObj.drawString(str, width / 2 + 36 - this.fontRendererObj.getStringWidth(str) / 2, this.height / 2 + 23, ColorUtil.to32BitColor(255, 255, 255, 255));

	                    if (this.renamingString == null)
	                    {
	                        Satellite selectedSatellite = (Satellite) this.selectedBody;
	                        String playerName = FMLClientHandler.instance().getClient().player.getGameProfile().getName();
	                        this.renamingString = this.spaceStationMap.get(getSatelliteParentID(selectedSatellite)).get(playerName).getStationName();
	                        if (this.renamingString == null)
	                        {
	                            this.renamingString = this.spaceStationMap.get(getSatelliteParentID(selectedSatellite)).get(playerName.toLowerCase()).getStationName();
	                        }
	                        if (this.renamingString == null)
	                        {
	                            this.renamingString = "";
	                        }
	                    }

	                    str = this.renamingString;
	                    String str0 = this.renamingString;

	                    if ((this.ticksSinceMenuOpen / 10) % 2 == 0)
	                    {
	                        str0 += "_";
	                    }

	                    this.fontRendererObj.drawString(str0, width / 2 - this.fontRendererObj.getStringWidth(str) / 2, this.height / 2 - 17, ColorUtil.to32BitColor(255, 255, 255, 255));
	                }

//	                this.mc.renderEngine.bindTexture(GuiCelestialSelection.guiMain0);
//	                GL11.glColor4f(0.0F, 0.6F, 1.0F, 1);
	            }
	        }

	        if (!handledSliderPos)
	        {
	            this.zoomTooltipPos = 0;
	            this.zoomPos = 0;
	            this.zoomPos1 = 0;
	        }
	}
	private String localeBoolean(boolean bol)
	{
		if(bol) return GCCoreUtil.translate("gui.message.yes");
		else 
		{
			if(this.selectedBody == GalacticraftCore.planetOverworld) return GCCoreUtil.translate("gui.message.yes");
				return GCCoreUtil.translate("gui.message.no");
		}
	}
	private String classPlanet(CelestialBody body, WorldProvider dim)
	{	
		
		if(body.getReachable() && body != GalacticraftCore.planetOverworld)
		{
			if(body.atmosphere.hasNoGases() && !((IGalacticraftWorldProvider)dim).hasBreathableAtmosphere())
			{
				if(dim instanceof IAdvancedSpace && ((IAdvancedSpace)dim).getClassPlanet() == IAdvancedSpace.EnumClassPlanet.ASTEROIDE || body == AsteroidsModule.planetAsteroids)return GCCoreUtil.translate("gui.info.asteroid.name");
				
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() >= 2.0F) return GCCoreUtil.translate("gui.info.hot.name") + " " + GCCoreUtil.translate("gui.info.selena.name");
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() >= 1.0F) return GCCoreUtil.translate("gui.info.warm.name") + " " + GCCoreUtil.translate("gui.info.selena.name");
				
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() == 0.0F) return GCCoreUtil.translate("gui.info.comfort.name") + " " + GCCoreUtil.translate("gui.info.selena.name");
				
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() <= -1.0F) return GCCoreUtil.translate("gui.info.cool.name") + " " + GCCoreUtil.translate("gui.info.selena.name");
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() <= -2.0F) return GCCoreUtil.translate("gui.info.cold.name") + " " + GCCoreUtil.translate("gui.info.selena.name");
			
			}
			else
			{
				if(body.atmosphere.isGasPresent(EnumAtmosphericGas.OXYGEN) || ((IGalacticraftWorldProvider)dim).hasBreathableAtmosphere())
				{
					if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() >= 1.0F) return GCCoreUtil.translate("gui.info.warm.name") + " " + GCCoreUtil.translate("gui.info.terra.name");
					
					else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() == 0.0F) return GCCoreUtil.translate("gui.info.comfort.name") + " " + GCCoreUtil.translate("gui.info.terra.name");
					
					else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() <= -1.0F) return GCCoreUtil.translate("gui.info.cool.name") + " " + GCCoreUtil.translate("gui.info.terra.name");
	
				}
				else if(!body.atmosphere.isGasPresent(EnumAtmosphericGas.OXYGEN) && body.atmosphere.isGasPresent(EnumAtmosphericGas.WATER))
				{
					if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() >= 1.0F) return GCCoreUtil.translate("gui.info.titan.name") + " (" + GCCoreUtil.translate("gui.info.iceworld.name") + ")";
					if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() <= -1.0F) return GCCoreUtil.translate("gui.info.icy.name") + " (" + GCCoreUtil.translate("gui.info.iceworld.name") + ")";
					
				}
				
				if(dim instanceof IAdvancedSpace && ((IAdvancedSpace)dim).getClassPlanet() == IAdvancedSpace.EnumClassPlanet.OCEANIDE) return GCCoreUtil.translate("gui.info.oceanide.name");
					
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() >= 2.0F) return GCCoreUtil.translate("gui.info.hot.name") + " " + GCCoreUtil.translate("gui.info.desert.name");
					
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() >= 1.0F) return GCCoreUtil.translate("gui.info.warm.name") + " " + GCCoreUtil.translate("gui.info.desert.name");
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() == 0.0F) return GCCoreUtil.translate("gui.info.comfort.name") + " " + GCCoreUtil.translate("gui.info.desert.name");
				
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() <= -1.0F) return GCCoreUtil.translate("gui.info.cool.name") + " " + GCCoreUtil.translate("gui.info.desert.name");
				else if(((IGalacticraftWorldProvider) dim).getThermalLevelModifier() <= -2.0F) return GCCoreUtil.translate("gui.info.cold.name") + " " + GCCoreUtil.translate("gui.info.desert.name");
					
			}
		}
		else if(body == GalacticraftCore.planetOverworld) return GCCoreUtil.translate("gui.info.comfort.name") + " " + GCCoreUtil.translate("gui.info.terra.name");
		
		return GCCoreUtil.translate("gui.message.unknown");
	}
	 
	@Override
    public void drawScreen(int mousePosX, int mousePosY, float partialTicks)
    {
        if (Mouse.hasWheel())
        {
            float wheel = Mouse.getDWheel() / (this.selectedBody == null ? 500.0F : 250.0F);

            if (wheel != 0)
            {
                if (this.selectedBody == null || (this.viewState == EnumView.PREVIEW && !this.isZoomed()))
                {
                    //Minimum zoom increased from 0.55F to 1F to allow zoom out to see other solar systems
                    this.zoom = Math.min(Math.max(this.zoom + wheel * ((this.zoom + 2.0F)) / 10.0F, -1.0F), 3);
                }
                else
                {
                    this.planetZoom = Math.min(Math.max(this.planetZoom + wheel, -4.9F), 5);
                }
            }
        }

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        Matrix4f camMatrix = new Matrix4f();
        Matrix4f.translate(new Vector3f(0.0F, 0.0F, -9000.0F), camMatrix, camMatrix); // See EntityRenderer.java:setupOverlayRendering
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.m00 = 2.0F / width;
        viewMatrix.m11 = 2.0F / -height;
        viewMatrix.m22 = -2.0F / 9000.0F;
        viewMatrix.m30 = -1.0F;
        viewMatrix.m31 = 1.0F;
        viewMatrix.m32 = -2.0F;

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        FloatBuffer fb = BufferUtils.createFloatBuffer(16 * Float.SIZE);
        fb.rewind();
        viewMatrix.store(fb);
        fb.flip();
        GL11.glMultMatrix(fb);
        fb.clear();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        fb.rewind();
        camMatrix.store(fb);
        fb.flip();
        fb.clear();
        GL11.glMultMatrix(fb);

        this.setBlackBackground();

        GL11.glPushMatrix();
        Matrix4f worldMatrix = this.setIsometric(partialTicks);
        mainWorldMatrix = worldMatrix;
        float gridSize = 7000F; //194.4F;
        //TODO: Add dynamic map sizing, to allow the map to be small by default and expand when more distant solar systems are added.
        //this.drawGrid(gridSize, height / 3 / 3.5F);
        this.drawCircles();
        GL11.glPopMatrix();

        HashMap<CelestialBody, Matrix4f> matrixMap = this.drawCelestialBodies(worldMatrix);

        this.planetPosMap.clear();

        for (Map.Entry<CelestialBody, Matrix4f> e : matrixMap.entrySet())
        {
            Matrix4f planetMatrix = e.getValue();
            Matrix4f matrix0 = Matrix4f.mul(viewMatrix, planetMatrix, planetMatrix);
            int x = (int) Math.floor((matrix0.m30 * 0.5 + 0.5) * Minecraft.getMinecraft().displayWidth);
            int y = (int) Math.floor(Minecraft.getMinecraft().displayHeight - (matrix0.m31 * 0.5 + 0.5) * Minecraft.getMinecraft().displayHeight);
            Vector2f vec = new Vector2f(x, y);

            Matrix4f scaleVec = new Matrix4f();
            scaleVec.m00 = matrix0.m00;
            scaleVec.m11 = matrix0.m11;
            scaleVec.m22 = matrix0.m22;
            Vector4f newVec = Matrix4f.transform(scaleVec, new Vector4f(2, -2, 0, 0), null);
            float iconSize = (newVec.y * (Minecraft.getMinecraft().displayHeight / 2.0F)) * (e.getKey() instanceof Star ? 2 : 1) * (e.getKey() == this.selectedBody ? 1.5F : 1.0F);

            this.planetPosMap.put(e.getKey(), new Vector3f(vec.x, vec.y, iconSize)); // Store size on-screen in Z-value for ease
        }

        this.drawSelectionCursor(fb, worldMatrix);

        try {
            this.drawButtons(mousePosX, mousePosY);
        } catch (Exception e)
        {
            if (!this.errorLogged)
            {
                this.errorLogged = true;
                GCLog.severe("Problem identifying planet or dimension in an add on for Galacticraft!");
                GCLog.severe("(The problem is likely caused by a dimension ID conflict.  Check configs for dimension clashes.  You can also try disabling Mars space station in configs.)");
                e.printStackTrace();
            }
        }

        this.drawBorder();
        GL11.glPopMatrix();

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
	 	
	private boolean isZoomed()
	{
		return this.selectionState == EnumSelection.ZOOMED;
	}
	 	
	private boolean isSelected()
	{
		return this.selectionState != EnumSelection.UNSELECTED;
	}
}
