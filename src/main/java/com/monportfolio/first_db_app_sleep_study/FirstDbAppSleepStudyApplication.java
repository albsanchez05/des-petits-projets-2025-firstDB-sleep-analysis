package com.monportfolio.first_db_app_sleep_study;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// Imports nécessaires
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@SpringBootApplication
public class FirstDbAppSleepStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstDbAppSleepStudyApplication.class, args);
	}

    // On dit à Spring de créer un "Bean" (un composant) de type CommandLineRunner
    @Bean
    public CommandLineRunner loadData(SleepStudyRepository repository)
    {
        // Spring va automatiquement nous injecter le Repository qu'on a créé

        return ( args ) -> {
            // 1. On vide la base pour être propre
            repository.deleteAll();
            System.out.println("--- BASE DE DONNÉES VIDÉE ---");

            // 2. On trouve notre fichier CSV dans les "resources"
            // C'est la façon "Spring" de trouver un fichier
            ClassPathResource resource = new ClassPathResource("data/sleep_data.csv");

            // 3. On utilise OpenCSV pour lire le fichier et le mapper
            try (Reader reader = new InputStreamReader(resource.getInputStream())) {

                // C'est la magie d'OpenCSV
                List<SleepStudy> sleepRecords = new CsvToBeanBuilder<SleepStudy>(reader)
                    .withType(SleepStudy.class) // La classe cible
                    .withIgnoreLeadingWhiteSpace(true) // Nettoyer les espaces
                    .build()
                    .parse(); // Convertit tout le fichier en une List<SleepStudy>

                // 4. On sauvegarde TOUTE LA LISTE d'un coup !
                // C'est 100x plus rapide que de faire une boucle.
                repository.saveAll(sleepRecords);

                System.out.println("--- DONNÉES DU CSV CHARGÉES ---");
                System.out.println("Nombre d'enregistrements en base: " + repository.count());

            } catch (Exception e) {
                System.err.println("ERREUR LORS DU CHARGEMENT CSV: " + e.getMessage());
                e.printStackTrace();
            }

        };
    }
}
