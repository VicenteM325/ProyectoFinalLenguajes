
package recursos;

import analizadorLexico.TipoError;
import analizadorLexico.Token;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dell
 */
public class ManejadorTabla {
    
    
    public void ReporteTokens(ArrayList<Token> list, JTable table) {
         DefaultTableModel modelo = new DefaultTableModel();
          table.setModel(modelo);
          modelo.addColumn("Nombre Token");
          modelo.addColumn("Lexema");
          modelo.addColumn("fila");
          modelo.addColumn("columna");
          for(int i=0; i<list.size();i++){
              modelo.addRow(new Object[]{list.get(i).getToken(),list.get(i).getLexema(),list.get(i).getFila(),list.get(i).getColumna()});
          }
           
    }
    
    
    public void ReporteError(ArrayList<TipoError> lista, JTable table) {
         DefaultTableModel modelo = new DefaultTableModel();
          table.setModel(modelo);
          modelo.addColumn("Tipo Error");
          modelo.addColumn("Lexema");
          modelo.addColumn("fila");
          modelo.addColumn("columna");
          for(int j=0; j<lista.size();j++){
              modelo.addRow(new Object[]{lista.get(j).getLexema(),lista.get(j).getTipoError(),lista.get(j).getFila(),lista.get(j).getColumna()});

          }
           
    }
    
    public void ReporteErrorSintactico(ArrayList<String> lista, JTable table){
          DefaultTableModel modelo = new DefaultTableModel();
          table.setModel(modelo);
          modelo.addColumn("Tipo Error");
          for(int j=0; j<lista.size();j++){
              modelo.addRow(new Object[]{lista.get(j)});

          }
    }
}