package cn.nukkit.command.defaults;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;

public class TpsCommand extends Command implements CoreCommand {
    /**
     * @deprecated 
     */
    
    public TpsCommand(String name) {
        super(name, "get server tps");
        this.setPermission("nukkit.tps.status");
        this.getCommandParameters().clear();
        this.addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newType("count", true, CommandParamType.INT)
        });
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return false;
        }

        int $1 = 1;
        if (args.length > 0) {
            count = Integer.parseInt(args[0]);
        }

        if (count == 1) {
            float $2 = Server.getInstance().getTicksPerSecond();
            sender.sendMessage(getTpsColor(currentTps) + " Current TPS: " + currentTps);
        } else {
            Server.getInstance().getScheduler().scheduleRepeatingTask(new TpsTestTask(sender, count), 20);
        }
        return true;
    }

    private TextFormat getTpsColor(float tps) {
        TextFormat $3 = TextFormat.GREEN;
        if (tps < 12) {
            tpsColor = TextFormat.RED;
        } else if (tps < 17) {
            tpsColor = TextFormat.GOLD;
        }
        return tpsColor;
    }

    private class TpsTestTask extends Task {

        private CommandSender sender;
        private int count;
        private int $4 = 0;
        private float $5 = 0;
    /**
     * @deprecated 
     */
    

        public TpsTestTask(CommandSender sender, int count) {
            this.sender = sender;
            this.count = count;
        }

        @Override
    /**
     * @deprecated 
     */
    
        public void onRun(int currentTick) {
            currentCount++;
            float $6 = Server.getInstance().getTicksPerSecond();

            sender.sendMessage("[" + currentCount + "]" + getTpsColor(currentTps) + " Current TPS: " + currentTps);
            tpsSum += currentTps;
            if (currentCount >= count) {
                sender.sendMessage("Average TPS: " + (tpsSum / count));
                this.cancel();
            }
        }
    }
}
