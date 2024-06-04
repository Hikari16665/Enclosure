package com.github.zly2006.enclosure.mixin;

import com.github.zly2006.enclosure.EnclosureArea;
import com.github.zly2006.enclosure.ServerMain;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import net.minecraft.village.raid.RaidManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RaidManager.class)
public class MixinRaidManager {
    @Unique
    private final static Logger LOGGER = LoggerFactory.getLogger("Enclosure Raid Alert");
    @Inject(method = "startRaid", at = @At("RETURN"))
    private void onStart(ServerPlayerEntity player, BlockPos p, CallbackInfoReturnable<Raid> cir) {
        Raid raid = cir.getReturnValue();
        if (raid != null) {
            BlockPos pos = raid.getCenter();
            EnclosureArea area = ServerMain.INSTANCE.getSmallestEnclosure(player.getServerWorld(), pos);
            if (area != null) {
                LOGGER.info("Raid {} started by {} in enclosure {} at {} {} {}.", raid.getRaidId(), player.getNameForScoreboard(), area.getName(), pos.getX(), pos.getY(), pos.getZ());
            }
        }
    }
}
