package fr.istic.vv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TLSSocketFactoryTestMocks {

    @Test
    public void preparedSocket_NullProtocols()  {
        TLSSocketFactory f = new TLSSocketFactory();
        
        // Création d'un mock pour SSLSocket
        SSLSocket sslsocket  = mock(SSLSocket.class);

        // Simule un socket qui ne renvoie pas de protocole supporté / activé
        when(sslsocket.getSupportedProtocols()).thenReturn(null);
        when(sslsocket.getEnabledProtocols()).thenReturn(null);

        // Appel de la méthode à tester
        f.prepareSocket(sslsocket);

        // Vérifie que `setEnabledProtocols` n'est jamais appelée depuis le mock.
        verify(sslsocket, never()).setEnabledProtocols(any(String[].class));
    }

    @Test
    public void typical()  {
        TLSSocketFactory f = new TLSSocketFactory();
        
        // Création d'un mock pour SSLSocket
        SSLSocket sslsocket = mock(SSLSocket.class);

        // Simule des protocoles supportés par le socket à l'aide de mockito, l'ordre est aléatoire grace a shuffle
        when(sslsocket.getSupportedProtocols()).thenReturn(shuffle(new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}));

        // Simule des protocoles activés par le socket à l'aide de mockito, l'ordre est aléatoire grace a shuffle
        when(sslsocket.getEnabledProtocols()).thenReturn(shuffle(new String[]{"SSLv3", "TLSv1"}));

        // Appel de la méthode à tester
        f.prepareSocket(sslsocket);

        // Vérifie que la methodes `setEnabledProtocols` est appelé avec les arguments qu'il faut.
        verify(sslsocket).setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3"});
    }

    private String[] shuffle(String[] in) {
        List<String> list = new ArrayList<>(Arrays.asList(in));
        Collections.shuffle(list);
        return list.toArray(new String[0]);
    }
}
