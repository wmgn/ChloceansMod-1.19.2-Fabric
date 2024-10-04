package github.wmgn.chloceansmod;

import github.wmgn.chloceansmod.block.ModBlocks;
import github.wmgn.chloceansmod.entity.ModEntities;
import github.wmgn.chloceansmod.entity.custom.GiantClamEntity;
import github.wmgn.chloceansmod.item.ModItems;
import github.wmgn.chloceansmod.particles.ModParticles;
import github.wmgn.chloceansmod.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class ChloceansMod implements ModInitializer {
	public static final String MOD_ID = "chloceansmod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	// Register your screen handler
	public static ScreenHandlerType<GenericContainerScreenHandler> GIANT_CLAM_SCREEN_HANDLER;


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info(MOD_ID + " Initializing, Hello Fabric world!");

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		GeckoLib.initialize();

		// Giant Clam Initialization
		FabricDefaultAttributeRegistry.register(ModEntities.GIANTCLAM, GiantClamEntity.setAttributes());

		// Giant Clam Screen Stuff
		ModScreenHandlers.registerAllScreenHandlers();

		//ModParticles.registerParticles();


	}
}