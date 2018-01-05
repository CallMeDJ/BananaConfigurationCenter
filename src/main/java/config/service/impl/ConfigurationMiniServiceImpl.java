package config.service.impl;

import config.Config;
import config.ConfigDTO;
import config.service.ConfigurationCenterService;
import config.service.ConfigurationMiniService;
import config.service.Logger;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationMiniServiceImpl extends UnicastRemoteObject implements ConfigurationMiniService {

    private ConfigurationCenterService configurationCenterService;
    private Map<String,Class<?>> configClasses = new HashMap<>();

    private static String master = "rmi://127.0.0.1:8888/cfg_center";
    private static String SERVER_URL = "/cfg_miniserver";
    private String ip;
    private String port;
    public String serverUri = "127.0.0.1:8000/"+SERVER_URL;

    public void setConfigurationCenterService(ConfigurationCenterService configurationCenterService) {
        this.configurationCenterService = configurationCenterService;
    }

    public ConfigurationMiniServiceImpl(String ip, String port) throws RemoteException, MalformedURLException, NotBoundException, AlreadyBoundException {
        super();
        if(serverUri != null) {
            this.serverUri = ip+":"+port+SERVER_URL;
        }
        this.ip = ip;
        this.port = port;

    }

    @Override
    public void init() throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {
        configurationCenterService = (ConfigurationCenterService) Naming.lookup(master);

        LocateRegistry.createRegistry(Integer.valueOf(this.port));

        Naming.bind("rmi://"+serverUri,this);
        Logger.log("register to "+master+" success!");
    }


    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    @Override
    public int changeConfig(ConfigDTO configDTO) throws RemoteException {
        Class targetClass = this.configClasses.get(configDTO.getClassName());
        if(targetClass == null){
            return 500;
        }

        try {
            Field field = targetClass.getField(configDTO.getFiled());
            field.setAccessible(true);
            switch (configDTO.getValueType()){

                case "java.lang.Integer":
                field.set(null,Integer.valueOf(configDTO.getValue()));
                    break;

                case "java.lang.Boolean":
                    field.set(null,Boolean.valueOf(configDTO.getValue()));
                    break;

            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return 200;
    }

    public void registerClass(Class currentClass) throws RemoteException {
        this.configClasses.put(currentClass.getName(),currentClass);
        Field[] fields = currentClass.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);

            if(field.isAnnotationPresent(Config.class)){
                ConfigDTO configDTO = new ConfigDTO();
                configDTO.setServer(serverUri);
                configDTO.setClassName(currentClass.getName());
                configDTO.setFiled(field.getName());
                configDTO.setDesc(field.getAnnotation(Config.class).desc());
                try {
                    configDTO.setValueType(field.getType().getName());
                    Object value = field.get(null);
                    configDTO.setValue(String.valueOf(value));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                configurationCenterService.register(configDTO);

            }

        }
    }
}
