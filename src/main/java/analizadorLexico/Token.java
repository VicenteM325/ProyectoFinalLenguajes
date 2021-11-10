package analizadorLexico;

public class Token {
    private String lexema;
    private String token;
    private int fila;
    private int columna;

    public Token(String lexema, String token, int fila, int columna) {
        this.lexema = lexema;
        this.token = token;
        this.fila = fila;
        this.columna = columna;
    }
    public Token(){
    
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
           
}