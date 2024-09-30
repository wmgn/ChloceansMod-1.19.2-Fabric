package github.wmgn.chloceansmod.block;

import github.wmgn.chloceansmod.ChloceansMod;
import github.wmgn.chloceansmod.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block PEARL_BLOCK = registerBlock("pearl_block",
            new Block(FabricBlockSettings.of(Material.STONE).luminance(15).strength(4f).requiresTool()),
            ModItemGroup.PEARL);

    public static final Block RAW_PEARL_BLOCK = registerBlock("raw_pearl_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool()),
            ModItemGroup.PEARL);


    public static final Block RAW_PEARL_ORE = registerBlock("raw_pearl_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(3,7)), ModItemGroup.PEARL);
    public static final Block DEEPSLATE_RAW_PEARL_ORE = registerBlock("deepslate_raw_pearl_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4f).requiresTool(),
                    UniformIntProvider.create(3,7)), ModItemGroup.PEARL);



    private static Block registerBlock(String name, Block block, ItemGroup tab){
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(ChloceansMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab){
        return Registry.register(Registry.ITEM, new Identifier(ChloceansMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }



    public static void registerModBlocks() {
        ChloceansMod.LOGGER.debug("Registering Mod Blocks for " + ChloceansMod.MOD_ID);
    }
}
