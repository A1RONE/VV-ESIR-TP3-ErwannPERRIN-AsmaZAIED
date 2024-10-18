# Test the Date class

Implement a class `Date` with the interface shown below:

```java
class Date implements Comparable<Date> {

    public Date(int day, int month, int year) { ... }

    public static boolean isValidDate(int day, int month, int year) { ... }

    public static boolean isLeapYear(int year) { ... }

    public Date nextDate() { ... }

    public Date previousDate { ... }

    public int compareTo(Date other) { ... }

}
```

The constructor throws an exception if the three given integers do not form a valid date.

`isValidDate` returns `true` if the three integers form a valid year, otherwise `false`.

`isLeapYear` says if the given integer is a leap year.

`nextDate` returns a new `Date` instance representing the date of the following day.

`previousDate` returns a new `Date` instance representing the date of the previous day.

`compareTo` follows the `Comparable` convention:

* `date.compareTo(other)` returns a positive integer if `date` is posterior to `other`
* `date.compareTo(other)` returns a negative integer if `date` is anterior to `other`
* `date.compareTo(other)` returns `0` if `date` and `other` represent the same date.
* the method throws a `NullPointerException` if `other` is `null` 

Design and implement a test suite for this `Date` class.
You may use the test cases discussed in classes as a starting point. 
Also, feel free to add any extra method you may need to the `Date` class.


Use the following steps to design the test suite:

1. With the help of *Input Space Partitioning* design a set of initial test inputs for each method. Write below the characteristics and blocks you identified for each method. Specify which characteristics are common to more than one method.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators check if the test cases written to far satisfy *Base Choice Coverage*. If needed add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Use the project in [tp3-date](../code/tp3-date) to complete this exercise.

## Answer

1 -
Pour cette exercice nous réaliserons également les tests de nos propres méthodes.

getDateTest :
 - Retourne un string donc pas de partition nécessaire, il suffit de verifier que le retour est le bon et donc que la classe à bien été crée

isValidDateTest :
    Nous avons partitionner selon les 3 variables : year/month/day
    Partition :
        year < 0 / 0 / leap / common
        month 2 / 30d / 31d / <= 0 / > 12
        day : <29 / 29 / 30 / 31 / < 0

    Pour le choix de base nous avons voulus verifier tout les cas valides.
    Base choice :
        year : leap = 2004 (29/02 is valid)
        month : 31d = 12 (all days are valid)
        day : 28 (valid for all)

isLeapYearTest :
    Nous ne verifions que l'année
    Partition :
        year : leap <0 / 0 / leap / common
    N'ayant qu'un block nous n'utiliserons pas BCC

nextDateTest :
    Comme pour isValid nous verifions year/month/day mais ici day corrsepond au nombre de jour avant la fin du mois car la fin du mois est traitée differement
    day :
        last : verifier que le dernier jour est le bon
        before last : verifier qu'on arrive bien au dernier jour (surtout pour fevrier)
        common : autres jours
    month aussi change un peu car nous devons tester 12 séparement car il peu changer l'année.
    Partition :
        year : < 0 / 0 / leap / common
        month : 2 / 30d / 31d / 12

    Cas de base : (pour verifier en priorité les changement de date important)
        year : leap
        month : 12
        day : last 

previousDateTest :
    previousDate test marche de manière similaire a nextDate mais ici on verifier month = 1 et month = 3 et le premier jour du mois etant fixe on teste juste day = 1.
    Partition
        year : < 0 / 0 / leap / common
        month : 3 / 30d / 31d / 1
        day : 1 / other

    On utilise BCC (pour verifier en priorité les changements de date important)
        Cas de base 
        year : leap = 2004
        month : 1
        day : 1   

compareToTest :
    Ici on verifier des combos jour mois pour savoir si changer de mois ne pose pas de problème. Pour resoudre ce problème le code calcul une date en nombre de jour et suppose que chaque moi à son nombe maximal de jours possible (31) ce qui donne en théorie toujours le resultat voulu <0, 0, >0 mais perd en précision.

    on a donc deux blocks :
    Partition :
        month + day : m-month + last day ; m-month+1 + first day / m-month+1 + first day ; m-month + last day / same / other
        year : 0 ; other / < 0 ; < 0 / > 0 ; < 0 / > 0 ; > 0 / same / other

    Cas de base (pour verifier les elements un a un sans etre influencer par l'autre.)
        month + day : same
        year : same    

approxToDaystest :
    Il suffit de verifier que la formule est valide pour un cas celle ci est fixe en int et sans division donc pas de problème d'approximation.

equalsTest :
    Partition :
        year : same / different
        month : same / different
        day : same / different

    Cas de base :
        year : same
        month : same
        day : same  

2 -

On evalue toujours la couverture à l'aide de jacoco qui nous donne un score de 7 echecss sur 38.

En effet il va falloir rajouter des cas a notre test

Pour isValidDate :
Il faut verifier le cas à part 29/2/2004
Mais aussi 29/3/2004
Et finalement le cas 31/11/2003

plutot que 28 en BCC qui est bien un cas de notre partition a part on aurrait pu couvrir ces tests en utilisant last and last+1 qui n'en font pas partit.

Pour nextDateTest :

On doit verifier le cas a part du 28/2
donc on ajoute des cas pour les années 2004 : leap, 2003 : common

Et toutes nos branches sont bien couvertes.

3- BCC ayant deja été utilisé pour creer les cas de test et apres les modifications apportées après 2 on a bien couvert toutes les solutions.

4- On utilise finalement PIT sur le code qui nous donne un score de mutation de 51/53.
Mais cette mutation demanderia de modifier le fonctionnement du code car elle modifie :
return month >[0] && month <= [12] && day > 0 && ( ( day <= max_days[month - 1] ) || ( isLeapYear(year) && day == 29 ));

or si ces paramètres sont modifier alors max_days[month - 1] ne marche pas.
Il faudrait donc renvoyer une erreur or on ne veux pas renvoyer d'erreur mais faux dans ce cas.

La solution est alors de modifier la constante 12 par max_days.length qui necessite que max_days ai bien été initialisée.
Or cela ne permet pas de résoudre tout les problème car pour month < 0 on n'a pas de solution, nous avons donc choisi de laisser le code comme tel.




