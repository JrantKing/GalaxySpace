package galaxyspace.core.api;

import java.util.List;

import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.blocks.BlockAdvancedTile;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlock;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

public class GSItemBlockDesc extends ItemBlock
{
    public static interface IBlockShiftDesc
    {
        String getShiftDescription(int meta);
        String getDescription(int meta);

        boolean showDescription(int meta);
    }

    public GSItemBlockDesc(Block block)
    {
        super(block);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote) return;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
        if (this.block instanceof IBlockShiftDesc && ((IBlockShiftDesc) this.block).showDescription(stack.getItemDamage()))
        {
        	if(((IBlockShiftDesc) this.block).getDescription(stack.getItemDamage()) != null) info.addAll(FMLClientHandler.instance().getClient().fontRendererObj.listFormattedStringToWidth(((IBlockShiftDesc) this.block).getDescription(stack.getItemDamage()), 150));
            
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            {
                info.addAll(FMLClientHandler.instance().getClient().fontRendererObj.listFormattedStringToWidth(((IBlockShiftDesc) this.block).getShiftDescription(stack.getItemDamage()), 150));
            }
            else
            {
                if (this.block instanceof BlockTileGC)
                {
                	TileEntity te = ((BlockTileGC) this.getBlock()).createTileEntity(null, getBlock().getStateFromMeta(stack.getItemDamage() & 12));
                    if (te instanceof TileBaseElectricBlock)
                    {
                        float powerDrawn = ((TileBaseElectricBlock) te).storage.getMaxExtract();
                        if (powerDrawn > 0)
                        {
                            info.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translateWithFormat("itemDesc.powerdraw.name", EnergyDisplayHelper.getEnergyDisplayS(powerDrawn * 20)));
                        }
                    }
                }
                else if (this.block instanceof BlockAdvancedTile)
                {
                	 TileEntity te = ((BlockAdvancedTile) this.getBlock()).createTileEntity(player.world, getBlock().getStateFromMeta(stack.getItemDamage() & 12));
                     if (te instanceof TileBaseElectricBlock)
                    {
                        float powerDrawn = ((TileBaseElectricBlock) te).storage.getMaxExtract();
                        if (powerDrawn > 0)
                        {
                            info.add(EnumColor.BRIGHT_GREEN + GCCoreUtil.translateWithFormat("itemDesc.powerdraw.name", EnergyDisplayHelper.getEnergyDisplayS(powerDrawn * 20)));
                        }
                    }
                }
                info.add(EnumColor.DARK_AQUA + GCCoreUtil.translateWithFormat("itemDesc.shift.name", GameSettings.getKeyDisplayString(FMLClientHandler.instance().getClient().gameSettings.keyBindSneak.getKeyCode())));
            }
        }
    }
}
