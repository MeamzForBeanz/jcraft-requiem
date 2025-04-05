package net.memezforbeanz.jcraft_requiem.registry;


import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.memezforbeanz.jcraft_requiem.JCraftRequiem;
import net.memezforbeanz.jcraft_requiem.common.entities.stand.KingCrimsonRequiemEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class JREntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTRY =
            DeferredRegister.create(JCraftRequiem.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<KingCrimsonRequiemEntity>> KING_CRIMSON_REQUIEM =
            ENTITY_TYPE_REGISTRY.register(JCraftRequiem.id("king_crimson_requiem"),
                    () -> EntityType.Builder.of(
                            WorldOnlyEntityFactory.from(KingCrimsonRequiemEntity::new),
                            MobCategory.CREATURE
                    ).sized(
                            0.6f,
                            1.8f
                    ).build("king_crimson_requiem")
            );

    public static void registerAttributes() {
        // Register attributes for your entity
        EntityAttributeRegistry.register(KING_CRIMSON_REQUIEM, KingCrimsonRequiemEntity::createMobAttributes);
    }

    public static void init() {
        ENTITY_TYPE_REGISTRY.register();
    }

    // Use the same factory pattern as JEntityTypeRegistry
    static class WorldOnlyEntityFactory<T extends net.minecraft.world.entity.Entity> implements EntityType.EntityFactory<T> {
        private final Function<Level, T> ctor;

        private WorldOnlyEntityFactory(Function<Level, T> ctor) {
            this.ctor = ctor;
        }

        public static <T extends net.minecraft.world.entity.Entity> WorldOnlyEntityFactory<T> from(Function<Level, T> ctor) {
            return new WorldOnlyEntityFactory<>(ctor);
        }

        @Override
        public T create(EntityType<T> type, Level world) {
            return ctor.apply(world);
        }
    }
}