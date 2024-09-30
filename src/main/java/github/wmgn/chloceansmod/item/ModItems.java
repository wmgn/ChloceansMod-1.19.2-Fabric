package github.wmgn.chloceansmod.item;

import github.wmgn.chloceansmod.ChloceansMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item RAW_PEARL = registerItem("raw_pearl",
            new Item(new FabricItemSettings().group(ModItemGroup.PEARL)));
    public static final Item PEARL = registerItem("pearl",
            new Item(new FabricItemSettings().group(ModItemGroup.PEARL)));
    public static final Item ETHAG = registerItem("ethag",
            new Item(new FabricItemSettings().group(ModItemGroup.PEARL)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ChloceansMod.MOD_ID, name), item);
    }

    /*private static Item registerItem(String name, Item item, ItemGroup group) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name), item);
    }*/


    public static void registerModItems() {
        ChloceansMod.LOGGER.debug("Registering Mod Items for " + ChloceansMod.MOD_ID);
    }
}
