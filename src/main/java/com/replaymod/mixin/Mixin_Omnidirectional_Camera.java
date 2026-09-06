package com.replaymod.mixin;

import com.replaymod.render.hooks.EntityRendererHandler;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class Mixin_Omnidirectional_Camera
        implements EntityRendererHandler.IEntityRenderer {

    @Redirect(
            method = "getProjectionMatrix",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/vector/Matrix4f;perspective(DFFF)Lnet/minecraft/util/math/vector/Matrix4f;"
            )
    )
    private Matrix4f replayModRender_perspective(
            double fovY,
            float aspect,
            float zNear,
            float zFar
    ) {
        EntityRendererHandler handler = replayModRender_getHandler();

        if (handler != null && handler.omnidirectional) {
            fovY = 90.0D;
            aspect = 1.0F;
        }

        return Matrix4f.perspective(fovY, aspect, zNear, zFar);
    }
}
