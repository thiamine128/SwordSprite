package cn.thiamine128.swordsprite.client.render.entity;

import cn.thiamine128.swordsprite.entity.SwordEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SwordEntityRenderer extends EntityRenderer<SwordEntity> {
    private final ItemRenderer itemRenderer;

    public SwordEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(SwordEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);

        if (entity.getItem() == null || entity.getItem() == ItemStack.EMPTY)
            return;

        ItemStack itemStack = entity.getItem();

        matrices.push();

        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.world, (LivingEntity)null, entity.getId());

        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.NONE, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, bakedModel);

        matrices.pop();
    }

    @Override
    public Identifier getTexture(SwordEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
