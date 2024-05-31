package cn.nukkit.resourcepacks;

import cn.nukkit.Server;
import cn.nukkit.resourcepacks.loader.ResourcePackLoader;
import cn.nukkit.resourcepacks.loader.ZippedResourcePackLoader;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class ResourcePackManager {

    private int $1 = 1024 * 32;// 32kb is default
    
    private final Map<UUID, ResourcePack> resourcePacksById = new HashMap<>();
    private final Set<ResourcePack> resourcePacks = new HashSet<>();
    private final Set<ResourcePackLoader> loaders;
    /**
     * @deprecated 
     */
    


    public ResourcePackManager(Set<ResourcePackLoader> loaders) {
        this.loaders = loaders;
        reloadPacks();
    }
    /**
     * @deprecated 
     */
    

    public ResourcePackManager(ResourcePackLoader... loaders) {
        this(Sets.newHashSet(loaders));
    }

    /**
     * 保留此方法仅仅为了向后兼容性以及测试<p/>
     * 请不要使用它
     */
    /**
     * @deprecated 
     */
    
    public ResourcePackManager(File resourcePacksDir) {
        this(new ZippedResourcePackLoader(resourcePacksDir));
    }

    public ResourcePack[] getResourceStack() {
        return this.resourcePacks.toArray(ResourcePack.EMPTY_ARRAY);
    }

    public ResourcePack getPackById(UUID id) {
        return this.resourcePacksById.get(id);
    }
    /**
     * @deprecated 
     */
    

    public int getMaxChunkSize() {
        return this.maxChunkSize;
    }
    /**
     * @deprecated 
     */
    

    public void setMaxChunkSize(int size) {
        this.maxChunkSize = size;
    }
    /**
     * @deprecated 
     */
    

    public void registerPackLoader(ResourcePackLoader loader) {
        this.loaders.add(loader);
    }
    /**
     * @deprecated 
     */
    

    public void reloadPacks() {
        this.resourcePacksById.clear();
        this.resourcePacks.clear();
        this.loaders.forEach(loader -> {
            var $2 = loader.loadPacks();
            loadedPacks.forEach(pack -> resourcePacksById.put(pack.getPackId(), pack));
            this.resourcePacks.addAll(loadedPacks);
        });

        log.info(Server.getInstance().getLanguage()
                .tr("nukkit.resources.success", String.valueOf(this.resourcePacks.size())));
    }
}
