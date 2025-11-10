package com.monportfolio.first_db_app_sleep_study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController // 1. Dis à Spring : "Ceci est un contrôleur d'API REST"
@RequestMapping("/api/sleep") // 2. Préfixe pour TOUTES les méthodes de cette classe
public class SleepStudyController
{
    @Autowired // 3. Injecte-moi le "cerveau" (le Service)
    private SleepStudyService sleepStudyService;

    /**
     * Endpoint pour GET /api/sleep
     * (Renvoie tous les 374 enregistrements)
     */
    @GetMapping // 4. Mappe les requêtes HTTP GET
    public List<SleepStudy> getAllRecords() {
        return sleepStudyService.getAllRecords();
    }

    @GetMapping("/occupation/{occupation}")
    public List<SleepStudy> getRecordsByOccupation(@PathVariable String occupation) {
        return sleepStudyService.getRecordsByOccupation(occupation);
    }

    @GetMapping("/sleepdisorder/{sleepDisorder}")
    public List<SleepStudy> getRecordsBySleepDisorder(@PathVariable String sleepDisorder) {
        return sleepStudyService.getRecordsBySleepDisorder(sleepDisorder);
    }

    /**
     * Expose la méthode "getRecordsByOccupation" sur l'URL: GET /api/sleep/occupation
     */
    @GetMapping("/occupations") //
    public List<String> getUniqueOccupations(
    ) {
        return sleepStudyService.getUniqueOccupations();
    }

    /**
     * Expose la méthode "getRecordsBySleepDisorder" sur l'URL: GET /api/sleep/sleepdisorder
     */
    @GetMapping("/sleepdisorders") //
    public List<String> getUniqueSleepDisorders(
    ) {
        return sleepStudyService.getUniqueSleepDisorders();
    }

    /**
     * Expose la méthode "getAverageSleepDurationByProfession"
     * sur l'URL: GET /api/sleep/analysis/average-by-occupation"}
     */

    @GetMapping("/analysis/average-by-occupation")
    public Map<String, Double> getAverageSleepDurationByProfession() {
        return sleepStudyService.getAverageSleepDurationByProfession();
    }

    /**
     * Expose la méthode "getAverageSleepDurationByGender"
     * sur l'URL: GET /api/sleep/analysis/average-by-gender"}
     */

    @GetMapping("/analysis/average-by-gender")
    public Map<String, Double> getAverageSleepDurationByGender() {
        return sleepStudyService.getAverageSleepDurationByGender();
    }
}

