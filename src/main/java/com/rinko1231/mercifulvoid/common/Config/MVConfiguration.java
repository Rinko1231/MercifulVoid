package com.rinko1231.mercifulvoid.common.Config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;


public class MVConfiguration
{
    private static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final MVConfiguration INSTANCE = new MVConfiguration();
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> dimensionWhitelist;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> entityWhitelist;
    private static final ForgeConfigSpec.BooleanValue itemVoidProtection;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> itemProtectionDimWhitelist;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> itemProtectionBlacklist;
    static
    {
        BUILDER.push("Merciful Void Config");

        dimensionWhitelist = BUILDER
                .comment("Dimension Whitelist for the living entity")
                .defineList("Dimension Whitelist", List.of("minecraft:the_end"),
                        element -> element instanceof String);

        entityWhitelist = BUILDER
                .comment("Living Entity Whitelist")
                .comment("Note: Only living entities like pig and zombie are allowed, so filling in 'minecraft:item' here will not take effect")
                .defineList("Entity Whitelist", List.of("corpse:corpse"),
                        element -> element instanceof String);

        itemVoidProtection = BUILDER
                .comment("If true, drop items will bounce back when it fall into void in whitelisted dimension")
                .define("Enable Item Void Protection", true);

        itemProtectionDimWhitelist = BUILDER
                .comment("Dimension Whitelist where itemVoidProtection is enabled")
                .defineList("Item Protection Dimension Whitelist", List.of("minecraft:the_end"),
                        element -> element instanceof String);

        itemProtectionBlacklist = BUILDER
                .comment("Drop items that will not be protected")
                .comment("In case some items need to be thrown into void like Create's Chromatic Compound")
                .defineList("Item Protection Blacklist", List.of("create:chromatic_compound"),
                        element -> element instanceof String);

        SPEC = BUILDER.build();
    }


    public List<? extends String> getDimensionWhitelist()
    {   return dimensionWhitelist.get();
    }
    public List<? extends String> getEntityWhitelist()
    {   return entityWhitelist.get();
    }
    public List<? extends String> getItemProtectionDimWhitelist()
    {   return itemProtectionDimWhitelist.get();
    }
    public List<? extends String> getItemProtectionBlacklist()
    {   return itemProtectionBlacklist.get();
    }

    public boolean isDimensionWhitelisted(String dimensionId) {
        return getDimensionWhitelist().contains(dimensionId);
    }
    public boolean isEntityWhitelisted(String entityId) {
        return getEntityWhitelist().contains(entityId);
    }
    public boolean isItemProtectionDimWhitelisted(String dimensionId) { return getItemProtectionDimWhitelist().contains(dimensionId);}
    public boolean enableItemVoidProtection() {return itemVoidProtection.get();}
    public boolean isItemProtectionBlacklisted(String itemId) { return getItemProtectionBlacklist().contains(itemId);}

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "MercifulVoid.toml");
    }
    public static MVConfiguration getInstance()
    {
        return INSTANCE;
    }

}