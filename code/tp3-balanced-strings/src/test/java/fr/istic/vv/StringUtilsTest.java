package fr.istic.vv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static fr.istic.vv.StringUtils.isBalanced;


class StringUtilsTest {
    
    @Test
    public void isBalancedTest()
    {
        String str;
        //Partition
        // Chaque cas marche avec une combinaison de chaque type de symboles
        // simple : () || {} || []
        str = "()";
        assertTrue(isBalanced(str), "La combinaison \'"+str+"\' devrait être valide.");
        str = "[]";
        assertTrue(isBalanced(str), "La combinaison \'"+str+"\' devrait être valide.");
        str = "{}";
        assertTrue(isBalanced(str), "La combinaison \'"+str+"\' devrait être valide.");
        // encapsulation : ([])
        str = "([])";
        assertTrue(isBalanced(str), "La combinaison \'"+str+"\' devrait être valide.");
        // double encapsulation : ([]{})
        str = "([]{})";
        assertTrue(isBalanced(str), "La combinaison \'"+str+"\' devrait être valide.");
        // encapsulation d'encapsulation : ([{}])
        str = "([{}])";
        assertTrue(isBalanced(str), "La combinaison \'"+str+"\' devrait être valide.");

        // on supposera que si la double encapsulation marche c'est pareil pour n-encapsulation
        // et si la l'encapsulation d'encapsultaion marche on valide l'encapsulation^n.

        // il faut egalement tester les cas qui ne marchent pas (impaire).
        // En suivant le principe de la BCC on ne testera que chaque cas en le rendant impaire deux fois
        // impaire par la gauche et impaire par la droite
        // On testera les 3 simples par précaution
        // simple : ( | { | [ | ] | } | )
        str = "(";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = ")";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = "{";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = "}";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = "[";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = "]";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        // encapsulation : ([] | (])
        str = "([]";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = "(])";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        // double encapsulation : ([]{) | (]{})
        str = "([]{)";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = "(]{})";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        // encapsulation d'encapsulation : ([}]) | ([{})
        str = "([}])";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
        str = "([{})";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
    
        // On ajoute un cas pour augmenter le coverage
        str = "(a";
        assertFalse(isBalanced(str), "La combinaison \'"+str+"\' ne devrait pas être valide.");
    }

}