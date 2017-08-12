package galaxyspace.core.render.sky;

import galaxyspace.GalaxySpace;

import java.util.Calendar;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.FMLClientHandler;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public abstract class SkyProviderBase extends IRenderHandler
{
    private static final ResourceLocation sunTexture = new ResourceLocation("textures/environment/sun.png");
    private static final ResourceLocation lmcTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/LMC.png");
    private static final ResourceLocation smcTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/SMC.png");
    private static final ResourceLocation andromedaTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/Andromeda.png");
    private static final ResourceLocation moonTexture = new ResourceLocation("textures/environment/moon_phases.png");
    private static final ResourceLocation barnardaloopTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/environment/background/BarnardaLoop.png");
 
    private final ResourceLocation planetToRender = new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/earth.png");
    
    public int starList;
    public int glSkyList;
    public int glSkyList2;
    
    private float sunSize;
    protected float ticks;
    public float[] afloat = new float[4];
    
    protected Minecraft mc = Minecraft.getMinecraft();
    
    public SkyProviderBase()
    {
        int displayLists = GLAllocation.generateDisplayLists(3);
        this.starList = displayLists;
        this.glSkyList = displayLists + 1;
        this.glSkyList2 = displayLists + 2;

        // Bind stars to display list
        GL11.glPushMatrix();
        GL11.glNewList(this.starList, GL11.GL_COMPILE);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();

        final Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldRenderer = tessellator.getBuffer();
        GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
        final byte byte2 = 64;
        final int i = 256 / byte2 + 2;
        float f = 16F;

        for (int j = -byte2 * i; j <= byte2 * i; j += byte2)
        {
            for (int l = -byte2 * i; l <= byte2 * i; l += byte2)
            {
                worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
                worldRenderer.pos(j + 0, f, l + 0).endVertex();
                worldRenderer.pos(j + byte2, f, l + 0).endVertex();
                worldRenderer.pos(j + byte2, f, l + byte2).endVertex();
                worldRenderer.pos(j + 0, f, l + byte2).endVertex();
                tessellator.draw();
            }
        }

        GL11.glEndList();
        GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
        f = -16F;
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        for (int k = -byte2 * i; k <= byte2 * i; k += byte2)
        {
            for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2)
            {
                worldRenderer.pos(k + byte2, f, i1 + 0).endVertex();
                worldRenderer.pos(k + 0, f, i1 + 0).endVertex();
                worldRenderer.pos(k + 0, f, i1 + byte2).endVertex();
                worldRenderer.pos(k + byte2, f, i1 + byte2).endVertex();
            }
        }

        tessellator.draw();
        GL11.glEndList();
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc)
    {
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        Vec3d vec3 = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
        float f1 = (float) vec3.xCoord;
        float f2 = (float) vec3.yCoord;
        float f3 = (float) vec3.zCoord;
        float f6;

        if (mc.gameSettings.anaglyph)
        {
            float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }

        GL11.glColor3f(f1, f2, f3);
        Tessellator tessellator1 = Tessellator.getInstance();
        VertexBuffer worldRenderer1 = tessellator1.getBuffer();
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glColor3f(f1, f2, f3);
        GL11.glCallList(this.glSkyList);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        float f7;
        float f8;
        float f9;
        float f10;

        float rain = 1.0F - world.getRainStrength(partialTicks);
       
        float f18 = world.getStarBrightness(partialTicks) * rain;

        if (f18 > 0.0F)
        {
        	 GL11.glPushMatrix();
        	 GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        	 GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        	 GL11.glRotatef(-19.0F, 0, 1.0F, 0);
        	 GL11.glColor4f(f18, f18, f18, f18);
        	 GL11.glCallList(this.starList);
        	 GL11.glPopMatrix();
        }

       
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glPushMatrix();
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        afloat[0] = 255 / 255.0F;
        afloat[1] = 194 / 255.0F;
        afloat[2] = 180 / 255.0F;
        afloat[3] = 0.3F;
        this.colorSunAura();
        f6 = afloat[0];
        f7 = afloat[1];
        f8 = afloat[2];
        float f11;

        if (mc.gameSettings.anaglyph)
        {
            f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
            f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
            f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
            f6 = f9;
            f7 = f10;
            f8 = f11;
        }

        f18 = 1.0F - f18;

        worldRenderer1.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        float r = f6 * f18;
        float g = f7 * f18;
        float b = f8 * f18;
        float a = afloat[3] * 2 / f18;
        worldRenderer1.pos(0.0D, 100.0D, 0.0D).color(r, g, b, a).endVertex();
        byte b0 = 16;
        r = afloat[0] * f18;
        g = afloat[1] * f18;
        b = afloat[2] * f18;
        a = 0.0F;
        
        if(this.modeLight() != 2)
        {
	        // Render sun aura
	        f10 = sunSize() + 5.5F;
	        worldRenderer1.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();
	        worldRenderer1.pos(0, 100.0D, (double) -f10 * 1.5F).color(r, g, b, a).endVertex();
	        worldRenderer1.pos(f10, 100.0D, -f10).color(r, g, b, a).endVertex();
	        worldRenderer1.pos((double) f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
	        worldRenderer1.pos(f10, 100.0D, f10).color(r, g, b, a).endVertex();
	        worldRenderer1.pos(0, 100.0D, (double) f10 * 1.5F).color(r, g, b, a).endVertex();
	        worldRenderer1.pos(-f10, 100.0D, f10).color(r, g, b, a).endVertex();
	        worldRenderer1.pos((double) -f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
	        worldRenderer1.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();
	
	        tessellator1.draw();
	        worldRenderer1.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
	        r = f6 * f18;
	        g = f7 * f18;
	        b = f8 * f18;
	        a = afloat[3] * f18;
	        worldRenderer1.pos(0.0D, 100.0D, 0.0D).color(r, g, b, a).endVertex();
	        r = afloat[0] * f18;
	        g = afloat[1] * f18;
	        b = afloat[2] * f18;
	        a = 0.0F;

        }
        
        // Render larger sun aura
        f10 = sunSize() + 15.5F; //19
        worldRenderer1.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();
        worldRenderer1.pos(0, 100.0D, (double) -f10 * 1.5F).color(r, g, b, a).endVertex();
        worldRenderer1.pos(f10, 100.0D, -f10).color(r, g, b, a).endVertex();
        worldRenderer1.pos((double) f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
        worldRenderer1.pos(f10, 100.0D, f10).color(r, g, b, a).endVertex();
        worldRenderer1.pos(0, 100.0D, (double) f10 * 1.5F).color(r, g, b, a).endVertex();
        worldRenderer1.pos(-f10, 100.0D, f10).color(r, g, b, a).endVertex();
        worldRenderer1.pos((double) -f10 * 1.5F, 100.0D, 0).color(r, g, b, a).endVertex();
        worldRenderer1.pos(-f10, 100.0D, -f10).color(r, g, b, a).endVertex();

        tessellator1.draw();
        GL11.glPopMatrix();
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
        GL11.glPushMatrix();

        f7 = 0.0F;
        f8 = 0.0F;
        f9 = 0.0F;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F - Minecraft.getMinecraft().world.getRainStrength(partialTicks));
        GL11.glTranslatef(f7, f8, f9);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        // Render sun
        if(this.modeLight() != 2)
        {
	        f10 = sunSize();
	        Calendar calendar = Calendar.getInstance();
	        if (calendar.get(2) + 1 == 10 && calendar.get(5) >= 30 && calendar.get(5) <= 31 || calendar.get(2) + 1 == 11 && calendar.get(5) <= 1)
	        {
	        	//FMLClientHandler.instance().getClient().renderEngine.bindTexture(SkyProviderMoon.pumpkinsunTexture);
	        }
	        else if(this.sunImage() != null) FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.sunImage());
	        else FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.sunTexture);
	        worldRenderer1.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
	        worldRenderer1.pos(-f10, 99.9D, -f10).endVertex();
	        worldRenderer1.pos(f10, 99.9D, -f10).endVertex();
	        worldRenderer1.pos(f10, 99.9D, f10).endVertex();
	        worldRenderer1.pos(-f10, 99.9D, f10).endVertex();
	        tessellator1.draw();
        }
	    if(this.enableBaseImages())
	    {
	        if(Minecraft.getMinecraft().world.provider instanceof WorldProviderSurface)
	        {
		        f10 = 20.0F;
		        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.moonTexture);
		        float sinphi = this.mc.world.getMoonPhase();
		        final int cosphi = (int) (sinphi % 4);
		        final int var29 = (int) (sinphi / 4 % 2);
		        final float yy = (cosphi) / 4.0F;
		        final float rand7 = (var29) / 2.0F;
		        final float zz = (cosphi + 1) / 4.0F;
		        final float rand9 = (var29 + 1) / 2.0F;
		        worldRenderer1.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		        worldRenderer1.pos(-r, -100.0D, r).tex(zz, rand9).endVertex();
		        worldRenderer1.pos(r, -100.0D, r).tex(yy, rand9).endVertex();
		        worldRenderer1.pos(r, -100.0D, -r).tex(yy, rand7).endVertex();
		        worldRenderer1.pos(-r, -100.0D, -r).tex(zz, rand7).endVertex();
		        tessellator1.draw();       
	        }
	        float light = 0.0F;
	        
	        if(this.modeLight() == 0) light = FMLClientHandler.instance().getClient().world.getStarBrightness(1.0F) - Minecraft.getMinecraft().world.getRainStrength(partialTicks);
	        if(this.modeLight() == 1) light = 1.0F;
	       	        
	        this.renderImage(this.lmcTexture, -90.0F, 90.0F, 0.0F, 15.0F, true,  light);
	        this.renderImage(this.smcTexture, 0.0F, -40.0F, 0.0F, 5.0F, true,  light);
	        this.renderImage(this.andromedaTexture, 100.0F, -150.0F, 0.0F, 4.0F, true, light);
	        this.renderImage(this.barnardaloopTexture, 200.0F, -70.0F, 0.0F, 40.0F, true, light);
        }
        GL11.glDisable(GL11.GL_BLEND);
        this.rendererSky(tessellator1, f10);
        
        GL11.glPopMatrix();        
    	GL11.glPushMatrix();
    	
        f10 = 120.0F;
        GL11.glScalef(0.6F, 0.6F, 0.6F);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-360F, 1.0F, 0.0F, 0.0F);

        GL11.glTranslatef(0.0F, -(float)mc.player.posY / 8, 0.0F);
        
        if(!(mc.world.provider instanceof WorldProviderSurface)) mc.renderEngine.bindTexture(((IGalacticraftWorldProvider)mc.world.provider).getCelestialBody().getBodyIcon());
        else mc.renderEngine.bindTexture(this.planetToRender);
        
        worldRenderer1.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldRenderer1.pos(-f10, 100.0D, -f10).tex(0.0D, 0.0D).endVertex();
        worldRenderer1.pos(f10, 100.0D, -f10).tex(1.0D, 0.0D).endVertex();
        worldRenderer1.pos(f10, 100.0D, f10).tex(1.0D, 1.0D).endVertex();
        worldRenderer1.pos(-f10, 100.0D, f10).tex(0.0D, 1.0D).endVertex();
        tessellator1.draw();
        
       
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_FOG);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.0F, 0.0F, 0.0F);
        double d0 = mc.player.getPosition().getY() - world.getHorizon();

        /*if (d0 < 0.0D)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 12.0F, 0.0F);
            GL11.glCallList(this.glSkyList2);
            GL11.glPopMatrix();
            f8 = 1.0F;
            f9 = -((float) (d0 + 65.0D));
            f10 = -f8;
            tessellator1.startDrawingQuads();
            tessellator1.setColorRGBA_I(0, 255);
            tessellator1.addVertex(-f8, f9, f8);
            tessellator1.addVertex(f8, f9, f8);
            tessellator1.addVertex(f8, f10, f8);
            tessellator1.addVertex(-f8, f10, f8);
            tessellator1.addVertex(-f8, f10, -f8);
            tessellator1.addVertex(f8, f10, -f8);
            tessellator1.addVertex(f8, f9, -f8);
            tessellator1.addVertex(-f8, f9, -f8);
            tessellator1.addVertex(f8, f10, -f8);
            tessellator1.addVertex(f8, f10, f8);
            tessellator1.addVertex(f8, f9, f8);
            tessellator1.addVertex(f8, f9, -f8);
            tessellator1.addVertex(-f8, f9, -f8);
            tessellator1.addVertex(-f8, f9, f8);
            tessellator1.addVertex(-f8, f10, f8);
            tessellator1.addVertex(-f8, f10, -f8);
            tessellator1.addVertex(-f8, f10, -f8);
            tessellator1.addVertex(-f8, f10, f8);
            tessellator1.addVertex(f8, f10, f8);
            tessellator1.addVertex(f8, f10, -f8);
            tessellator1.draw();
        }*/

        if (world.provider.isSkyColored())
        {
            GL11.glColor3f(f1 * 0.2F + 0.04F, f2 * 0.2F + 0.04F, f3 * 0.6F + 0.1F);
        }
        else
        {
            GL11.glColor3f(f1, f2, f3);
        }

        //GL11.glPushMatrix();
        //GL11.glTranslatef(0.0F, -((float) (d0 - 16.0D)), 0.0F);
        //GL11.glCallList(this.glSkyList2);
        //GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
    }

    protected void renderImage(ResourceLocation image, float x, float y, float z,float f10, boolean withsun)
    {
    	this.renderImage(image, x, y, z, f10, withsun, FMLClientHandler.instance().getClient().world.getStarBrightness(1.0F));
    }
    protected void renderImage(ResourceLocation image, float x, float y, float z, float f10, boolean withsun, float alpha)
    {

	    	if(!withsun)
	    	{
		    	GL11.glPopMatrix();
		    	GL11.glPushMatrix();
	    	}
	    	
	    	Tessellator tessellator1 = Tessellator.getInstance();
	    	VertexBuffer worldRenderer1 = tessellator1.getBuffer();

	    	GL11.glRotatef(x, 0.0F, 1.0F, 0.0F); //Расположение по X
	    	GL11.glRotatef(y, 1.0F, 0.0F, 0.0F); //Расположение по Y
	    	GL11.glRotatef(z, 0.0F, 0.0F, 1.0F); //Расположение по Y
	    	GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
	    	FMLClientHandler.instance().getClient().renderEngine.bindTexture(image);
	    	worldRenderer1.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	        worldRenderer1.pos(-f10, 100.0D, -f10).tex(0.0D, 0.0D).endVertex();
	        worldRenderer1.pos(f10, 100.0D, -f10).tex(1.0D, 0.0D).endVertex();
	        worldRenderer1.pos(f10, 100.0D, f10).tex(1.0D, 1.0D).endVertex();
	        worldRenderer1.pos(-f10, 100.0D, f10).tex(0.0D, 1.0D).endVertex();
	    	tessellator1.draw();
    	
    	
    }
    private void renderStars()
    {
        final Random rand = new Random(10842L);
        final Tessellator var2 = Tessellator.getInstance();
        VertexBuffer worldRenderer = var2.getBuffer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
	
	        for (int starIndex = 0; starIndex < (ConfigManagerCore.moreStars ? 48000 : 6000); ++starIndex)
	        {
	            double var4 = rand.nextFloat() * 2.0F - 1.0F;
	            double var6 = rand.nextFloat() * 2.0F - 1.0F;
	            double var8 = rand.nextFloat() * 2.0F - 1.0F;
	            final double var10 = 0.15F + rand.nextFloat() * 0.1F;
	            double var12 = var4 * var4 + var6 * var6 + var8 * var8;
	
	            if (var12 < 1.0D && var12 > 0.01D)
	            {
	                var12 = 1.0D / Math.sqrt(var12);
	                var4 *= var12;
	                var6 *= var12;
	                var8 *= var12;
	                final double var14 = var4 * (ConfigManagerCore.moreStars ? rand.nextDouble() * 150D + 130D : 100.0D);
	                final double var16 = var6 * (ConfigManagerCore.moreStars ? rand.nextDouble() * 150D + 130D : 100.0D);
	                final double var18 = var8 * (ConfigManagerCore.moreStars ? rand.nextDouble() * 150D + 130D : 100.0D);
	                final double var20 = Math.atan2(var4, var8);
	                final double var22 = Math.sin(var20);
	                final double var24 = Math.cos(var20);
	                final double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
	                final double var28 = Math.sin(var26);
	                final double var30 = Math.cos(var26);
	                final double var32 = rand.nextDouble() * Math.PI * 2.0D;
	                final double var34 = Math.sin(var32);
	                final double var36 = Math.cos(var32);
	
	                for (int var38 = 0; var38 < 4; ++var38)
	                {
	                    final double var39 = 0.0D;
	                    final double var41 = ((var38 & 2) - 1) * var10;
	                    final double var43 = ((var38 + 1 & 2) - 1) * var10;
	                    final double var47 = var41 * var36 - var43 * var34;
	                    final double var49 = var43 * var36 + var41 * var34;
	                    final double var53 = var47 * var28 + var39 * var30;
	                    final double var55 = var39 * var28 - var47 * var30;
	                    final double var57 = var55 * var22 - var49 * var24;
	                    final double var61 = var49 * var22 + var55 * var24;
	                    worldRenderer.pos(var14 + var57, var16 + var53, var18 + var61).endVertex();
	                }
	            }
	        }
	
	        var2.draw();
	}
    

    private Vec3d getCustomSkyColor()
    {
        return new Vec3d(0.26796875D, 0.1796875D, 0.0D);
    }

    public float getSkyBrightness(float par1)
    {
        final float var2 = FMLClientHandler.instance().getClient().world.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.sin(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 1F;
    }
    
    protected abstract void rendererSky(Tessellator tessellator, float f10); 
    protected abstract int modeLight();
    protected abstract boolean enableBaseImages();
    protected abstract float sunSize();
    protected abstract ResourceLocation sunImage();
    protected abstract boolean enableStar();
    protected abstract void colorSunAura();
    
}