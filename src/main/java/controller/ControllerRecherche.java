/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import org.python.util.PythonInterpreter;
import org.python.core.PyObject;
import java.util.List;
import org.python.core.*;

import model.Episode;
import model.Personnage;
import model.Saison;

/**
 * Temporaire, quand la liaisons python-java sera implémenté on fera autrement
 * Les données sont seulement des données de tests
 */
public class ControllerRecherche {
   
    public List<Saison> getSaisons() {
        return List.of(new Saison(1), new Saison(2), new Saison(3), new Saison(4), new Saison(5),
                new Saison(6), new Saison(7), new Saison(8), new Saison(9), new Saison(10));
    }
    
    public List<Episode> getEpisodes() {
        return List.of(new Episode(1, 1, "Monica Gets A Roomate"), new Episode(1, 2, "The Sonogram at the End"));
    }
    
    public List<Personnage> getPersonnages() {
        return List.of(new Personnage("Monica"), new Personnage("Ross"), new Personnage("Rachel"), 
                new Personnage("Joey"), new Personnage("Chandler"), new Personnage("Phoebe"));
    }
    
    public List<String> recherchePersonnage(Personnage p) {
        return List.of("x", "xx", "xxx", "xxxx", "xxxxx");
    }
    
    public int getNombreRepliquesEpisode(int saison, int episode) {
        PythonInterpreter py = new PythonInterpreter();
        py.exec("import sys");
        py.exec("sys.path.append('python')"); //Chemin corrigé vers le dossier python
            
        //Ajout de l'encodage UTF 8
        py.exec("# -*- coding: utf-8 -*-");
        py.exec("from stats_saison import compter_repliques_episode");

        //Formatage des paramètres au format "S01" et "E01"
        String saisonStr = String.format("S%02d", saison);
        String episodeStr = String.format("E%02d", episode);
            
        //Passage des paramètres formatés à la fonction Python
        String pythonCode = String.format("compter_repliques_episode('%s', '%s')", saisonStr, episodeStr);
        Object resultat = py.eval(pythonCode);
        return Integer.parseInt(resultat.toString().trim());
    }
    
    public int getNombreRepliquesSaison(int saison) {
        PythonInterpreter py = new PythonInterpreter();
        py.exec("import sys");
        py.exec("sys.path.append('python')"); //Chemin corrigé vers le dossier python
            
        //Ajout de l'encodage UTF 8
        py.exec("# -*- coding: utf-8 -*-");
        py.exec("from stats_saison import compter_repliques_saison");

        //Formatage des paramètres au format "S01"
        String saisonStr = String.format("S%02d", saison);
            
        //Passage des paramètres formatés à la fonction Python
        String pythonCode = String.format("compter_repliques_saison('%s')", saisonStr);
        Object resultat = py.eval(pythonCode);

        return Integer.parseInt(resultat.toString().trim());
           
    }
    public List<String> getPersonnagesSaison(int saison) {
        List<String> personnages = new ArrayList<>();
        PythonInterpreter py = new PythonInterpreter();
        py.exec("import sys");
        py.exec("sys.path.append('python')"); //Chemin corrigé vers le dossier python
        py.exec("# -*- coding: utf-8 -*-");
        py.exec("from stats_saison import get_personnages_saison");

        //Format "S01"
        String saisonStr = String.format("S%02d", saison);

        //Passage de l'argument Python
        py.set("saison_id", saisonStr);

        // Appel de la fonction Python et récupération de la liste
        py.exec("resultats = get_personnages_saison(saison_id)");

        // Conversion Java de la liste Python
        PyObject pyList = py.get("resultats");
        if (pyList != null) {
            for (int i = 0; i < pyList.__len__(); i++) {
                PyObject item = pyList.__getitem__(i);
                if (item != null) {
                    personnages.add(item.toString());
                }
            }
        }
        return personnages;
    }
    
    public List<String> getPersonnagesEpisode(int saison, int episode) {
        List<String> personnages = new ArrayList<>();
        PythonInterpreter py = new PythonInterpreter();
        py.exec("import sys");
        py.exec("sys.path.append('python')"); //Chemin vers le dossier contenant stats_saison.py
        py.exec("# -*- coding: utf-8 -*-");
        py.exec("from stats_saison import get_personnages_episode");

        //Format "S01 E01"
        String saisonStr = String.format("S%02d", saison);
        String episodeStr = String.format("E%02d", episode);
            
        //Passage de l'argument Python
        py.set("saison_id", saisonStr);
        py.set("episode_id", episodeStr);

        //Appel de la fonction Python et récupération de la liste
        py.exec("resultats = get_personnages_episode(saison_id, episode_id)");

        //Conversion Java de la liste Python
        PyObject pyList = py.get("resultats");
        if (pyList != null) {
            for (int i = 0; i < pyList.__len__(); i++) {
                PyObject item = pyList.__getitem__(i);
                if (item != null) {
                    personnages.add(item.toString());
                }
            }
        }
        return personnages;
    }
    
    public List<String> getTopMotsEpisode(int saison, int episode) {
        List<String> mots = new ArrayList<>();
        PythonInterpreter py = new PythonInterpreter();
        py.exec("import sys");
        py.exec("sys.path.append('python')"); //Chemin vers le dossier Python
        py.exec("# -*- coding: utf-8 -*-");
        py.exec("from stats_saison import top_mots_episode");

        String saisonStr = String.format("S%02d", saison);
        String episodeStr = String.format("E%02d", episode);

        py.set("saison_id", saisonStr);
        py.set("episode_id", episodeStr);

        py.exec("resultats = top_mots_episode(saison_id, episode_id)");

        PyObject resultList = py.get("resultats");
        if (resultList != null) {
            for (int i = 0; i < resultList.__len__(); i++) {
                PyObject tuple = resultList.__getitem__(i); 
                String mot = tuple.__getitem__(0).toString();
                mots.add(mot);
            }
        }
        return mots;
    }
    
    public List<String> getTopMotsSaison(int saison) {
        List<String> mots = new ArrayList<>();
        PythonInterpreter py = new PythonInterpreter();
        py.exec("import sys");
        py.exec("sys.path.append('python')");
        py.exec("# -*- coding: utf-8 -*-");
        py.exec("from stats_saison import top_mots_saison");

        String saisonStr = String.format("S%02d", saison);
        py.set("saison_id", saisonStr);

        py.exec("resultats = top_mots_saison(saison_id)");

        PyObject resultList = py.get("resultats");
        if (resultList != null) {
            for (int i = 0; i < resultList.__len__(); i++) {
                PyObject tuple = resultList.__getitem__(i);
                String mot = tuple.__getitem__(0).toString();
                mots.add(mot);
            }
        }
        return mots;
    }
    
    public void creerGrapheNombreDeRepliquesSaison(int saison) {
        PythonInterpreter py = new PythonInterpreter();

        //Préparation de l'environnement Python
        py.exec("import sys");
        py.exec("sys.path.append('python')"); //Adapter si le dossier s'appelle autrement
        py.exec("# -*- coding: utf-8 -*-");

        //Importer le fichier contenant la fonction
        py.exec("from stats_saison import graph_nombre_de_repliques_saison");

        //Formatage de la saison ("S01", "S02", etc.)
        String saisonStr = String.format("S%02d", saison);

        //Appel de la fonction Python avec la saison
        String pythonCode = String.format("graph_nombre_de_repliques_saison('%s')", saisonStr);
        py.exec(pythonCode);
    }
    
    public void creerGrapheNombreDeRepliquesEpisode(int saison, int episode) {
        PythonInterpreter py = new PythonInterpreter();

        //Préparation de l'environnement Python
        py.exec("import sys");
        py.exec("sys.path.append('python')"); //Adapter si le dossier s'appelle autrement
        py.exec("# -*- coding: utf-8 -*-");

        //Importer le fichier contenant la fonction
        py.exec("from stats_saison import graph_nombre_de_repliques_episode");

        // Formatage de la saison ("S01""E01")
        String saisonStr = String.format("S%02d", saison);
        String episodeStr = String.format("E%02d", episode);

        //Appel de la fonction Python avec la saison et l'épisode
        String pythonCode = String.format("graph_nombre_de_repliques_episode('%s', '%s')", saisonStr, episodeStr);
        py.exec(pythonCode);
    }
   
    
    

}
