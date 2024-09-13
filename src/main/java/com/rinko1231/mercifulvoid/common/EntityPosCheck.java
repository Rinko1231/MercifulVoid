package com.rinko1231.mercifulvoid.common;

import com.rinko1231.mercifulvoid.common.Config.MVConfiguration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityPosCheck {
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        String entityId = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString();
            // 检查该实体是否在白名单中
        if (MVConfiguration.getInstance().isEntityWhitelisted(entityId)) {
            String dimensionId = entity.level().dimension().location().toString();
            // 检查该维度是否在白名单中
            if (MVConfiguration.getInstance().isDimensionWhitelisted(dimensionId)) {
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                // 检查是否处于指定的X、Z位置范围
                if (y < entity.level().getMinBuildHeight()) {
                    if (entity.isVehicle()) {
                        entity.ejectPassengers();
                    }
                    entity.stopRiding();
                    // 获取当前维度的最大建筑高度
                    int maxY = entity.level().getMaxBuildHeight();
                    entity.setPos(x, maxY+8, z);
                    //赋予不摔死tag
                    if (!entity.getTags().contains("ForgivingFalling")){
                    entity.addTag("ForgivingFalling");}
                }
            }
        }

    }
    @SubscribeEvent
    public static void onLivingEntityFall(LivingFallEvent event) {
        // 获取摔落的实体
        LivingEntity entity = event.getEntity();
        // 检查实体是否拥有“ForgivingFalling”标签
        if (entity.getTags().contains("ForgivingFalling")) {
            // 取消摔落伤害
            event.setDamageMultiplier(0);
            // 落地后移除“ForgivingFalling”标签
            entity.removeTag("ForgivingFalling");
        }
    }

}