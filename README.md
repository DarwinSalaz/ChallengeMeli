# Prueba Técnica: MercadoLibre

Proyecto que expone un recurso API REST que permite identificar si un humano es mutante a partir de la secuencia 
de su ADN, almacenando la información para generación de estadisticas que igualmente son expuestas en un servicio REST

## Detalles Técnicos

* Spring Boot - Java 11
* PostgreSQL
* AWS - Elastic Beanstalk

## Uso API Rest

* Itentificar mutante
```
# URL

POST - http://challengemeli-dev.us-east-2.elasticbeanstalk.com:80/mutant/

# BODY

{
    "dna": [ "AAGTT", "AAGGG", "ATGTA", "AGAAT", "GTCCT"]
}

# RESPONSE

- 200 - Si es mutante

{
    "is_mutant": true
}

- 403 - No es mutante

{
    "is_mutant": false
}

```

* Itentificar mutante
```
# URL

GET - http://challengemeli-dev.us-east-2.elasticbeanstalk.com:80/stats

# RESPONSE

- 200

{
    "count_mutant_dna": 16,
    "count_human_dna": 18,
    "ratio": 0.89
}

```
