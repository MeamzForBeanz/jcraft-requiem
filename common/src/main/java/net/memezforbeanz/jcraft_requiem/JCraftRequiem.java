package net.memezforbeanz.jcraft_requiem;


import net.arna.jcraft.common.util.StandTypeManager;
import net.arna.jcraft.registry.JCreativeMenuTabRegistry;
import net.memezforbeanz.jcraft_requiem.common.entities.stand.CustomStandType;
import net.memezforbeanz.jcraft_requiem.common.entities.stand.KingCrimsonRequiemEntity;
import net.memezforbeanz.jcraft_requiem.registry.JRStandRegistry;
import net.memezforbeanz.jcraft_requiem.registry.JREntityTypeRegistry;
import net.minecraft.resources.ResourceLocation;

public final class JCraftRequiem {
    public static final String MOD_ID = "jcraft-requiem";

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    // Initialize method - called during mod setup
    public static void init() {
        // Initialize registries
        JREntityTypeRegistry.init();
        JRStandRegistry.init();

        StandTypeManager.register(
                JRStandRegistry.KING_CRIMSON_REQUIEM);
    }

    // Register entity attributes after entities are registered
    public static void registerEntityAttributes() {
        JREntityTypeRegistry.registerAttributes();
    }
}