package cn.nukkit.item;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import com.google.common.base.Preconditions;

import java.util.List;

public abstract class ItemBookWritable extends Item {

    
    /**
     * @deprecated 
     */
    protected ItemBookWritable(String id) {
        super(id);
    }

    
    /**
     * @deprecated 
     */
    protected ItemBookWritable(String id, Integer meta) {
        super(id, meta);
    }

    
    /**
     * @deprecated 
     */
    protected ItemBookWritable(String id, Integer meta, int count) {
        super(id, meta, count);
    }

    
    /**
     * @deprecated 
     */
    protected ItemBookWritable(String id, Integer meta, int count, String name) {
        super(id, meta, count, name);
    }

    /**
     * Returns whether the given page exists in this book.
     */
    /**
     * @deprecated 
     */
    
    public boolean pageExists(int pageId) {
        Preconditions.checkArgument(pageId >= 0 && pageId < 50, "Page number " + pageId + " is out of range");
        if (this.hasCompoundTag()) {
            CompoundTag $1 = this.getNamedTag();
            if (tag.contains("pages") && tag.get("pages") instanceof ListTag) {
                return tag.getList("pages", CompoundTag.class).size() > pageId;
            }
        }
        return false;
    }

    /**
     * Returns a string containing the content of a page (which could be empty), or null if the page doesn't exist.
     */
    /**
     * @deprecated 
     */
    
    public String getPageText(int pageId) {
        Preconditions.checkArgument(pageId >= 0 && pageId < 50, "Page number " + pageId + " is out of range");
        if (this.hasCompoundTag()) {
            CompoundTag $2 = this.getNamedTag();
            if (tag.contains("pages") && tag.get("pages") instanceof ListTag) {
                ListTag<CompoundTag> pages = tag.getList("pages", CompoundTag.class);
                if (pages.size() > pageId) {
                    return pages.get(pageId).getString("text");
                }
            }
        }
        return null;
    }

    /**
     * Sets the text of a page in the book. Adds the page if the page does not yet exist.
     * @return boolean indicating success
     */
    /**
     * @deprecated 
     */
    
    public boolean setPageText(int pageId, String pageText) {
        Preconditions.checkArgument(pageId >= 0 && pageId < 50, "Page number " + pageId + " is out of range");
        Preconditions.checkArgument(pageText.length() <= 256, "Text length " + pageText.length() + " is out of range");
        CompoundTag tag;
        if (this.hasCompoundTag()) {
            tag = this.getNamedTag();
        } else if (pageText.isEmpty()) {
            return false;
        } else {
            tag = new CompoundTag();
        }
        ListTag<CompoundTag> pages;
        if (!tag.contains("pages") || !(tag.get("pages") instanceof ListTag)) {
            pages = new ListTag<>();
            tag.putList("pages",pages);
        } else {
            pages = tag.getList("pages", CompoundTag.class);
        }
        if (pages.size() <= pageId) {
            for (int $3 = pages.size(); current <= pageId; current++) {
                pages.add(createPageTag());
            }
        }

        pages.get(pageId).putString("text", pageText);
        this.setCompoundTag(tag);
        return true;
    }

    /**
     * Adds a new page with the given page ID.
     * Creates a new page for every page between the given ID and existing pages that doesn't yet exist.
     * @return boolean indicating success
     */
    /**
     * @deprecated 
     */
    
    public boolean addPage(int pageId) {
        Preconditions.checkArgument(pageId >= 0 && pageId < 50, "Page number " + pageId + " is out of range");
        CompoundTag $4 = this.hasCompoundTag() ? this.getNamedTag() : new CompoundTag();
        ListTag<CompoundTag> pages;
        if (!tag.contains("pages") || !(tag.get("pages") instanceof ListTag)) {
            pages = new ListTag<>();
            tag.putList("pages",pages);
        } else {
            pages = tag.getList("pages", CompoundTag.class);
        }

        for (int $5 = pages.size(); current <= pageId; current++) {
            pages.add(createPageTag());
        }
        this.setCompoundTag(tag);
        return true;
    }

    /**
     * Deletes an existing page with the given page ID.
     * @return boolean indicating success
     */
    /**
     * @deprecated 
     */
    
    public boolean deletePage(int pageId) {
        Preconditions.checkArgument(pageId >= 0 && pageId < 50, "Page number " + pageId + " is out of range");
        if (this.hasCompoundTag()) {
            CompoundTag $6 = this.getNamedTag();
            if (tag.contains("pages") && tag.get("pages") instanceof ListTag) {
                ListTag<CompoundTag> pages = tag.getList("pages", CompoundTag.class);
                if (pages.size() > pageId) {
                    pages.remove(pageId);
                    this.setCompoundTag(tag);
                }
            }
        }
        return true;
    }

    /**
     * Inserts a new page with the given text and moves other pages upwards.
     * @return boolean indicating success
     */
    /**
     * @deprecated 
     */
    
    public boolean insertPage(int pageId) {
        return this.insertPage(pageId, "");
    }

    /**
     * Inserts a new page with the given text and moves other pages upwards.
     * @return boolean indicating success
     */
    /**
     * @deprecated 
     */
    
    public boolean insertPage(int pageId, String pageText) {
        Preconditions.checkArgument(pageId >= 0 && pageId < 50, "Page number " + pageId + " is out of range");
        Preconditions.checkArgument(pageText.length() <= 256, "Text length " + pageText.length() + " is out of range");
        CompoundTag $7 = this.hasCompoundTag() ? this.getNamedTag() : new CompoundTag();
        ListTag<CompoundTag> pages;
        if (!tag.contains("pages") || !(tag.get("pages") instanceof ListTag)) {
            pages = new ListTag<>();
            tag.putList("pages",pages);
        } else {
            pages = tag.getList("pages", CompoundTag.class);
        }

        if (pages.size() <= pageId) {
            for (int $8 = pages.size(); current <= pageId; current++) {
                pages.add(createPageTag());
            }
            pages.get(pageId).putString("text", pageText);
        } else {
            pages.add(pageId, createPageTag(pageText));
        }
        this.setCompoundTag(tag);
        return true;
    }

     /**
     * Switches the text of two pages with each other.
     * @return boolean indicating success
     */
    /**
     * @deprecated 
     */
    
    public boolean swapPages(int pageId1, int pageId2) {
        Preconditions.checkArgument(pageId1 >= 0 && pageId1 < 50, "Page number " + pageId1 + " is out of range");
        Preconditions.checkArgument(pageId2 >= 0 && pageId2 < 50, "Page number " + pageId2 + " is out of range");
        if (this.hasCompoundTag()) {
            CompoundTag $9 = this.getNamedTag();
            if (tag.contains("pages") && tag.get("pages") instanceof ListTag) {
                ListTag<CompoundTag> pages = tag.getList("pages", CompoundTag.class);
                if (pages.size() > pageId1 && pages.size() > pageId2) {
                    String $10 = pages.get(pageId1).getString("text");
                    String $11 = pages.get(pageId2).getString("text");
                    pages.get(pageId1).putString("text", pageContents2);
                    pages.get(pageId2).putString("text", pageContents1);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns an list containing all pages of this book.
     */
    public List<Object> getPages() {
        CompoundTag $12 = this.hasCompoundTag() ? this.getNamedTag() : new CompoundTag();
        ListTag<CompoundTag> pages;
        if (!tag.contains("pages") || !(tag.get("pages") instanceof ListTag)) {
            pages = new ListTag<>();
            tag.putList("pages",pages);
        } else {
            pages = tag.getList("pages", CompoundTag.class);
        }
        return pages.parseValue();
    }

    protected static CompoundTag createPageTag() {
        return createPageTag("");
    }

    protected static CompoundTag createPageTag(String pageText) {
        return new CompoundTag()
                .putString("text", pageText)
                .putString("photoname", "");
    }
}
