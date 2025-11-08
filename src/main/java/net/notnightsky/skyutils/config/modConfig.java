package net.notnightsky.skyutils.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.notnightsky.skyutils.config.keyBindingHelper.keybinding;

public class modConfig {
    private static final String LOCAL_NAMESPACE_PATH = "skyutils.yacl.";

    public static ConfigClassHandler<modConfig> HANDLER = ConfigClassHandler.createBuilder(modConfig.class)
            .id(Identifier.of("skyutils", "fconfig"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("skyutils.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public static boolean fullBright = false;

    @SerialEntry
    public static double defaultGamma = 0.5;

    @SerialEntry
    public static double gamma = 100.0;

    public static Screen openConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable(LOCAL_NAMESPACE_PATH + "title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "name"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.fullBright.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.fullBright.description")))
                                .binding(false, () -> modConfig.fullBright, keybinding::setFullbright)
                                .controller(opt -> BooleanControllerBuilder.create(opt)
                                        .coloured(true)
                                        .yesNoFormatter())
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.description")))
                                .binding(100.0, () -> modConfig.gamma, newVal -> modConfig.gamma = newVal)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                                        .range(100.0, 1000.0)
                                        .step(100.0)
                                        .formatValue(val -> Text.literal(val.intValue() + "%")))
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.default-Gamma.name"))
                                .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.default-Gamma.description")))
                                .binding(0.5, () -> modConfig.defaultGamma, newVal -> modConfig.defaultGamma = newVal)
                                .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                                        .range(0.0, 100.0)
                                        .step(10.0)
                                        .formatValue(val -> Text.literal(val.intValue() + "%")))
                                .build())
                        .build())
                .build().generateScreen(parent);
    }
}
