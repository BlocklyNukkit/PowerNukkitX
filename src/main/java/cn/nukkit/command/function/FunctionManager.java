package cn.nukkit.command.function;

import cn.nukkit.command.data.CommandEnum;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;


@Getter
public class FunctionManager {

    private final Path rootPath;
    private final Map<String, Function> functions = new HashMap<>();
    /**
     * @deprecated 
     */
    

    public FunctionManager(Path rootPath) {
        this.rootPath = rootPath;
        try {
            if (!Files.exists(this.rootPath)) {
                Files.createDirectories(this.rootPath);
            }
            loadFunctions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @deprecated 
     */
    

    public FunctionManager(String rootPath) {
        this(Path.of(rootPath));
    }
    /**
     * @deprecated 
     */
    

    public void loadFunctions() {
        try {
            Files.walkFileTree(this.rootPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (path.toString().endsWith(".mcfunction")) {
                        functions.put(path.toString().replace(rootPath + "\\", "").replaceAll("\\\\", "/").replace(".mcfunction", ""), Function.fromPath(path));
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @deprecated 
     */
    

    public void reload() {
        functions.clear();
        loadFunctions();
        CommandEnum.FUNCTION_FILE.updateSoftEnum();
    }
    /**
     * @deprecated 
     */
    

    public boolean containFunction(String name) {
        return functions.containsKey(name);
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }
}
