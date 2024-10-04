package github.wmgn.chloceansmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import github.wmgn.chloceansmod.ChloceansMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/* // OLD
@Environment(EnvType.CLIENT)
public class GiantClamScreen extends HandledScreen<GenericContainerScreenHandler> {
    public GiantClamScreen(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // Draw the background here, using the Minecraft chest GUI texture
        RenderSystem.setShaderTexture(0, new Identifier("textures/gui/container/generic_54.png"));
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}*/

//@Environment(EnvType.CLIENT)
public class GiantClamScreen extends HandledScreen<GiantClamScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(ChloceansMod.MOD_ID, "textures/gui/generic_27_attempt1.png");

    public GiantClamScreen(GiantClamScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        //titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2; // centers the title???
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        // Adjust the Y-coordinate of the "Inventory" label to position it correctly
        // The default Y-coordinate might be too high; adjust it by changing the second parameter
        this.textRenderer.draw(matrices, "Inventory", 8.0F, this.backgroundHeight - 94.0F + 2, 4210752); // Move it down by adding to the Y value
        this.textRenderer.draw(matrices, "Giant Clam", 8.0F, this.backgroundHeight - 94.0F - 66, 4210752); // Move it down by adding to the Y value
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}

