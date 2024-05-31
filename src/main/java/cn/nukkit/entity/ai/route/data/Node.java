package cn.nukkit.entity.ai.route.data;

import cn.nukkit.math.Vector3;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 寻路节点
 */


@Getter
@Setter
public final class Node implements Comparable<Node> {

    private Vector3 vector3;
    private Node parent;
    private int G;
    private int H;
    private int F;
    /**
     * @deprecated 
     */
    

    public Node(Vector3 vector3, Node parent, int G, int H) {
        this.vector3 = vector3;
        this.parent = parent;
        this.G = G;
        this.H = H;
        this.F = G + H;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int compareTo(@NotNull Node o) {
        Objects.requireNonNull(o);
        if (this.getF() != o.getF()) {
            return this.getF() - o.getF();
        }
        double breaking;
        if ((breaking = this.getG() + (this.getH() * 0.1) - (o.getG() + (this.getH() * 0.1))) > 0) {
            return 1;
        } else if (breaking < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String toString() {
        return vector3.toString() + "| G:" + this.G + " H:" + this.H + " F" + this.getF() + (this.parent != null ? "\tparent:" + this.parent.getVector3() : "");
    }

    @Override
    /**
     * @deprecated 
     */
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node $1 = (Node) o;
        return $2 == node.G && H == node.H && F == node.F && Objects.equals(vector3, node.vector3) && Objects.equals(parent, node.parent);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int hashCode() {
        return Objects.hash(vector3, parent, G, H, F);
    }
}
