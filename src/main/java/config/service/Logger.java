package config.service;

public class Logger {
    public static  void log(Object... object){
        for(Object current : object){
            System.out.println(current);
        }
    }
}
