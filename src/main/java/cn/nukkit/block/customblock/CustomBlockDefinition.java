package cn.nukkit.block.customblock;

import cn.nukkit.block.Block;
import cn.nukkit.block.customblock.data.*;
import cn.nukkit.block.property.type.BlockPropertyType;
import cn.nukkit.block.property.type.BooleanPropertyType;
import cn.nukkit.block.property.type.EnumPropertyType;
import cn.nukkit.block.property.type.IntPropertyType;
import cn.nukkit.item.customitem.data.CreativeCategory;
import cn.nukkit.item.customitem.data.CreativeGroup;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import cn.nukkit.nbt.tag.*;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Set;
import java.util.function.Consumer;

/**
 * CustomBlockDefinition用于获得发送给客户端的方块行为包数据。{@link CustomBlockDefinition.Builder}中提供的方法都是控制发送给客户端数据，如果需要控制服务端部分行为，请覆写{@link Block Block}中的方法。
 * <p>
 * CustomBlockDefinition is used to get the data of the block behavior_pack sent to the client. The methods provided in {@link CustomBlockDefinition.Builder} control the data sent to the client, if you need to control some of the server-side behavior, please override the methods in {@link Block Block}.
 */


public record CustomBlockDefinition(String identifier, CompoundTag nbt) {
    /**
     * Builder custom block definition.
     *
     * @param customBlock           the custom block
     * @return the custom block definition builder.
     */
    public static CustomBlockDefinition.Builder builder(@NotNull CustomBlock customBlock) {
        return new CustomBlockDefinition.Builder(customBlock);
    }

    public static class Builder {
        protected final String identifier;
        protected final CustomBlock customBlock;

        protected CompoundTag nbt = new CompoundTag()
                .putCompound("components", new CompoundTag());

        protected Builder(CustomBlock customBlock) {
            this.identifier = customBlock.getId();
            this.customBlock = customBlock;

            var components = this.nbt.getCompound("components");

            //设置一些与PNX内部对应的方块属性
            components.putCompound("minecraft:friction", new CompoundTag()
                            .putFloat("value", (float) (1 - Block.DEFAULT_FRICTION_FACTOR)))
                    .putCompound("minecraft:destructible_by_explosion", new CompoundTag()
                            .putInt("explosion_resistance", (int) customBlock.getResistance()))
                    .putCompound("minecraft:light_dampening", new CompoundTag()
                            .putByte("lightLevel", (byte) customBlock.getLightFilter()))
                    .putCompound("minecraft:light_emission", new CompoundTag()
                            .putByte("emission", (byte) customBlock.getLightLevel()))
                    .putCompound("minecraft:destructible_by_mining", new CompoundTag()
                            .putFloat("value", 99999f));//default server-side mining time calculate
            //设置材质
            components.putCompound("minecraft:material_instances", new CompoundTag()
                    .putCompound("mappings", new CompoundTag())
                    .putCompound("materials", new CompoundTag()));
            //默认单位立方体方块
            components.putCompound("minecraft:unit_cube", new CompoundTag());
            //设置方块在创造栏的分类
            this.nbt.putCompound("menu_category", new CompoundTag()
                    .putString("category", CreativeCategory.NONE.name())
                    .putString("group", CreativeGroup.NONE.getGroupName()));
            //molang版本
            this.nbt.putInt("molangVersion", 6);

            //设置方块的properties
            var propertiesNBT = getPropertiesNBT();
            if (propertiesNBT != null) {
                nbt.putList("properties", propertiesNBT);
            }
        }

        public Builder texture(String texture) {
            this.materials(Materials.builder().any(Materials.RenderMethod.OPAQUE, texture));
            return this;
        }

        public Builder materials(Materials materials) {
            this.nbt.getCompound("components").putCompound("minecraft:material_instances", new CompoundTag()
                    .putCompound("mappings", new CompoundTag())
                    .putCompound("materials", materials.toCompoundTag()));
            return this;
        }

        public Builder creativeGroupAndCategory(CreativeGroup creativeGroup, CreativeCategory creativeCategory) {
            this.nbt.getCompound("menu_category")
                    .putString("category", creativeCategory.name().toLowerCase(Locale.ENGLISH))
                    .putString("group", creativeGroup.getGroupName());
            return this;
        }

        public Builder creativeCategory(String creativeCategory) {
            this.nbt.getCompound("menu_category")
                    .putString("category", creativeCategory.toLowerCase(Locale.ENGLISH));
            return this;
        }

        public Builder creativeCategory(CreativeCategory creativeCategory) {
            this.nbt.getCompound("menu_category")
                    .putString("category", creativeCategory.name().toLowerCase(Locale.ENGLISH));
            return this;
        }

        /**
         * 控制自定义方块客户端侧的挖掘时间(单位秒)
         * <p>
         * 自定义方块的挖掘时间取决于服务端侧和客户端侧中最小的一个
         * <p>
         * Control the digging time (in seconds) on the client side of the custom block
         * <p>
         * The digging time of a custom cube depends on the smallest of the server-side and client-side
         */
        public Builder breakTime(double second) {
            this.nbt.getCompound("components")
                    .putCompound("minecraft:destructible_by_mining", new CompoundTag()
                            .putFloat("value", (float) second));
            return this;
        }

        /**
         * 控制自定义方块在创造栏中的组。
         * <p>
         * Control the grouping of custom blocks in the creation inventory.
         *
         * @see <a href="https://wiki.bedrock.dev/documentation/creative-categories.html">wiki.bedrock.dev</a>
         */
        public Builder creativeGroup(String creativeGroup) {
            if (creativeGroup.isBlank()) {
                System.out.println("creativeGroup has an invalid value!");
                return this;
            }
            this.nbt.getCompound("components").getCompound("menu_category").putString("group", creativeGroup.toLowerCase(Locale.ENGLISH));
            return this;
        }

        /**
         * 控制自定义方块在创造栏中的组。
         * <p>
         * Control the grouping of custom blocks in the creation inventory.
         *
         * @see <a href="https://wiki.bedrock.dev/documentation/creative-categories.html">wiki.bedrock.dev</a>
         */
        public Builder creativeGroup(CreativeGroup creativeGroup) {
            this.nbt.getCompound("components").getCompound("menu_category").putString("group", creativeGroup.getGroupName());
            return this;
        }

        /**
         * supports rotation, scaling, and translation. The component can be added to the whole block and/or to individual block permutations. Transformed geometries still have the same restrictions that non-transformed geometries have such as a maximum size of 30/16 units.
         */
        public Builder transformation(@NotNull Transformation transformation) {
            this.nbt.getCompound("components").putCompound("minecraft:transformation", transformation.toCompoundTag());
            return this;
        }

        /**
         * 以度为单位设置块围绕立方体中心的旋转,旋转顺序为 xyz.角度必须是90的倍数。
         * <p>
         * Set the rotation of the block around the center of the block in degrees, the rotation order is xyz. The angle must be a multiple of 90.
         */
        public Builder rotation(@NotNull Vector3f rotation) {
            this.transformation(new Transformation(new Vector3(0, 0, 0), new Vector3(1, 1, 1), rotation.asVector3()));
            return this;
        }

        /**
         * @see #geometry(Geometry)
         * 默认不设置骨骼显示
         * <p>
         * defalut not set boneVisibilities
         */
        public Builder geometry(String geometry) {
            if (geometry.isBlank()) {
                System.out.println("geometry has an invalid value!");
                return this;
            }
            var components = this.nbt.getCompound("components");
            //默认单位立方体方块，如果定义几何模型需要移除
            if (components.contains("minecraft:unit_cube")) components.remove("minecraft:unit_cube");
            //设置方块对应的几何模型
            components.putCompound("minecraft:geometry", new CompoundTag()
                    .putString("identifier", geometry.toLowerCase(Locale.ENGLISH)));
            return this;
        }

        /**
         * 控制自定义方块的几何模型,如果不设置默认为单位立方体
         * <p>
         * Control the geometric model of the custom block, if not set the default is the unit cube.<br>
         * Geometry identifier from geo file in 'RP/models/blocks' folder
         */

        public Builder geometry(@NotNull Geometry geometry) {
            var components = this.nbt.getCompound("components");
            //默认单位立方体方块，如果定义几何模型需要移除
            if (components.contains("minecraft:unit_cube")) components.remove("minecraft:unit_cube");
            //设置方块对应的几何模型
            components.putCompound("minecraft:geometry", geometry.toCompoundTag());
            return this;
        }

        /**
         * 控制自定义方块的变化特征，例如条件渲染，部分渲染等
         * <p>
         * Control custom block permutation features such as conditional rendering, partial rendering, etc.
         */

        public Builder permutation(Permutation permutation) {
            if (!this.nbt.contains("permutations")) {
                this.nbt.putList("permutations", new ListTag<CompoundTag>());
            }
            ListTag<CompoundTag> permutations = this.nbt.getList("permutations", CompoundTag.class);
            permutations.add(permutation.toCompoundTag());
            this.nbt.putList("permutations", permutations);
            return this;
        }

        /**
         * 控制自定义方块的变化特征，例如条件渲染，部分渲染等
         * <p>
         * Control custom block permutation features such as conditional rendering, partial rendering, etc.
         */
        public Builder permutations(Permutation... permutations) {
            var per = new ListTag<CompoundTag>();
            for (var permutation : permutations) {
                per.add(permutation.toCompoundTag());
            }
            this.nbt.putList("permutations", per);
            return this;
        }

        /**
         * 设置此方块的客户端碰撞箱。
         * <p>
         * Set the client collision box for this block.
         *
         * @param origin 碰撞箱的原点 The origin of the collision box
         * @param size   碰撞箱的大小 The size of the collision box
         */
        public Builder collisionBox(@NotNull Vector3f origin, @NotNull Vector3f size) {
            this.nbt.getCompound("components")
                    .putCompound("minecraft:collision_box", new CompoundTag()
                            .putBoolean("enabled", true)
                            .putList("origin", new ListTag<FloatTag>()
                                    .add(new FloatTag(origin.x))
                                    .add(new FloatTag(origin.y))
                                    .add(new FloatTag(origin.z)))
                            .putList("size", new ListTag<FloatTag>()
                                    .add(new FloatTag(size.x))
                                    .add(new FloatTag(size.y))
                                    .add(new FloatTag(size.z))));
            return this;
        }

        /**
         * 设置此方块的客户端选择箱。
         * <p>
         * Set the client collision box for this block.
         *
         * @param origin 选择箱的原点 The origin of the collision box
         * @param size   选择箱的大小 The size of the collision box
         */
        public Builder selectionBox(@NotNull Vector3f origin, @NotNull Vector3f size) {
            this.nbt.getCompound("components")
                    .putCompound("minecraft:selection_box", new CompoundTag()
                            .putBoolean("enabled", true)
                            .putList("origin", new ListTag<FloatTag>()
                                    .add(new FloatTag(origin.x))
                                    .add(new FloatTag(origin.y))
                                    .add(new FloatTag(origin.z)))
                            .putList("size", new ListTag<FloatTag>()
                                    .add(new FloatTag(size.x))
                                    .add(new FloatTag(size.y))
                                    .add(new FloatTag(size.z))));
            return this;
        }

        public Builder blockTags(String... tag) {
            Preconditions.checkNotNull(tag);
            Preconditions.checkArgument(tag.length > 0);
            ListTag<StringTag> stringTagListTag = new ListTag<>();
            for (String s : tag) {
                stringTagListTag.add(new StringTag(s));
            }
            this.nbt.putList("blockTags", stringTagListTag);
            return this;
        }

        /**
         * 客户端摩擦系数，用于控制玩家在自定义方块上行走的速度，值越大，移动越快。
         * <p>
         * the client friction, which is used to control the speed at which the player walks on the custom block.The larger the value, the faster the movement.
         */

        public Builder clientFriction(float friction) {
            this.nbt.getCompound("components")
                    .putCompound("minecraft:friction", new CompoundTag()
                            .putFloat("value", friction));
            return this;
        }

        /**
         * PNX无法准确提供三个以上属性方块的属性解析,如果出现地图异常,请制作一个该方块相同属性的行为包,利用proxypass和bds抓包获取准确的属性解析。
         * <p>
         * PNX can not provide accurate properties analysis for more than three properties block, if there is a level exception to block error, please make a behavior_pack of the same properties of the block, and use proxypass and bds to get the packet to accurate get properties.
         *
         * @return Block Properties in NBT Tag format
         */
        @Nullable
        private ListTag<CompoundTag> getPropertiesNBT() {
            if (this.customBlock instanceof Block block) {
                var properties = block.getProperties();
                Set<BlockPropertyType<?>> propertyTypeSet = properties.getPropertyTypeSet();
                if (propertyTypeSet.isEmpty()) {
                    return null;
                }
                var nbtList = new ListTag<CompoundTag>();
                for (var each : propertyTypeSet) {
                    if (each instanceof BooleanPropertyType booleanBlockProperty) {
                        nbtList.add(new CompoundTag().putString("name", booleanBlockProperty.getName())
                                .putList("enum", new ListTag<>()
                                        .add(new IntTag(0))
                                        .add(new IntTag(1))));
                    } else if (each instanceof IntPropertyType intBlockProperty) {
                        var enumList = new ListTag<IntTag>();
                        for (int i = intBlockProperty.getMin(); i <= intBlockProperty.getMax(); i++) {
                            enumList.add(new IntTag(i));
                        }
                        nbtList.add(new CompoundTag().putString("name", intBlockProperty.getName()).putList("enum", enumList));
                    } else if (each instanceof EnumPropertyType<?> arrayBlockProperty) {
                        var enumList = new ListTag<StringTag>();
                        for (var e : arrayBlockProperty.getValidValues()) {
                            enumList.add(new StringTag(e.name().toLowerCase(Locale.ENGLISH)));
                        }
                        nbtList.add(new CompoundTag().putString("name", arrayBlockProperty.getName()).putList("enum", enumList));
                    }
                }
                return nbtList;
            }
            return null;
        }

        /**
         * 对要发送给客户端的方块ComponentNBT进行自定义处理，这里包含了所有对自定义方块的定义。在符合条件的情况下，你可以任意修改。
         * <p>
         * Custom processing of the block to be sent to the client ComponentNBT, which contains all definitions for custom block. You can modify them as much as you want, under the right conditions.
         */
        public CustomBlockDefinition customBuild(@NotNull Consumer<CompoundTag> nbt) {
            var def = this.build();
            nbt.accept(def.nbt);
            return def;
        }

        public CustomBlockDefinition build() {
            return new CustomBlockDefinition(this.identifier, this.nbt);
        }
    }
}
