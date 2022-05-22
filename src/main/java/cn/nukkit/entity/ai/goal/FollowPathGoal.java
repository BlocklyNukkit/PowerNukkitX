package cn.nukkit.entity.ai.goal;

import cn.nukkit.entity.EntityIntelligent;
import cn.nukkit.entity.ai.path.Node;
import cn.nukkit.entity.ai.path.PathFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.LinkedList;

public class FollowPathGoal extends AbstractGoal {
    public static final String ID = "minecraft:follow_path";

    @NotNull
    protected Deque<Node> nodeStack = new LinkedList<>();
    @Nullable
    protected PathFinder currentPathFinder = null;
    protected int lastUpdateNodeTick = -1;

    @Override
    public boolean shouldStart(int currentTick, EntityIntelligent entity) {
        return entity.getPathFinder() != null && super.shouldStart(currentTick, entity);
    }

    @Override
    public void start(int currentTick, EntityIntelligent entity) {
        super.start(currentTick, entity);
        var pathFinder = entity.getPathFinder();
        if (pathFinder == null) {
            goalState = GoalState.NOT_WORKING;
            return;
        }
        var current = pathFinder.getDestination();
        while (current != null) {
            nodeStack.addFirst(current);
            current = current.getParent();
        }
        nodeStack.pop();
        currentPathFinder = pathFinder;
    }

    @Override
    public boolean shouldContinue(int currentTick, EntityIntelligent entity) {
        return !nodeStack.isEmpty() && super.shouldContinue(currentTick, entity);
    }

    protected int getFailToMoveTick() {
        return 60;
    }

    @Override
    public void execute(int currentTick, EntityIntelligent entity) {
        if (entity.getPathFinder() != currentPathFinder) {
            nodeStack.clear();
            start(currentTick, entity);
//            var pathFinder = entity.getPathFinder();
//            if (pathFinder == null) {
//                goalState = GoalState.NOT_WORKING;
//                return;
//            }
//            var current = pathFinder.getDestination();
//            var tmpNodeStack = new LinkedList<Node>();
//            while (current != null) {
//                tmpNodeStack.addFirst(current);
//                current = current.getParent();
//            }
//            var itOld = nodeStack.iterator();
//            var itNew = tmpNodeStack.iterator();
//            while (itNew.hasNext() && itOld.hasNext()){
//                var newNext = itNew.next();
//                if(!newNext.equals(itOld.next())){
//                    itOld.remove();
//                    while (itOld.hasNext()) {
//                        itOld.next();
//                        itOld.remove();
//                    }
//                    nodeStack.addLast(newNext);
//                    while (itNew.hasNext()) {
//                        nodeStack.addLast(itNew.next());
//                    }
//                }
//            }
        }
        if (entity.movingNearDestination == null) {
            if (nodeStack.isEmpty()) {
                return;
            }
            var tmp = nodeStack.pop();
            lastUpdateNodeTick = currentTick;
            entity.movingNearDestination = tmp.toRealVector();
        }
    }

    @Override
    public boolean shouldStop(int currentTick, EntityIntelligent entity) {
        return nodeStack.isEmpty() || entity.getPathFinder() == null || currentTick - lastUpdateNodeTick > getFailToMoveTick();
    }

    @Override
    public void stop(int currentTick, EntityIntelligent entity) {
        super.stop(currentTick, entity);
        nodeStack.clear();
        entity.setPathFinder(null);
        currentPathFinder = null;
    }

    @Override
    public int getOrder() {
        return ORDER_FOLLOW_PATH;
    }

    @Override
    public String getId() {
        return ID;
    }
}
