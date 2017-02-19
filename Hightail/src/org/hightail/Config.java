package org.hightail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    static protected Properties properties = new Properties();
    static final protected String CONFIG_FILE_NAME = "hightail.config";

    static public synchronized Object set(String key, String value) {
        return properties.setProperty(key, value);
    }

    static public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    static public String get(String key) {
        return properties.getProperty(key);
    }

    static protected void setIfUnset(String key, String value) {
        if (!properties.containsKey(key)) {
            set(key, value);
        }
    }

    static public void fillInUnsetValuesWithDefaults() {
        setIfUnset("workingDirectory", new File("").getAbsolutePath());
        setIfUnset("pathFromWorkingDirToExec", "%P.exe");
        // TODO: if under Unix, this better be "%L"
        setIfUnset("prependingCommand", "");
        setIfUnset("checkExistence", "1");
        setIfUnset("putWholeName", "0");
    }

    static public boolean load() {
        boolean ok = true;
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_NAME);
            properties.loadFromXML(fis);
        } catch (IOException e) {
            ok = false;
        }

        fillInUnsetValuesWithDefaults();

        return ok;
    }

    static public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(CONFIG_FILE_NAME);
        properties.storeToXML(fos, "This is the configuration file for Hightail.", "utf-8");
    }

    static public boolean isPrependingCommandNonempty() {
        return !Config.get("prependingCommand").trim().isEmpty();
    }

    public static boolean getBoolean(String key) {
        return !get(key).equals("0");
    }

    public static void setBoolean(String key, boolean value) {
        set(key, value ? "1" : "0");
    }
}
