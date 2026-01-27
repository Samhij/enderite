package net.lonk.enderite.screen;

import net.lonk.enderite.Enderite;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class VoidInfusionTableScreen extends HandledScreen<VoidInfusionTableScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Enderite.MOD_ID, "textures/gui/container/void_infusion_table.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.ofVanilla("container/furnace/burn_progress");

    public VoidInfusionTableScreen(VoidInfusionTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY = 6;
        playerInventoryTitleX = 8;
        playerInventoryTitleY = backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // Draw fuel/burn indicator (vertical flame)
        if (handler.isBurning()) {
            int fuelHeight = MathHelper.ceil(handler.getFuelProgress() * 13.0F) + 1;
            context.drawGuiTexture(LIT_PROGRESS_TEXTURE, 14, 14, 0, 14 - fuelHeight, x + 56, y + 36 + 14 - fuelHeight, 14, fuelHeight);
        }

        // Draw progress arrow (horizontal arrow)
        int progressWidth = MathHelper.ceil(handler.getInfusionProgress() * 24.0F);
        context.drawGuiTexture(BURN_PROGRESS_TEXTURE, 24, 16, 0, 0, x + 79, y + 34, progressWidth, 16);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0x404040, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, 0x404040, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
