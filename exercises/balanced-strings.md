# Balanced strings

A string containing grouping symbols `{}[]()` is said to be balanced if every open symbol `{[(` has a matching closed symbol `)]}` and the substrings before, after and between each pair of symbols is also balanced. The empty string is considered as balanced.

For example: `{[][]}({})` is balanced, while `][`, `([)]`, `{`, `{(}{}` are not.

Implement the following method:

```java
public static boolean isBalanced(String str) {
    ...
}
```

`isBalanced` returns `true` if `str` is balanced according to the rules explained above. Otherwise, it returns `false`.

Use the coverage criteria studied in classes as follows:

1. Use input space partitioning to design an initial set of inputs. Explain below the characteristics and partition blocks you identified.
2. Evaluate the statement coverage of the test cases designed in the previous step. If needed, add new test cases to increase the coverage. Describe below what you did in this step.
3. If you have in your code any predicate that uses more than two boolean operators, check if the test cases written so far satisfy *Base Choice Coverage*. If needed, add new test cases. Describe below how you evaluated the logic coverage and the new test cases you added.
4. Use PIT to evaluate the test suite you have so far. Describe below the mutation score and the live mutants. Add new test cases or refactor the existing ones to achieve a high mutation score.

Write below the actions you took on each step and the results you obtained.
Use the project in [tp3-balanced-strings](../code/tp3-balanced-strings) to complete this exercise.

## Answer

1 -
Nous avons partitionner le tableau en 2 blocs :
- Type d'encapsulation :
    : - simple / () ; {} ; []
    : - encapsulation simple / ([])
    : - double encapsulation / ([]{})
    : - encapsulation^2 / ([{}])
( On supposera que si la double encapsulation marche c'est pareil pour n-encapsulation. Et si la l'encapsulation^2 marche on valide l'encapsulation^n. )
- Est balancé ? :
    : - balancé
    : - non balancé par la gauche
    : - non balancé par la droite
( On teste séparement la gauche et la droite car elles ne sont pas verifiée de la même manière dans le code. )

Les cas etant facile a couvrir nous utiliserons MCC, soit tester si chaque type est balancé et non balancé à gauche ou a droite.

2 -
Nous avons evaluer la couverture en utilisant le plugin jacoco qui d'après ces résultat nous donnait une couverture de 27/28 en effet il ne nous manquait qu'un cas à tester :
- le cas absurde ou on a un autre caractère que ceux prévus en fermeture
: "(a" par exemple.
Après avoir rajouter ce cas on avais bien tout couvert ce qui est cohérent puisqu'on a utiliser MCC.

3 - 
Comme nous avons utiliser MCC pour creer nos tests BCC est bien couvert.

4 -
En utilisans pit pour creer des mutations on obtient un score de 12/13.
Néanmoins on peux considerer que c'est un 12/12 car l'erreur de viens pas de la fonction isBalanced que nous testons mais du constructeur que nous ne testons pas.