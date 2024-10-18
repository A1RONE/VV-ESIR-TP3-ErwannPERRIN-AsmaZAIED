# On assertions

Answer the following questions:

1. The following assertion fails `assertTrue(3 * .4 == 1.2)`. Explain why and describe how this type of check should be done.

2. What is the difference between `assertEquals` and `assertSame`? Show scenarios where they produce the same result and scenarios where they do not produce the same result.

3. In classes we saw that `fail` is useful to mark code that should not be executed because an exception was expected before. Find other uses for `fail`. Explain the use case and add an example.

4. In JUnit 4, an exception was expected using the `@Test` annotation, while in JUnit 5 there is a special assertion method `assertThrows`. In your opinion, what are the advantages of this new way of checking expected exceptions?

## Answer

assertTrue()

En théorie, 3 * 0.4 devrait effectivement être égal à 1.2. Cependant, dans ce cas, ce n'est pas vrai. La raison est que Java n'enregistre pas avec précision les valeurs des nombres décimaux lorsqu'il effectue des calculs. Dans notre cas, 3 * 0.4 retourne 1.2000000000000002 au lieu de 1.2.

Cela est donc lié à la manière dont java code ces décimales selon l’ IEEE 754 une norme de représentation de ces nombres qui les codes en binaires et non en base 10. Cela pose le problème que en base 2 certaines représentation de nombres sont infinies à l’instar de ⅓ en base 10 qui donne 0.333333… . Ces nombres doivent donc être approximés ce qui fait que le calcule entre ces valeurs comme 0.4 par exemple ne donnent pas les valeurs exactes que l’on retrouverai en base 10.

Une solution serait donc au lieu de chercher une égalité parfaite sur le résultat de chercher également une approximation sur le résultat. Pour cela il y a plusieurs méthodes en voiçi une :

assertTrue( abs( 3 * .4 - 1.2 ) < approximation )

avec approximation une valeur très faible.

assertEquals, assertSame

assertEquals et assertSame on des utilisations très proches dans le sens ou il comparent deux entrées mais ne s’appliquent pas dans le même cas.
assertEquals cherche à vérifier si les deux paramètres en entrée sont égaux en termes de valeurs. Cela veut dire qu’on peut aussi bien utiliser des int, des longs, des strings, etc mais aussi des objets en vérifiant si les deux objets ont les mêmes valeurs.
assertSame par contre vérifie si les deux entrées pointes sur la même instance d’objet et ne s’applique donc que sur des objets.

assertEquals permet donc de comparer deux entrées différentes et assertSame d’identifier deux pointeurs vers un même objet.

fail()

La méthode fail() permet de forcer l'échec d’un test, elle permet donc en effet de sauter des tests que l’on sait avoir raté et qui n'ont pas encore été résolus mais ce n’est pas tout.

Il est notamment possible d'utiliser le fail pour passer les tests qui définissent les spécifications logiciels non implémentées cela peut notamment être utile dans le cas de l’approche Constructive qui veut que l’on définisse une grande partie des cas de tests dès le début du projet.

Finalement une dernière utilisation que nous allons aborder est l’optimisation de temps de calcul, par exemple quand notre programme utilise des boucles et qu’on sait qu’une erreur arrive à partir du moment où trop de boucles sont effectuées (exemple boucle infini ou erreur de condition d'arrêt) et donc qu’on suppose déjà une condition d’arrêt on peut effectuer un fail pour ne pas avoir à continuer le test plus longtemps.

exemple : 

public void exemple() {
 for (int i = 0; i < 10; i++) {
 if (i == 5) { 
fail("Le test ne doit jamais atteindre cette valeur de i !");
 }
 }
 }

assertThrows()

Le premier argument est assez basique mais c’est une méthode plus facile de tester ce genre de situation, plus élégante mais aussi plus rapide.
En effet ne pas avoir à effectuer de try catch permet de réecrire cette solution qui est au départ complexe sous la forme d'une simple assertion soit environ 2 à 3 lignes.

Cela permet également de clarifier le but d’un test et donc facilite la compréhension du développeur quand il revient dessus ou même d’un développeur externe qui reprend un code.

Ce nouvel outil permet donc d’avoir des tests unitaires plus concis et de vérifier de manière plus encadrée le code et donc de pouvoir bien vérifier tous les cas d’erreurs possibles sans en oublier.