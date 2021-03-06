package com.github.tartaricacid.bakaintouhou.common.item.danmaku;

import com.github.tartaricacid.bakaintouhou.BakaInTouhou;
import com.github.tartaricacid.bakaintouhou.common.entity.danmaku.EntityDanmaku;
import com.github.tartaricacid.bakaintouhou.common.item.ItemObjectHolder;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDanmaku extends Item {
    public ItemDanmaku() {
        setUnlocalizedName(BakaInTouhou.MOD_ID + ".danmaku");
        setRegistryName("danmaku");
        setCreativeTab(ItemObjectHolder.bakaInTouhouTabs);
        setMaxStackSize(16);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote) {
            ItemStack stack = playerIn.getHeldItem(handIn);
            EntityDanmaku entityDanmaku;
            switch (getDanmakuType(stack)) {
                case 0:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack), 0.1f, 0.1f);
                    break;
                case 1:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack), 0.2f, 0.2f);
                    break;
                case 2:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack), 1f, 1f);
                    break;
                case 3:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack), 4f, 4f);
                    break;
                case 4:
                case 9:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack), 0.2f, 0.4f);
                    break;
                case 5:
                case 7:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack), 0.25f, 0.4f);
                    break;
                case 6:
                case 8:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack));
                    break;

                case 10:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack), 0.15f, 0.35f);
                    break;
                default:
                    entityDanmaku = new EntityDanmaku(worldIn, playerIn, getDanmakuDamage(stack),
                            getDanmakuGravity(stack), getDanmakuType(stack));
            }
            playerIn.getHeldItem(handIn).shrink(1);
            Vec3d v = playerIn.getLookVec();
            entityDanmaku.shoot(v.x, v.y, v.z, 0.9f, 5f);
            worldIn.spawnEntity(entityDanmaku);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemStack = new ItemStack(this);
        getTagCompoundSafe(itemStack).setInteger("danmaku_type", 0);
        getTagCompoundSafe(itemStack).setInteger("danmaku_damage", 1);
        getTagCompoundSafe(itemStack).setFloat("danmaku_gravity", 0.01f);
        return itemStack;
    }

    // 为标签页所添加的物品
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            items.add(getDefaultInstance());
            items.add(setDanmakuType(getDefaultInstance(), 1));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 2), 3), 0.005f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 3), 4), 0.003f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 4), 2), 0.002f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 5), 2), 0.005f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 6), 2), 0.01f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 7), 2), 0.01f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 8), 2), 0.01f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 9), 3), 0.01f));
            items.add(setDanmakuGravity(setDanmakuDamage(setDanmakuType(getDefaultInstance(), 10), 3), 0.01f));
        }
    }

    private NBTTagCompound getTagCompoundSafe(ItemStack stack) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound == null) {
            tagCompound = new NBTTagCompound();
            stack.setTagCompound(tagCompound);
        }
        return tagCompound;
    }

    public boolean hasDanmakuDamage(ItemStack stack) {
        return getTagCompoundSafe(stack).hasKey("danmaku_damage");
    }

    public int getDanmakuDamage(ItemStack stack) {
        return getTagCompoundSafe(stack).getInteger("danmaku_damage");
    }

    public ItemStack setDanmakuDamage(ItemStack stack, int damage) {
        getTagCompoundSafe(stack).setInteger("danmaku_damage", damage);
        return stack;
    }

    public boolean hasDanmakuGravity(ItemStack stack) {
        return getTagCompoundSafe(stack).hasKey("danmaku_gravity");
    }

    public float getDanmakuGravity(ItemStack stack) {
        return getTagCompoundSafe(stack).getFloat("danmaku_gravity");
    }

    public ItemStack setDanmakuGravity(ItemStack stack, float gravity) {
        getTagCompoundSafe(stack).setFloat("danmaku_gravity", gravity);
        return stack;
    }

    public boolean hasDanmakuType(ItemStack stack) {
        return getTagCompoundSafe(stack).hasKey("danmaku_type");
    }

    public int getDanmakuType(ItemStack stack) {
        return getTagCompoundSafe(stack).getInteger("danmaku_type");
    }

    public ItemStack setDanmakuType(ItemStack stack, int type) {
        getTagCompoundSafe(stack).setInteger("danmaku_type", type);
        return stack;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format(BakaInTouhou.MOD_ID + ".danmaku.tooltips.type",
                I18n.format(BakaInTouhou.MOD_ID + ".danmaku.type_name." + getDanmakuType(stack))));
        tooltip.add(I18n.format(BakaInTouhou.MOD_ID + ".danmaku.tooltips.damage", getDanmakuDamage(stack)));
        tooltip.add(I18n.format(BakaInTouhou.MOD_ID + ".danmaku.tooltips.gravity", getDanmakuGravity(stack)));
    }
}
