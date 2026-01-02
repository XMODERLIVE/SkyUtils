package net.notnightsky.skyutils.hud.component;

import net.minecraft.client.gui.DrawContext;
import net.notnightsky.skyutils.hud.layout.AnchorPoint;
import net.notnightsky.skyutils.utils.render.DrawPosition;
import net.notnightsky.skyutils.utils.render.Rectangle;
import net.minecraft.util.Identifier;

public interface HudEntry {

    /**
     * Gets the id of the hud element
     *
     * @return The id
     */
    Identifier getId();

    /**
     * Gets the translated name key of the hud element
     *
     * @return The name key
     */
    default String getNameKey() {
        return "hud." + getId().getNamespace() + "." + getId().getPath();
    }

    /**
     * Whether the hud element is enabled or not
     *
     * @return If enabled
     */
    boolean isEnabled();

    /**
     * Sets whether the hud element is enabled or not
     *
     * @param value If enabled
     */
    void setEnabled(boolean value);

    /**
     * Gets the width of the hud element
     *
     * @return Width
     */
    int getWidth();

    /**
     * Gets the height of the hud element
     *
     * @return Height
     */
    int getHeight();

    /**
     * Gets the scale of the hud element
     *
     * @return Scale
     */
    float getScale();

    /**
     * Gets the position of the hud element
     *
     * @return Position
     */
    DrawPosition getPos();

    /**
     * Gets the true position of the hud element (after scaling)
     *
     * @return True position
     */
    DrawPosition getTruePos();

    /**
     * Gets the raw x position
     *
     * @return Raw x
     */
    int getRawX();

    /**
     * Gets the raw y position
     *
     * @return Raw y
     */
    int getRawY();

    /**
     * Gets the raw true x position
     *
     * @return Raw true x
     */
    int getRawTrueX();

    /**
     * Gets the raw true y position
     *
     * @return Raw true y
     */
    int getRawTrueY();

    /**
     * Gets the bounds of the hud element
     *
     * @return Bounds
     */
    Rectangle getBounds();

    /**
     * Gets the true bounds of the hud element (after scaling)
     *
     * @return True bounds
     */
    Rectangle getTrueBounds();

    /**
     * Gets the true width of the hud element (after scaling)
     *
     * @return True width
     */
    default int getTrueWidth() {
        return (int) (getWidth() * getScale());
    }

    /**
     * Gets the true height of the hud element (after scaling)
     *
     * @return True height
     */
    default int getTrueHeight() {
        return (int) (getHeight() * getScale());
    }

    /**
     * Renders the hud element
     *
     * @param matrices The draw matrices
     * @param delta    The delta time
     */
    void render(DrawContext context, float delta);

    /**
     * Renders the placeholder of the hud element
     *
     * @param matrices The draw matrices
     * @param delta    The delta time
     */
    void renderPlaceholder(DrawContext context, float delta);

    /**
     * Updates the bounds of the hud element
     */
    void onBoundsUpdate();

    /**
     * Whether the hud element is movable or not
     *
     * @return If movable
     */
    default boolean movable() {
        return true;
    }

    /**
     * Whether the hud element is tickable or not
     *
     * @return If tickable
     */
    default boolean tickable() {
        return false;
    }

    /**
     * Called every tick if {@link #tickable()} returns true
     */
    default void tick() {
    }

    /**
     * Gets the anchor point of the hud element
     *
     * @return Anchor point
     */
    default AnchorPoint getAnchor() {
        return AnchorPoint.MIDDLE_MIDDLE;
    }

    /**
     * Initializes the hud element
     */
    default void init() {
    }
}