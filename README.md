# Plugin d'OCR pour ImageJ

Projet IUT Informatique de Bordeaux 2016-2017

## Quels sont les enjeux ?
Le mot OCR (en anglais : optical character recognition) signifie reconnaissance optique de caractères ou reconnaissance de texte, une technologie qui vous permet de convertir différents types de documents tels que les documents papiers scannés, les fichiers PDF ou les photos numériques en fichiers modifiables et interrogeables.

Dans notre cas, nous allons tenter d'analyser des images de chiffres et d'en retrouver la valeur.

## Quels sont les résultats attendus ?
Nous souhaitons obtenir une reconnaissance des caractères optimale, la matrice de confusion est là pour vérifier la précision de notre algorithme.

## Chaine de traitement
* Redimensionnement des images en 20x20
* Niveaux moyens de gris
* Profil horizontal et vertical
## Résultats
Matrice de confusion avec pour seule caractéristique les niveaux de gris :

![result1](https://github.com/ShellCode33/OCR-Project/raw/master/screenshots/result1.png)

* Taux de réussite : 24%

La reconnaissance commence à être interessante lorsque l'on analyse le profil horizontal et vertical des images. En effet le taux de réussite augmente drastiquement :

![result2](https://github.com/ShellCode33/OCR-Project/raw/master/screenshots/result2.png)

* Taux de réussite : 72%

Après avoir appliqué un algorithme comparant les rapports isopérimetriques, il semblera que celui-ci ne change pas le taux de réussite qui est maintenant à 72%.

Ajout du zoning, celui-ci permet... TODO

Amélioration de la classification en faisant une moyenne entres les différentes références.