package link.botwmcs.davinci.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final CommonConfig CONFIG;

    public final ForgeConfigSpec.BooleanValue enableDemo;
    public final ForgeConfigSpec.BooleanValue enableLtsxComponents;

    static {
        Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("demo");
        enableDemo = builder
                .comment("If true, the demo will be enabled.")
                .define("enableDemo", true);
        builder.pop();

        builder.push("ltsx");
        enableLtsxComponents = builder
                .comment("If true, the Ltsx components will be enabled.")
                .define("enableLtsxComponents", true);
        builder.pop();
    }
}
