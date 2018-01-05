package config.service;

import config.ConfigDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConfigurationCenterService extends Remote {
    int register(ConfigDTO configDTO)  throws RemoteException;
    int pushConfig(ConfigDTO configDTO)  throws RemoteException;
    void getAllConfig() throws RemoteException;
}
