package acertijo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;

/**
 *
 * @author Duvan
 */
public class Tablero implements ActionListener {
    //1 = Rojo , 2 = verde , 3 = Amarillo , 4 = Azul , 5 = Morado
    private final int[][] respuesta = {{1,1,2,3,3},{1,2,2,4,3},{1,2,4,4,3},{1,2,5,5,3},{2,2,5,3,3}};
    private final int[][] posicionInicial = {{0,1,2,3,0},{0,0,0,4,0},{0,0,4,0,0},{1,0,0,5,0},{2,0,5,3,0}};
    private ArrayList<JButton> botones;
    private final int tamañoBoton = 100;
    private int contadorPistas = 0;
    private VistaPrincipal vista;

    public Tablero(VistaPrincipal view) {
        this.botones = new ArrayList<>();
        this.vista = view;
        crearBotones();
        escucharBotones();
        agregarBotones();
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
    private void crearBotones(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JButton bton = new JButton(" ");
                bton.setBounds(i*tamañoBoton + 50, j*tamañoBoton + 50, tamañoBoton,tamañoBoton);
                bton.setBackground(selecionarColor(j, i));
                bton.setForeground(Color.gray);
                bton.setActionCommand(respuesta[j][i]+"");
                bton.setVisible(true);
                this.botones.add(bton);
            }
        }
    }
    
    private Color selecionarColor(int i,int j){
        Color c = null;
        if(posicionInicial[i][j] == 0){
            c = Color.WHITE;
        }
        else if(posicionInicial[i][j] == 1){
            c = Color.RED;            
        }
        else if(posicionInicial[i][j] == 2){
            c = Color.green;            
        }
        else if(posicionInicial[i][j] == 3){
            c = Color.YELLOW;            
        }
        else if(posicionInicial[i][j] == 4){
            c = Color.BLUE;            
        }
        else if(posicionInicial[i][j] == 5){
            c = Color.magenta;            
        }
        return c;
    }
    
    private Color selecionarColorRespuesta(int i,int j){
        Color c = null;
        if(respuesta[i][j] == 0){
            c = Color.WHITE;
        }
        else if(respuesta[i][j] == 1){
            c = Color.RED;            
        }
        else if(respuesta[i][j] == 2){
            c = Color.green;            
        }
        else if(respuesta[i][j] == 3){
            c = Color.YELLOW;            
        }
        else if(respuesta[i][j] == 4){
            c = Color.BLUE;            
        }
        else if(respuesta[i][j] == 5){
            c = Color.magenta;            
        }
        return c;
    }
    
    private Color cambiarColor(Color btonColor){
        Color c = null;
        if(btonColor == Color.magenta){
            c = Color.WHITE;
        }
        else if(btonColor == Color.WHITE){
            c = Color.RED;            
        }
        else if(btonColor == Color.RED){
            c = Color.green;            
        }
        else if(btonColor == Color.green){
            c = Color.YELLOW;            
        }
        else if(btonColor == Color.YELLOW){
            c = Color.BLUE;            
        }
        else if(btonColor == Color.BLUE){
            c = Color.magenta;            
        }        
        return c;
    }
       
    private void escucharBotones(){
        for (Iterator<JButton> iterator = botones.iterator(); iterator.hasNext();) {
            JButton next = iterator.next();
            next.addActionListener(this);
        }
        vista.jButtonComprobar.addActionListener(this);
    }
    
    private void agregarBotones(){
        for (Iterator<JButton> iterator = botones.iterator(); iterator.hasNext();) {
            JButton next = iterator.next();
            vista.jPanelPrincipal.add(next);
        }
    }
    
    private boolean comprobarResultado(){
        boolean completado = true;
        int k =0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Color a = selecionarColorRespuesta(j, i);
                Color b = botones.get(k).getBackground();
                if(!a.equals(b)){
                    completado = false;
                }
                k++;
            }
        }
        return completado;
    }
    
    private void agregarPista(){
        if(contadorPistas < 25){
            JButton bton = botones.get(contadorPistas);
            String pista = bton.getActionCommand();
            bton.setText(pista);
            contadorPistas++;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.jButtonComprobar){
            if(comprobarResultado()){
                vista.jLabelWin.setText("Ganaste !!!");
            }
            else{
                agregarPista();
            }
        }
        else{
            for (Iterator<JButton> iterator = botones.iterator(); iterator.hasNext();) {
                JButton next = iterator.next();
                if (e.getSource() == next) {
                    next.setBackground(cambiarColor(next.getBackground()));
                }
            }
        }
    }
    
}
