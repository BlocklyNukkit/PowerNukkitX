package cn.nukkit.lang;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@Log4j2
public class BaseLang {
    /**
     * 默认备选语言，对应language文件夹
     */
    public static final String FALLBACK_LANGUAGE = "eng";

    /**
     * The Lang name.
     */
    protected final String langName;

    /**
     * 本地语言，从nukkit.yml中指定
     */
    protected Map<String, String> lang;

    /**
     * 备选语言映射，当从本地语言映射中无法翻译时调用备选语言映射，默认为英文
     */
    protected Map<String, String> fallbackLang = new HashMap<>();

    private final Pattern split = Pattern.compile("%[A-Za-z0-9_.-]+");


    public BaseLang(String lang) {
        this(lang, null);
    }

    public BaseLang(String lang, String path) {
        this(lang, path, FALLBACK_LANGUAGE);
    }

    public BaseLang(String lang, String path, String fallback) {
        this.langName = lang.toLowerCase();
        boolean useFallback = !lang.equals(fallback);

        if (path == null) {
            path = "language/";
            try {
                this.lang = this.loadLang(this.getClass().getModule().getResourceAsStream(path + this.langName + "/lang.ini"));
                if (useFallback)
                    this.fallbackLang = this.loadLang(this.getClass().getModule().getResourceAsStream(path + fallback + "/lang.ini"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.lang = this.loadLang(path + this.langName + "/lang.ini");
            if (useFallback) this.fallbackLang = this.loadLang(path + fallback + "/lang.ini");
        }
        if (this.fallbackLang == null) this.fallbackLang = this.lang;


    }

    public Map<String, String> getLangMap() {
        return lang;
    }

    public Map<String, String> getFallbackLangMap() {
        return fallbackLang;
    }

    public String getName() {
        return this.get("language.name");
    }

    public String getLang() {
        return langName;
    }

    protected Map<String, String> loadLang(String path) {
        try {
            File file = new File(path);
            if (!file.exists() || file.isDirectory()) {
                throw new FileNotFoundException();
            }
            try (FileInputStream stream = new FileInputStream(file)) {
                return parseLang(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            log.fatal("Failed to load language at {}", path, e);
            return null;
        }
    }

    protected Map<String, String> loadLang(InputStream stream) {
        try {
            return parseLang(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
        } catch (IOException e) {
            log.error("Failed to parse the language input stream", e);
            return null;
        }
    }

    private Map<String, String> parseLang(BufferedReader reader) throws IOException {
        Map<String, String> d = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.charAt(0) == '#') {
                continue;
            }
            String[] t = line.split("=", 2);
            if (t.length < 2) {
                continue;
            }
            String key = t[0];
            String value = t[1];
            if (value.length() > 1 && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
                value = value.substring(1, value.length() - 1).replace("\\\"", "\"").replace("\\\\", "\\");
            }
            if (value.isEmpty()) {
                continue;
            }
            d.put(key, value);
        }
        return d;
    }

    /**
     * 翻译一个文本key，key从语言文件中查询
     *
     * @param key the key
     * @return the string
     */
    @PowerNukkitXOnly
    @Since("1.19.50-r4")
    public String tr(String key) {
        return this.translateString(key);
    }

    /**
     * 翻译一个文本key，key从语言文件中查询，并且按照给定参数填充结果
     *
     * @param key  the key
     * @param args the args
     * @return the string
     */
    @PowerNukkitXOnly
    @Since("1.19.50-r4")
    public String tr(String key, String... args) {
        return this.translateString(key, args);
    }

    /**
     * 翻译一个文本key，key从语言文件中查询，并且按照给定参数填充结果
     *
     * @param key  the key
     * @param args the args
     * @return the string
     */
    @PowerNukkitXOnly
    @Since("1.19.50-r4")
    public String tr(String key, Object... args) {
        String baseText = parseText(key);
        for (int i = 0; i < args.length; i++) {
            baseText = baseText.replace("{%" + i + "}", parseText(String.valueOf(args[i])));
        }
        return baseText;
    }

    @PowerNukkitXOnly
    @Since("1.19.50-r4")
    public String tr(TextContainer c) {
        String baseText = this.parseText(c.getText());
        if (c instanceof TranslationContainer cc) {
            for (int i = 0; i < cc.getParameters().length; i++) {
                baseText = baseText.replace("{%" + i + "}", this.parseText(cc.getParameters()[i]));
            }
        }
        return baseText;
    }

    @Deprecated
    @DeprecationDetails(since = "1.19.50-r4", reason = "old", replaceWith = "BaseLang#tr(String)")
    public String translateString(String str) {
        return tr(str);
    }

    @Deprecated
    @DeprecationDetails(since = "1.19.50-r4", reason = "old", replaceWith = "BaseLang#tr(String,String...)")
    public String translateString(String str, @NotNull String... params) {
        return this.tr(str, params);
    }

    @Deprecated
    @DeprecationDetails(since = "1.19.50-r4", reason = "old", replaceWith = "BaseLang#tr(String,Object...)")
    public String translateString(String str, @NotNull Object... params) {
        return this.tr(str, params);
    }

    @Deprecated
    @DeprecationDetails(since = "1.19.50-r4", reason = "old", replaceWith = "BaseLang#tr(String,Object...)")
    public String translateString(String str, String param, String onlyPrefix) {
        return this.tr(str, param);
    }

    @Deprecated
    @DeprecationDetails(since = "1.19.50-r4", reason = "old", replaceWith = "BaseLang#tr(String,Object...)")
    public String translateString(String str, String[] params, String onlyPrefix) {
        return tr(str, params);
    }

    @Deprecated
    @DeprecationDetails(since = "1.19.50-r4", reason = "old", replaceWith = "BaseLang#tr(TextContainer)")
    public String translate(TextContainer c) {
        String baseText = this.parseTranslation(c.getText());
        if (c instanceof TranslationContainer) {
            baseText = this.internalGet(c.getText());
            baseText = this.parseTranslation(baseText != null ? baseText : c.getText());
            for (int i = 0; i < ((TranslationContainer) c).getParameters().length; i++) {
                baseText = baseText.replace("{%" + i + "}", this.parseTranslation(((TranslationContainer) c).getParameters()[i]));
            }
        }
        return baseText;
    }

    public String internalGet(String id) {
        if (this.lang.containsKey(id)) {
            return this.lang.get(id);
        } else if (this.fallbackLang.containsKey(id)) {
            return this.fallbackLang.get(id);
        }
        return null;
    }

    public String get(String id) {
        if (this.lang.containsKey(id)) {
            return this.lang.get(id);
        } else if (this.fallbackLang.containsKey(id)) {
            return this.fallbackLang.get(id);
        }
        return id;
    }

    protected String parseText(String str) {
        String result = internalGet(str);
        if (result != null) {
            return result;
        } else {
            var matcher = split.matcher(str);
            return matcher.replaceAll(m -> this.get(m.group().substring(1)));
        }
    }

    @Deprecated
    protected String parseTranslation(String text) {
        return this.parseTranslation(text, null);
    }

    @Deprecated
    protected String parseTranslation(String text, String onlyPrefix) {
        StringBuilder newString = new StringBuilder();
        text = String.valueOf(text);

        String replaceString = null;

        int len = text.length();

        for (int i = 0; i < len; ++i) {
            char c = text.charAt(i);
            if (replaceString != null) {
                int ord = c;
                if ((ord >= 0x30 && ord <= 0x39) // 0-9
                        || (ord >= 0x41 && ord <= 0x5a) // A-Z
                        || (ord >= 0x61 && ord <= 0x7a) || // a-z
                        c == '.' || c == '-') {
                    replaceString += String.valueOf(c);
                } else {
                    String t = this.internalGet(replaceString.substring(1));
                    if (t != null && (onlyPrefix == null || replaceString.indexOf(onlyPrefix) == 1)) {
                        newString.append(t);
                    } else {
                        newString.append(replaceString);
                    }
                    replaceString = null;
                    if (c == '%') {
                        replaceString = String.valueOf(c);
                    } else {
                        newString.append(c);
                    }
                }
            } else if (c == '%') {
                replaceString = String.valueOf(c);
            } else {
                newString.append(c);
            }
        }

        if (replaceString != null) {
            String t = this.internalGet(replaceString.substring(1));
            if (t != null && (onlyPrefix == null || replaceString.indexOf(onlyPrefix) == 1)) {
                newString.append(t);
            } else {
                newString.append(replaceString);
            }
        }
        return newString.toString();
    }
}
