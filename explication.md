Impl (implementation): dossier/paquet service.impl contient les classes qui implémentent les interfaces de service. Ex.: AeroportServiceImpl implémente AeroportService et contient la logique métier (appel des Repository, transactions, validations, règles).


Mapper: classe responsable de convertir entre entités JPA et DTOs (request/response). Ex.: AeroportMapper convertit AeroportRequest → Aeroport et Aeroport → AeroportResponse.