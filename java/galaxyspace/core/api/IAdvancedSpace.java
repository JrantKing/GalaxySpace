package galaxyspace.core.api;

import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;


public interface IAdvancedSpace
{
	enum EnumClassPlanet
	{
		OCEANIDE,
		ASTEROIDE
	}
	public EnumClassPlanet getClassPlanet();
	public int AtmosphericPressure();
	public boolean SolarRadiation();
	public double getSolarWindMultiplier();
}
