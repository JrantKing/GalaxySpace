package galaxyspace.systems.SolarSystem;

import galaxyspace.GalaxySpace;
import galaxyspace.core.coremod.bodies.BodiesInfo;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Satellite;
import micdoodle8.mods.galacticraft.api.world.EnumAtmosphericGas;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.venus.VenusModule;
import net.minecraft.util.ResourceLocation;


public class SolarSystemPlanets {

	public static Planet planetMercury;
	public static Planet planetVenus;
	public static Planet planetCeres;
	public static Planet planetJupiter;
	public static Planet planetSaturn;
	public static Planet planetUranus;
	public static Planet planetNeptune;
	public static Planet planetPluto;
	public static Planet planetKuiperBelt;
	public static Planet planetHaumea;
	public static Planet planetMakemake;
	public static Planet planetEris;
	public static Planet planetDeeDee;
	public static Planet planetZTest;
	
	public static Moon phobosMars;
	public static Moon deimosMars;
	
	public static Moon ioJupiter;
	public static Moon europaJupiter;
	public static Moon ganymedeJupiter;
	public static Moon callistoJupiter;
	
	public static Moon mimasSaturn;
	public static Moon enceladusSaturn;
	public static Moon tethysSaturn;
	public static Moon dioneSaturn;
	public static Moon rheyaSaturn;
	public static Moon titanSaturn;
	public static Moon iapetusSaturn;
	
	public static Moon mirandaUranus;
	public static Moon arielUranus;
	public static Moon umbrielUranus;
	public static Moon titaniaUranus;
	public static Moon oberonUranus;
	
	public static Moon proteusNeptune;
	public static Moon tritonNeptune;
	
	public static Moon charonPluto;
	
	//public static Satellite marsSpaceStation;
	public static Satellite venusSpaceStation;
	
	public static void init()
	{
		/*
		 * Sun Distance: 0.0F
		 * Mercury Distance: 0.5F
		 * Venus Distance: 0.75F
		 * Overworld Distance: 1.0F
		 		*Moon Distance: 13.0F	 
		 * Mars Distance: 1.25F
		 		*Phobos Distance: 8.0F
		 		*Deimos Distance: 16.0F 
		 * Ceres Distance: 1.5F
		 * Asteroids Distance: 1.75F
		 * Jupiter Distance: 2.0F
		 		*Io Distance: 10.0F
		 		*Europa Distance: 15.0F
		 		*Ganymede Distance: 20.0F
		 		*Callisto Distance: 30.0F
		 * Saturn Distance: 2.25F
		 		*Enceladus Distance: 15.0F
		 		*Titan Distance: 35.0F 
		 * Uranus Distance: 2.5F
		 * Neptune Distance: 2.75F
		 * Pluto Distance: 3.0F
		 * Kuiper Belt Distance: 3.25F
		 * Haumea Distance: 3.5F
		 * Makemake Distance: 3.75F
		 * Eris Distance: 4.0F
		*/
		GalacticraftCore.solarSystemSol.getMainStar().setTierRequired(-2);
		// TODO Mercury -------------------------------
    	planetMercury = (Planet) new Planet("mercury").setParentSolarSystem(GalacticraftCore.solarSystemSol);
    	planetMercury.setRingColorRGB(0.0F, 0.4F, 0.9F);
    	planetMercury.setPhaseShift(1.45F);
    	planetMercury.setTierRequired(4);
    	planetMercury.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.5F, 0.5F));
    	planetMercury.setRelativeOrbitTime(0.24096385542168674698795180722892F);
    	planetMercury.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/mercury.png"));
    	//planetMercury.setDimensionInfo(GSConfigDimensions.dimensionIDMercury, /*GSConfigCore.enableWorldEngine ? WorldProviderMercuryWE.class :*/ WorldProviderMercury.class);
    	// --------------------------------------------
    	// TODO Venus ---------------------------------
    	VenusModule.planetVenus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		/*planetVenus = (Planet) new Planet("venus").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetVenus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetVenus.setPhaseShift(2.0F);
		planetVenus.setTierRequired(4);
		planetVenus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(0.75F, 0.75F));
		planetVenus.setRelativeOrbitTime(0.61527929901423877327491785323111F);
		planetVenus.setBodyIcon(new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/venus.png"));
		//planetVenus.setDimensionInfo(GSConfigDimensions.dimensionIDVenus, WorldProviderVenus.class);
		planetVenus.atmosphereComponent(EnumAtmosphericGas.CO2).atmosphereComponent(EnumAtmosphericGas.HELIUM).atmosphereComponent(EnumAtmosphericGas.ARGON);
		
		venusSpaceStation = (Satellite) new Satellite("spaceStation.venus").setParentBody(SolarSystemPlanets.planetVenus);
		venusSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);
		venusSpaceStation.setRelativeSize(0.2667F);
		venusSpaceStation.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		venusSpaceStation.setRelativeOrbitTime(1 / 0.05F);
		//venusSpaceStation.setDimensionInfo(GSConfigDimensions.dimensionIDVenusOrbit, GSConfigDimensions.dimensionIDVenusOrbitStatic, WorldProviderVenusSS.class);
		venusSpaceStation.setTierRequired(4);
		venusSpaceStation.setBodyIcon(new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/gui/celestialbodies/spaceStation.png"));
		*/
		// --------------------------------------------
		// TODO Overworld -----------------------------
		//if(GSConfigCore.enableLeadGeneration) GameRegistry.registerWorldGenerator(new OverworldGenerator(GSBlocks.LeadOre, 0, 6, 0, 45, 4), 4);
		GalacticraftCore.satelliteSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);
		GalacticraftCore.moonMoon.setRingColorRGB(0.0F, 0.4F, 0.9F);
		// --------------------------------------------
		// TODO Mars ----------------------------------
		MarsModule.planetMars.setRingColorRGB(0.0F, 0.4F, 0.9F);
		
		phobosMars = (Moon) new Moon("Phobos").setParentPlanet(MarsModule.planetMars);
		phobosMars.setRingColorRGB(0.0F, 0.4F, 0.9F);
		phobosMars.setRelativeSize(0.0017F);
		phobosMars.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(8F, 8F));
		phobosMars.setRelativeOrbitTime(100F);
		phobosMars.setTierRequired(2);
		phobosMars.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/phobos.png"));
		//phobosMars.setDimensionInfo(GSConfigDimensions.dimensionIDPhobos, WorldProviderPhobos.class);
		
		deimosMars = (Moon) new Moon("Deimos").setParentPlanet(MarsModule.planetMars);
		deimosMars.setRingColorRGB(0.0F, 0.4F, 0.9F);
		deimosMars.setRelativeSize(0.0017F);
		deimosMars.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(16F, 16F));
		deimosMars.setRelativeOrbitTime(300F);
		deimosMars.setTierRequired(2);
		deimosMars.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/deimos.png"));
		//deimosMars.setDimensionInfo(GSConfigDimensions.dimensionIDDeimos, WorldProviderDeimos.class);
	/*	
		marsSpaceStation = (Satellite) new Satellite("spaceStation.mars").setParentBody(MarsModule.planetMars);
		marsSpaceStation.setRingColorRGB(0.0F, 0.4F, 0.9F);
		marsSpaceStation.setRelativeSize(0.2667F);
		marsSpaceStation.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		marsSpaceStation.setRelativeOrbitTime(1 / 0.05F);
		//marsSpaceStation.setDimensionInfo(GSConfigDimensions.dimensionIDMarsOrbit, GSConfigDimensions.dimensionIDMarsOrbitStatic, WorldProviderMarsSS.class);
		marsSpaceStation.setTierRequired(2);
		marsSpaceStation.setBodyIcon(new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/spaceStation.png"));
		*/
		// --------------------------------------------
		// TODO Asteroids -----------------------------
		planetCeres = (Planet) new Planet("Ceres").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetCeres.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetCeres.setPhaseShift(2.0F);
		planetCeres.setTierRequired(3);
		planetCeres.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.5F, 1.5F));
		planetCeres.setRelativeOrbitTime(15.0F);
		planetCeres.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/ceres.png"));
		//planetCeres.setDimensionInfo(GSConfigDimensions.dimensionIDCeres, WorldProviderCeres.class);
		
		AsteroidsModule.planetAsteroids.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(1.75F, 1.75F));
		// --------------------------------------------
		// TODO Jupiter -------------------------------
		planetJupiter = (Planet) new Planet("jupiter").setParentSolarSystem(GalacticraftCore.solarSystemSol);
        planetJupiter.setRingColorRGB(0.0F, 0.4F, 0.9F);
        planetJupiter.setPhaseShift((float) Math.PI);
        planetJupiter.setBodyIcon(new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/jupiter.png"));
        planetJupiter.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.0F, 2.0F));
        planetJupiter.setRelativeOrbitTime(11.861993428258488499452354874042F);
		
        ioJupiter = (Moon) new Moon("ioJupiter").setParentPlanet(SolarSystemPlanets.planetJupiter);
        ioJupiter.setRingColorRGB(0.0F, 0.4F, 0.9F);
		ioJupiter.setRelativeSize(0.0017F);
		ioJupiter.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		ioJupiter.setRelativeOrbitTime(50F);
		ioJupiter.setTierRequired(4);
		ioJupiter.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/io.png"));
		//ioJupiter.setDimensionInfo(GSConfigDimensions.dimensionIDIo, WorldProviderIo.class);
		
		europaJupiter = (Moon) new Moon("Europa").setParentPlanet(SolarSystemPlanets.planetJupiter);
		europaJupiter.setRingColorRGB(0.0F, 0.4F, 0.9F);
		europaJupiter.setRelativeSize(0.0017F);
		europaJupiter.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(15F, 15F));
		europaJupiter.setRelativeOrbitTime(200F);
		europaJupiter.setTierRequired(3);
		europaJupiter.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/europa.png"));
		//europaJupiter.setDimensionInfo(GSConfigDimensions.dimensionIDEuropa, WorldProviderEuropa.class);
		
		ganymedeJupiter = (Moon) new Moon("Ganymede").setParentPlanet(SolarSystemPlanets.planetJupiter);
		ganymedeJupiter.setRingColorRGB(0.0F, 0.4F, 0.9F);
		ganymedeJupiter.setRelativeSize(0.0017F);
		ganymedeJupiter.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(20F, 20F));
		ganymedeJupiter.setRelativeOrbitTime(350F);
		ganymedeJupiter.setTierRequired(3);
		ganymedeJupiter.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/ganymede.png"));
		//ganymedeJupiter.setDimensionInfo(GSConfigDimensions.dimensionIDGanymede, WorldProviderGanymede.class);
        
		callistoJupiter = (Moon) new Moon("Callisto").setParentPlanet(SolarSystemPlanets.planetJupiter);
		callistoJupiter.setRingColorRGB(0.0F, 0.4F, 0.9F);
		callistoJupiter.setRelativeSize(0.0017F);
		callistoJupiter.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(30F, 30F));
		callistoJupiter.setRelativeOrbitTime(500F);
		callistoJupiter.setTierRequired(3);
		callistoJupiter.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/callisto.png"));
		//callistoJupiter.setDimensionInfo(GSConfigDimensions.dimensionIDCallisto, WorldProviderCallisto.class);

		// -------------------------------------------- 
        // TODO Saturn --------------------------------
		planetSaturn = (Planet) new Planet("saturn").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetSaturn.setPhaseShift(5.45F);
		planetSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.25F, 2.25F));
		planetSaturn.setRelativeOrbitTime(29.463307776560788608981380065717F);
		planetSaturn.setBodyIcon(new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/saturn.png"));
		
		mimasSaturn = (Moon) new Moon("Mimas").setParentPlanet(SolarSystemPlanets.planetSaturn);
		mimasSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		mimasSaturn.setPhaseShift((float) (Math.PI / 2));
		mimasSaturn.setRelativeSize(0.0017F);
		mimasSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		mimasSaturn.setRelativeOrbitTime(20F);
		mimasSaturn.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/mimas.png"));
		
		enceladusSaturn = (Moon) new Moon("Enceladus").setParentPlanet(SolarSystemPlanets.planetSaturn);
		enceladusSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		enceladusSaturn.setPhaseShift((float) (Math.PI / 3));
		enceladusSaturn.setRelativeSize(0.0017F);
		enceladusSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(15F, 15F));
		enceladusSaturn.setRelativeOrbitTime(50F);
		enceladusSaturn.setTierRequired(5);
		enceladusSaturn.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/enceladus.png"));
		//enceladusSaturn.setDimensionInfo(GSConfigDimensions.dimensionIDEnceladus, WorldProviderEnceladus.class);
		
		tethysSaturn = (Moon) new Moon("Tethys").setParentPlanet(SolarSystemPlanets.planetSaturn);
		tethysSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		tethysSaturn.setPhaseShift((float) (Math.PI / 1));
		tethysSaturn.setRelativeSize(0.0017F);
		tethysSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(20F, 20F));
		tethysSaturn.setRelativeOrbitTime(120F);
		tethysSaturn.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/tethys.png"));
		
		dioneSaturn = (Moon) new Moon("Dione").setParentPlanet(SolarSystemPlanets.planetSaturn);
		dioneSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		dioneSaturn.setPhaseShift((float) (Math.PI / 4));
		dioneSaturn.setRelativeSize(0.0017F);
		dioneSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(25F, 25F));
		dioneSaturn.setRelativeOrbitTime(180F);
		dioneSaturn.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/dione.png"));
		
		rheyaSaturn = (Moon) new Moon("Rheya").setParentPlanet(SolarSystemPlanets.planetSaturn);
		rheyaSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		rheyaSaturn.setPhaseShift((float) (Math.PI / 3));
		rheyaSaturn.setRelativeSize(0.0017F);
		rheyaSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(30F, 30F));
		rheyaSaturn.setRelativeOrbitTime(220F);
		rheyaSaturn.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/rheya.png"));
		
		titanSaturn = (Moon) new Moon("Titan").setParentPlanet(SolarSystemPlanets.planetSaturn);
		titanSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		titanSaturn.setPhaseShift((float) (Math.PI / 5));
		titanSaturn.setRelativeSize(0.0017F);
		titanSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(35F, 35F));
		titanSaturn.setRelativeOrbitTime(280F);
		titanSaturn.setTierRequired(5);
		titanSaturn.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/titan.png"));
		//titanSaturn.setDimensionInfo(GSConfigDimensions.dimensionIDTitan, WorldProviderTitan.class);
		titanSaturn.atmosphereComponent(EnumAtmosphericGas.NITROGEN);
		
		iapetusSaturn = (Moon) new Moon("Iapetus").setParentPlanet(SolarSystemPlanets.planetSaturn);
		iapetusSaturn.setRingColorRGB(0.0F, 0.4F, 0.9F);
		iapetusSaturn.setPhaseShift(2.0F);
		iapetusSaturn.setRelativeSize(0.0017F);
		iapetusSaturn.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(40F, 40F));
		iapetusSaturn.setRelativeOrbitTime(350F);
		iapetusSaturn.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/iapetus.png"));
		
		// --------------------------------------------
		// TODO Uranus -------------------------------- 
		planetUranus = (Planet) new Planet("uranus").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetUranus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetUranus.setPhaseShift(1.38F);
		planetUranus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.5F, 2.5F));
		planetUranus.setRelativeOrbitTime(84.063526834611171960569550930997F);
		planetUranus.setBodyIcon(new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/uranus.png"));
		
		mirandaUranus = (Moon) new Moon("Miranda").setParentPlanet(SolarSystemPlanets.planetUranus);
		mirandaUranus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		mirandaUranus.setRelativeSize(0.0017F);
		mirandaUranus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		mirandaUranus.setRelativeOrbitTime(20F);
		mirandaUranus.setTierRequired(5);
		//mirandaUranus.setDimensionInfo(GSConfigDimensions.dimensionIDMiranda, WorldProviderMiranda.class);
		mirandaUranus.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/miranda.png"));
		
		arielUranus = (Moon) new Moon("Ariel").setParentPlanet(SolarSystemPlanets.planetUranus);
		arielUranus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		arielUranus.setRelativeSize(0.0017F);
		arielUranus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(15F, 15F));
		arielUranus.setRelativeOrbitTime(50F);
		arielUranus.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/ariel.png"));
		
		umbrielUranus = (Moon) new Moon("Umbriel").setParentPlanet(SolarSystemPlanets.planetUranus);
		umbrielUranus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		umbrielUranus.setRelativeSize(0.0017F);
		umbrielUranus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(20F, 20F));
		umbrielUranus.setRelativeOrbitTime(120F);
		umbrielUranus.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/umbriel.png"));
			
		titaniaUranus = (Moon) new Moon("Titania").setParentPlanet(SolarSystemPlanets.planetUranus);
		titaniaUranus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		titaniaUranus.setRelativeSize(0.0017F);
		titaniaUranus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(25F, 25F));
		titaniaUranus.setRelativeOrbitTime(180F);
		titaniaUranus.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/titania.png"));
		
		oberonUranus = (Moon) new Moon("Oberon").setParentPlanet(SolarSystemPlanets.planetUranus);
		oberonUranus.setRingColorRGB(0.0F, 0.4F, 0.9F);
		oberonUranus.setRelativeSize(0.0017F);
		oberonUranus.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(30F, 30F));
		oberonUranus.setRelativeOrbitTime(200F);
		oberonUranus.setTierRequired(5);
		oberonUranus.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/oberon.png"));
		//oberonUranus.setDimensionInfo(GSConfigDimensions.dimensionIDOberon, WorldProviderOberon.class);
		// TODO Neptune---------------------------------
		planetNeptune = (Planet) new Planet("neptune").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetNeptune.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetNeptune.setPhaseShift(1.0F);
		planetNeptune.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(2.75F, 2.75F));
		planetNeptune.setRelativeOrbitTime(164.84118291347207009857612267251F);
		planetNeptune.setBodyIcon(new ResourceLocation(Constants.ASSET_PREFIX, "textures/gui/celestialbodies/neptune.png"));
		
		proteusNeptune = (Moon) new Moon("Proteus").setParentPlanet(SolarSystemPlanets.planetNeptune);
		proteusNeptune.setRingColorRGB(0.0F, 0.4F, 0.9F);
		proteusNeptune.setRelativeSize(0.0017F);
		proteusNeptune.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		proteusNeptune.setRelativeOrbitTime(50F);
		proteusNeptune.setTierRequired(6);
		proteusNeptune.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/proteus.png"));
		//proteusNeptune.setDimensionInfo(GSConfigDimensions.dimensionIDProteus, WorldProviderProteus.class);
		
		tritonNeptune = (Moon) new Moon("Triton").setParentPlanet(SolarSystemPlanets.planetNeptune);
		tritonNeptune.setRingColorRGB(0.0F, 0.4F, 0.9F);
		tritonNeptune.setRelativeSize(0.0017F);
		tritonNeptune.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(25F, 25F));
		tritonNeptune.setRelativeOrbitTime(-200F);
		tritonNeptune.setTierRequired(6);
		tritonNeptune.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/triton.png"));
		//tritonNeptune.setDimensionInfo(GSConfigDimensions.dimensionIDTriton, WorldProviderTriton.class);
		// ---------------------------------------------
		// TODO Pluto ----------------------------------
		planetPluto = (Planet) new Planet("pluto").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetPluto.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetPluto.setPhaseShift(0.1F);
		planetPluto.setTierRequired(7);
		planetPluto.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(3.0F, 3.0F));
		planetPluto.setRelativeOrbitTime(250.9F);
		planetPluto.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/pluto.png"));
		//planetPluto.setDimensionInfo(GSConfigDimensions.dimensionIDPluto, WorldProviderPluto.class);
		planetPluto.atmosphereComponent(EnumAtmosphericGas.NITROGEN);		
		
		charonPluto = (Moon) new Moon("Charon").setParentPlanet(SolarSystemPlanets.planetPluto);
		charonPluto.setRingColorRGB(0.0F, 0.4F, 0.9F);
		charonPluto.setRelativeSize(0.0017F);
		charonPluto.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(10F, 10F));
		charonPluto.setRelativeOrbitTime(50F);
 		charonPluto.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/charon.png"));
		// --------------------------------------------
		// TODO Kuiper Belt ---------------------------
		planetKuiperBelt = (Planet) new Planet("kuiperbelt").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetKuiperBelt.setRingColorRGB(1.0F, 0.0F, 0.0F);
		planetKuiperBelt.setPhaseShift(1.5F);
		planetKuiperBelt.setTierRequired(7);
		planetKuiperBelt.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(3.25F, 3.25F));
		planetKuiperBelt.setRelativeOrbitTime(300.9F);
		planetKuiperBelt.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/kuiperbelt.png"));
		//planetKuiperBelt.setDimensionInfo(GSConfigDimensions.dimensionIDKuiperBelt, WorldProviderKuiper.class);
		// --------------------------------------------
		// TODO Haumea --------------------------------
		planetHaumea = (Planet) new Planet("haumea").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetHaumea.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetHaumea.setPhaseShift(21.5F);
		planetHaumea.setTierRequired(7);
		planetHaumea.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(3.5F, 3.5F));
		planetHaumea.setRelativeOrbitTime(280.9F);
		planetHaumea.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/haumea.png"));
		//planetHaumea.setDimensionInfo(GSConfigDimensions.dimensionIDHaumea, WorldProviderHaumea.class);
		// --------------------------------------------
		// TODO Makemake ------------------------------
		planetMakemake = (Planet) new Planet("makemake").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetMakemake.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetMakemake.setPhaseShift(11.5F);
		planetMakemake.setTierRequired(7);
		planetMakemake.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(3.75F, 3.75F));
		planetMakemake.setRelativeOrbitTime(300.9F);
		planetMakemake.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/makemake.png"));
		//planetMakemake.setDimensionInfo(GSConfigDimensions.dimensionIDMakemake, WorldProviderMakemake.class);
		// --------------------------------------------
		// TODO Eris ----------------------------------
		planetEris = (Planet) new Planet("eris").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetEris.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetEris.setPhaseShift(41.5F);
		planetEris.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(4.0F, 4.0F));
		planetEris.setRelativeOrbitTime(360.9F);
		planetEris.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/eris.png"));
		// --------------------------------------------
		// TODO DeeDee --------------------------------
		planetDeeDee = (Planet) new Planet("deedee").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetDeeDee.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetDeeDee.setPhaseShift((float)Math.PI * 0.5F);
		planetDeeDee.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(4.5F, 4.5F));
		planetDeeDee.setRelativeOrbitTime(510.9F);
		planetDeeDee.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/deedee.png"));
		// --------------------------------------------
		
		planetZTest = (Planet) new Planet("ZTest").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetZTest.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetZTest.setPhaseShift(11.5F);
		planetZTest.setTierRequired(1);
		planetZTest.setRelativeDistanceFromCenter(new CelestialBody.ScalableDistance(4.75F, 4.75F));
		planetZTest.setRelativeOrbitTime(300.9F);
		planetZTest.setBodyIcon(new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/makemake.png"));
		//planetZTest.setDimensionInfo(-1100, WorldProviderZTest.class);
		
    	registrycelestial();
    	registryteleport();
    	
    	//body, classP, gravity, pressure, temp, wind, daytime, breath, solar

	}
	
	private static void registrycelestial()
	{
		BodiesInfo.registerBody(GalacticraftCore.solarSystemSol.getMainStar(), BodiesInfo.yellow + " " + BodiesInfo.dwarf, 28.088F, 0, 999, 0, 0, false, false);
		/*if(GSConfigDimensions.enableMercury) */BodiesInfo.registerBody(planetMercury, BodiesInfo.selena, BodiesInfo.calculateGravity(3.8F), 0, 4, 0.0F, 182000, false, true, true);
		/*if(GSConfigDimensions.enableVenus)*/ BodiesInfo.registerBody(planetVenus, BodiesInfo.desert, BodiesInfo.calculateGravity(8.88F), 92, 5, 1.2F, 182000, false, false, true);
		BodiesInfo.registerBody(GalacticraftCore.planetOverworld, BodiesInfo.terra, BodiesInfo.calculateGravity(10.0F), 1, 1, 1, 24000, true, false);
    	BodiesInfo.registerBody(MarsModule.planetMars, BodiesInfo.desert, BodiesInfo.calculateGravity(3.7F), 12, -2, 0.3F, 24000, false, false);
    	/*if(GSConfigDimensions.enableCeres) */BodiesInfo.registerBody(planetCeres, BodiesInfo.asteroid, BodiesInfo.calculateGravity(2.37F), 0, -2, 0, 48000, false, false, true);
    	BodiesInfo.registerBody(AsteroidsModule.planetAsteroids, BodiesInfo.selena, 0.075F, 0, -2, 0, 0, false, false);
    	BodiesInfo.registerBody(planetJupiter, BodiesInfo.gasgiant, BodiesInfo.calculateGravity(8.375F), 100, 5, 10, 9000, false, false, true);
    	BodiesInfo.registerBody(planetSaturn, BodiesInfo.gasgiant, BodiesInfo.calculateGravity(7.37F), 100, 5, 10, 11000, false, false, true);
    	BodiesInfo.registerBody(planetUranus, BodiesInfo.gasgiant, BodiesInfo.calculateGravity(8.61F), 100, 5, 10, 16000, false, false, true);
    	BodiesInfo.registerBody(planetNeptune, BodiesInfo.gasgiant, BodiesInfo.calculateGravity(8.547F), 100, 5, 10, 18000, false, false, true);
		/*if(GSConfigDimensions.enablePluto)*/ BodiesInfo.registerBody(planetPluto, BodiesInfo.iceworld, BodiesInfo.calculateGravity(2.62F), 0, -2, 0, 48000, false, true, true);
    	BodiesInfo.registerBody(planetKuiperBelt, BodiesInfo.asteroid, 0.075F, 0, -2, 0, 0, false, false, true);
		/*if(GSConfigDimensions.enableHaumea)*/ BodiesInfo.registerBody(planetHaumea, BodiesInfo.iceworld, BodiesInfo.calculateGravity(2.07F), 0, -2, 0, 3000, false, true, true);
		/*if(GSConfigDimensions.enableMakemake)*/ BodiesInfo.registerBody(planetMakemake, BodiesInfo.iceworld, BodiesInfo.calculateGravity(3.47F), 0, -2, 0, 7000, false, true, true);
		/*if(GSConfigCore.enableUnreachable)
		{*/
			BodiesInfo.registerBody(planetEris, BodiesInfo.iceworld, BodiesInfo.calculateGravity(2.83F), 0, -2, 0, 25000, false, true, true);
			BodiesInfo.registerBody(planetDeeDee, BodiesInfo.iceworld, BodiesInfo.calculateGravity(2.53F), 0, -2, 0, 25000, false, true, true);
		//}
		
		if(GalaxySpace.debug) BodiesInfo.registerBody(planetZTest, BodiesInfo.iceworld, 0.04F, 0, -2, 0, 48000, false, true, true);
		
		/*if(GSConfigDimensions.enablePhobos)*/ BodiesInfo.registerBody(phobosMars, BodiesInfo.asteroid, 0.068F, 0, -2, 0, 12000, false, false, true);
		/*if(GSConfigDimensions.enableDeimos)*/ BodiesInfo.registerBody(deimosMars, BodiesInfo.asteroid, 0.064F, 0, -2, 0, 24000, false, false, true);
		/*if(GSConfigDimensions.enableIo)*/ BodiesInfo.registerBody(ioJupiter, BodiesInfo.selena, 0.052F, 0, -2, 0, 48000, false, true, true);
		/*if(GSConfigDimensions.enableEuropa)*/ BodiesInfo.registerBody(europaJupiter, BodiesInfo.iceworld, 0.062F, 0, -2, 0, 48000, false, true, true);
		/*if(GSConfigDimensions.enableGanymede)*/ BodiesInfo.registerBody(ganymedeJupiter, BodiesInfo.iceworld, 0.057F, 0, -2, 0, 48000, false, true, true);
		/*if(GSConfigDimensions.enableCallisto)*/ BodiesInfo.registerBody(callistoJupiter, BodiesInfo.iceworld, 0.054F, 0, -2, 0, 48000, false, true, true);
		/*if(GSConfigDimensions.enableEnceladus)*/ BodiesInfo.registerBody(enceladusSaturn, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 24000, false, true, true);
		/*if(GSConfigDimensions.enableTitan)*/ BodiesInfo.registerBody(titanSaturn, BodiesInfo.titan, 0.058F, 5, -2, 0, 48000, false, false, true);
		/*if(GSConfigDimensions.enableMiranda)*/ BodiesInfo.registerBody(mirandaUranus, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 18000, false, true, true);
		/*if(GSConfigDimensions.enableOberon)*/ BodiesInfo.registerBody(oberonUranus, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 24000, false, true, true);
    	/*if(GSConfigDimensions.enableProteus)*/ BodiesInfo.registerBody(proteusNeptune, BodiesInfo.asteroid, 0.058F, 0, -2, 0, 24000, false, true, true);
		/*if(GSConfigDimensions.enableTriton)*/ BodiesInfo.registerBody(tritonNeptune, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 24000, false, true, true); 	
		/*if(GSConfigCore.enableUnreachable) {*/
			BodiesInfo.registerBody(mimasSaturn, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(tethysSaturn, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(dioneSaturn, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(rheyaSaturn, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(iapetusSaturn, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(arielUranus, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(umbrielUranus, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(titaniaUranus, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
			BodiesInfo.registerBody(charonPluto, BodiesInfo.iceworld, 0.058F, 0, -2, 0, 48000, false, true, true); 	
		//}

		//if(GSConfigDimensions.enableMarsSS) GalaxyRegistry.registerSatellite(marsSpaceStation);
		//if(GSConfigDimensions.enableVenusSS) GalaxyRegistry.registerSatellite(venusSpaceStation);

	}
	
	private static void registryteleport()
	{
		//GalacticraftRegistry.registerTeleportType(/*GSConfigCore.enableWorldEngine ? WorldProviderMercuryWE.class :*/ WorldProviderMercury.class, new TeleportTypeMercury());
		/*GalacticraftRegistry.registerTeleportType(WorldProviderVenus.class, new WorldProviderVenus());
		GalacticraftRegistry.registerTeleportType(WorldProviderCeres.class, new TeleportTypeCeres());
		GalacticraftRegistry.registerTeleportType(WorldProviderPluto.class, new WorldProviderPluto());
		GalacticraftRegistry.registerTeleportType(WorldProviderKuiper.class, new WorldProviderKuiper());
		GalacticraftRegistry.registerTeleportType(WorldProviderHaumea.class, new TeleportTypeHaumea());
		GalacticraftRegistry.registerTeleportType(WorldProviderMakemake.class, new TeleportTypeMakemake());
		
		if(GalaxySpace.debug) GalacticraftRegistry.registerTeleportType(WorldProviderZTest.class, new WorldProviderZTest());
		
		GalacticraftRegistry.registerTeleportType(WorldProviderPhobos.class, new WorldProviderPhobos());
		GalacticraftRegistry.registerTeleportType(WorldProviderDeimos.class, new WorldProviderDeimos());
		GalacticraftRegistry.registerTeleportType(WorldProviderIo.class, new WorldProviderIo());
		GalacticraftRegistry.registerTeleportType(WorldProviderEuropa.class, new WorldProviderEuropa());
		GalacticraftRegistry.registerTeleportType(WorldProviderGanymede.class, new WorldProviderGanymede());
		GalacticraftRegistry.registerTeleportType(WorldProviderCallisto.class, new WorldProviderCallisto());
		GalacticraftRegistry.registerTeleportType(WorldProviderEnceladus.class, new WorldProviderEnceladus());
		GalacticraftRegistry.registerTeleportType(WorldProviderTitan.class, new WorldProviderTitan());
		GalacticraftRegistry.registerTeleportType(WorldProviderMiranda.class, new WorldProviderMiranda());
		GalacticraftRegistry.registerTeleportType(WorldProviderOberon.class, new WorldProviderOberon());
		GalacticraftRegistry.registerTeleportType(WorldProviderProteus.class, new WorldProviderProteus());
		GalacticraftRegistry.registerTeleportType(WorldProviderTriton.class, new WorldProviderTriton());
		  
		
		
		if(GSConfigDimensions.enableMarsSS)
		{ 
			GalacticraftRegistry.registerTeleportType(WorldProviderMarsSS.class, new TeleportTypeSpaceStation());
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbit, WorldProviderMarsSS.class, false, -40);
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDMarsOrbitStatic, WorldProviderMarsSS.class, true, -41);
		}
		if(GSConfigDimensions.enableVenusSS)
		{
			GalacticraftRegistry.registerTeleportType(WorldProviderVenusSS.class, new TeleportTypeSpaceStation());
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDVenusOrbit, WorldProviderVenusSS.class, false, -42);
			GalacticraftRegistry.registerProvider(GSConfigDimensions.dimensionIDVenusOrbitStatic, WorldProviderVenusSS.class, true, -43);
		}
		*/
	}
	

}
