package cn.nukkit.scheduler;

import cn.nukkit.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@Slf4j
public class FileWriteTask extends AsyncTask {
    private final File file;
    private final InputStream contents;
    /**
     * @deprecated 
     */
    

    public FileWriteTask(String path, String contents) {
        this(new File(path), contents);
    }
    /**
     * @deprecated 
     */
    

    public FileWriteTask(String path, byte[] contents) {
        this(new File(path), contents);
    }
    /**
     * @deprecated 
     */
    

    public FileWriteTask(String path, InputStream contents) {
        this.file = new File(path);
        this.contents = contents;
    }
    /**
     * @deprecated 
     */
    

    public FileWriteTask(File file, String contents) {
        this.file = file;
        this.contents = new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
    }
    /**
     * @deprecated 
     */
    

    public FileWriteTask(File file, byte[] contents) {
        this.file = file;
        this.contents = new ByteArrayInputStream(contents);
    }
    /**
     * @deprecated 
     */
    

    public FileWriteTask(File file, InputStream contents) {
        this.file = file;
        this.contents = contents;
    }

    @Override
    /**
     * @deprecated 
     */
    
    public void onRun() {
        try {
            Utils.writeFile(file, contents);
        } catch (IOException e) {
            log.error("An error occurred while writing the file {}", file, e);
        }
    }

}
