package com.monportfolio.first_db_app_sleep_study;

import org.springframework.data.jpa.repository.JpaRepository; // Importe la classe Spring
import org.springframework.stereotype.Repository; // Importe l'annotation

import java.util.List;

@Repository // 1. Dis à Spring : "Ceci est un composant Repository"
public interface SleepStudyRepository extends JpaRepository<SleepStudy, Long> // 2. L'héritage magique. Long est el type de la cle premier
{
    // --- AJOUTE CECI ---
    // Spring va lire ce nom et comprendre :
    // "SELECT * FROM sleep_study WHERE profession = ?"
    List<SleepStudy> findByOccupation(String occupation);

    // "SELECT * FROM sleep_study WHERE sleepDisorder = ?"
    List<SleepStudy> findBySleepDisorder(String sleepDisorder);
}
