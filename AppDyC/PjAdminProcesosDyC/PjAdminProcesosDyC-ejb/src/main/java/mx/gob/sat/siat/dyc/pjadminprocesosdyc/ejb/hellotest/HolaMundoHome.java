package mx.gob.sat.siat.dyc.pjadminprocesosdyc.ejb.hellotest;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface HolaMundoHome extends EJBHome {

    HolaMundoRemote create() throws RemoteException, CreateException;
}
