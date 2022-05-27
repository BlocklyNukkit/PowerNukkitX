package cn.nukkit.entity.passive;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.NPCCommandSender;
import cn.nukkit.dialog.element.ElementDialogButton;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityInteractable;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.dialog.window.FormWindowDialog;
import cn.nukkit.item.Item;
import cn.nukkit.lang.TextContainer;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.NPCRequestPacket;
import cn.nukkit.permission.PermissibleBase;
import cn.nukkit.permission.Permission;
import cn.nukkit.permission.PermissionAttachment;
import cn.nukkit.permission.PermissionAttachmentInfo;
import cn.nukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * @author good777LUCKY
 */
@Since("1.4.0.0-PN")
@PowerNukkitOnly
public class EntityNPCEntity extends EntityLiving implements EntityNPC, EntityInteractable {

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public static final int NETWORK_ID = 51;

    private static final String KEY_DIALOG_TITLE = "DialogTitle";
    private static final String KEY_DIALOG_CONTENT = "DialogContent";
    private static final String KEY_DIALOG_SKINDATA = "DialogSkinData";
    private static final String KEY_DIALOG_BUTTONS = "DialogButtons";

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
        this.dialog = new FormWindowDialog(this.namedTag.getString(KEY_DIALOG_TITLE), this.namedTag.getString(KEY_DIALOG_CONTENT));
        this.setNameTag(this.dialog.getTitle());
        if (!this.namedTag.getString(KEY_DIALOG_SKINDATA).isEmpty())
            this.dialog.setSkinData(this.namedTag.getString(KEY_DIALOG_SKINDATA));
        if (!this.namedTag.getString(KEY_DIALOG_BUTTONS).isEmpty())
            this.dialog.setButtonJSONData(this.namedTag.getString(KEY_DIALOG_BUTTONS));
        this.dialog.addHandler((player,response) -> {
            if (response.getRequestType() == NPCRequestPacket.RequestType.SET_ACTIONS) {
                if (!response.getData().isEmpty())
                    this.dialog.setButtonJSONData(response.getData());
            }
            if (response.getRequestType() == NPCRequestPacket.RequestType.SET_INTERACTION_TEXT) {
                this.dialog.setContent(response.getData());
            }
            if (response.getRequestType() == NPCRequestPacket.RequestType.SET_NAME){
                this.dialog.setTitle(response.getData());
            }
            if (response.getRequestType() == NPCRequestPacket.RequestType.SET_SKIN) {
                //todo: set skin data
            }
            if (response.getRequestType() == NPCRequestPacket.RequestType.EXECUTE_ACTION) {
                ElementDialogButton clickedButton = response.getClickedButton();
                for(ElementDialogButton.CmdLine line : clickedButton.getData()){
                    Server.getInstance().dispatchCommand(new NPCCommandSender(this,player),line.cmd_line.startsWith("/") ? line.cmd_line.substring(1) : line.cmd_line);
                }
            }
            if (response.getRequestType() == NPCRequestPacket.RequestType.EXECUTE_OPENING_COMMANDS) {
                for(ElementDialogButton button : this.dialog.getButtons()){
                    if (button.getMode() == ElementDialogButton.Mode.ON_ENTER) {
                        for(ElementDialogButton.CmdLine line : button.getData()) {
                            Server.getInstance().dispatchCommand(new NPCCommandSender(this,player),line.cmd_line.startsWith("/") ? line.cmd_line.substring(1) : line.cmd_line);
                        }
                    }
                }
            }
            if (response.getRequestType() == NPCRequestPacket.RequestType.EXECUTE_CLOSING_COMMANDS) {
                for(ElementDialogButton button : this.dialog.getButtons()){
                    if (button.getMode() == ElementDialogButton.Mode.ON_EXIT) {
                        for(ElementDialogButton.CmdLine line : button.getData()) {
                            Server.getInstance().dispatchCommand(new NPCCommandSender(this,player),line.cmd_line.startsWith("/") ? line.cmd_line.substring(1) : line.cmd_line);
                        }
                    }
                }
            }
        });
        this.dialog.setBindEntity(this);
    }

    @Override
    public void saveNBT() {
        super.saveNBT();
        this.namedTag.putString(KEY_DIALOG_TITLE, this.dialog.getTitle());
        this.namedTag.putString(KEY_DIALOG_CONTENT, this.dialog.getContent());
        this.namedTag.putString(KEY_DIALOG_SKINDATA, this.dialog.getSkinData());
        this.namedTag.putString(KEY_DIALOG_BUTTONS, this.dialog.getButtonJSONData());
    }

    @Override
    public boolean onInteract(Player player, Item item, Vector3 clickedPos) {
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
