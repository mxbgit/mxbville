package mxbville.common.blocks;

import mxbville.common.blocks.decorative.BlockBarsPanel;
import mxbville.common.blocks.decorative.BlockFlowerInPot;
import mxbville.common.blocks.decorative.BlockTable;
import mxbville.util.MxRef;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MxRef.MOD_ID)
public class ModBlocks {

	public static final BlockBarsPanel BARSPANEL				= null;
	public static final BlockFlowerInPot FLOWER_REDROSE			= null; 	
	public static final BlockFlowerInPot FLOWER_HEARTMUSHROOM	= null; 
	public static final BlockFlowerInPot FLOWER_HYDRANGEAS		= null; 
	public static final BlockFlowerInPot FLOWER_PLUMBLOSSOM		= null; 
	
	public static final BlockFlowerInPot FLOWER_RANUNCULUS		= null; 
	public static final BlockFlowerInPot FLOWER_ROSYSPIRAEA		= null; 
	public static final BlockFlowerInPot FLOWER_GARDENIA		= null; 
	public static final BlockFlowerInPot FLOWER_BONSAI 			= null; 
	public static final BlockTable TABLE_ACACIA 				= null;
	public static final BlockTable TABLE_BIRCH 					= null;	
	public static final BlockTable TABLE_DARKOAK 				= null;
	public static final BlockTable TABLE_JUNGLE 				= null;
	public static final BlockTable TABLE_OAK 					= null;
	public static final BlockTable TABLE_SPRUCE 				= null;
	
	@EventBusSubscriber(modid = MxRef.MOD_ID)
	public static class BlockFullRegistrationHandler {
	
		@SubscribeEvent
		public static void onBlockRegister(RegistryEvent.Register<Block> event) {
			final Block[] blocks = {
					new BlockBarsPanel(),
					new BlockFlowerInPot("flower_redrose"),	
					new BlockFlowerInPot("flower_heartmushroom"), 
					new BlockFlowerInPot("flower_hydrangeas"), 
					new BlockFlowerInPot("flower_plumblossom"), 
					new BlockFlowerInPot("flower_ranunculus"), 
					new BlockFlowerInPot("flower_rosyspiraea"), 
					new BlockFlowerInPot("flower_gardenia"),
					new BlockFlowerInPot("flower_bonsai"),
					new BlockTable("table_acacia"), 
					new BlockTable("table_birch"), 
					new BlockTable("table_darkoak"),
					new BlockTable("table_jungle"),
					new BlockTable("table_oak"), 	
					new BlockTable("table_spruce")
			};
			
			event.getRegistry().registerAll(blocks);
		}
		
		@SubscribeEvent
		public static void onBlockItemRegister(RegistryEvent.Register<Item> event) {
			final Item[] block_items = {
					new ItemBlock(ModBlocks.BARSPANEL).setRegistryName(ModBlocks.BARSPANEL.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_REDROSE).setRegistryName(ModBlocks.FLOWER_REDROSE.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_HEARTMUSHROOM).setRegistryName(ModBlocks.FLOWER_HEARTMUSHROOM.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_HYDRANGEAS).setRegistryName(ModBlocks.FLOWER_HYDRANGEAS.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_PLUMBLOSSOM).setRegistryName(ModBlocks.FLOWER_PLUMBLOSSOM.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_RANUNCULUS).setRegistryName(ModBlocks.FLOWER_RANUNCULUS.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_ROSYSPIRAEA).setRegistryName(ModBlocks.FLOWER_ROSYSPIRAEA.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_GARDENIA).setRegistryName(ModBlocks.FLOWER_GARDENIA.getRegistryName()),
					new ItemBlock(ModBlocks.FLOWER_BONSAI).setRegistryName(ModBlocks.FLOWER_BONSAI.getRegistryName()),
					new ItemBlock(ModBlocks.TABLE_ACACIA).setRegistryName(ModBlocks.TABLE_ACACIA.getRegistryName()),
					new ItemBlock(ModBlocks.TABLE_BIRCH).setRegistryName(ModBlocks.TABLE_BIRCH.getRegistryName()),
					new ItemBlock(ModBlocks.TABLE_DARKOAK).setRegistryName(ModBlocks.TABLE_DARKOAK.getRegistryName()),
					new ItemBlock(ModBlocks.TABLE_JUNGLE).setRegistryName(ModBlocks.TABLE_JUNGLE.getRegistryName()),
					new ItemBlock(ModBlocks.TABLE_OAK).setRegistryName(ModBlocks.TABLE_OAK.getRegistryName()),
					new ItemBlock(ModBlocks.TABLE_SPRUCE).setRegistryName(ModBlocks.TABLE_SPRUCE.getRegistryName())
			};
			
			event.getRegistry().registerAll(block_items);
		}
	}
	
}
