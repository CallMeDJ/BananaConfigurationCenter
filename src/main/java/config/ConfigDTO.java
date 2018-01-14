package config;

import config.service.ConfigTypeEmun;

import java.io.Serializable;

public class ConfigDTO implements Serializable{


    private String server;
    private String className;
    private  String filed;
    private  String desc;
    private ConfigTypeEmun valueType;
    private  String value;

    public String getValue() {
        return value;
    }

    public ConfigTypeEmun getValueType() {
        return valueType;
    }

    public void setValueType(ConfigTypeEmun valueType) {
        this.valueType = valueType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return "server : "+this.server+" , className: "+className+", field: "+filed+",desc: "+desc+"value: "+String.valueOf(value)+" valueType: "+valueType;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
