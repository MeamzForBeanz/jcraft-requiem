package net.memezforbeanz.jcraft_requiem.common.entities.stand;

import net.arna.jcraft.common.entity.stand.StandEntity;
import net.arna.jcraft.common.util.IStandType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class CustomStandType implements IStandType {
    private final Supplier<EntityType<? extends StandEntity<?, ?>>> entityType;
    private final String nameKey;
    private final Component nameText;
    private final List<Component> skinNames;
    private final boolean evolution;
    private final boolean obtainable;
    private final String ID;

    public CustomStandType(Supplier<EntityType<? extends StandEntity<?, ?>>> entityType,
                           String nameKey,
                           boolean evolution,
                           boolean obtainable,
                           String ID, 
                           Component... skinNames) {
        this.entityType = entityType;
        this.nameKey = nameKey;
        this.evolution = evolution;
        this.obtainable = obtainable;
        this.nameText = Component.translatable("entity.jcraft-requiem." + nameKey);
        this.skinNames = List.of(skinNames);
        this.ID = ID;
    }

    @Override
    public String getId() {
        return this.ID;
    }

    @Override
    public EntityType<? extends StandEntity<?, ?>> getEntityType() {
        return entityType.get();
    }

    @Override
    public boolean isEvolution() {
        return evolution;
    }

    @Override
    public boolean isObtainable() {
        return obtainable;
    }

    @Override
    public String getNameKey() {
        return nameKey;
    }

    @Override
    public Component getNameText() {
        return nameText;
    }

    @Override
    public List<Component> getSkinNames() {
        return skinNames;
    }

    @Override
    public StandEntity<?, ?> createNew(Level world) {
        return new KingCrimsonRequiemEntity(world);
    }

    @Override
    public int getSkinCount() {
        return skinNames.size() + 1;
    }

    @Override
    public Component getName() {
        return nameText;
    }
}