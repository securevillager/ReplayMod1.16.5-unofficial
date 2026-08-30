package com.replaymod.mixin;

import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FMLHandshakeHandler.class)
public abstract class MixinFMLHandshakeHandler {
    @Shadow
    private List<NetworkRegistry.LoginPayload> messageList;

    @Shadow
    @Final
    private NetworkDirection direction;

    @Inject(method = "<init>(Lnet/minecraft/network/NetworkManager;Lnet/minecraftforge/fml/network/NetworkDirection;)V", at = @At("TAIL"))
    public void replayModRecording_setupForLocalRecording(NetworkManager networkManager, NetworkDirection side, CallbackInfo ci) {
        if (networkManager.isLocalChannel()) {
            // Forge already has a dedicated local-connection path.  Replacing it
            // with remote login payloads triggers a registry injection on the
            // render thread and can deadlock the integrated server.
            return;
        }
    }

    @Redirect(method = "handleRegistryLoading", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;closeChannel(Lnet/minecraft/util/text/ITextComponent;)V"))
    public void replayMod_ignoreHandshakeConnectionClose(NetworkManager networkManager, ITextComponent message) {
    }
}
