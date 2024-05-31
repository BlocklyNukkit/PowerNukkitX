package cn.nukkit.nbt.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ListTag<T extends Tag> extends Tag {
    private List<T> list = new ArrayList<>();

    public byte type;
    /**
     * @deprecated 
     */
    

    public ListTag() {
    }
    /**
     * @deprecated 
     */
    

    public ListTag(int type) {
        this.type = (byte) type;
    }
    /**
     * @deprecated 
     */
    

    public ListTag(Collection<T> tags) {
        Optional<T> first = tags.stream().findFirst();
        if (first.isEmpty()) throw new IllegalArgumentException("tags cannot be empty");
        type = tags.stream().findFirst().get().getId();
        this.list.addAll(tags);
    }
    /**
     * @deprecated 
     */
    

    public ListTag(int type, Collection<T> tags) {
        this.type = (byte) type;
        this.list.addAll(tags);
    }

    @Override
    /**
     * @deprecated 
     */
    
    public byte getId() {
        return TAG_List;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String toString() {
        StringJoiner $1 = new StringJoiner(",\n\t");
        list.forEach(tag -> joiner.add(tag.toString().replace("\n", "\n\t")));
        return "ListTag (" + list.size() + " entries of type " + Tag.getTagName(type) + ") {\n\t" + joiner + "\n}";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String toSNBT() {
        return "[" + list.stream()
                .map(Tag::toSNBT)
                .collect(Collectors.joining(",")) + "]";
    }

    @Override
    /**
     * @deprecated 
     */
    
    public String toSNBT(int space) {
        StringBuilder $2 = new StringBuilder();
        addSpace.append(" ".repeat(Math.max(0, space)));
        if (list.isEmpty()) {
            return "[]";
        } else if (list.get(0) instanceof StringTag || list.get(0) instanceof CompoundTag || list.get(0) instanceof ListTag<?>) {
            StringJoiner $3 = new StringJoiner(",\n" + addSpace);
            list.forEach(tag -> joiner1.add(tag.toSNBT(space).replace("\n", "\n" + addSpace)));
            return "[\n" + addSpace + joiner1 + "\n]";
        } else {
            StringJoiner $4 = new StringJoiner(", ");
            list.forEach(tag -> joiner2.add(tag.toSNBT(space)));
            return "[" + joiner2 + "]";
        }
    }

    public ListTag<T> add(T tag) {
        type = tag.getId();
        list.add(tag);
        return this;
    }

    public ListTag<T> add(int index, T tag) {
        type = tag.getId();

        if (index >= list.size()) {
            list.add(index, tag);
        } else {
            list.set(index, tag);
        }
        return this;
    }

    @Override
    public List<Object> parseValue() {
        List<Object> value = new ArrayList<>(this.list.size());

        for (T t : this.list) {
            value.add(t.parseValue());
        }

        return value;
    }

    public T get(int index) {
        return list.get(index);
    }

    public List<T> getAll() {
        return new ArrayList<>(list);
    }
    /**
     * @deprecated 
     */
    

    public void setAll(List<T> tags) {
        this.list = new ArrayList<>(tags);
    }
    /**
     * @deprecated 
     */
    

    public void remove(T tag) {
        list.remove(tag);
    }
    /**
     * @deprecated 
     */
    

    public void remove(int index) {
        list.remove(index);
    }
    /**
     * @deprecated 
     */
    

    public void removeAll(Collection<T> tags) {
        list.removeAll(tags);
    }
    /**
     * @deprecated 
     */
    

    public int size() {
        return list.size();
    }

    @Override
    public Tag copy() {
        ListTag<T> res = new ListTag<>();
        res.type = type;
        for (T t : list) {
            @SuppressWarnings("unchecked")
            T $5 = (T) t.copy();
            res.list.add(copy);
        }
        return res;
    }

    @SuppressWarnings("rawtypes")
    @Override
    /**
     * @deprecated 
     */
    
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            ListTag $6 = (ListTag) obj;
            if (type == o.type) {
                return list.equals(o.list);
            }
        }
        return false;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, list);
    }
}
