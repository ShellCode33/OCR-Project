# Plugin d'OCR pour ImageJ

Projet IUT Informatique de Bordeaux 2016-2017

## Quels sont les enjeux ?
Le mot OCR (en anglais : optical character recognition) signifie reconnaissance optique de caractères ou reconnaissance de texte, une technologie qui vous permet de convertir différents types de documents tels que les documents papiers scannés, les fichiers PDF ou les photos numériques en fichiers modifiables et interrogeables.

## Qu'est ce qu'on attend ?
Nous souhaitons obtenir une reconnaissance des caractères optimale, la matrice de confusion est là pour vérifier la précision de notre algorithme.

## Bases, bien expliquer, quelles formats, les difficultés
## Chaine de traitement
* Redimensionnement des images en 20x20
* Niveaux moyens de gris
## Résultats
Matrice de confusion avec pour seule caractéristique les niveaux de gris :

Mon Apr 03 13:16:50 CEST 2017

![result1](https://github.com/ShellCode33/OCR-Project/raw/master/screenshots/matrix_grey_levels.png)

* Success rate : 16.0%
* En redimensionant toutes les images en 20x20, le taux de réussite tombe à 14%

 