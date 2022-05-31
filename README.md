# OC_P8Tourguide

## Repository :

```diff
Ce repository contient :
- L'API passerelle :
  * api-tourguide
- Trois microservices :
  * microservice-gps
  * microservice-reward
  * microservice-trip
- Les fichiers Docker :
  * Docker-compose
  * Docker-compose-debug (pour le dev)
  * Dockerfile
```

## Technical :

1. Framework: Spring Boot v2.1.6.RELEASE
2. Java 8
3. Gradle 4.8.1
4. Docker

## Install :

- Ouvrir un terminal dans api-tourguide :
  > ./gradlew bootjar

```diff
  Répéter l'opération précédente dans chaque microservice pour créer les fichier jar.
```

- Ouvrir un terminal dans OC_P8Tourguide :
  > docker-compose -d up

- Les microservices sont prêts à être utilisés.

## URL :

```diff
GET
```
  - http://localhost:8080/
  - http://localhost:8080/getAllCurrentLocations
  - http://localhost:8080/getLocation (Params userName)
  - http://localhost:8080/getNearbyAttractions (Params userName)
  - http://localhost:8080/getRewards (Params userName)
  - http://localhost:8080/getTripDeals (Params userName)

```diff
PUT
```
- http://localhost:8080/updatePreferences (Params userName, Body UserPreferenceDTO)
