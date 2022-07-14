package cn.thiamine128.swordsprite.client.render.entity;

import cn.thiamine128.swordsprite.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModEntityRenderers {
    public static void register() {
        EntityRendererRegistry.register(
                ModEntityTypes.SWORD,
                ctx -> new SwordEntityRenderer(ctx)
        );
    }
}
