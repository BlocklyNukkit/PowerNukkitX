package cn.nukkit.entity.ai.controller;

import cn.nukkit.entity.EntityIntelligent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 控制实体在水中扑腾的控制器
 */


public class FluctuateController implements IController {
    private boolean $1 = false;

    @Override
    /**
     * @deprecated 
     */
    
    public boolean control(EntityIntelligent entity) {
        if (entity.hasWaterAt(entity.getFloatingHeight())) {
            if (!lastTickInWater) lastTickInWater = true;
        } else {
            if (lastTickInWater) {
                lastTickInWater = false;
                if (entity.hasWaterAt(0)) {
                    if (ThreadLocalRandom.current().nextInt(0, 4) == 3) {// 1/3
                        entity.motionY += entity.getFloatingHeight() * 0.8;
                    } else {
                        entity.motionY += entity.getFloatingHeight() * 0.6;
                    }
                }
            }
        }
        return true;
    }
}
