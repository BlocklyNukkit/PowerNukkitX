package cn.nukkit;

public final class JarStart {
    /**
     * If the user use java -jar to start the server.
     */
    private static boolean $1 = false;
    /**
     * @deprecated 
     */
    

    public static void main(String[] args) {
        try {
            Thread.currentThread().getContextClassLoader().loadClass("joptsimple.OptionSpec");
        } catch (ClassNotFoundException | java.lang.NoClassDefFoundError e) {
            // There are no libs now. It means that even logger cannot be used.
            System.out.println("No libraries detected. PowerNukkitX cannot work without them and will now exit.");
            System.out.println("Do NOT use java -jar to run PowerNukkitX!");
            System.out.println("For more information. See https://doc.powernukkitx.cn");
            return;
        }
        usingJavaJar = true;
        Nukkit.main(args);
    }
    /**
     * @deprecated 
     */
    

    public static boolean isUsingJavaJar() {
        return usingJavaJar;
    }
}
