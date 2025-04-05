package net.memezforbeanz.jcraft_requiem.registry;


import net.arna.jcraft.common.util.IStandType;
import net.memezforbeanz.jcraft_requiem.JCraftRequiem;
import net.memezforbeanz.jcraft_requiem.common.entities.stand.CustomStandType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;



public class JRStandRegistry {
    // Map to store your custom stands
    private static final Map<ResourceLocation, IStandType> STAND_TYPES = new HashMap<>();


    // Instance of your custom stand type
    public static final IStandType KING_CRIMSON_REQUIEM = new CustomStandType(
            JREntityTypeRegistry.KING_CRIMSON_REQUIEM::get,
            "king_crimson_requiem",
            false, // Not an evolution
            true,  // Is obtainable
            "king_crimson_requiem",
            Component.literal("Crimson Variant"),
            Component.literal("Shadow Variant"),
            Component.literal("Royal Variant")
    );

    public static void init() {
        // Register your stand type
        STAND_TYPES.put(JCraftRequiem.id("king_crimson_requiem"), KING_CRIMSON_REQUIEM);
    }

    // Method to access your stand types
    public static Map<ResourceLocation, IStandType> getStandTypes() {
        return STAND_TYPES;
    }
}