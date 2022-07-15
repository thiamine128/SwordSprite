package cn.thiamine128.swordsprite.client.render.entity.animation;


import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public record SwordAnimation(float lengthInSeconds, boolean looping, Map<String, List<SwordTransformation>> boneAnimations) {
    public SwordAnimation(float lengthInSeconds, boolean looping, Map<String, List<SwordTransformation>> boneAnimations) {
        this.lengthInSeconds = lengthInSeconds;
        this.looping = looping;
        this.boneAnimations = boneAnimations;
    }

    public float lengthInSeconds() {
        return this.lengthInSeconds;
    }

    public boolean looping() {
        return this.looping;
    }

    public Map<String, List<SwordTransformation>> boneAnimations() {
        return this.boneAnimations;
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final float lengthInSeconds;
        private final Map<String, List<SwordTransformation>> transformations = Maps.newHashMap();
        private boolean looping;

        public static SwordAnimation.Builder create(float lengthInSeconds) {
            return new SwordAnimation.Builder(lengthInSeconds);
        }

        private Builder(float lengthInSeconds) {
            this.lengthInSeconds = lengthInSeconds;
        }

        public SwordAnimation.Builder looping() {
            this.looping = true;
            return this;
        }

        public SwordAnimation.Builder addBoneAnimation(String name, SwordTransformation transformation) {
            ((List)this.transformations.computeIfAbsent(name, (namex) -> {
                return Lists.newArrayList();
            })).add(transformation);
            return this;
        }

        public SwordAnimation build() {
            return new SwordAnimation(this.lengthInSeconds, this.looping, this.transformations);
        }
    }
}
