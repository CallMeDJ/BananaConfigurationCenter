package config;

@ConfigClass
public class Configuration {
    @Config(desc = "数字配置")
    public static Integer NUMBER_CONFIG  = 5;
    @Config(desc = "开关型配置")
    public static Boolean SWITCH_CONFIG = true;

}
