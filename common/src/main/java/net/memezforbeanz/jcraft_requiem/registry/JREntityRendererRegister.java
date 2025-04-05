package net.memezforbeanz.jcraft_requiem.registry;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import lombok.NonNull;
import net.arna.jcraft.client.renderer.entity.stands.*;
import net.arna.jcraft.registry.JEntityTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public interface JREntityRendererRegister {
    record RendererData <T extends Entity> (RegistrySupplier<? extends EntityType<? extends T>> supplier, EntityRendererProvider<T> provider) {
        public void registerFabric() {
            EntityRendererRegistry.register(supplier, provider);
        }
    }

    RendererData<?>[] entries = {

            new RendererData<>(JEntityTypeRegistry.KING_CRIMSON, KingCrimsonRenderer::new),
    };

    static void registerEntityRenderers(Consumer<RendererData<?>> consumer) {
        for (RendererData<?> entry : entries) consumer.accept(entry);
    }

    static <T extends Entity> EntityRenderer<T> createEmpty(EntityRendererProvider.Context ctx) {
        return new EntityRenderer<>(ctx) {
            @Override
            public ResourceLocation getTextureLocation(@NonNull T entity) {
                return null;
            }
        };
    }
}
