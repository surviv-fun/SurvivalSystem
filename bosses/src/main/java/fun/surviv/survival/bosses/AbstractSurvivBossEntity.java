/*
 * Copyright (c) LuciferMorningstarDev <contact@lucifer-morningstar.dev>
 * Copyright (c) surviv.fun <contact@surviv.fun>
 * Copyright (C) surviv.fun team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package fun.surviv.survival.bosses;

import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.TargetEntityInfo;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.EntityEffect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * SurvivalSystem; fun.surviv.survival.bosses:AbstractSurvivBossEntity
 *
 * @author LuciferMorningstarDev - https://github.com/LuciferMorningstarDev
 * @since 12.08.2022
 */
public abstract class AbstractSurvivBossEntity implements SurvivBossEntity {

    @Getter
    private Creature creature;
    @Getter
    private SurvivBossLootTable survivBossLootTable;
    @Getter
    private SurvivBossType survivBossType;

    @Getter
    private int spawnedTick;

    private UUID bossTicker;

    public AbstractSurvivBossEntity(SurvivBossType type) {
        this.survivBossType = type;
        this.survivBossLootTable = new SurvivBossLootTable(new NamespacedKey("surviv-boss", type.getIdName().toLowerCase()));
    }

    public synchronized AbstractSurvivBossEntity spawnBoss(Location location) {
        if (!this.survivBossType.getEntityType().isSpawnable() || !this.survivBossType.getEntityType().isAlive()) {
            throw new IllegalStateException("the given entity type is not a living or spawnable entity");
        }
        this.creature = (Creature) location.getWorld().spawnEntity(location, this.survivBossType.getEntityType(), CreatureSpawnEvent.SpawnReason.CUSTOM);
        this.spawnedTick = this.creature.getServer().getCurrentTick();
        this.bossTicker = BossManager.timings().addAsyncTask((currentTick) -> tick(currentTick));
        return this;
    }

    @Override
    public abstract void tick(final long tick);

    @Override
    public Creature asCreature() {
        return this.creature;
    }

    @Override
    public Mob asMob() {
        return this.creature;
    }

    @Override
    public Entity asEntity() {
        return this.creature;
    }

    @Override
    public double getEyeHeight() {
        return this.creature.getEyeHeight();
    }

    @Override
    public double getEyeHeight(final boolean ignorePose) {
        return this.creature.getEyeHeight(ignorePose);
    }

    @Override
    public @NotNull Location getEyeLocation() {
        return this.creature.getEyeLocation();
    }

    @Override
    public @NotNull List<Block> getLineOfSight(@Nullable final Set<Material> transparent, final int maxDistance) {
        return this.creature.getLineOfSight(transparent, maxDistance);
    }

    @Override
    public @NotNull Block getTargetBlock(@Nullable final Set<Material> transparent, final int maxDistance) {
        return this.creature.getTargetBlock(transparent, maxDistance);
    }

    @Override
    public @Nullable Block getTargetBlock(final int maxDistance, final TargetBlockInfo.@NotNull FluidMode fluidMode) {
        return this.creature.getTargetBlock(maxDistance, fluidMode);
    }

    @Override
    public @Nullable BlockFace getTargetBlockFace(final int maxDistance, final TargetBlockInfo.@NotNull FluidMode fluidMode) {
        return this.creature.getTargetBlockFace(maxDistance, fluidMode);
    }

    @Override
    public @Nullable TargetBlockInfo getTargetBlockInfo(
            final int maxDistance, final TargetBlockInfo.@NotNull FluidMode fluidMode
    ) {
        return this.creature.getTargetBlockInfo(maxDistance, fluidMode);
    }

    @Override
    public @Nullable Entity getTargetEntity(final int maxDistance, final boolean ignoreBlocks) {
        return this.creature.getTargetEntity(maxDistance, ignoreBlocks);
    }

    @Override
    public @Nullable TargetEntityInfo getTargetEntityInfo(final int maxDistance, final boolean ignoreBlocks) {
        return this.creature.getTargetEntityInfo(maxDistance, ignoreBlocks);
    }

    @Override
    public @NotNull List<Block> getLastTwoTargetBlocks(@Nullable final Set<Material> transparent, final int maxDistance) {
        return this.creature.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    @Override
    public @Nullable Block getTargetBlockExact(final int maxDistance) {
        return this.creature.getTargetBlockExact(maxDistance);
    }

    @Override
    public @Nullable Block getTargetBlockExact(final int maxDistance, @NotNull final FluidCollisionMode fluidCollisionMode) {
        return this.creature.getTargetBlockExact(maxDistance, fluidCollisionMode);
    }

    @Override
    public @Nullable RayTraceResult rayTraceBlocks(final double maxDistance) {
        return this.creature.rayTraceBlocks(maxDistance);
    }

    @Override
    public @Nullable RayTraceResult rayTraceBlocks(
            final double maxDistance, @NotNull final FluidCollisionMode fluidCollisionMode
    ) {
        return this.creature.rayTraceBlocks(maxDistance, fluidCollisionMode);
    }

    @Override
    public int getRemainingAir() {
        return this.creature.getRemainingAir();
    }

    @Override
    public void setRemainingAir(final int ticks) {
        this.creature.setRemainingAir(ticks);
    }

    @Override
    public int getMaximumAir() {
        return this.creature.getMaximumAir();
    }

    @Override
    public void setMaximumAir(final int ticks) {
        this.creature.setMaximumAir(ticks);
    }

    @Override
    public int getArrowCooldown() {
        return this.creature.getArrowCooldown();
    }

    @Override
    public void setArrowCooldown(final int ticks) {
        this.creature.setArrowCooldown(ticks);
    }

    @Override
    public int getArrowsInBody() {
        return this.creature.getArrowsInBody();
    }

    @Override
    public void setArrowsInBody(final int count) {
        this.creature.setArrowsInBody(count);
    }

    @Override
    public int getBeeStingerCooldown() {
        return this.creature.getBeeStingerCooldown();
    }

    @Override
    public void setBeeStingerCooldown(final int ticks) {
        this.creature.setBeeStingerCooldown(ticks);
    }

    @Override
    public int getBeeStingersInBody() {
        return this.creature.getBeeStingersInBody();
    }

    @Override
    public void setBeeStingersInBody(final int count) {
        this.creature.setBeeStingersInBody(count);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return this.creature.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(final int ticks) {
        this.creature.setMaximumNoDamageTicks(ticks);
    }

    @Override
    public double getLastDamage() {
        return this.creature.getLastDamage();
    }

    @Override
    public void setLastDamage(final double damage) {
        this.creature.setLastDamage(damage);
    }

    @Override
    public int getNoDamageTicks() {
        return this.creature.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(final int ticks) {
        this.creature.setNoDamageTicks(ticks);
    }

    @Override
    public @Nullable Player getKiller() {
        return this.creature.getKiller();
    }

    @Override
    public void setKiller(@Nullable final Player killer) {
        this.creature.setKiller(killer);
    }

    @Override
    public boolean addPotionEffect(@NotNull final PotionEffect effect) {
        return this.creature.addPotionEffect(effect);
    }

    @Override
    public boolean addPotionEffect(@NotNull final PotionEffect effect, final boolean force) {
        return this.creature.addPotionEffect(effect, force);
    }

    @Override
    public boolean addPotionEffects(@NotNull final Collection<PotionEffect> effects) {
        return this.creature.addPotionEffects(effects);
    }

    @Override
    public boolean hasPotionEffect(@NotNull final PotionEffectType type) {
        return this.creature.hasPotionEffect(type);
    }

    @Override
    public @Nullable PotionEffect getPotionEffect(@NotNull final PotionEffectType type) {
        return this.creature.getPotionEffect(type);
    }

    @Override
    public void removePotionEffect(@NotNull final PotionEffectType type) {
        this.creature.removePotionEffect(type);
    }

    @Override
    public @NotNull Collection<PotionEffect> getActivePotionEffects() {
        return this.creature.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(@NotNull final Entity other) {
        return this.creature.hasLineOfSight(other);
    }

    @Override
    public boolean hasLineOfSight(@NotNull final Location location) {
        return this.creature.hasLineOfSight(location);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return this.creature.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(final boolean remove) {
        this.creature.setRemoveWhenFarAway(remove);
    }

    @Override
    public @NotNull EntityEquipment getEquipment() {
        return this.creature.getEquipment();
    }

    @Override
    public boolean getCanPickupItems() {
        return this.creature.getCanPickupItems();
    }

    @Override
    public void setCanPickupItems(final boolean pickup) {
        this.creature.setCanPickupItems(pickup);
    }

    @Override
    public boolean isLeashed() {
        return this.creature.isLeashed();
    }

    @Override
    public @NotNull Entity getLeashHolder() throws IllegalStateException {
        return this.creature.getLeashHolder();
    }

    @Override
    public boolean setLeashHolder(@Nullable final Entity holder) {
        return this.creature.setLeashHolder(holder);
    }

    @Override
    public boolean isGliding() {
        return this.creature.isGliding();
    }

    @Override
    public void setGliding(final boolean gliding) {
        this.creature.setGliding(gliding);
    }

    @Override
    public boolean isSwimming() {
        return this.creature.isSwimming();
    }

    @Override
    public void setSwimming(final boolean swimming) {
        this.creature.setSwimming(swimming);
    }

    @Override
    public boolean isRiptiding() {
        return this.creature.isRiptiding();
    }

    @Override
    public boolean isSleeping() {
        return this.creature.isSleeping();
    }

    @Override
    public boolean isClimbing() {
        return this.creature.isClimbing();
    }

    @Override
    public void setAI(final boolean ai) {
        this.creature.setAI(ai);
    }

    @Override
    public boolean hasAI() {
        return this.creature.hasAI();
    }

    @Override
    public void attack(@NotNull final Entity target) {
        this.creature.attack(target);
    }

    @Override
    public void swingMainHand() {
        this.creature.swingMainHand();
    }

    @Override
    public void swingOffHand() {
        this.creature.swingOffHand();
    }

    @Override
    public boolean isCollidable() {
        return this.creature.isCollidable();
    }

    @Override
    public void setCollidable(final boolean collidable) {
        this.creature.setCollidable(collidable);
    }

    @Override
    public @NotNull Set<UUID> getCollidableExemptions() {
        return this.creature.getCollidableExemptions();
    }

    @Override
    public <T> @Nullable T getMemory(@NotNull final MemoryKey<T> memoryKey) {
        return this.creature.getMemory(memoryKey);
    }

    @Override
    public <T> void setMemory(@NotNull final MemoryKey<T> memoryKey, @Nullable final T memoryValue) {
        this.creature.setMemory(memoryKey, memoryValue);
    }

    @Override
    public @NotNull EntityCategory getCategory() {
        return this.creature.getCategory();
    }

    @Override
    public boolean isInvisible() {
        return this.creature.isInvisible();
    }

    @Override
    public void setInvisible(final boolean invisible) {
        this.creature.setInvisible(invisible);
    }

    @Override
    public int getArrowsStuck() {
        return this.creature.getArrowsStuck();
    }

    @Override
    public void setArrowsStuck(final int arrows) {
        this.creature.setArrowsStuck(arrows);
    }

    @Override
    public int getShieldBlockingDelay() {
        return this.creature.getShieldBlockingDelay();
    }

    @Override
    public void setShieldBlockingDelay(final int delay) {
        this.creature.setShieldBlockingDelay(delay);
    }

    @Override
    public @NotNull ItemStack getActiveItem() {
        return this.creature.getActiveItem();
    }

    @Override
    public void clearActiveItem() {
        this.creature.clearActiveItem();
    }

    @Override
    public int getItemUseRemainingTime() {
        return this.creature.getItemUseRemainingTime();
    }

    @Override
    public int getHandRaisedTime() {
        return this.creature.getHandRaisedTime();
    }

    @Override
    public boolean isHandRaised() {
        return this.creature.isHandRaised();
    }

    @Override
    public @NotNull EquipmentSlot getHandRaised() {
        return this.creature.getHandRaised();
    }

    @Override
    public boolean isJumping() {
        return this.creature.isJumping();
    }

    @Override
    public void setJumping(final boolean jumping) {
        this.creature.setJumping(jumping);
    }

    @Override
    public void playPickupItemAnimation(@NotNull final Item item, final int quantity) {
        this.creature.playPickupItemAnimation(item, quantity);
    }

    @Override
    public float getHurtDirection() {
        return this.creature.getHurtDirection();
    }

    @Override
    public void setHurtDirection(final float hurtDirection) {
        this.creature.setHurtDirection(hurtDirection);
    }

    @Override
    public @NotNull Pathfinder getPathfinder() {
        return this.creature.getPathfinder();
    }

    @Override
    public boolean isInDaylight() {
        return this.creature.isInDaylight();
    }

    @Override
    public void lookAt(@NotNull final Location location) {
        this.creature.lookAt(location);
    }

    @Override
    public void lookAt(@NotNull final Location location, final float headRotationSpeed, final float maxHeadPitch) {
        this.creature.lookAt(location, headRotationSpeed, maxHeadPitch);
    }

    @Override
    public void lookAt(@NotNull final Entity entity) {
        this.creature.lookAt(entity);
    }

    @Override
    public void lookAt(@NotNull final Entity entity, final float headRotationSpeed, final float maxHeadPitch) {
        this.creature.lookAt(entity, headRotationSpeed, maxHeadPitch);
    }

    @Override
    public void lookAt(final double x, final double y, final double z) {
        this.creature.lookAt(x, y, z);
    }

    @Override
    public void lookAt(final double x, final double y, final double z, final float headRotationSpeed, final float maxHeadPitch) {
        this.creature.lookAt(x, y, z, headRotationSpeed, maxHeadPitch);
    }

    @Override
    public int getHeadRotationSpeed() {
        return this.creature.getHeadRotationSpeed();
    }

    @Override
    public int getMaxHeadPitch() {
        return this.creature.getMaxHeadPitch();
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        return this.creature.getTarget();
    }

    @Override
    public void setTarget(@Nullable final LivingEntity target) {
        this.creature.setTarget(target);
    }

    @Override
    public boolean isAware() {
        return this.creature.isAware();
    }

    @Override
    public void setAware(final boolean aware) {
        this.creature.setAware(aware);
    }

    @Override
    public boolean isLeftHanded() {
        return this.creature.isLeftHanded();
    }

    @Override
    public void setLeftHanded(final boolean leftHanded) {
        this.creature.setLeftHanded(leftHanded);
    }

    @Override
    public @Nullable AttributeInstance getAttribute(@NotNull final Attribute attribute) {
        return this.creature.getAttribute(attribute);
    }

    @Override
    public void registerAttribute(@NotNull final Attribute attribute) {
        this.creature.registerAttribute(attribute);
    }

    @Override
    public void damage(final double amount) {
        this.creature.damage(amount);
    }

    @Override
    public void damage(final double amount, @Nullable final Entity source) {
        this.creature.damage(amount, source);
    }

    @Override
    public double getHealth() {
        return this.creature.getHealth();
    }

    @Deprecated
    @Override
    public void setHealth(final double health) {
        this.creature.setMaxHealth(health);
    }

    @Override
    public double getAbsorptionAmount() {
        return this.creature.getAbsorptionAmount();
    }

    @Override
    public void setAbsorptionAmount(final double amount) {
        this.creature.setAbsorptionAmount(amount);
    }

    @Deprecated
    @Override
    public double getMaxHealth() {
        return this.creature.getMaxHealth();
    }

    @Deprecated
    @Override
    public void setMaxHealth(final double health) {
        this.creature.setMaxHealth(health);
    }

    @Override
    public void resetMaxHealth() {
        this.creature.resetMaxHealth();
    }

    @Override
    public @NotNull Location getLocation() {
        return this.creature.getLocation();
    }

    @Override
    public @Nullable Location getLocation(@Nullable final Location loc) {
        return this.creature.getLocation(loc);
    }

    @Override
    public @NotNull Vector getVelocity() {
        return this.creature.getVelocity();
    }

    @Override
    public void setVelocity(@NotNull final Vector velocity) {
        this.creature.setVelocity(velocity);
    }

    @Override
    public double getHeight() {
        return this.creature.getHeight();
    }

    @Override
    public double getWidth() {
        return this.creature.getWidth();
    }

    @Override
    public @NotNull BoundingBox getBoundingBox() {
        return this.creature.getBoundingBox();
    }

    @Override
    public boolean isOnGround() {
        return this.creature.isOnGround();
    }

    @Override
    public boolean isInWater() {
        return this.creature.isInWater();
    }

    @Override
    public @NotNull World getWorld() {
        return this.creature.getWorld();
    }

    @Override
    public void setRotation(final float yaw, final float pitch) {
        this.creature.setRotation(yaw, pitch);
    }

    @Override
    public boolean teleport(
            @NotNull final Location location, final PlayerTeleportEvent.@NotNull TeleportCause cause, final boolean ignorePassengers, final boolean dismount
    ) {
        return this.creature.teleport(location, cause, ignorePassengers, dismount);
    }

    @Override
    public boolean teleport(@NotNull final Location location) {
        return this.creature.teleport(location);
    }

    @Override
    public boolean teleport(@NotNull final Location location, final PlayerTeleportEvent.@NotNull TeleportCause cause) {
        return this.creature.teleport(location, cause);
    }

    @Override
    public boolean teleport(@NotNull final Entity destination) {
        return this.creature.teleport(destination);
    }

    @Override
    public boolean teleport(@NotNull final Entity destination, final PlayerTeleportEvent.@NotNull TeleportCause cause) {
        return this.creature.teleport(destination, cause);
    }

    @Override
    public @NotNull List<Entity> getNearbyEntities(final double x, final double y, final double z) {
        return this.creature.getNearbyEntities(x, y, z);
    }

    @Override
    public int getEntityId() {
        return this.creature.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return this.creature.getFireTicks();
    }

    @Override
    public void setFireTicks(final int ticks) {
        this.creature.setFireTicks(ticks);
    }

    @Override
    public int getMaxFireTicks() {
        return this.creature.getMaxFireTicks();
    }

    @Override
    public boolean isVisualFire() {
        return this.creature.isVisualFire();
    }

    @Override
    public void setVisualFire(final boolean fire) {
        this.creature.setVisualFire(fire);
    }

    @Override
    public int getFreezeTicks() {
        return this.creature.getFreezeTicks();
    }

    @Override
    public void setFreezeTicks(final int ticks) {
        this.creature.setFreezeTicks(ticks);
    }

    @Override
    public int getMaxFreezeTicks() {
        return this.creature.getMaxFreezeTicks();
    }

    @Override
    public boolean isFrozen() {
        return this.creature.isFrozen();
    }

    @Override
    public boolean isFreezeTickingLocked() {
        return this.creature.isFreezeTickingLocked();
    }

    @Override
    public void lockFreezeTicks(final boolean locked) {
        this.creature.lockFreezeTicks(locked);
    }

    @Override
    public void remove() {
        BossManager.timings().removeAsyncTask(this.bossTicker);
        this.creature.remove();
    }

    @Override
    public boolean isDead() {
        if (this.creature.isDead()) {
            BossManager.timings().removeAsyncTask(this.bossTicker);
        }
        return this.creature.isDead();
    }

    @Override
    public boolean isValid() {
        return this.creature.isValid();
    }

    @Override
    public void sendMessage(@NotNull final String message) {
        this.creature.sendMessage(message);
    }

    @Override
    public void sendMessage(final @NotNull String... messages) {
        this.creature.sendMessage(messages);
    }

    @Override
    public void sendMessage(@Nullable final UUID sender, @NotNull final String message) {
        this.creature.sendMessage(sender, message);
    }

    @Override
    public void sendMessage(@Nullable final UUID sender, final @NotNull String... messages) {
        this.creature.sendMessage(sender, messages);
    }

    @Override
    public @NotNull Server getServer() {
        return this.creature.getServer();
    }

    @Override
    public @NotNull String getName() {
        return this.creature.getName();
    }

    @Override
    public boolean isPersistent() {
        return this.creature.isPersistent();
    }

    @Override
    public void setPersistent(final boolean persistent) {
        this.creature.setPersistent(persistent);
    }

    @Override
    public @Nullable Entity getPassenger() {
        return this.creature.getPassenger();
    }

    @Override
    public boolean setPassenger(@NotNull final Entity passenger) {
        return this.creature.setPassenger(passenger);
    }

    @Override
    public @NotNull List<Entity> getPassengers() {
        return this.creature.getPassengers();
    }

    @Override
    public boolean addPassenger(@NotNull final Entity passenger) {
        return this.creature.addPassenger(passenger);
    }

    @Override
    public boolean removePassenger(@NotNull final Entity passenger) {
        return this.creature.removePassenger(passenger);
    }

    @Override
    public boolean isEmpty() {
        return this.creature.isEmpty();
    }

    @Override
    public boolean eject() {
        return this.creature.eject();
    }

    @Override
    public float getFallDistance() {
        return this.creature.getFallDistance();
    }

    @Override
    public void setFallDistance(final float distance) {
        this.creature.setFallDistance(distance);
    }

    @Override
    public @Nullable EntityDamageEvent getLastDamageCause() {
        return this.creature.getLastDamageCause();
    }

    @Override
    public void setLastDamageCause(@Nullable final EntityDamageEvent event) {
        this.creature.setLastDamageCause(event);
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.creature.getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return this.creature.getTicksLived();
    }

    @Override
    public void setTicksLived(final int value) {
        this.creature.setTicksLived(value);
    }

    @Override
    public void playEffect(@NotNull final EntityEffect type) {
        this.creature.playEffect(type);
    }

    @Override
    public @NotNull EntityType getType() {
        return this.creature.getType();
    }

    @Override
    public boolean isInsideVehicle() {
        return this.creature.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return this.creature.leaveVehicle();
    }

    @Override
    public @Nullable Entity getVehicle() {
        return this.creature.getVehicle();
    }

    @Override
    public boolean isCustomNameVisible() {
        return this.creature.isCustomNameVisible();
    }

    @Override
    public void setCustomNameVisible(final boolean flag) {
        this.creature.setCustomNameVisible(flag);
    }

    @Override
    public boolean isGlowing() {
        return this.creature.isGlowing();
    }

    @Override
    public void setGlowing(final boolean flag) {
        this.creature.setGlowing(flag);
    }

    @Override
    public boolean isInvulnerable() {
        return this.creature.isInvulnerable();
    }

    @Override
    public void setInvulnerable(final boolean flag) {
        this.creature.setInvulnerable(flag);
    }

    @Override
    public boolean isSilent() {
        return this.creature.isSilent();
    }

    @Override
    public void setSilent(final boolean flag) {
        this.creature.setSilent(flag);
    }

    @Override
    public boolean hasGravity() {
        return this.creature.hasGravity();
    }

    @Override
    public void setGravity(final boolean gravity) {
        this.creature.setGravity(gravity);
    }

    @Override
    public int getPortalCooldown() {
        return this.creature.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown(final int cooldown) {
        this.creature.setPortalCooldown(cooldown);
    }

    @Override
    public @NotNull Set<String> getScoreboardTags() {
        return this.creature.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag(@NotNull final String tag) {
        return this.creature.addScoreboardTag(tag);
    }

    @Override
    public boolean removeScoreboardTag(@NotNull final String tag) {
        return this.creature.removeScoreboardTag(tag);
    }

    @Override
    public @NotNull PistonMoveReaction getPistonMoveReaction() {
        return this.creature.getPistonMoveReaction();
    }

    @Override
    public @NotNull BlockFace getFacing() {
        return this.creature.getFacing();
    }

    @Override
    public @NotNull Pose getPose() {
        return this.creature.getPose();
    }

    @Override
    public @NotNull SpawnCategory getSpawnCategory() {
        return this.creature.getSpawnCategory();
    }

    @Override
    public @NotNull Spigot spigot() {
        return this.creature.spigot();
    }

    @Override
    public @NotNull Component name() {
        return this.creature.name();
    }

    @Override
    public @NotNull Component teamDisplayName() {
        return this.creature.teamDisplayName();
    }

    @Override
    public @Nullable Location getOrigin() {
        return this.creature.getOrigin();
    }

    @Override
    public boolean fromMobSpawner() {
        return this.creature.fromMobSpawner();
    }

    @Override
    public CreatureSpawnEvent.@NotNull SpawnReason getEntitySpawnReason() {
        return this.creature.getEntitySpawnReason();
    }

    @Override
    public boolean isInRain() {
        return this.creature.isInRain();
    }

    @Override
    public boolean isInBubbleColumn() {
        return this.creature.isInBubbleColumn();
    }

    @Override
    public boolean isInWaterOrRain() {
        return this.creature.isInWaterOrRain();
    }

    @Override
    public boolean isInWaterOrBubbleColumn() {
        return this.creature.isInWaterOrBubbleColumn();
    }

    @Override
    public boolean isInWaterOrRainOrBubbleColumn() {
        return this.creature.isInWaterOrBubbleColumn();
    }

    @Override
    public boolean isInLava() {
        return this.creature.isInLava();
    }

    @Override
    public boolean isTicking() {
        return this.creature.isTicking();
    }

    @Override
    public @NotNull Set<Player> getTrackedPlayers() {
        return this.creature.getTrackedPlayers();
    }

    @Override
    public boolean spawnAt(@NotNull final Location location, final CreatureSpawnEvent.@NotNull SpawnReason reason) {
        return this.creature.spawnAt(location, reason);
    }

    @Override
    public boolean isInPowderedSnow() {
        return this.creature.isInPowderedSnow();
    }

    @Override
    public boolean collidesAt(@NotNull final Location location) {
        return this.creature.collidesAt(location);
    }

    @Override
    public boolean wouldCollideUsing(@NotNull final BoundingBox boundingBox) {
        return this.creature.wouldCollideUsing(boundingBox);
    }

    @Override
    public @Nullable Component customName() {
        return this.creature.customName();
    }

    @Override
    public void customName(@Nullable final Component customName) {
        this.creature.customName(customName);
    }

    @Override
    public @Nullable String getCustomName() {
        return this.creature.getCustomName();
    }

    @Override
    public void setCustomName(@Nullable final String name) {
        this.creature.setCustomName(name);
    }

    @Override
    public @Nullable LootTable getLootTable() {
        return this.creature.getLootTable();
    }

    @Override
    public void setLootTable(@Nullable final LootTable table) {
        this.creature.setLootTable(table);
    }

    @Override
    public long getSeed() {
        return this.creature.getSeed();
    }

    @Override
    public void setSeed(final long seed) {
        this.creature.setSeed(seed);
    }

    @Override
    public void setMetadata(@NotNull final String metadataKey, @NotNull final MetadataValue newMetadataValue) {
        this.creature.setMetadata(metadataKey, newMetadataValue);
    }

    @Override
    public @NotNull List<MetadataValue> getMetadata(@NotNull final String metadataKey) {
        return this.creature.getMetadata(metadataKey);
    }

    @Override
    public boolean hasMetadata(@NotNull final String metadataKey) {
        return this.creature.hasMetadata(metadataKey);
    }

    @Override
    public void removeMetadata(@NotNull final String metadataKey, @NotNull final Plugin owningPlugin) {
        this.creature.removeMetadata(metadataKey, owningPlugin);
    }

    @Override
    public boolean isPermissionSet(@NotNull final String name) {
        return this.creature.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull final Permission perm) {
        return this.creature.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(@NotNull final String name) {
        return this.creature.hasPermission(name);
    }

    @Override
    public boolean hasPermission(@NotNull final Permission perm) {
        return this.creature.hasPermission(perm);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(
            @NotNull final Plugin plugin, @NotNull final String name, final boolean value
    ) {
        return this.creature.addAttachment(plugin, name, value);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull final Plugin plugin) {
        return this.creature.addAttachment(plugin);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(
            @NotNull final Plugin plugin, @NotNull final String name, final boolean value, final int ticks
    ) {
        return this.creature.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull final Plugin plugin, final int ticks) {
        return this.creature.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull final PermissionAttachment attachment) {
        this.creature.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        this.creature.recalculatePermissions();
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return this.creature.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return this.creature.isOp();
    }

    @Override
    public void setOp(final boolean value) {
        this.creature.setOp(value);
    }

    @Override
    public @NotNull PersistentDataContainer getPersistentDataContainer() {
        return this.creature.getPersistentDataContainer();
    }

    @Override
    public <T extends Projectile> @NotNull T launchProjectile(@NotNull final Class<? extends T> projectile) {
        return this.creature.launchProjectile(projectile);
    }

    @Override
    public <T extends Projectile> @NotNull T launchProjectile(
            @NotNull final Class<? extends T> projectile, @Nullable final Vector velocity
    ) {
        return this.creature.launchProjectile(projectile, velocity);
    }

}
