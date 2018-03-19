package mx.gob.sat.siat.dyc.pjadminprocesosdyc.ejb.hellotest;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;


public class HolaMundoBean implements SessionBean {

    private static final long serialVersionUID = 1L;

    public String saludo(String nombre) {
        return "Hola, " + nombre;
    }

    public void ejbCreate() {
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException {
    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException {
    }

    @Override
    public void ejbRemove() throws EJBException, RemoteException {
    }

    @Override
    public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
    }
}
