package github.wmgn.chloceansmod.entity.custom;


import github.wmgn.chloceansmod.ChloceansMod;
import github.wmgn.chloceansmod.entity.ImplementedInventory;
import github.wmgn.chloceansmod.particles.ModParticles;
import github.wmgn.chloceansmod.screen.GiantClamScreen;
import github.wmgn.chloceansmod.screen.GiantClamScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class GiantClamEntity extends WaterCreatureEntity implements IAnimatable, NamedScreenHandlerFactory, ImplementedInventory {
    public static final Logger LOGGER = LoggerFactory.getLogger(ChloceansMod.MOD_ID);

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    private PlayerEntity currentPlayerUsing;

    public DefaultedList<ItemStack> getItems(){
        return this.inventory;
    }


    @Override
    public Text getDisplayName(){
        return Text.literal("Giant Clam"); // may be unnecessary and or weird
    }

    /*private final SimpleInventory inventory;
    public SimpleInventory getInventory() {
        return this.inventory;
    }*/

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.world.isClient) {
            LOGGER.info("isClient, Tried to open clam--- this.isOpenAndClosing(): " + this.isOpenAndClosing());
        }

        if (!this.world.isClient) { // on server
            LOGGER.info("onServer, Tried to open clam--- this.isOpenAndClosing(): " + this.isOpenAndClosing());

            if(this.isOpenAndClosing()){
                // Open the screen (inventory UI) for the player
                NamedScreenHandlerFactory namedScreenHandlerFactory = createScreenHandlerFactory();

                if (namedScreenHandlerFactory != null) {
                    player.openHandledScreen(namedScreenHandlerFactory);
                    // Keep track of the player currently using the clam
                    this.currentPlayerUsing = player;
                }

                return ActionResult.SUCCESS;
            } else {
                // Optionally, send a message to the player that the clam isn't open
                player.sendMessage(Text.translatable("clam.not_open"), true);
            }
        }
        return super.interactMob(player, hand);
    }

    /*
    // from ShulkerBoxBlock.class
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return ActionResult.CONSUME;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ShulkerBoxBlockEntity) {
                ShulkerBoxBlockEntity shulkerBoxBlockEntity = (ShulkerBoxBlockEntity)blockEntity;
                if (canOpen(state, world, pos, shulkerBoxBlockEntity)) {
                    player.openHandledScreen(shulkerBoxBlockEntity);
                    player.incrementStat(Stats.OPEN_SHULKER_BOX);
                    PiglinBrain.onGuardedBlockInteracted(player, true);
                }

                return ActionResult.CONSUME;
            } else {
                return ActionResult.PASS;
            }
        }
    }*/

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GiantClamScreenHandler(syncId, playerInventory, this);
        //return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }



    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory() {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
            return new GiantClamScreenHandler(syncId, inventory, this); //ScreenHandlerType.GENERIC_9X3, inventory, Text.translatable("container.giant_clam")
        }, Text.translatable("container.giant_clam"));
    }

    public GiantClamEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        //setOpenAndClosing(false);
        //this.inventory = new SimpleInventory(27);
        //this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);


    }


    private static final TrackedData<Boolean> OPEN_AND_CLOSING =
            DataTracker.registerData(GiantClamEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> OPEN_AND_CLOSING_TIMEOUT =
            DataTracker.registerData(GiantClamEntity.class, TrackedDataHandlerRegistry.INTEGER);


    public static DefaultAttributeContainer.Builder setAttributes() {
        return PassiveEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 250.0D)
                //.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0f)
                //.add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.0f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.99)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0f);

    }

    /*@Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }*/

    @Override
    public void tick() {
        super.tick();

        LOGGER.info("this.isOpenAndClosing(), " + this.isOpenAndClosing() + ",  timeout: " + this.getOpenAndClosingTimeout());

        if(!this.getWorld().isClient){
            // Only update and log on the server side
            if(this.isSubmergedInWater()){
                setOpenAndClosingTimeout(getOpenAndClosingTimeout()-1);

                if(!this.isOpenAndClosing() && getOpenAndClosingTimeout() <= 0){
                    LOGGER.info("setting setOpenAndClosing true, Animation should be starting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    setOpenAndClosing(true);
                    setOpenAndClosingTimeout(3*20); //5-15 seconds * 20 ticks/second
                }

                if (this.isOpenAndClosing() && getOpenAndClosingTimeout() <= 0) { // Ensure this matches your animation length
                    LOGGER.info("setting OpenAndClosing false, Animation should be stopped!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    setOpenAndClosing(false);   // Reset the flag when the animation is complete
                    setOpenAndClosingTimeout(getRandom().nextBetween(5,15)*20); //5-15 seconds * 20 ticks/second

                    // if the clam is no longer "opening and closing" (closed state)
                    if(this.currentPlayerUsing != null){
                        // Ensure the player is interacting with the Giant Clam's screen handler
                        if (this.currentPlayerUsing.currentScreenHandler instanceof GiantClamScreenHandler) {
                            // Kick the player out of the inventory (handled screen)
                            // Send a packet to the client to close the screen
                            if (this.currentPlayerUsing instanceof ServerPlayerEntity serverPlayer) {
                                serverPlayer.networkHandler.sendPacket(new CloseScreenS2CPacket(serverPlayer.currentScreenHandler.syncId));
                            }
                            // Optionally, apply damage to the player
                            this.currentPlayerUsing.damage(DamageSource.GENERIC, 10.0F); // Adjust damage value
                            // Reset the current player using the clam to avoid repeat actions
                            this.currentPlayerUsing = null;
                        }
                    }
                }
            }

        }


        //setupAnimationStates();
        if(this.world.isClient){
            if(this.isOpenAndClosing() && this.getOpenAndClosingTimeout() > 47){ // will be counting down from 60 to 0
                for(int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.BUBBLE, this.getParticleX(0.5), this.getRandomBodyY()+0.25, this.getParticleZ(0.5), 0.0, 0.3, 0.0);
                }
            }
        }
    }



    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(OPEN_AND_CLOSING, false);
        this.dataTracker.startTracking(OPEN_AND_CLOSING_TIMEOUT, getRandom().nextBetween(5,10)*20);
    }
    public boolean isOpenAndClosing() {
        return this.dataTracker.get(OPEN_AND_CLOSING);
    }
    public int getOpenAndClosingTimeout() {
        return this.dataTracker.get(OPEN_AND_CLOSING_TIMEOUT);
    }
    public void setOpenAndClosing(boolean openAndClosing){
        this.dataTracker.set(OPEN_AND_CLOSING, openAndClosing);
    }
    public void setOpenAndClosingTimeout(int openAndClosingTimeout){
        this.dataTracker.set(OPEN_AND_CLOSING_TIMEOUT, openAndClosingTimeout);
    }

    /*
    @Override
    protected void mobTick() {
        super.mobTick();
        animationTimer++;

        // Trigger the open and close animation every 'animationInterval' ticks
        if (animationTimer >= animationInterval) {
            LOGGER.info("---playOpenAndCloseAnimation and reset animationTimer to 0");
            playOpenAndCloseAnimation();
            animationTimer = 0;  // Reset the timer after playing the animation
        }
    }*/



    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    // Define the animation-playing method
    private void playOpenAndCloseAnimation() {
        AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, 0, "controller");  // Get the controller handling the animation
        //LOGGER.info("---getController: " + controller);
        controller.setAnimation(new AnimationBuilder().addAnimation("animation.giantclam.openandclose", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        //LOGGER.info("getAnimatable(): "+event.getAnimatable()
        //        + "\nevent.getAnimationTick(): "+event.getAnimationTick()
        //        + "\nisSubmergedInWater: "+this.isSubmergedInWater());

        if(this.isSubmergedInWater()){

            if (event.getController().getAnimationState().equals(AnimationState.Stopped) && this.isOpenAndClosing()) { // Ensure this matches your animation length
                LOGGER.info("predicate: Animation stopped/finished");
                //setOpenAndClosing(false);   // Reset the flag when the animation is complete
            }

            if (this.isOpenAndClosing() && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
                //setOpenAndClosing(true);
                LOGGER.info("predicate: Animation starting");
                event.getController().markNeedsReload();
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.giantclam.openandclose", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            } else {
                // Optionally handle the idle animation here
                //event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.giantclam.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }

        }

        return PlayState.CONTINUE; // Always return CONTINUE
    }


    // maybe implement a Goal class for goals to randomly open and close every random(x,y) ticks (play openandclose animation)

    /*private PlayState openAndClosePredicate(AnimationEvent event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.giantclam.openandclose", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        return PlayState.CONTINUE;
    }*/



    // This will be called when the particle keyframe is reached
    private void handleParticleEffects(ParticleKeyFrameEvent<GiantClamEntity> event) {
        // Spawn your particle here
        if (event.effect.equals("chloceansmod:giantclambubbles")) {
            // Add logic to spawn your custom particle effect here
            //this.world.addParticle(ModParticles.GIANT_CLAM_BUBBLES, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            this.world.addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), 0, 1, 0);
        }
    }

    // Handle particle effects, This will be called when the particle keyframe is reached
    private void summonParticle(ParticleKeyFrameEvent<GiantClamEntity> event) {
        if (event.effect.equals("chloceansmod:giantclambubbles")) {
            // Spawning logic of custom particle effect in the world
            //this.world.addParticle(ModParticles.GIANT_CLAM_BUBBLES, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            this.world.addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), 0, 1, 0);
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<GiantClamEntity> controller = //private final
                new AnimationController<>(this, "controller", 0, this::predicate);

        // Register a particle listener for the animation controller
        //controller.registerParticleListener(this::particleListener);
        animationData.addAnimationController(controller);

    }

    private <ENTITY extends IAnimatable> void particleListener(ParticleKeyFrameEvent<ENTITY> event) {
        // Spawn particles when the keyframe is hit
        World world = this.getWorld();  // Get the world instance
        if (world != null && world.isClient) {  // Ensure we're on the server side for spawning particles
            world.addParticle(
                    //ModParticles.GIANT_CLAM_BUBBLES,  // Replace with your actual particle type
                    ParticleTypes.BUBBLE,
                    this.getX(), this.getY() + 1, this.getZ(),  // Position where particles spawn
                    0, 0, 0  // Velocity of particles
            );
        }
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public boolean canBreatheInWater() {
        return true;
    }

    public boolean isCollidable() {
        return false;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, inventory);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    /*// obviously we dont want this
    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean push(){

    }*/

    @Override
    public boolean isPushable() {
        return false;
    }

    public boolean isPushedByFluids() {
        return false;
    }

    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }


}
