package config.service.impl;

import config.ConfigDTO;
import config.service.ConfigurationCenterService;
import config.service.ConfigurationMiniService;
import config.service.Logger;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationCenterServiceImpl  extends UnicastRemoteObject implements ConfigurationCenterService {
    Map<String,ConfigurationMiniService> configurationMiniServerciceBundle;
    private List<ConfigDTO> configs ;
    public ConfigurationCenterServiceImpl() throws RemoteException {
        super();
        this.configs = new ArrayList<>();
        this.configurationMiniServerciceBundle = new HashMap<>(32);
    }


    @Override
    public int register(ConfigDTO configDTO) throws RemoteException {
        configs.add(configDTO);
        getOrCreateBundle(configDTO.getServer());
        Logger.log(configDTO);
        return 200;
    }

    private ConfigurationMiniService getOrCreateBundle(String server){


        try {
            if(!configurationMiniServerciceBundle.containsKey(server)){
                ConfigurationMiniService configurationMiniServercice = (ConfigurationMiniService) Naming.lookup("rmi://"+server);
                configurationMiniServerciceBundle.put(server,configurationMiniServercice);
            }

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return configurationMiniServerciceBundle.get(server);
    }

    @Override
    public int pushConfig(ConfigDTO configDTO) throws RemoteException {
        ConfigurationMiniService currentService =  getOrCreateBundle(configDTO.getServer());

        return currentService.changeConfig(configDTO);
    }

    @Override
    public void getAllConfig() throws RemoteException {
        for(ConfigDTO configDTO: configs){
            Logger.log(configDTO);
        }
    }

}
