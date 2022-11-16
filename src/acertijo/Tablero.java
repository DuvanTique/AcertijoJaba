package acertijo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JButton;

/**
 *
 * @author DuvanTique
 */
public class Tablero implements ActionListener {
    //0=Blanco , 1 = Rojo , 2 = verde , 3 = Amarillo , 4 = Azul , 5 = Morado
    private final int[][] respuesta = {{1,1,2,3,3},{1,2,2,4,3},{1,2,4,4,3},{1,2,5,5,3},{2,2,5,3,3}};
    private int[][] posicionInicial = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
    private ArrayList<JButton> botones;
    private final int tamañoBoton = 100;
    private int Puntuacion = 100;
    private int contadorPistas = 0;
    private VistaPrincipal vista;
    
    public Tablero(VistaPrincipal view) {
        this.botones = new ArrayList<>();
        this.vista = view;
        generarPosicionInicial();
        crearBotones();
        escucharBotones();
        agregarBotones();
        numerosIniciales();
        vista.jTextFieldPuntuacion.setText(Puntuacion+"");
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
    }
    
    private void crearBotones(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JButton bton = new JButton(" ");
                bton.setBounds(i*tamañoBoton + 50, j*tamañoBoton + 50, tamañoBoton,tamañoBoton);
                bton.setBackground(selecionarColor(j, i));
                bton.setForeground(Color.BLACK);
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
        Random r = new Random();
        if(contadorPistas < 50){
            JButton bton = botones.get(r.nextInt(25));
            String pista = bton.getActionCommand();
            bton.setText(pista);
            contadorPistas++;
            Puntuacion -= 2;
        }
        if(contadorPistas == 10){
            JButton bton = botones.get(3);
            bton.setText("Rojo = 1");
        }
        else if(contadorPistas == 30){
            JButton bton = botones.get(10);
            bton.setText("Verde = 2");            
        }
    }
    
    private void numerosIniciales(){
        Random r = new Random();
            for (int i = 0; i < 8; i++) {
                JButton bton = botones.get(r.nextInt(25));
                String pista = bton.getActionCommand();
                bton.setText(pista);
            }
    }
    
    private void generarPosicionInicial(){
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(r.nextInt(2) <1){
                    posicionInicial[j][i]=respuesta[j][i];
                }
                else{
                    posicionInicial[j][i]=0;                    
                }
            }
        }        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.jButtonComprobar){
            if(comprobarResultado()){
                vista.jLabelWin.setText("Ganaste !!!");
                vista.jButtonComprobar.setVisible(false);
            }
            else{
                agregarPista();
                vista.jTextFieldPuntuacion.setText(""+Puntuacion);
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
