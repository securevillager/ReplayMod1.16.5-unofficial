package com.replaymod.mixin;

import com.replaymod.render.hooks.EntityRendererHandler;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class Mixin_Omnidirectional_Camera implements EntityRendererHandler.IEntityRenderer {
    @Redirect(method = "method_22973", at = @At(value = "INVOKE", target ="Lnet/minecraft/util/math/vector/Matrix4f;perspective(FFFF)Lnet/minecraft/util/math/vector/Matrix4f;"
    private Matrix4f replayModRender_perspective$0(double fovY, float aspect, float zNear, float zFar) {
        return replayModRender_perspective((float) fovY, aspect, zNear, zFar);
    }

    private Matrix4f replayModRender_perspective$0(
        float fovY,
        float aspect,
        float zNear,
        float zFar
    ) {
    return replayModRender_perspective(fovY, aspect, zNear, zFar);
 }
}
