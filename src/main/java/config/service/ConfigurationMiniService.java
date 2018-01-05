package config.service;

import config.ConfigDTO;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConfigurationMiniService extends Remote {
    int changeConfig(ConfigDTO configDTO)  throws RemoteException;
    void registerClass(Class c) throws RemoteException;
    void init() throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException;
}
