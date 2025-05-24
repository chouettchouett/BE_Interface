/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.python.util.PythonInterpreter;
import org.python.core.PyObject;
import java.util.List;
import java.util.Map;
import org.python.core.*;

import model.Episode;
import model.Model;
import model.Personnage;
import model.Saison;
import py4j.GatewayServer;

/**
 *
 * @author ember
 */
public class ControllerRecherche {
    private final Model model;
    private PythonProcessor python;
    private static GatewayServer gateway;
    Thread pythonThread;
    Process pythonProcess;
    
    public ControllerRecherche(Model model) {
        this.model = model;
        //gateway = new GatewayServer(this);
        //gateway.start(true);
        
        //launchPython();
    }
    
    public Map<String, Object> rechercheMots(String mots) {
        System.out.println("[controller.test]");
        Map<String, Object> data = new HashMap<>();// = python.rechercheMots(mots);
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "python/recherche/stats_par_ensemble_de_mots.py", mots);
            pb.redirectErrorStream(true); // pour avoir erreurs et sortie stdout ensemble

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Erreur lors de l'exécution du script Python");
                System.exit(exitCode);
            }

            String json = output.toString();

            Gson gson = new Gson();
            data = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public void launchPython() {
        String pythonScriptPath = "python\\gateway_adapter_python_java.py";

        System.out.println("[before thread python run]");
        pythonThread = new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder("py", pythonScriptPath);
                processBuilder.redirectErrorStream(true);
                pythonProcess = processBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = pythonProcess.waitFor();
                System.out.println("Script terminé avec code : " + exitCode);

            } catch (IOException | InterruptedException e) {
                //e.printStackTrace();
            }
        });

        pythonThread.start(); // Lancer Python dans un thread séparé
        System.out.println("[thread python run]");
    }
    
    public void properlyCloseWindow() {
        // Fermer Py4J gateway
        if (gateway != null) {
            System.out.println("Fermeture du gateway...");
            gateway.shutdown();
        }

        // Tuer le processus Python
        if (pythonProcess != null && pythonProcess.isAlive()) {
            System.out.println("Destruction du process Python...");
            pythonProcess.destroy();
        }

        // Arrêter le thread Python
        if (pythonThread != null && pythonThread.isAlive()) {
            System.out.println("Interruption du thread Python...");
            pythonThread.interrupt();
        }
    }
    
    public void register(PythonProcessor p) {
        System.out.println("[register]");
        python = p;
    }
   
    public List<Saison> getSaisons() {
        return List.of(new Saison(1), new Saison(2), new Saison(3), new Saison(4), new Saison(5),
                new Saison(6), new Saison(7), new Saison(8), new Saison(9), new Saison(10));
    }
    
    
    public List<Episode> getEpisodes() {
        return List.of(new Episode(1, 1, "The One Where Monica Gets a Roommate"),
                        new Episode(1, 2, "The One with the Sonogram at the End"),
                        new Episode(1, 3, "The One with the Thumb"),
                        new Episode(1, 4, "The One with George Stephanopoulos"),
                        new Episode(1, 5, "The One with the East German Laundry Detergent"),
                        new Episode(1, 6, "The One with the Butt"),
                        new Episode(1, 7, "The One with the Blackout"),
                        new Episode(1, 8, "The One Where Nana Dies Twice"),
                        new Episode(1, 9, "The One Where Underdog Gets Away"),
                        new Episode(1, 10, "The One with the Monkey"),
                        new Episode(1, 11, "The One with Mrs. Bing"),
                        new Episode(1, 12, "The One with the Dozen Lasagnas"),
                        new Episode(1, 13, "The One with the Boobies"),
                        new Episode(1, 14, "The One with the Candy Hearts"),
                        new Episode(1, 15, "The One with the Stoned Guy"),
                        new Episode(1, 16, "The One with Two Parts: Part 1"),
                        new Episode(1, 17, "The One with Two Parts: Part 2"),
                        new Episode(1, 18, "The One with All the Poker"),
                        new Episode(1, 19, "The One Where the Monkey Gets Away"),
                        new Episode(1, 20, "The One with the Evil Orthodontist"),
                        new Episode(1, 21, "The One with the Fake Monica"),
                        new Episode(1, 22, "The One with the Ick Factor"),
                        new Episode(1, 23, "The One with the Birth"),
                        new Episode(1, 24, "The One Where Rachel Finds Out"),
                        new Episode(2, 1, "The One with Ross's New Girlfriend"),
                        new Episode(2, 2, "The One with the Breast Milk"),
                        new Episode(2, 3, "The One Where Heckles Dies"),
                        new Episode(2, 4, "The One with Phoebe's Husband"),
                        new Episode(2, 5, "The One with Five Steaks and an Eggplant"),
                        new Episode(2, 6, "The One with the Baby on the Bus"),
                        new Episode(2, 7, "The One Where Ross Finds Out"),
                        new Episode(2, 8, "The One with the List"),
                        new Episode(2, 9, "The One with Phoebe's Dad"),
                        new Episode(2, 10, "The One with Russ"),
                        new Episode(2, 11, "The One with the Lesbian Wedding"),
                        new Episode(2, 12, "The One After the Superbowl, Part 1"),
                        new Episode(2, 13, "The One After the Superbowl, Part 2"),
                        new Episode(2, 14, "The One with the Prom Video"),
                        new Episode(2, 15, "The One Where Ross and Rachel... You Know"),
                        new Episode(2, 16, "The One Where Joey Moves Out"),
                        new Episode(2, 17, "The One Where Eddie Moves In"),
                        new Episode(2, 18, "The One Where Dr. Ramoray Dies"),
                        new Episode(2, 19, "The One Where Eddie Won't Go"),
                        new Episode(2, 20, "The One Where Old Yeller Dies"),
                        new Episode(2, 21, "The One with the Bullies"),
                        new Episode(2, 22, "The One with the Two Parties"),
                        new Episode(2, 23, "The One with the Chicken Pox"),
                        new Episode(2, 24, "The One with Barry and Mindy's Wedding"),
                        new Episode(3, 1, "The One with the Princess Leia Fantasy"),
                        new Episode(3, 2, "The One Where No One's Ready"),
                        new Episode(3, 3, "The One with the Jam"),
                        new Episode(3, 4, "The One with the Metaphorical Tunnel"),
                        new Episode(3, 5, "The One with Frank Jr."),
                        new Episode(3, 6, "The One with the Flashback"),
                        new Episode(3, 7, "The One with the Race Car Bed"),
                        new Episode(3, 8, "The One with the Giant Poking Device"),
                        new Episode(3, 9, "The One with the Football"),
                        new Episode(3, 10, "The One Where Rachel Quits"),
                        new Episode(3, 11, "The One Where Chandler Can't Remember Which Sister"),
                        new Episode(3, 12, "The One with All the Jealousy"),
                        new Episode(3, 13, "The One Where Monica and Richard Are Just Friends"),
                        new Episode(3, 14, "The One with Phoebe's Ex-Partner"),
                        new Episode(3, 15, "The One Where Ross and Rachel Take a Break"),
                        new Episode(3, 16, "The One the Morning After"),
                        new Episode(3, 17, "The One Without the Ski Trip"),
                        new Episode(3, 18, "The One with the Hypnosis Tape"),
                        new Episode(3, 19, "The One with the Tiny T-Shirt"),
                        new Episode(3, 20, "The One with the Dollhouse"),
                        new Episode(3, 21, "The One with a Chick and a Duck"),
                        new Episode(3, 22, "The One with the Screamer"),
                        new Episode(3, 23, "The One with Ross's Thing"),
                        new Episode(3, 24, "The One with the Ultimate Fighting Champion"),
                        new Episode(3, 25, "The One at the Beach"),
                        new Episode(4, 1, "The One with the Jellyfish"),
                        new Episode(4, 2, "The One with the Cat"),
                        new Episode(4, 3, "The One with the 'Cuffs"),
                        new Episode(4, 4, "The One with the Ballroom Dancing"),
                        new Episode(4, 5, "The One with Joey's New Girlfriend"),
                        new Episode(4, 6, "The One with the Dirty Girl"),
                        new Episode(4, 7, "The One Where Chandler Crosses the Line"),
                        new Episode(4, 8, "The One with Chandler in a Box"),
                        new Episode(4, 9, "The One Where They're Going to Party!"),
                        new Episode(4, 10, "The One with the Girl from Poughkeepsie"),
                        new Episode(4, 11, "The One with Phoebe's Uterus"),
                        new Episode(4, 12, "The One with the Embryos"),
                        new Episode(4, 13, "The One with Rachel's Crush"),
                        new Episode(4, 14, "The One with Joey's Dirty Day"),
                        new Episode(4, 15, "The One with All the Rugby"),
                        new Episode(4, 16, "The One with the Fake Party"),
                        new Episode(4, 17, "The One with the Free Porn"),
                        new Episode(4, 18, "The One with Rachel's New Dress"),
                        new Episode(4, 19, "The One with All the Haste"),
                        new Episode(4, 20, "The One with All the Wedding Dresses"),
                        new Episode(4, 21, "The One with the Invitation"),
                        new Episode(4, 22, "The One with the Worst Best Man Ever"),
                        new Episode(4, 23, "The One with Ross's Wedding: Part 1"),
                        new Episode(4, 24, "The One with Ross's Wedding: Part 2"),
                        new Episode(5, 1, "The One After Ross Says Rachel"),
                        new Episode(5, 2, "The One with All the Kissing"),
                        new Episode(5, 3, "The One Hundredth"),
                        new Episode(5, 4, "The One Where Phoebe Hates PBS"),
                        new Episode(5, 5, "The One with the Kips"),
                        new Episode(5, 6, "The One with the Yeti"),
                        new Episode(5, 7, "The One Where Ross Moves In"),
                        new Episode(5, 8, "The One with the Thanksgiving Flashbacks"),
                        new Episode(5, 9, "The One with Ross's Sandwich"),
                        new Episode(5, 10, "The One with the Inappropriate Sister"),
                        new Episode(5, 11, "The One with All the Resolutions"),
                        new Episode(5, 12, "The One with Chandler's Work Laugh"),
                        new Episode(5, 13, "The One with Joey's Bag"),
                        new Episode(5, 14, "The One Where Everybody Finds Out"),
                        new Episode(5, 15, "The One with the Girl Who Hits Joey"),
                        new Episode(5, 16, "The One with the Cop"),
                        new Episode(5, 17, "The One with Rachel's Inadvertent Kiss"),
                        new Episode(5, 18, "The One Where Rachel Smokes"),
                        new Episode(5, 19, "The One Where Ross Can't Flirt"),
                        new Episode(5, 20, "The One with the Ride-Along"),
                        new Episode(5, 21, "The One with the Ball"),
                        new Episode(5, 22, "The One with Joey's Big Break"),
                        new Episode(5, 23, "The One in Vegas: Part 1"),
                        new Episode(5, 24, "The One in Vegas: Part 2"),
                        new Episode(6, 1, "The One After Vegas"),
                        new Episode(6, 2, "The One Where Ross Hugs Rachel"),
                        new Episode(6, 3, "The One with Ross's Denial"),
                        new Episode(6, 4, "The One Where Joey Loses His Insurance"),
                        new Episode(6, 5, "The One with Joey's Porsche"),
                        new Episode(6, 6, "The One on the Last Night"),
                        new Episode(6, 7, "The One Where Phoebe Runs"),
                        new Episode(6, 8, "The One with Ross's Teeth"),
                        new Episode(6, 9, "The One Where Ross Got High"),
                        new Episode(6, 10, "The One with the Routine"),
                        new Episode(6, 11, "The One with the Apothecary Table"),
                        new Episode(6, 12, "The One with the Joke"),
                        new Episode(6, 13, "The One with Rachel's Sister"),
                        new Episode(6, 14, "The One Where Chandler Can't Cry"),
                        new Episode(6, 15, "The One That Could Have Been: Part 1"),
                        new Episode(6, 16, "The One That Could Have Been: Part 2"),
                        new Episode(6, 17, "The One with Unagi"),
                        new Episode(6, 18, "The One Where Ross Dates a Student"),
                        new Episode(6, 19, "The One with Joey's Fridge"),
                        new Episode(6, 20, "The One with Mac and C.H.E.E.S.E."),
                        new Episode(6, 21, "The One Where Ross Meets Elizabeth's Dad"),
                        new Episode(6, 22, "The One Where Paul's the Man"),
                        new Episode(6, 23, "The One with the Ring"),
                        new Episode(6, 24, "The One with the Proposal: Part 1"),
                        new Episode(6, 25, "The One with the Proposal: Part 2"),
                        new Episode(7, 1, "The One with Monica's Thunder"),
                        new Episode(7, 2, "The One with Rachel's Book"),
                        new Episode(7, 3, "The One with Phoebe's Cookies"),
                        new Episode(7, 4, "The One with Rachel's Assistant"),
                        new Episode(7, 5, "The One with the Engagement Picture"),
                        new Episode(7, 6, "The One with the Nap Partners"),
                        new Episode(7, 7, "The One with Ross's Library Book"),
                        new Episode(7, 8, "The One Where Chandler Doesn't Like Dogs"),
                        new Episode(7, 9, "The One with All the Candy"),
                        new Episode(7, 10, "The One with the Holiday Armadillo"),
                        new Episode(7, 11, "The One with All the Cheesecakes"),
                        new Episode(7, 12, "The One Where They're Up All Night"),
                        new Episode(7, 13, "The One Where Rosita Dies"),
                        new Episode(7, 14, "The One Where They All Turn Thirty"),
                        new Episode(7, 15, "The One with Joey's New Brain"),
                        new Episode(7, 16, "The One with the Truth About London"),
                        new Episode(7, 17, "The One with the Cheap Wedding Dress"),
                        new Episode(7, 18, "The One with Joey's Award"),
                        new Episode(7, 19, "The One with Ross and Monica's Cousin"),
                        new Episode(7, 20, "The One with Rachel's Big Kiss"),
                        new Episode(7, 21, "The One with the Vows"),
                        new Episode(7, 22, "The One with Chandler's Dad"),
                        new Episode(7, 23, "The One with Monica and Chandler's Wedding: Part 1"),
                        new Episode(7, 24, "The One with Monica and Chandler's Wedding: Part 2"),
                        new Episode(8, 1, "The One After 'I Do'"),
                        new Episode(8, 2, "The One with the Red Sweater"),
                        new Episode(8, 3, "The One Where Rachel Tells..."),
                        new Episode(8, 4, "The One with the Videotape"),
                        new Episode(8, 5, "The One with Rachel's Date"),
                        new Episode(8, 6, "The One with the Halloween Party"),
                        new Episode(8, 7, "The One with the Stain"),
                        new Episode(8, 8, "The One with the Stripper"),
                        new Episode(8, 9, "The One with the Rumor"),
                        new Episode(8, 10, "The One with Monica's Boots"),
                        new Episode(8, 11, "The One with Ross's Step Forward"),
                        new Episode(8, 12, "The One Where Joey Dates Rachel"),
                        new Episode(8, 13, "The One Where Chandler Takes a Bath"),
                        new Episode(8, 14, "The One with the Secret Closet"),
                        new Episode(8, 15, "The One with the Birthing Video"),
                        new Episode(8, 16, "The One Where Joey Tells Rachel"),
                        new Episode(8, 17, "The One with the Tea Leaves"),
                        new Episode(8, 18, "The One in Massapequa"),
                        new Episode(8, 19, "The One with Joey's Interview"),
                        new Episode(8, 20, "The One with the Baby Shower"),
                        new Episode(8, 21, "The One with the Cooking Class"),
                        new Episode(8, 22, "The One Where Rachel is Late"),
                        new Episode(8, 23, "The One Where Rachel Has a Baby: Part 1"),
                        new Episode(8, 24, "The One Where Rachel Has a Baby: Part 2"),
                        new Episode(9, 1, "The One Where No One Proposes"),
                        new Episode(9, 2, "The One Where Emma Cries"),
                        new Episode(9, 3, "The One with the Pediatrician"),
                        new Episode(9, 4, "The One with the Sharks"),
                        new Episode(9, 5, "The One with Phoebe's Birthday Dinner"),
                        new Episode(9, 6, "The One with the Male Nanny"),
                        new Episode(9, 7, "The One with Ross's Inappropriate Song"),
                        new Episode(9, 8, "The One with Rachel's Other Sister"),
                        new Episode(9, 9, "The One with Rachel's Phone Number"),
                        new Episode(9, 10, "The One with Christmas in Tulsa"),
                        new Episode(9, 11, "The One Where Rachel Goes Back to Work"),
                        new Episode(9, 12, "The One with Phoebe's Rats"),
                        new Episode(9, 13, "The One Where Monica Sings"),
                        new Episode(9, 14, "The One with the Blind Dates"),
                        new Episode(9, 15, "The One with the Mugging"),
                        new Episode(9, 16, "The One with the Boob Job"),
                        new Episode(9, 17, "The One with the Memorial Service"),
                        new Episode(9, 18, "The One with the Lottery"),
                        new Episode(9, 19, "The One with Rachel's Dream"),
                        new Episode(9, 20, "The One with the Soap Opera Party"),
                        new Episode(9, 21, "The One with the Fertility Test"),
                        new Episode(9, 22, "The One with the Donor"),
                        new Episode(9, 23, "The One in Barbados: Part 1"),
                        new Episode(9, 24, "The One in Barbados: Part 2"),
                        new Episode(10, 1, "The One After Joey and Rachel Kiss"),
                        new Episode(10, 2, "The One Where Ross is Fine"),
                        new Episode(10, 3, "The One with Ross's Tan"),
                        new Episode(10, 4, "The One with the Cake"),
                        new Episode(10, 5, "The One Where Rachel's Sister Babysits"),
                        new Episode(10, 6, "The One with Ross's Grant"),
                        new Episode(10, 7, "The One with the Home Study"),
                        new Episode(10, 8, "The One with the Late Thanksgiving"),
                        new Episode(10, 9, "The One with the Birth Mother"),
                        new Episode(10, 10, "The One Where Chandler Gets Caught"),
                        new Episode(10, 11, "The One Where the Stripper Cries"),
                        new Episode(10, 12, "The One with Phoebe's Wedding"),
                        new Episode(10, 13, "The One Where Joey Speaks French"),
                        new Episode(10, 14, "The One with Princess Consuela"),
                        new Episode(10, 15, "The One Where Estelle Dies"),
                        new Episode(10, 16, "The One with Rachel's Going Away Party"),
                        new Episode(10, 17, "The Last One: Part 1"),
                        new Episode(10, 18, "The Last One: Part 2"));
    }
   
    public List<Episode> getEpisodesSaison(int saison){
        List<Episode> listeEpisodes = new ArrayList<>();
        for(Episode episode : getEpisodes()){
            if (episode.getNumeroSaison() == saison){
                listeEpisodes.add(episode);
            }
        }
        return listeEpisodes;
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
        py.exec("sys.path.append('src/main/python')"); //Chemin corrigé vers le dossier python
            
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
        py.exec("sys.path.append('src/main/python')"); //Chemin corrigé vers le dossier python
            
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
        py.exec("sys.path.append('src/main/python')"); //Chemin corrigé vers le dossier python
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
        py.exec("sys.path.append('src/main/python')"); //Chemin vers le dossier contenant stats_saison.py
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
        py.exec("sys.path.append('src/main/python')"); //Chemin vers le dossier Python
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
        py.exec("sys.path.append('src/main/python')");
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
        py.exec("sys.path.append('src/main/python')"); //Adapter si le dossier s'appelle autrement
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
        py.exec("sys.path.append('src/main/python')"); //Adapter si le dossier s'appelle autrement
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
   
    public int getNombreMotRepliqueEpisode(int saison, int episode) {
        PythonInterpreter py = new PythonInterpreter();

        py.exec("import sys");
        py.exec("sys.path.append('src/main/python')");
        py.exec("from stats_saison import nombre_mot_replique_episode");

        String saisonStr = String.valueOf(saison);
        String episodeStr = String.valueOf(episode);

        PyObject result = py.eval(String.format("nombre_mot_replique_episode('%s', '%s')", saisonStr, episodeStr));

        //Conversion en int (arrondi standard)
        return (int) Math.round(result.asDouble());
    }
    
    public int getNombreMotRepliqueSaison(int saison) {
        PythonInterpreter py = new PythonInterpreter();

        py.exec("import sys");
        py.exec("sys.path.append('src/main/python')");
        py.exec("from stats_saison import nombre_mot_replique_saison");

        String saisonStr = String.valueOf(saison);

        PyObject result = py.eval(String.format("nombre_mot_replique_saison('%s')", saisonStr));
        
       
        return (int) Math.round(result.asDouble());
    }
    
    public void creerGrapheEvolutionNombreMotEpisode(int saison, int episode) {
        PythonInterpreter py = new PythonInterpreter();

        //Préparation de l'environnement Python
        py.exec("import sys");
        py.exec("sys.path.append('src/main/python')"); //Adapter si le dossier s'appelle autrement
        py.exec("# -*- coding: utf-8 -*-");

        //Importer le fichier contenant la fonction
        py.exec("from stats_saison import graph_evolution_mots_par_replique_episode");

        // Formatage de la saison ("S01""E01")
        String saisonStr = String.format("S%02d", saison);
        String episodeStr = String.format("E%02d", episode);

        //Appel de la fonction Python avec la saison et l'épisode
        String pythonCode = String.format("graph_evolution_mots_par_replique_episode('%s', '%s')", saisonStr, episodeStr);
        py.exec(pythonCode);
    }
    
    public void creerGrapheEvolutionNombreMotSaison(int saison) {
        PythonInterpreter py = new PythonInterpreter();

        //Préparation de l'environnement Python
        py.exec("import sys");
        py.exec("sys.path.append('src/main/python')"); //Adapter si le dossier s'appelle autrement
        py.exec("# -*- coding: utf-8 -*-");

        //Importer le fichier contenant la fonction
        py.exec("from stats_saison import graph_evolution_mots_par_replique_saison");

        //Formatage de la saison ("S01", "S02", etc.)
        String saisonStr = String.format("S%02d", saison);

        //Appel de la fonction Python avec la saison
        String pythonCode = String.format("graph_evolution_mots_par_replique_saison('%s')", saisonStr);
        py.exec(pythonCode);
    }
    

}
