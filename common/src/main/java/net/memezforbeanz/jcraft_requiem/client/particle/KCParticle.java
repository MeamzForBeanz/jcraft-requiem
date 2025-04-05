package net.memezforbeanz.jcraft_requiem.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class KCParticle extends RisingParticle {
    private final SpriteSet spriteProvider;

    KCParticle(final ClientLevel world, final double x, final double y, final double z, final double velocityX, final double velocityY, final double velocityZ, final SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.quadSize = 0.2f + random.nextFloat() * 0.2f;
        this.lifetime = 4;
        this.setSpriteFromAge(spriteProvider);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        final float c = 1f - (float) age / (float) lifetime;
        this.setColor(c, c, c);

        if (!this.removed) {
            this.setSprite(spriteProvider.get(random));
        }
    }

    @Override
    protected int getLightColor(float tint) {
        return 255;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(final SimpleParticleType defaultParticleType, final ClientLevel clientWorld, final double d, final double e, final double f, final double g, final double h, final double i) {
            return new KCParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
