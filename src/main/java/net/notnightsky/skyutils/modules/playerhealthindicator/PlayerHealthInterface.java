package net.notnightsky.skyutils.modules.playerhealthindicator;

public interface PlayerHealthInterface {
    float skyutils$getHealth();
    float skyutils$getMaxHealth();

    void skyutils$setHealth(float health);
    void skyutils$setMaxHealth(float maxHealth);
}
