package mx.gob.sat.siat.dyc.generico.web.util.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ManejadorExcepcionesFactory extends ExceptionHandlerFactory{

      private ExceptionHandlerFactory instancia;
     
      public ManejadorExcepcionesFactory(ExceptionHandlerFactory instancia) {
        this.instancia = instancia;
      }
      
      @Override
      public ExceptionHandler getExceptionHandler() {
        return new ManejadorExcepciones(instancia.getExceptionHandler()) ;
      }
}
