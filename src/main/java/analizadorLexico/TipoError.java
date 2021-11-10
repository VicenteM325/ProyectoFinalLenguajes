
package analizadorLexico;

public class TipoError {
    private String lexema;
    private String TipoError;
    private int fila;
    private int columna;
    
    public TipoError(){
    }
    
    public TipoError(String lexema, String TipoError, int fila, int columna) {
        this.lexema = lexema;
        this.TipoError = TipoError;
        this.fila = fila;
        this.columna = columna;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipoError() {
        return TipoError;
    }

    public void setTipoError(String TipoError) {
        this.TipoError = TipoError;
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
    
    
}