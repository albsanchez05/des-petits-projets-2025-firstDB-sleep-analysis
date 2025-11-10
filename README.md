# API d'Analyse de Données de Sommeil (Spring Boot)

Ce projet est une API REST full-stack construite avec Java et Spring Boot. L'objectif était de prendre un jeu de données public: Sleep Health and Lifestyle Dataset (Source: Kaggle) , de le charger dans une base de données, et de créer des endpoints d'analyse pour exposer ces données.

Le frontend est une interface simple (HTML/CSS/JS) qui consomme cette API.


## 🛠️ Stack Technique

* **Backend:**
    * Java 17
    * Spring Boot 3
    * Spring Data JPA (pour parler à la BDD)
    * H2 Database (base de données en mémoire)
    * OpenCSV (pour lire le fichier .csv)
    * Lombok
* **Frontend:**
    * HTML5
    * CSS3 (avec Flexbox)
    * JavaScript (ES6+ avec `fetch` et `async/await`)

## 🚀 Fonctionnalités (Features)

* **Chargement Automatique des Données :** Au démarrage, l'application utilise un `CommandLineRunner` pour lire 374 lignes d'un fichier `.csv` (en utilisant OpenCSV) et les charger dans la base de données H2.
* **API REST Complète :** L'API expose des endpoints pour filtrer et analyser les données.
* **Logique d'Analyse (Java Streams) :** Utilisation des Streams Java (`groupingBy`, `averagingDouble`) pour calculer des statistiques en temps réel (ex: moyenne de sommeil par profession).
* **Frontend Dynamique :** L'interface appelle l'API pour remplir dynamiquement les listes déroulantes (`<select>`) au chargement de la page.
* **Rendu Propre :** Les résultats JSON de l'API sont parsés et affichés dans des tableaux HTML clairs et lisibles pour l'utilisateur final.

## 📊 Endpoints de l'API

* `GET /api/sleep`: Renvoie les 374 enregistrements.
* `GET /api/sleep/occupations`: Renvoie la liste *unique* de toutes les professions.
* `GET /api/sleep/sleepdisorders`: Renvoie la liste *unique* de tous les troubles du sommeil.
* `GET /api/sleep/occupation/{occupation}`: Filtre les enregistrements par profession.
* `GET /api/sleep/analysis/average-by-occupation`: Calcule et renvoie la durée moyenne de sommeil par profession.
* `GET /api/sleep/analysis/average-by-gender`: Calcule et renvoie la durée moyenne de sommeil par genre.
