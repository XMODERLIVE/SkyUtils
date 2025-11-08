package net.notnightsky.skyutils.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.notnightsky.skyutils.config.modConfig;

@Environment(EnvType.CLIENT)
public class modMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return modConfig::openConfigScreen;
    }
}
