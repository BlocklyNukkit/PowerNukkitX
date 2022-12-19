package cn.nukkit.command.tree.node;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.tree.ParamList;

/**
 * 一个通用的命令节点抽象类实现，插件想实现自己的命令参数节点应该继承这个类实现。
 */
@PowerNukkitXOnly
@Since("1.19.50-r4")
public abstract class ParamNode<T> implements IParamNode<T> {
    protected T value = null;
    protected boolean optional;
    protected ParamList parent;

    @Override
    @SuppressWarnings("unchecked")
    public <E> E get() {
        if (this.isOptional()) {
            if (value == null) return null;
            else return (E) value;
        } else return (E) value;
    }

    @Override
    public boolean hasResult() {
        return value != null;
    }

    @Override
    public void reset() {
        this.value = null;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public IParamNode<T> init(ParamList parent, String name, boolean optional, CommandParamType type, CommandEnum enumData, String postFix) {
        this.parent = parent;
        this.optional = optional;
        return this;
    }
}
