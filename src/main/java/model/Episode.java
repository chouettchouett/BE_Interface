/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ember
 */
public class Episode {
    private int numeroSaison;
    private int numeroEpisode;
    private String titre;
    
    public Episode(int numero_saison, int numero_episode, String title) {
        this.numeroSaison = numero_saison;
        this.numeroEpisode = numero_episode;
        this.titre = title;
    }

    /**
     * @return the numeroSaison
     */
    public int getNumeroSaison() {
        return numeroSaison;
    }

    /**
     * @return the numeroEpisode
     */
    public int getNumeroEpisode() {
        return numeroEpisode;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    @Override
    public String toString() {
        return String.format("S%02dE%02d", numeroSaison, numeroEpisode) + " " + titre;
    }
    

    
}
