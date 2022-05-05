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

    // TODO: Use config
    public static int DEFAULT_LIFETIME = 5;
    public static float DEFAULT_HIT_DAMAGE = 2f;
    public static int DEFAULT_IGNITE_DURATION_MIN = 2;
    public static int DEFAULT_IGNITE_DURATION_RANGE = 5;

    private int lifetime = DEFAULT_LIFETIME;
    private float hitDamage = DEFAULT_HIT_DAMAGE;

    private int igniteDurationMin = DEFAULT_IGNITE_DURATION_MIN;
    private int igniteDurationRange = DEFAULT_IGNITE_DURATION_RANGE;

    private int tickLeft = lifetime;

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
        entity.attackEntityFrom(new IndirectEntityDamageSource("magic", this,
                getShooter()).setFireDamage().setMagicDamage(), hitDamage);

        entity.setFire(getRandomIgniteDuration());
    }

    @Override
    public void tick() {
        if (tickLeft <= 0) {
            remove();
        }
        tickLeft -= 1;

        // Movement
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x, vector3d.y - (double)this.getGravityVelocity(), vector3d.z);
        vector3d = this.getMotion();
        double d2 = this.getPosX() + vector3d.x;
        double d0 = this.getPosY() + vector3d.y;
        double d1 = this.getPosZ() + vector3d.z;
        this.setPosition(d2, d0, d1);

        if (world.isRemote) {
            for (int count = 0; count < 8; ++count) {
                world.addParticle(ParticleTypes.FLAME, getPosXRandom(0.5), getPosYRandom(), getPosZRandom(0.5), 0, 0, 0);
            }
            return;
        }

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

    public int getRandomIgniteDuration() {
        return igniteDurationMin + rand.nextInt(igniteDurationRange);
    }

    public int getIgniteDurationMin() {
        return igniteDurationMin;
    }

    public void setIgniteDurationMin(int igniteDurationMin) {
        this.igniteDurationMin = igniteDurationMin;
    }

    public int getIgniteDurationRange() {
        return igniteDurationRange;
    }

    public void setIgniteDurationRange(int igniteDurationRange) {
        this.igniteDurationRange = igniteDurationRange;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public float getHitDamage() {
        return hitDamage;
    }

    public void setHitDamage(float hitDamage) {
        this.hitDamage = hitDamage;
    }

    public int getTickLeft() {
        return tickLeft;
    }

    public void setTickLeft(int tickLeft) {
        this.tickLeft = tickLeft;
    }
}
