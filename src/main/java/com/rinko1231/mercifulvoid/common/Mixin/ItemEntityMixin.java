package com.rinko1231.mercifulvoid.common.Mixin;

import com.rinko1231.mercifulvoid.common.Config.MVConfiguration;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if(MVConfiguration.getInstance().enableItemVoidProtection()) {
            ItemEntity itemEntity = (ItemEntity) (Object) this;
            String itemId = ForgeRegistries.ITEMS.getKey(itemEntity.getItem().getItem()).toString();
            if (!MVConfiguration.getInstance().isItemProtectionBlacklisted(itemId)){
                String dimensionId = itemEntity.level().dimension().location().toString();
            if (MVConfiguration.getInstance().isItemProtectionDimWhitelisted(dimensionId)) {
                double x = itemEntity.getX();
                double y = itemEntity.getY();
                double z = itemEntity.getZ();
                // 检查是否处于指定的X、Z位置范围
                if (y < itemEntity.level().getMinBuildHeight()) {
                    itemEntity.setPos(x, itemEntity.level().getMinBuildHeight(), z);
                    Vec3 currentMotion = itemEntity.getDeltaMovement();
                    // 设置向上的动量值
                    double bounceSpeed = 1.0;
                    // 创建一个新的动量矢量，保留X和Z轴的动量，改变Y轴的动量
                    Vec3 newMotion = new Vec3(currentMotion.x, bounceSpeed, currentMotion.z);
                    // 将新的动量赋予实体
                    itemEntity.setDeltaMovement(newMotion);
                }
            }
          }
        }
    }
}