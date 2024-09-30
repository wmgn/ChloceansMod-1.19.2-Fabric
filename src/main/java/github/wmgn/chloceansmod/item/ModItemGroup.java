package github.wmgn.chloceansmod.item;

import github.wmgn.chloceansmod.ChloceansMod;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup PEARL = FabricItemGroupBuilder.build(
            new Identifier(ChloceansMod.MOD_ID, "pearl"), () -> new ItemStack(ModItems.PEARL));
}
