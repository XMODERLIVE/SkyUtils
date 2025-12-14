package net.notnightsky.skyutils.mixins;

import net.notnightsky.skyutils.modules.playerhealthindicator.PlayerHealthInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(net.minecraft.client.render.entity.state.PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements PlayerHealthInterface {
    @Unique
    private float skyutils$health;

    @Unique
    private float skyutils$maxHealth;

    @Override
    public float skyutils$getHealth() {
        return skyutils$health;
    }

    @Override
    public float skyutils$getMaxHealth() {
        return skyutils$maxHealth;
    }

    @Override
    public void skyutils$setHealth(float health) {
        this.skyutils$health = health;
    }

    @Override
    public void skyutils$setMaxHealth(float maxHealth) {
        this.skyutils$maxHealth = maxHealth;
    }
}
