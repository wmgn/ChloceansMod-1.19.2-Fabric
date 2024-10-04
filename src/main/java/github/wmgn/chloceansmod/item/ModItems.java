package github.wmgn.chloceansmod.item;

import github.wmgn.chloceansmod.ChloceansMod;
import github.wmgn.chloceansmod.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item RAW_PEARL = registerItem("raw_pearl",
            new Item(new FabricItemSettings().group(ModItemGroup.PEARL)));
    public static final Item PEARL = registerItem("pearl",
            new Item(new FabricItemSettings().group(ModItemGroup.PEARL)));

    public static final Item GIANTCLAM_SPAWN_EGG = registerItem("giantclam_spawn_egg",
            new SpawnEggItem(ModEntities.GIANTCLAM, 0xC5936A, 0x82444E,
                    new FabricItemSettings().group(ModItemGroup.PEARL)));

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
