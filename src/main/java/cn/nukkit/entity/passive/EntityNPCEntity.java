package cn.nukkit.entity.passive;

import cn.nukkit.Player;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.entity.EntityInteractable;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.dialog.window.FormWindowDialog;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author good777LUCKY
 */
@Since("1.4.0.0-PN")
@PowerNukkitOnly
public class EntityNPCEntity extends EntityLiving implements EntityNPC, EntityInteractable {

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public static final int NETWORK_ID = 51;

    private FormWindowDialog dialog;

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public EntityNPCEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 2.1f;
    }

    @Override
    public boolean canDoInteraction() {
        return true;
    }

    @Override
    public String getInteractButtonText() {
        return "action.interact.edit";
    }

    @PowerNukkitOnly
    @Since("1.5.1.0-PN")
    @Override
    public String getOriginalName() {
        return "NPC";
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(Integer.MAX_VALUE); // Should be Float max value
        this.setHealth(20);
        this.setNameTagVisible(true);
    }

    //todo: remove this DEBUG
    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
        if (this.dialog == null) {
            this.dialog = new FormWindowDialog("TestTitle", "TestContent");
            this.dialog.addButton("TestButton1");
            this.dialog.addButton("TestButton2");
            this.dialog.addButton("TestButton3");
            this.dialog.addButton("TestButton4");
            this.dialog.addButton("TestButton5");
            this.dialog.addButton("TestButton6");
            this.dialog.setBindEntity(this);
            this.dialog.addHandler((p,r) -> p.sendMessage("button: " + r.getClickedButton()));
        }
        player.showDialogWindow(this.dialog);
        return true;
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        if (source instanceof EntityDamageByEntityEvent event && event.getDamager() instanceof Player damager && damager.isCreative()) {
            this.kill();
        }
        return false;
    }
}
