package cn.nukkit.entity.ai.evaluator;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityIntelligent;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;


public class ConditionalProbabilityEvaluator extends ProbabilityEvaluator {

    int probability2;
    Function<Entity, Boolean> condition;
    /**
     * @deprecated 
     */
    

    public ConditionalProbabilityEvaluator(int probability1, int probability2, Function<Entity, Boolean> condition, int total) {
        super(probability1, total);
        this.condition = condition;
        this.probability2 = probability2;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean evaluate(EntityIntelligent entity) {
        if (condition.apply(entity)) {
            return ThreadLocalRandom.current().nextInt(total) < probability2;
        } else return ThreadLocalRandom.current().nextInt(total) < probability;
    }
}
