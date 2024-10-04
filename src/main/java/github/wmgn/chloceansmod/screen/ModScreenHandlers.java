package github.wmgn.chloceansmod.screen;

import github.wmgn.chloceansmod.ChloceansMod;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModScreenHandlers {
    public static ScreenHandlerType<GiantClamScreenHandler> GIANT_CLAM_SCREEN_HANDLER; //final

    public static void registerAllScreenHandlers() {
        GIANT_CLAM_SCREEN_HANDLER = new ScreenHandlerType<>(GiantClamScreenHandler::new);
        //Registry.register(Registry.SCREEN_HANDLER, new Identifier(ChloceansMod.MOD_ID, "giant_clam"), GIANT_CLAM_SCREEN_HANDLER);
    }
}




/* // OLD 2
public class ModScreenHandlers {
    public static final ScreenHandlerType<GiantClamScreenHandler> GIANT_CLAM_SCREEN_HANDLER;

    static {
        // Register the screen handler type with a factory
        GIANT_CLAM_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER,
                new Identifier(ChloceansMod.MOD_ID, "giant_clam"),
                new ScreenHandlerType<GiantClamScreenHandler>(GiantClamScreenHandler::new));
    }
}*/