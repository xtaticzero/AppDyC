package mx.gob.sat.siat.dyc.pjadminprocesosdyc.ejb.hellotest;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface HolaMundoRemote extends EJBObject {

    public String saludo(String nombre) throws RemoteException;
}
