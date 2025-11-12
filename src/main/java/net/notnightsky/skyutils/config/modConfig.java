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
import net.notnightsky.skyutils.config.keyBindingHelper.toggleHandler;

public class modConfig {
    private static final String LOCAL_NAMESPACE_PATH = "skyutils.yacl.";

    public static ConfigClassHandler<modConfig> HANDLER = ConfigClassHandler.createBuilder(modConfig.class)
            .id(Identifier.of("skyutils"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("skyutils.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public static boolean fullBright = false;

    @SerialEntry
    public static double defaultGamma = 50.0;

    @SerialEntry
    public static double gamma = 500.0;

    @SerialEntry
    public static double increment = 50.0;

    @SerialEntry
    public static double decrement = 50.0;

    public static Screen openConfigScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Text.translatable(LOCAL_NAMESPACE_PATH + "title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "name"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable( LOCAL_NAMESPACE_PATH + "group.fullBright"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.fullBright.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.fullBright.description")))
                                        .binding(false, () -> modConfig.fullBright, toggleHandler::setFullbright)
                                        .controller((Option<Boolean> opt) -> BooleanControllerBuilder.create(opt)
                                                .coloured(true)
                                                .yesNoFormatter())
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.description")))
                                        .binding(100.0, () -> modConfig.gamma, newVal -> modConfig.gamma = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(100.0, 1000.0)
                                                .step(100.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.default-Gamma.name"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.default-Gamma.description")))
                                        .binding(50.0, () -> modConfig.defaultGamma, newVal -> modConfig.defaultGamma = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(0.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.increment"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.increment.description")))
                                        .binding(50.0, () -> modConfig.increment, newVal -> modConfig.increment = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(10.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.decrement"))
                                        .description(OptionDescription.of(Text.translatable(LOCAL_NAMESPACE_PATH + "options.gamma.decrement.description")))
                                        .binding(50.0, () -> modConfig.decrement, newVal -> modConfig.decrement = newVal)
                                        .controller((Option<Double> opt) -> DoubleSliderControllerBuilder.create(opt)
                                                .range(10.0, 100.0)
                                                .step(10.0)
                                                .formatValue((Double val) -> Text.literal(val.intValue() + "%")))
                                        .build())
                                .build())
                        .build())
                .build().generateScreen(parent);
    }
}
