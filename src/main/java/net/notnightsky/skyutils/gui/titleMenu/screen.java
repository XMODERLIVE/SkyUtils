package net.notnightsky.skyutils.gui.titleMenu;

import com.terraformersmc.modmenu.gui.ModsScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.SkyutilsClient;
import net.notnightsky.skyutils.gui.titleMenu.widget.AbstractIconButton;
import net.notnightsky.skyutils.gui.titleMenu.widget.AbstractTextButton;
import net.notnightsky.skyutils.utils.font.FontRenderer;
import net.notnightsky.skyutils.utils.render.render2D;

public class screen extends Screen {
    private static final Identifier CROSS_ICON = Identifier.of("skyutils", "icon/cross.png");
    private static final Identifier MODMENU_ICON = Identifier.of(SkyutilsClient.modID, "icon/modmenu_logo.png");

    public screen() {
        super(Text.literal("SkyUtils Menu"));
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int centerY = height / 2;

        int buttonWidth = 140;
        int buttonHeight = 22;
        int spacing = 26;

        // Singleplayer button
        addDrawableChild(new AbstractTextButton(centerX - buttonWidth / 2, centerY - spacing - 10, buttonWidth, buttonHeight, Text.translatable("menu.singleplayer"), () -> {
            if (client != null) {
                client.setScreen(new SelectWorldScreen(this));
            }}));

        addDrawableChild(new AbstractTextButton(centerX - buttonWidth / 2, centerY - 10, buttonWidth, buttonHeight, Text.translatable("menu.multiplayer"), () -> {
            if(client != null){
                client.setScreen(new MultiplayerScreen(this));
            }
        }));

        // Multiplayer button
        addDrawableChild(new AbstractTextButton(centerX - buttonWidth / 2, centerY + spacing - 10, buttonWidth, buttonHeight, Text.translatable("menu.online"), () -> {
            if (client != null) {
                client.setScreen(new RealmsMainScreen(this));
            }}));

        // Options button
        addDrawableChild(new AbstractTextButton(centerX - buttonWidth / 2, centerY + spacing * 2 - 10, buttonWidth, buttonHeight, Text.translatable("menu.options"), () -> {
            if (client != null) {
                client.setScreen(new OptionsScreen(this, client.options));
            }}));

        // Close/Exit button (top-right)
        addDrawableChild(new AbstractIconButton(width - 28, 12, 20, CROSS_ICON, () -> {
            if (client != null) {
                client.scheduleStop();
            }}));
        //                                                        btn size
        //                                                           +
        //ModMenu                                   stop btn x     padding
        addDrawableChild(new AbstractIconButton((width - 28) - (20 + 5), 12, 20, MODMENU_ICON, () -> {
            if (client != null) {
                client.setScreen(new ModsScreen(this));
            }}));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {

        // Semi-transparent dark background
        ctx.fill(0, 0, width, height, 0x88000000);
        super.render(ctx, mouseX, mouseY, delta);
        drawCopyright(ctx);
        drawMod(ctx);
    }

    private void drawMod(DrawContext ctx){
        String version = SkyutilsClient.modName + " " + SkyutilsClient.modVersion + " " + SkyutilsClient.mcVer;
        render2D.drawPicture(ctx,width / 2 - 30,height / 2 - 114,64,64,0x40ffffff,Identifier.of(SkyutilsClient.modID, "icon/skylogo.png"));
        FontRenderer.drawText(ctx, version,1,height - FontRenderer.getFontHeight(),0x50ffffff);
    }

    private void drawCopyright(DrawContext ctx) {
        String copyright = "Copyright Mojang Studios. Do not distribute!";
        FontRenderer.drawText(ctx, copyright,width - FontRenderer.getStringWidth(copyright), height - FontRenderer.getFontHeight(),0x50ffffff);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}