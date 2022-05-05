package com.dirtboll.terramagic.entities;

import com.dirtboll.terramagic.registries.EntityRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireSparkEntity extends ProjectileItemEntity {

    private static int LIFETIME = 5;
    private static float HIT_DAMAGE = 2f;
    private static int IGNITE_DURATION = 3;

    private int tickLeft = LIFETIME;

    public FireSparkEntity(EntityType<FireSparkEntity> entityType, World world) {
        super(entityType, world);
    }

    public FireSparkEntity(World worldIn, PlayerEntity player) {
        super(EntityRegistry.FIRE_SPARK, player, worldIn);
        setPosition(getPosX(), getPosY() - getSize(Pose.STANDING).height/2, getPosZ());
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return super.getBoundingBox();
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.01f;
    }

    protected void onEntityHit(Entity entity) {
        entity.attackEntityFrom(new IndirectEntityDamageSource("magic", this, getShooter()).setFireDamage().setMagicDamage(), HIT_DAMAGE);
        entity.setFire(IGNITE_DURATION);
    }

    @Override
    public void tick() {
        if (tickLeft <= 0) {
            remove();
        }
        tickLeft -= 1;

        // Particles
        for (int count = 0; count < 8; ++count) {
            world.addParticle(ParticleTypes.FLAME, getPosXRandom(0.5), getPosYRandom(), getPosZRandom(0.5), 0, 0, 0);
        }

        // Movement
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x, vector3d.y - (double)this.getGravityVelocity(), vector3d.z);
        vector3d = this.getMotion();
        double d2 = this.getPosX() + vector3d.x;
        double d0 = this.getPosY() + vector3d.y;
        double d1 = this.getPosZ() + vector3d.z;
        this.setPosition(d2, d0, d1);

        if (world.isRemote) // is client side
            return;

        // Collision detection
        AxisAlignedBB motionBB = getBoundingBox().expand(getMotion());
        for (Entity entity : world.getEntitiesInAABBexcluding(this, motionBB, this::func_230298_a_ /* Entity filter */)) {
            onEntityHit(entity);
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
