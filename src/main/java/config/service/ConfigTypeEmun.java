package config.service;

import java.util.HashMap;
import java.util.Map;

public enum ConfigTypeEmun {
    INTEGER("java.lang.Integer"),BOOLEAN("java.lang.Boolean");
    private static Map<String,ConfigTypeEmun> typeEmunMap = new HashMap<>();

    static{
        for(ConfigTypeEmun configTypeEmun : ConfigTypeEmun.values()){
            typeEmunMap.put(configTypeEmun.getType(),configTypeEmun);
        }
    }
    private String type;

    ConfigTypeEmun(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ConfigTypeEmun ofJavaType(String javaType){
        return typeEmunMap.get(javaType);
    }


}


