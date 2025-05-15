/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

//Class pour lire les donnés csv

public class CsvUtils {
    private static CsvUtils instance = null;
    private static final String CSV_PATH = "/csv/csv_utils.csv";//Fichier a lire
    private final Map<String, Map<String, Map<String, List<String>>>> dataMap = new HashMap<>();

    public CsvUtils() {
        System.out.println("CsvUtils CONSTRUCTOR");
        loadCSV();
    }
    public static CsvUtils getInstance() {
        if (instance == null) {
            instance = new CsvUtils();
        }
        return instance;
    }

    private void loadCSV() {
        try (InputStream is = getClass().getResourceAsStream(CSV_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line = reader.readLine(); //on lit pas la premiere
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4); // type, season, episode, top10
                if (parts.length < 4) continue;
                String type = parts[0].trim();      
                String saison = parts[1].trim();
                String episode = parts[2].trim();
                List<String> valeurs = Arrays.asList(parts[3].split(";"));

                dataMap
                    .computeIfAbsent(type, t -> new HashMap<>())
                    .computeIfAbsent(saison, s -> new HashMap<>())
                    .put(episode, valeurs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lecture " + CSV_PATH + " : " + e.getMessage());
        }
    }

    // on cherche dans le fichier csv
    public List<String> getSaisonStats(String saison) {
        
        // Pour l'instant, episode == "" pour les lignes "SAISON"
        return dataMap.getOrDefault("SAISON", Collections.emptyMap())
                .getOrDefault(saison, Collections.emptyMap())
                .getOrDefault("", Collections.emptyList());
    }

    public List<String> getEpisodeStats(String saison, String episode) {
        return dataMap.getOrDefault("EPISODE", Collections.emptyMap())
                .getOrDefault(saison, Collections.emptyMap())
                .getOrDefault(episode, Collections.emptyList());
    }
    
}