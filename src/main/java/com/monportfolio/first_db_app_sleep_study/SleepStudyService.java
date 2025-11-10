package com.monportfolio.first_db_app_sleep_study;

import org.springframework.beans.factory.annotation.Autowired; // Importe l'injection
import org.springframework.stereotype.Service; // Importe l'annotation Service
import java.util.List; // On aura besoin de listes
import java.util.Map;
import java.util.stream.Collectors;

@Service // 1. Dis à Spring : "Ceci est un composant Service (la logique métier)
public class SleepStudyService
{
    @Autowired // 2. Dis à Spring : "Injecte-moi le Repository"
    private SleepStudyRepository sleepStudyRepository;

    // --- C'est ici qu'on va recréer la logique du projet ---

    // Charge les données initiales dans la BDD.
    public List<SleepStudy> getAllRecords() {
        // 3. On utilise la méthode 'findAll()' héritée de JpaRepository
        return sleepStudyRepository.findAll();

    }

    // Toutes les possibles methodes...

    // 1
    public List<SleepStudy> getRecordsByOccupation(String occupation) {
        return sleepStudyRepository.findByOccupation(occupation);
    }

    // 2

    public List<SleepStudy> getRecordsBySleepDisorder(String sleepDisorder) {
        return sleepStudyRepository.findBySleepDisorder(sleepDisorder);
    }

    // 3

    public Map<String, Double> getAverageSleepDurationByProfession() {

        // 1. On prend toutes les données
        List<SleepStudy> allRecords = sleepStudyRepository.findAll();

        // 2. On utilise les Streams Java pour l'analyse
        return allRecords.stream() // Commence le "pipeline" de stream
            .collect(Collectors.groupingBy( // Regroupe-les...
                SleepStudy::getOccupation, // ...par profession
                Collectors.averagingDouble(SleepStudy::getSleepDuration) // ...et calcule la moyenne de la durée de sommeil
            ));
    }

    //4

    public Map<String, Double> getAverageSleepDurationByGender() {

        // 1. On prend toutes les données
        List<SleepStudy> allRecords = sleepStudyRepository.findAll();

        // 2. On utilise les Streams Java pour l'analyse
        return allRecords.stream() // Commence le "pipeline" de stream
            .collect(Collectors.groupingBy( // Regroupe-les...
                SleepStudy::getGender, // ...par sexe
                Collectors.averagingDouble(SleepStudy::getSleepDuration) // ...et calcule la moyenne de la durée de sommeil
            ));
    }

    /**
     * Renvoie la liste de toutes les professions uniques.
     */
    public List<String> getUniqueOccupations() {
        return sleepStudyRepository.findAll().stream() // Prend les 374 enregistrements
            .map(SleepStudy::getOccupation) // Ne garde que la colonne "occupation"
            .distinct() // Ne garde que les valeurs UNIQUES
            .sorted() // Trie-les par ordre alphabétique
            .collect(Collectors.toList()); // Rassemble-les dans une liste
    }

    /**
     * Renvoie la liste de tous les troubles du sommeil uniques.
     */
    public List<String> getUniqueSleepDisorders() {
        return sleepStudyRepository.findAll().stream()
            .map(SleepStudy::getSleepDisorder)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }


}
