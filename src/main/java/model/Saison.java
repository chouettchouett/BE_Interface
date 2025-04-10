/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ember
 */
public class Saison {
    private int numeroSaison;
    private int numeroEpisode;
    private String titre;
    
    public Saison(int numero_saison) {
        this.numeroSaison = numero_saison;
    }

    /**
     * @return the numeroSaison
     */
    public int getNumeroSaison() {
        return numeroSaison;
    }

    @Override
    public String toString() {
        return String.format("S%02d", numeroSaison);
    }
    

    
}
