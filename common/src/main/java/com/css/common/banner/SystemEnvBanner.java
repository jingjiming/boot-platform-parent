package com.css.common.banner;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by jiming.jing on 2023/1/18
 */
public class SystemEnvBanner implements Banner {

    private static final String[] BANNER = new String[]{"", "  .   ____          _            __ _ _", " /\\\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\", "( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\", " \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )", "  '  |____| .__|_| |_|_| |_\\__, | / / / /", " =========|_|==============|___/=/_/_/_/"};
    private static final String SPRING_BOOT = " :: Spring Boot :: ";
    private static final int STRAP_LINE_SIZE = 42;


    private static final String SPRING_BOOT_BERSION = "Spring Boot Version";
    private static final String SPRING_VERSION = "Spring Version";
    private static final String BUILD_DATE_TIME = "Build Date/Time";
    private static final String JAVA_HOME = "Java Home";
    private static final String JAVA_VENDOR = "Java Vendor";
    private static final String JAVA_VERSION = "Java Version";
    private static final String FILE_ENCODING = "File Encoding";
    private static final String JVM_FREE_MEMORY = "JVM Free Memory";
    private static final String JVM_MAXIMUM_MEMORY = "JVM Maximum Memory";
    private static final String JVM_TOTAL_MEMORY = "JVM Total Memory";
    private static final String OS_ARCHITECTURE = "OS Architecture";
    private static final String OS_NAME = "OS Name";
    private static final String OS_VERSION = "OS Version";
    private static final String OS_TIMEZONE = "OS Timezone";
    private static final String OS_DATE_TIME = "OS Date/Time";
    private static final String OS_TEMP_DIRECTORY = "OS Temp Directory";
    private static final String BOOTSTRAP_CLASS = "Bootstrap Class";
    private static final String CURRENT_COMMAND = "Current Command";
    private static final String JAVA_MEMORY = "Java Memory";
    private static final String MEMRORY_POOLS = "Memory Pools";
    private static final String NUMBER_OF_PROCESSORS = "NUMBER OF PROCESSORS";

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        /**
         * @see org.springframework.boot.SpringBootBanner
         */
        String[] var4 = BANNER;
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String line = var4[var6];
            out.println(line);
        }

        String version = SpringBootVersion.getVersion();
        version = version != null ? " (v" + version + ")" : "";
        StringBuilder padding = new StringBuilder();

        while(padding.length() < 42 - (version.length() + " :: Spring Boot :: ".length())) {
            padding.append(" ");
        }

        out.println(AnsiOutput.toString(new Object[]{AnsiColor.GREEN, " :: Spring Boot :: ", AnsiColor.DEFAULT, padding.toString(), AnsiStyle.FAINT, version}));
        out.println();
        out.println("------------------------------------------------------------------");

        Properties properties = System.getProperties();
        /*Set<Object> propkeySet = properties.keySet();
        for (Object key : propkeySet) {
            System.out.println(key + " : " + properties.getProperty(key.toString()));
        }
        System.out.println("-----------------------------------------------");*/
        out.println();
        out.println(AnsiOutput.toString(new Object[]{AnsiColor.RED, "                      系统环境参数"}));
        out.println();
        Map<String, String> map = new LinkedHashMap<>();
        // Spring Boot Version
        String springBootVersion = SpringBootVersion.getVersion();
        springBootVersion = springBootVersion != null ? springBootVersion : "";
        map.put(SPRING_BOOT_BERSION, springBootVersion);
        // Spring Version
        String springVersion = SpringVersion.getVersion();
        springVersion = springVersion != null ? springVersion : "";
        map.put(SPRING_VERSION, springVersion);
        // Build Date/Time
        map.put(BUILD_DATE_TIME, LocalDateTime.now().toString());
        // Java Home
        map.put(JAVA_HOME, properties.getProperty("java.home"));
        // Java Vendor
        map.put(JAVA_VENDOR, properties.getProperty("java.vendor"));
        // Java Version
        map.put(JAVA_VERSION, properties.getProperty("java.version"));
        // File Encoding
        map.put(FILE_ENCODING, properties.getProperty("file.encoding"));
        // JVM Free Memory
        map.put(JVM_FREE_MEMORY, Runtime.getRuntime().freeMemory() / 1024 / 1024 + " MB");
        // JVM Maximum Memory
        map.put(JVM_MAXIMUM_MEMORY, Runtime.getRuntime().maxMemory() / 1024 / 1024 / 1024 + " GB");
        // JVM Total Memory
        map.put(JVM_TOTAL_MEMORY, Runtime.getRuntime().totalMemory() / 1024 / 1024 + " MB");
        // OS Architecture
        map.put(OS_ARCHITECTURE, properties.getProperty("os.arch"));
        // OS Name
        map.put(OS_NAME, properties.getProperty("os.name"));
        // OS Version
        map.put(OS_VERSION, properties.getProperty("os.version"));
        // OS Timezone
        map.put(OS_TIMEZONE, TimeZone.getDefault().getID());
        // OS Date/Time
        map.put(OS_DATE_TIME, LocalDateTime.now().toString());
        // OS Temp Directory
        map.put(OS_TEMP_DIRECTORY, System.getenv().get("TEMP"));
        // Bootstrap Class
        map.put(BOOTSTRAP_CLASS, properties.getProperty("sun.java.command"));
        // NUMBER_OF_PROCESSORS
        map.put(NUMBER_OF_PROCESSORS, System.getenv().get("NUMBER_OF_PROCESSORS"));


        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 堆内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        // 初始的总内存
        long initTotalMemorySize = memoryUsage.getInit();
        // 最大可用内存
        long maxMemorySize = memoryUsage.getMax();
        // 已使用的内存
        long usedMemorySize = memoryUsage.getUsed();

        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            while(sb.length() < 23 - key.length()) {
                sb.append(" ");
            }
            out.println(AnsiOutput.toString(new Object[]{AnsiColor.BLUE, " " + key, AnsiColor.DEFAULT, sb.toString(), AnsiStyle.FAINT, AnsiColor.BLUE, " : " + map.get(key)}));
        }
        out.println();

        /*Map<String, String> env = System.getenv();
        Set<String> envKeySet = env.keySet();
        for (String key : envKeySet) {
            System.out.println(key + " : " + env.get(key));
        }
        System.out.println("-----------------------------------------------");*/

    }

}
