# Plugin d'OCR pour ImageJ

Projet IUT Informatique de Bordeaux 2016-2017

## Quels sont les enjeux ?
Le mot OCR (en anglais : optical character recognition) signifie reconnaissance optique de caractères ou reconnaissance de texte, une technologie qui vous permet de convertir différents types de documents tels que les documents papiers scannés, les fichiers PDF ou les photos numériques en fichiers modifiables et interrogeables.

Dans notre cas, nous allons tenter d'analyser des images de chiffres et d'en retrouver la valeur.

## Quels sont les résultats attendus ?
Nous souhaitons obtenir une reconnaissance des caractères optimale, la matrice de confusion est là pour vérifier la précision de nos algorithmes.
Nous avons une base de référence composée de 100 images (10 images de 0, 10 images de 1, etc...), l'OCR n'étant pas une science exacte, nous espérons être en mesure de reconnaitre au moins 3/4 des chiffres que nous allons tester.

## Chaine de traitement
* Redimensionnement des images en 20x20
    * Nous permet de comparer les mêmes choses entre les images, si celles-ci ne sont pas de la meme taille, les résultats risquent d'être imprécis.

* Niveaux moyens de gris
    * On calcule la somme des différentes nuances de gris afin de comparer celle-ci entre les différentes images. Un 1 aura une somme de nuances de gris bien plus faible qu'un 8 ou un 9 par exemple.

* Profil horizontal et vertical
    * TODO THEO

* Rapport isopérimétrique
    * On effectue un rapport entre le périmetre (contour) du chiffre et sa surface (de pixels noirs).

* Zoning
    * TODO THEO

## Résultats
Matrice de confusion avec pour seule caractéristique les niveaux de gris :

![result1](https://github.com/ShellCode33/OCR-Project/raw/master/screenshots/result1.png)

* Taux de réussite : 24%

La reconnaissance commence à être interessante lorsque l'on analyse le profil horizontal et vertical des images. En effet le taux de réussite augmente drastiquement :

![result2](https://github.com/ShellCode33/OCR-Project/raw/master/screenshots/result2.png)

* Taux de réussite : 72%

Après avoir appliqué un algorithme comparant les rapports isopérimetriques, il semblerait que celui-ci ne change pas le taux de réussite qui est toujours de 72%.

Ajout du zoning qui nous permet d'être un peu plus précis dans nos résultats :

![result3](https://github.com/ShellCode33/OCR-Project/raw/master/screenshots/result3.png)

* Taux de réussite : 83%

Amélioration de la classification en faisant une moyenne entres les différentes références. Plutot que de tester 1 à 1 toutes les références, on fait une moyenne entre tous les 0, 1, 2, etc... Afin de préciser le résultat.
Cela nous permet également de gagner légèrement en précision comme ce screenshot en témoigne :

![result4](https://github.com/ShellCode33/OCR-Project/raw/master/screenshots/result4.png)

* Taux de réussite : 86%

Conclusion : Avec de simples algorithmes de reconnaissance, nous arrivons à un taux de reconnaissance plus que convenable. Nous esperions reconnaitre les 3/4 des images et nous sommes en mesure de reconnaitre 86% ! Les erreurs commises sont dûes à une ambiguïté entre certains caractères comme le 3 et le 9 qui est l'exemple le plus flagrant selon la matrice de confusion.