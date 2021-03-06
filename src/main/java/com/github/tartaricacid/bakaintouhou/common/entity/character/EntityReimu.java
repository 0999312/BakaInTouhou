package com.github.tartaricacid.bakaintouhou.common.entity.character;

import com.github.tartaricacid.bakaintouhou.common.util.DanmakuShoot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityReimu extends EntityTouhouCharacter {
    public EntityReimu(World worldIn) {
        super(worldIn);
        setSize(0.6f, 1.5f);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        DanmakuShoot.fanShapedShot(this.world, this, target, 0.1f, 30f,
                5, 0.05f, 2 * Math.PI, 6, 15, 0.0003f, 0.25f, 0.25f);
    }
}
