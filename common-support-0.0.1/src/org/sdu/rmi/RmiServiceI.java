package org.sdu.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServiceI extends Remote {

	public RmiResponse requestProcesser(RmiRequest request) throws RemoteException;

}