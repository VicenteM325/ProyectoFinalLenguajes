
package analizadorLexico;

import analizadorSintactico.AnalizadorSintactico;
import Jframe.VentanaInicio;
import recursos.ManejadorTabla;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Analizador {
    private VentanaInicio ventana;
    private ArrayList <Token> listaToken = new ArrayList<Token>();
    private ArrayList <TipoError> listaErrores = new ArrayList<TipoError>();
    int estado=0;
    String lexema="";
    private PalabraReservadas reservada;
    private TipoToken tipoToken;
    
  
 public Analizador( VentanaInicio ventana){
     this.ventana=ventana;
     
 }
  
  public void analizar(){
    listaToken.clear();
    listaErrores.clear();
    Buscar bs=new Buscar();
    String todoTexto= this.ventana.getjTextArea1().getText()+" ";
    String textolimpio="";
   
   for(int i=0;i<todoTexto.length(); i++){
   
       char letra=todoTexto.charAt(i);
       switch (letra) {
           case '\r' :
           case '\t' :
           case '\b' :
           case '\f' :
               
               break;
           default:
                textolimpio=textolimpio+letra;
       }
       
   }
    int fila=1;
    int columna=0;
   for(int indice=0; indice<textolimpio.length();indice++){
       char letra=textolimpio.charAt(indice);
       char Error;
       switch (estado) {
           case 0:
               if(Character.isLetter(letra) || letra=='_'){
                estado=1;
                lexema=""+letra;
               }
               else if(Character.isDigit(letra) || letra=='-'){
                   estado=2;
                   lexema=""+letra;
               }
               else if(letra==' '){
                   estado=0;
               }
               else if(letra=='\n'){
                   fila++;
                   columna=indice+1;
                   estado=0;
               }
               
               else if (letra=='"') {
                   estado=3;
                   lexema=""+letra;
               }
               
               else if (letra=='/'){
                    estado=4;
                    lexema=""+letra;
               }
               
               else if(letra=='+'|| letra=='-' || letra=='*' || letra=='=' || letra=='(' || letra==')' ){
                   listaToken.add(new Token(String.valueOf(letra),tipoToken.SIMBOLO.name(),fila,indice-columna));
                    estado=0;
               }
               
               else {
               listaErrores.add(new TipoError("No existe en el alfabeto",String.valueOf(letra),fila,indice-columna));
               }
               
               break;
               
           case 1:
               if(Character.isLetter(letra) || Character.isDigit(letra) || letra=='-' || letra=='_' ){
                   estado=1;
                   lexema=lexema+letra;
                  
               }
               else if(letra==' '){
                    
                   agregarToken(lexema,tipoToken.IDENTIFICADOR,fila,indice-columna);
                    
               }
               else if( letra=='\n'){
                  
                    agregarToken(lexema,tipoToken.IDENTIFICADOR,fila,indice-columna);
                    fila++;
                    columna=indice+1;
                   
               }
               else {
                  
                  listaErrores.add(new TipoError("Se esperaba un numero o una letra",lexema+letra,fila,indice-columna));
                  lexema="";
                  estado=0;
                
               }
               break;
               
           case 2:
               if(Character.isDigit(letra)){
                        estado=2;
                        lexema=lexema+letra;
                        
                  }
               
               else if(letra==' '){
                    agregarToken(lexema,tipoToken.ENTERO,fila,indice-columna);
  
               }
               else if(letra=='\n'){
                    agregarToken(lexema,tipoToken.ENTERO,fila,indice-columna);
                    fila++;
                    columna=indice+1;
               }
       
               else{
                 listaErrores.add(new TipoError("Se esperaba un numero",lexema+letra,fila,indice-columna));
                 lexema="";
                  estado=0;
                 
               }
                
               break;
               
           
           case 3:
                String comilla="'";
                if(Character.isLetter(letra) || Character.isLetterOrDigit(letra)  || letra==' ' || letra=='<' || letra=='>'  || letra== ':' || letra==',' || letra==';' || letra=='/'|| letra=='.' || letra==')' || letra=='(' || letra=='=' || letra=='+' || letra=='-'){
                   estado=3;
                   lexema=lexema+letra;
                }
               
                else if(comilla.equals(String.valueOf(letra))){
                    estado=3;
                    lexema=lexema+letra;
                }
            
                else if(letra=='"'){
                    lexema=lexema+letra;
                    estado=6;
                         
                }
            
               else {
                  
                listaErrores.add(new TipoError("no es una literal",lexema+letra,fila,indice-columna));
                  lexema="";
                  estado=0;
                }
           
               break;
               
           case 4:
               if(letra=='/' ){
                   estado=5;
                    lexema=lexema+letra;
                    
                  }
               else {
                    listaErrores.add(new TipoError("Se esperaba  una /",lexema+letra,fila,indice-columna));
                    lexema="";
                    estado=0;
                    
               }
               break;
           case 5:
             if( letra=='\n'){
                 agregarToken(lexema,tipoToken.COMENTARIO,fila,indice-columna);
                 columna=indice+1;
                 
                  }
               else {
                  estado=5;
                  lexema=lexema+letra;
               }
               break;
           case 6:
                if(letra==' '){
                    agregarToken(lexema,tipoToken.LITERAL,fila,indice-columna);
                }
                else if(letra=='\n'){
                     agregarToken(lexema,tipoToken.LITERAL,fila,indice-columna);
                     fila++;
                     columna=indice+1;
                     
                }
                else {
                    listaErrores.add(new TipoError("Se esperaba un espacio",lexema+letra,fila,indice-columna));
                    lexema="";
                }
               break;
               
           default:
               throw new AssertionError();
       }
               
   }
  }
  
  /*  public void contarLexemas(){
        
        for(int i=0; i<listLexema.size() ;i++){
            int cont=0;
            String texto="";
            for(int j=0;j<listLexema.size();j++){
                if(listLexema.get(i).equals(listLexema.get(j))){
                    cont++;
                    texto=listLexema.get(i);
                } 
            }
            agregarlista(texto+" cantidad de vez: "+cont);
        }    
    }
    
   //* public  void agregarlista(String campo){
       if(list.contains(campo)){
       
       }
       else {
           list.add(campo);
       }
        
         
    }
  
    */
  /*  public void repetidos(){
        ventana.getjTextArea2().setText("");
        for(int i=0; i<list.size(); i++){
             System.out.println("------"+list.get(i));
              this.ventana.getjTextArea2().append(list.get(i)+"\n");
        }
    }
    */
    
  
    
    public void palabraReservadas(){
        for(int i=0; i<listaToken.size();i++){
            
            
            if(listaToken.get(i).getLexema().equals(reservada.ESCRIBIR.name())){ 
               listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());
            }
            else if(reservada.FIN.name().equals(listaToken.get(i).getLexema())){
                listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());  
            }
            else if(listaToken.get(i).getLexema().equals(reservada.REPETIR.name())){
                listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());
            }
            else if(listaToken.get(i).getLexema().equals(reservada.INICIAR.name())){
                listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());
            }
            else if(listaToken.get(i).getLexema().equals(reservada.SI.name())){
                listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());
            }
            else if(listaToken.get(i).getLexema().equals(reservada.VERDADERO.name())){
                listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());
            }
            else if(listaToken.get(i).getLexema().equals(reservada.FALSO.name())){
                listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());  
            }
            else if(listaToken.get(i).getLexema().equals(reservada.ENTONCES.name())){
                listaToken.get(i).setToken(tipoToken.PALABRA_RESERVADA.name());  
            }
            
        }
    }
    
    public void imprimirLexemas(){
       // AnalizadorSintactico As=new AnalizadorSintactico();
        ManejadorTabla tabla =new ManejadorTabla();
        if(this.listaErrores.size()==0){
            ventana.getjButton9().setVisible(true);
            tabla.ReporteTokens(listaToken,ventana.getjTable1());
            
        }
        else {
            tabla.ReporteError(listaErrores, ventana.getjTable1());
        }
        
       
              
        
    }
    public void crearArchivo(){
    
        AnalizadorSintactico As=new AnalizadorSintactico();
        int result = JOptionPane.showConfirmDialog(ventana, "Desea guardar el archivo de salida del analizador sintactico .");
        if(result==0){
             JFileChooser archivo = new JFileChooser(System.getProperty("user.dir"));
             archivo.showSaveDialog(ventana);
             
             try {
                As.analizar(listaToken,archivo,ventana.getjTable1());
                
            } catch (IOException ex) {
               
            }
        }
        
    }
    
    
    public void agregarToken(String lexema, TipoToken tipo ,int fila,int indice ){
                    listaToken.add(new Token(lexema,tipo.name(),fila,indice));
                    lexema="";
                    estado=0;
                    indice--;
    }

  
    
    
    
    
    
}

