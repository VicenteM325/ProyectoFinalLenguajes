package analizadorLexico;

import javax.swing.JTextArea;

public class GeneradorAFD {
    private JTextArea text;
    private JTextArea textarea;
    String palabra;
    int posicion = 0;
    int contErrores=0;
    int matriz[][] = new int[10][7];
    int estadosFinalizacion[] = new int[8];
    String descripcionFinalizacion[] = new String[8];
    int estadoActual = 0;

    // filas s1 --> 1 s2 -> 2
    // \digito --> 1
    // . --> 2
    // no pertenece a mi alfabeto -1
    {
           
        matriz[0][0] = 1;  matriz[0][1] = -1;   matriz[0][2] = 4;     matriz[0][3] = 6;   matriz[0][4] = 7;     matriz[0][5] = 8;       matriz[0][6] = 9;
        
        matriz[1][0] = 1;  matriz[1][1] = 2;    matriz[1][2] = -1;    matriz[1][3] = -1;  matriz[1][4] = -1;    matriz[1][5] = -1;      matriz[1][6] = 0;  
        
        matriz[2][0] = 3;  matriz[2][1] = -1;   matriz[2][2] = -1;    matriz[2][3] = -1;  matriz[2][4] = -1;    matriz[2][5] = -1;      matriz[2][6] = 0;
        
        matriz[3][0] = 3;  matriz[3][1] = -1;   matriz[3][2] = -1;    matriz[3][3] = -1;  matriz[3][4] = -1;    matriz[3][5] = -1;      matriz[3][6] = 0;
            
        matriz[4][0] = 5;  matriz[4][1] = -1;   matriz[4][2] = 4;     matriz[4][3] = -1;  matriz[4][4] = -1;    matriz[4][5] = -1;      matriz[4][6] = 0;
       
        matriz[5][0] = 5;  matriz[5][1] = -1;   matriz[5][2] = 4;     matriz[5][3] = -1;  matriz[5][4] = -1;    matriz[5][5] = -1;      matriz[5][6] = 0;
         
        matriz[6][0] = -1; matriz[6][1] = -1;   matriz[6][2] = -1;    matriz[6][3] = -1;  matriz[6][4] = -1;    matriz[6][5] = -1;      matriz[6][6] = 0;
        
        matriz[7][0] = -1; matriz[7][1] = -1;   matriz[7][2] = -1;    matriz[7][3] = -1;  matriz[7][4] = -1;    matriz[7][5] = -1;      matriz[7][6] = 0;

        matriz[8][0] = -1; matriz[8][1] = -1;   matriz[8][2] = -1;    matriz[8][3] = -1;  matriz[8][4] = -1;    matriz[8][5] = -1;      matriz[8][6] = 0;
       
        matriz[9][0] = -1; matriz[9][1] = -1;   matriz[9][2] = -1;    matriz[9][3] = -1;  matriz[9][4] = -1;    matriz[9][5] = -1;      matriz[9][6] = -1;

        //numero entero
        estadosFinalizacion[0]=1;
        descripcionFinalizacion[0]="Numero entero";
        //numero flotante
        estadosFinalizacion[1]=3;
        descripcionFinalizacion[1]="Numero double";
        // letra
        estadosFinalizacion[2]=4;
        descripcionFinalizacion[2]="Identificador";
        // numero
        estadosFinalizacion[3]=5;
        descripcionFinalizacion[3]="Identificar";
          // operador
        estadosFinalizacion[4]=6;
        descripcionFinalizacion[4]="operador";
          // signo de agrupacion
        estadosFinalizacion[5]=7;
        descripcionFinalizacion[5]="signo de agrupacion";
           // signo de puntacion
        estadosFinalizacion[6]=8;
        descripcionFinalizacion[6]="signo de puntacion";
        // espacios y todo
        estadosFinalizacion[7]=9;
        descripcionFinalizacion[7]="espacios";
       

    }
    
    public GeneradorAFD(JTextArea text,JTextArea tokenstext){
        this.text=text;
        this.textarea =tokenstext;
    
    }

    public void Main(String p) {
        palabra = p;

        while (posicion < palabra.length()) 
            getToken();
        textarea.setEditable(false);
        return;

       
    }

    //revisa movimiento den la matriz
    public int getSiguienteEstado(int estadoActual, int caracter) {
        int resultado = -1;
        try {
             if (caracter >= 0 && caracter <= 6) {
            resultado = matriz[estadoActual][caracter];
            
        }
        } catch (Exception e) {
           //resultado = matriz[estadoActual][caracter]=0;
        }
       
         
        
        return resultado;
    }


    //alfabeto
    public int getIntCaracter(char caracter) {
        int resultado = -1;

           if (Character.isDigit(caracter)) {
            resultado = 0;
        } 
        else {
            if (caracter == '.'){
                resultado = 1;
            }
            else if(Character.isLetter(caracter))
            {
                resultado=2;
            }
            else if (caracter=='+' | caracter=='-' | caracter=='*' | caracter=='/' | caracter=='%' ){
              
                resultado=3;
            }
            else if (caracter=='(' | caracter==')' | caracter=='[' | caracter==']' | caracter=='{' | caracter==']')
            {
                resultado =4;
            }
            else if(  caracter==',' | caracter==';' | caracter==':' ){
                resultado=5;
            }
            else if(caracter==' '| caracter=='\n' | caracter=='\r' | caracter=='\t' | caracter=='\b' | caracter=='\f'){
                resultado=6;
            }
        }

        return resultado;
    }

    public String getEstadoAceptacion(int i){
        String res = "Error";
        int indice = 0;
        for (int estadoAceptacion : estadosFinalizacion) {
            try {
                if (estadoAceptacion == i){
                
                res = descripcionFinalizacion[indice];
                break;
            }
            } catch (Exception e) {
            }
            
            indice++;
        }
      
        return res;
    }

    public void getToken() {
        estadoActual = 0;
        boolean seguirLeyendo = true;
        char tmp;
        String token = "";
            
        while ((seguirLeyendo) && (posicion < palabra.length())) {
            tmp = palabra.charAt(posicion);            
            if ( tmp==' ' |tmp=='\n' | tmp=='\r' | tmp=='\t' | tmp=='\b' | tmp=='\f' ) {
                seguirLeyendo = false;
                 
  
            } else {
                // para mi automata
                int estadoTemporal = getSiguienteEstado(estadoActual, getIntCaracter(tmp));   
                System.out.println("Estado actual " + estadoActual + " caracter "+ tmp + " transicion a "+estadoTemporal);
                this.textarea.append("Estado actual " + estadoActual + " caracter es------> "+ tmp+" <-----   transicion a "+estadoTemporal+"\n");
                token+=tmp;
                 estadoActual= estadoTemporal;
                System.out.println(tmp);
            }
            posicion++;
        }
       
        
    }
}