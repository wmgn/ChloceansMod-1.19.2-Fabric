package github.wmgn.chloceansmod.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ShulkerBoxSlot;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

/*
public class GiantClamScreenHandler extends GenericContainerScreenHandler {
    public GiantClamScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.GIANT_CLAM_SCREEN_HANDLER, syncId, playerInventory, inventory, 3); // 3 rows of inventory (9 slots each)
    }

    public GiantClamScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.GIANT_CLAM_SCREEN_HANDLER, syncId, playerInventory);
    }
}
*/

public class GiantClamScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    //private final PropertyDelegate propertyDelegate;

    public GiantClamScreenHandler(int syncId, PlayerInventory playerInventory){
        this(syncId, playerInventory, new SimpleInventory(27));
    }

    public GiantClamScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) { //@Nullable ScreenHandlerType<?> type
        super(ModScreenHandlers.GIANT_CLAM_SCREEN_HANDLER, syncId);
        checkSize(inventory, 27);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        //this.propertyDelegate = delegate;

        //int i = true;
        //int j = true;

        int k;
        int l;
        for(k = 0; k < 3; ++k) {
            for(l = 0; l < 9; ++l) {
                this.addSlot(new ShulkerBoxSlot(inventory, l + k * 9, 8 + l * 18, 18 + k * 18));
            }
        }

        /*
        for(k = 0; k < 3; ++k) {
            for(l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }*/

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        //addProperties(delegate); //propertyDelegate stuff
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }


    /* //from ShulkerBoxScreenHandler.class
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
     */
}