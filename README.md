# Spring AI partie 3 : Implémentation des appels de fonctions avec OpenAI

Ce repository contient des exemples montrant l'implémentation des appels de fonctions et l'utilisation d'un modèle
personnalisé avec le fine-tuning dans Spring AI en
utilisant [OpenAI](https://platform.openai.com/).

## Prérequis

Avant de démarrer le projet, il est impératif d'avoir une clé API d'OpenAI. Pour obtenir cette clé, vous pouvez
consulter la [documentation des clés API d'OpenAI](https://platform.openai.com/api-keys). Une fois obtenue, vous pouvez
soit l'exporter dans une variable d'environnement, soit la renseigner dans le fichier `application.properties`.

````
#Exporter la clé dans la variable d'environnement OPENAI_API_KEY
spring.ai.openai.api-key=${OPENAI_API_KEY:renseignez-la-ici}
````

**Note :** Ce projet utilise la version 1.0.0-SNAPSHOT de Spring AI, ce qui pourrait entraîner des divergences au
niveau des exemples si vous utilisez
une version ultérieure. En cas de divergences, vous avez deux options :

- Si vous générez le projet avec [Spring Initializer](https://start.spring.io/), vous pouvez vous assurer de travailler
  avec la même version que moi en consultant le fichier `pom.xml` du projet.
- Si cette version snapshot n'est plus disponible, vous pouvez simplement consulter la documentation de Spring AI de la
  version que vous utilisez, pour voir les divergences.

## Fine-tuning

Pour ce project, j'utilise un modèle personnalisé via fine-tuning du modèle gpt-4o-mini d'openAI.

**Attention :** Vous n'aurez certainement pas accès au modèle "fine-tuné", vu qu'il est privé. Vous devrez alors
utiliser un autre modèle de votre choix. Les exemples d'appels de fonctions resteront les mêmes. Il suffit de remplacer
le modèle par le modèle de votre choix dans `application.properties`

````
#gpt-4o par exemple
spring.ai.openai.chat.options.model=${FINE_TUNED_MODEL:ft:gpt-4o-mini-2024-07-18:personal:fly-intelligent:A1AK83lh}
````

## Structure du projet

### Dépendances

Pour simplifier le test des différents concepts, j'ai utilisé spring-boot-starter-web, où les exemples sont répartis
sur des endpoints. J'utilise une base de données en mémoire (H2), et Spring Data pour enregistrer et récupérer les
données des clients.

### Initialisation de la base de données

Pour ce projet, je réutilise l'exemple d'étude de cas mentionnée
dans [cet article](https://www.linkedin.com/pulse/architecture-et-impl%C3%A9mentation-de-la-g%C3%A9n%C3%A9ration-par-rag-ali-ibrahim-xgu1e).
Pour cela, j'ai créé les différentes
entités, et l'initialisation des données est faite au niveau `SpringAiFunctionsCallingApplication`

### FunctionController

Contient les exemples de chat en intégrant les appels des fonctions.

* Utilisation d'une fonction

````
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage), OpenAiChatOptions.builder()
                .withFunction("getFlightsByUser")
                .build());
````

* Utilisation de plusieurs fonctions

````
        Prompt prompt = new Prompt(List.of(message, systemMessage), OpenAiChatOptions.builder()
                .withFunctions(Set.of("getDestinationBySeasons",
                        "getBudgetByDestinationAndNumberOfDays"))
                .build());
````

#### FlightsService

Le service qui définit la fonction `getFlightsByUser` qui permet de récupérer les vols d'un utilisateur.

#### BestDestinationService

Le service qui définit la fonction `getDestinationBySeasons` qui permet de déterminer les meilleures destinations en
fonction des saisons.

#### BudgetByDestinationService

Le service qui définit la fonction `getBudgetByDestinationAndNumberOfDays` qui calcule le budget en fonction du nombre
de jours et la destination.

## Ressources

- [Spring AI partie 1](https://www.linkedin.com/pulse/spring-boot-et-ia-g%C3%A9n%C3%A9rative-un-duo-innovant-avec-ai-ali-ibrahim-mso7e/)
- [Spring AI partie 2](https://www.linkedin.com/pulse/architecture-et-impl%C3%A9mentation-de-la-g%C3%A9n%C3%A9ration-par-rag-ali-ibrahim-xgu1e/)
- [Spring AI](https://docs.spring.io/spring-ai/reference/)
- [OpenAI](https://platform.openai.com/docs/overview)
