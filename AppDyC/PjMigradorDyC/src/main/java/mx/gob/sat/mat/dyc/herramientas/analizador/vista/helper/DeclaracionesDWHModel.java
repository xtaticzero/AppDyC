package mx.gob.sat.mat.dyc.herramientas.analizador.vista.helper;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;


public class DeclaracionesDWHModel extends AbstractTableModel
{
    private List<DeclaracionDWHBean> declaraciones;

    public static final String[] COLUMNAS = {
        "Estatus", "Tipo declaración", "Fecha presentación", "Número operación", "Saldo a favor"
    };
    
    public DeclaracionesDWHModel(List<DeclaracionDWHBean> d){
        declaraciones = d;
    }

    @Override
    public int getRowCount() {
        return declaraciones.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    public String getColumnName(int col) {
        return COLUMNAS[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0: return declaraciones.get(rowIndex).getEstatus();
            case 1: return declaraciones.get(rowIndex).getTipoDeclaracion();
            case 2: return declaraciones.get(rowIndex).getFechPresentacion();
            case 3: return declaraciones.get(rowIndex).getNumOper();
            case 4: return declaraciones.get(rowIndex).getSaldoFavor();
        }
        return "";
    }
}
