package cn.thiamine128.swordsprite.client.render.entity.animation;

import net.minecraft.client.render.entity.animation.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class TransformationInfo {
    public Vec3f translation;
    public Vec3f rotation;
    public Vec3f scaling;

    public TransformationInfo(Vec3f translation, Vec3f rotation, Vec3f scaling) {
        this.translation = translation;
        this.rotation = rotation;
        this.scaling = scaling;
    }

    public void translate(Vec3f offset) {
        translation.add(offset);
    }

    public void rotate(Vec3f rotation) {
        this.rotation.add(rotation);
    }

    public void scale(Vec3f scaling) {
        this.scaling.add(scaling);
    }

    public Vec3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vec3f translation) {
        this.translation = translation;
    }

    public Vec3f getRotation() {
        return rotation;
    }

    public void setRotation(Vec3f rotation) {
        this.rotation = rotation;
    }

    public Vec3f getScaling() {
        return scaling;
    }

    public void setScaling(Vec3f scaling) {
        this.scaling = scaling;
    }

    public void apply(MatrixStack matrixStack) {
        matrixStack.translate(translation.getX(), translation.getY(), translation.getZ());

        if (this.rotation.getZ() != 0.0F) {
            matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(this.rotation.getZ()));
        }

        if (this.rotation.getY() != 0.0F) {
            matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(this.rotation.getY()));
        }

        if (this.rotation.getX() != 0.0F) {
            matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(this.rotation.getX()));
        }

        if (this.scaling.getX() != 1.0F || this.scaling.getY() != 1.0F || this.scaling.getZ() != 1.0F) {
            matrixStack.scale(this.scaling.getX(), this.scaling.getY(), this.scaling.getZ());
        }
    }
}
