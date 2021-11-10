
package analizadorSintactico;


import analizadorLexico.PalabraReservadas;
import analizadorLexico.TipoError;
import analizadorLexico.TipoToken;
import analizadorLexico.Token;
import Jframe.VentanaInicio;
import recursos.ManejadorTabla;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author dell
 */
public class AnalizadorSintactico {
   
    
    private PalabraReservadas reservada;
    private TipoToken tipoToken;
    private ArrayList<String> listaErrores = new ArrayList<String> ();

   
    int estado=0;
    int indice;
    public void analizar(ArrayList<Token> listaToken,  JFileChooser  arch ,JTable table) throws IOException{
        FileWriter archivo = new FileWriter(arch.getSelectedFile());
        for(int i=0; i<listaToken.size(); i++){
             
            switch (estado) {
                case 0:
                    if(listaToken.get(i).getLexema().equals(reservada.ESCRIBIR.name())){
                       
                        estado=1;
                     }
                    else if(listaToken.get(i).getLexema().equals(reservada.REPETIR.name())){
                        estado=3;
                    }
                    else if(listaToken.get(i).getLexema().equals(reservada.SI.name())){
                        estado=9;
                    }
                    else  if(listaToken.get(i).getToken().equals(tipoToken.IDENTIFICADOR.name())){
                        estado=11;
                    }
                    
                    else {
                       // listaErrores.add("Error en la fila "+listaToken.get(i-1).getFila()+" No existe en el alfabeto");
                        estado=0;  
                    }
                break;
                case 1:
                    if(listaToken.get(i).getToken().equals(tipoToken.LITERAL.name()) || listaToken.get(i).getToken().equals(tipoToken.ENTERO.name()) || listaToken.get(i).getToken().equals(tipoToken.IDENTIFICADOR.name())){     
                        estado=2;
                    }
                    
                    else {
                        listaErrores.add("Error sintactico");
                        System.out.println("Error sintactico");
                        estado=0;
                    }
                         
                break;
                case 2:
                    
                    if(listaToken.get(i).getLexema().equals(reservada.FIN.name()) && listaToken.get(i-1).getToken().equals(tipoToken.LITERAL.name())){
                        System.out.println("La palabra reservada es "+listaToken.get(i-1).getLexema());  
                        archivo.write("     " +limpiartexto(listaToken.get(i-1).getLexema())+"\n");
                        i--;
                        estado=0;
                    }
                    else if(listaToken.get(i).getLexema().equals(reservada.FIN.name()) && listaToken.get(i-1).getToken().equals(tipoToken.ENTERO.name())){
                        System.out.println("La palabra reservada es "+listaToken.get(i-1).getLexema());
                        archivo.write("         "+listaToken.get(i-1).getLexema()+"\n"); 
                        i--;
                        estado=0;
                    }
                    else if(listaToken.get(i).getLexema().equals(reservada.FIN.name()) && listaToken.get(i-1).getToken().equals(tipoToken.IDENTIFICADOR.name())){
                        System.out.println("La palabra reservada es "+listaToken.get(i-1).getLexema());
                        archivo.write("             "+listaToken.get(i-1).getLexema()+"\n"); 
                        i--;
                        estado=0;
                     }
                     
                     else {
                         listaErrores.add("Error en la fila "+listaToken.get(i-1).getFila()+"    se esperaba la palabra reservada FIN ");
                        System.out.println(" Error se esperaba la palabra reservada FIN ");
                        i--;
                        estado=0;
                     }
                break;
                case 3:
                    if(listaToken.get(i).getToken().equals(tipoToken.ENTERO.name()) || listaToken.get(i).getToken().equals(tipoToken.IDENTIFICADOR.name())){
                        estado=4;
                    }
                    else {
                        this.listaErrores.add("Error en la fila"+listaToken.get(i-1).getFila()+"    no ingreso un numero positvo o un identificador");
                        System.out.println("Error no ingreso un numero positvo o un identificador");
                    }
                    
                break;
                case 4:
                        if(listaToken.get(i).getLexema().equals(reservada.INICIAR.name())){
                           i--;
                            estado=5;
                           
                        }
                        
                        
                        else {
                            this.listaErrores.add("Error en la fila  "+ listaToken.get(i-1).getFila()+"   se esperaba la palabra reservada INICIAR");
                            estado=0;
 
                         System.out.print("error");
                        }
                        
                    
                break;
                case 5:
                    if(listaToken.get(i).getLexema().equals(reservada.ESCRIBIR.name())){
                        estado=6;
                        
                     }
                    else if (listaToken.get(i).getLexema().equals(reservada.FIN.name())){
                         estado=0;
                        }
                    
                    
                break;
                
                case 6:
                    if(listaToken.get(i).getToken().equals(tipoToken.LITERAL.name()) || listaToken.get(i).getToken().equals(tipoToken.ENTERO.name()) || listaToken.get(i).getToken().equals(tipoToken.IDENTIFICADOR.name())){     
                        estado=7;
                    }
                    
                    else {
                         listaErrores.add(" Error en la fila "+listaToken.get(i-1).getFila()+"      se esperaba en identificador,numero o literal");
                        System.out.println("Error sintactico");
                        estado=0;
                    }
                    
                break;
                case 7:
                    if(listaToken.get(i).getLexema().equals(reservada.FIN.name()) && listaToken.get(i-1).getToken().equals(tipoToken.LITERAL.name())){
                        System.out.println("La palabra reservada es "+listaToken.get(i-1).getLexema());  
                        archivo.write("     " +limpiartexto(listaToken.get(i-1).getLexema())+"\n");
                        estado=8;
                    }
                    else if(listaToken.get(i).getLexema().equals(reservada.FIN.name()) && listaToken.get(i-1).getToken().equals(tipoToken.ENTERO.name())){
                        System.out.println("La palabra reservada es "+listaToken.get(i-1).getLexema());
                        archivo.write("         "+listaToken.get(i-1).getLexema()+"\n"); 
                        i--;
                        estado=0;
                    }
                    else if(listaToken.get(i).getLexema().equals(reservada.FIN.name()) && listaToken.get(i-1).getToken().equals(tipoToken.IDENTIFICADOR.name())){
                        System.out.println("La palabra reservada es "+listaToken.get(i-1).getLexema());
                        archivo.write("             "+listaToken.get(i-1).getLexema()+"\n"); 
                        i--;
                        estado=0;
                     }
                     
                     else {
                        listaErrores.add(" Error en la fila "+listaToken.get(i-1).getFila()+"      se esperaba la palabra reservada FIN");
                        System.out.println(" Error se esperaba la palabra reservada FIN ");
                        i--;
                        estado=5;
                     }
                    
                    break;
                    case 8:
                        if(listaToken.get(i).getLexema().equals(reservada.FIN.name()) ){
                             estado=0;
                          }
                          else if(listaToken.get(i).getLexema().equals(reservada.ESCRIBIR.name())){
                           i--;
                           estado=5;
                          }
                          else 
                          {
                              this.listaErrores.add("Error enla fila"+listaToken.get(i-1).getFila()+"  FALTA FIN  DE CIERE ");
                            System.out.println("ERROR FALTA FIN  DE CIERE ");

                            estado=0;
                          }
                    break;
                    case 9:

                        if(listaToken.get(i).getLexema().equals(reservada.VERDADERO.name())){
                           estado=10;
                        }

                     else if (listaToken.get(i).getLexema().equals(reservada.FALSO.name())){

                            estado=10;
                        }


                         else 
                          {
                              this.listaErrores.add("Error enla fila"+listaToken.get(i-1).getFila()+"  FALTA FIN  DE CIERRE ");
                           System.out.println("------ERROR FALTA FIN  DE CIERE ");


                          }
                    break;
                    case 10:
                        if(listaToken.get(i).getLexema().equals(reservada.ENTONCES.name()) && listaToken.get(i-1).getLexema().equals(reservada.VERDADERO.name()) ){
                         estado=5;
                        }
                        else if(listaToken.get(i).getLexema().equals(reservada.ENTONCES.name()) && listaToken.get(i-1).getLexema().equals(reservada.FALSO.name()) ){
                         i++;
                         estado=0;
                        }
                        else {
                            System.out.println("Error");
                        }
                    break;
                    case 11:
                        if(listaToken.get(i).getLexema().equals("=")){
                            estado=12;
                        }
                        else {
                            this.listaErrores.add("ERROR  SINTACTICO SE ESPERABA UN SIGO = ");
                            System.out.println("ERROR  SINTACTICO SE ESPERABA UN SIGO = ");
                            estado=0;
                        }
                                       
                    break;
                    case 12:
                            if(listaToken.get(i).getLexema().equals("(")){
                              estado=13;
                            }
                            else if(listaToken.get(i).getToken().equals(tipoToken.IDENTIFICADOR.name()) || listaToken.get(i).getToken().equals(tipoToken.ENTERO.name())){
                                i--;
                                estado=13;
                            }
                            else {
                                this.listaErrores.add("Error de token");
                            System.out.println("Error de token");
                            }
                        
                    break;

                    case 13:
                            if(listaToken.get(i).getToken().equals(tipoToken.IDENTIFICADOR.name()) || listaToken.get(i).getToken().equals(tipoToken.ENTERO.name())){
                                estado=14;
                            }
                           
                            
                    break;
                    case 14:
                            if(listaToken.get(i).getLexema().equals("+") || listaToken.get(i).getLexema().equals("*")){
                                estado=17;
                            }
                            else 
                          {
                              this.listaErrores.add("Error en la fila "+listaToken.get(i-1).getFila()+" FALTA EL SIGNO + O -");
                           System.out.println("------ERROR FALTA EL SIGNO + O -");
                           
                          }
                    break;
                    case 15:
                             if(listaToken.get(i).getLexema().equals(")")){
                              estado=16;
                            }
                             else  if(listaToken.get(i).getLexema().equals(reservada.FIN.name())){
                              estado=0;
                            }
                           else 
                          {
                              this.listaErrores.add("Error en la fila "+listaToken.get(i-1).getFila()+"  FALTA  EL SIMBOLO )  O FIN ");
                           System.out.println("------ERROR FALTA  EL SIMBOLO )  O FIN ");
                           estado=0;
                          }
                    break;
                    case 16:
                         if(listaToken.get(i).getLexema().equals(reservada.FIN.name())){
                              estado=0;
                            }
                         else 
                          {
                              
                              this.listaErrores.add("Error en la fila "+listaToken.get(i-1).getFila()+" FALTA EL FIN DE CIERRE");
                           System.out.println("------ERROR FALTA FIN  DE CIERRE ");
                           estado=0;
                          }
                    break;
                    case 17:
                        if(listaToken.get(i).getToken().equals(tipoToken.IDENTIFICADOR.name()) || listaToken.get(i).getToken().equals(tipoToken.ENTERO.name())){
                                estado=15;
                            }
                    break;

                    default:
                        throw new AssertionError();
                }   
        }
       archivo.close();
      verificarErrores(table,arch);
       
     }
    
     public String limpiartexto(String texto){
        String palabra="";
        for(int i=0;i<texto.length();i++){
            char letra=texto.charAt(i);
            if(letra!='"'){
              palabra=palabra+letra;
            }
          
         }
        return palabra;
      
    }

    public ArrayList<String> getListaErrores() {
        return listaErrores;
    }
    
    
    public void verificarErrores(JTable table,JFileChooser archivo ){
         ManejadorTabla tabla =new ManejadorTabla();
            if(listaErrores.size()==0){
                JOptionPane.showMessageDialog(null, "Archivo guardado con éxito");
            }
                
              
            else {
                
                try {
                    File fichero = new File(archivo.getSelectedFile().getAbsolutePath());
                    System.out.println(archivo.getSelectedFile());
                    if(fichero.delete()){
                         JOptionPane.showMessageDialog(null, "Existen errores, corrijalos para guardar");
                          tabla.ReporteErrorSintactico(listaErrores, table);
                    }
                } catch (Exception e) {
                }
                 
                 
                
            
            }    
            
    }
    
    
}