package cn.thiamine128.swordsprite.entity;

import cn.thiamine128.swordsprite.SwordSpriteMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class ModEntityTypes {
    public static final EntityType<SwordEntity> SWORD = Registry.register(
            Registry.ENTITY_TYPE,
            SwordSpriteMod.identify("sword"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SwordEntity::new)
                    .dimensions(EntityDimensions.fixed(0.2f, 0.4f))
                    .trackRangeChunks(10)
                    .build());
}
