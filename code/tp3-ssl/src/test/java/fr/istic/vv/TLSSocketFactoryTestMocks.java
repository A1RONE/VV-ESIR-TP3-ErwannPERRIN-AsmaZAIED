package fr.istic.vv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.net.ssl.SSLSocket;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TLSSocketFactoryTestMocks {

    @Mock
    private SSLSocket mockSocket;

    @InjectMocks
    private TLSSocketFactory tlsSocketFactory;

    @BeforeEach
    public void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void preparedSocket_NullProtocols() throws Exception {
        // Simuler un socket avec null pour les protocols supportés et activés
        when(mockSocket.getSupportedProtocols()).thenReturn(null);
        when(mockSocket.getEnabledProtocols()).thenReturn(null);

        // Exécuter la méthode à tester
        tlsSocketFactory.prepareSocket(mockSocket);

        // Vérifier que setEnabledProtocols() n'est pas appelé
        verify(mockSocket, never()).setEnabledProtocols(any());
    }

    @Test
    public void typical() throws Exception {
        // Simuler des protocoles supportés et activés
        when(mockSocket.getSupportedProtocols()).thenReturn(new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"});
        when(mockSocket.getEnabledProtocols()).thenReturn(new String[]{"SSLv3", "TLSv1"});

        // Exécuter la méthode à tester
        tlsSocketFactory.prepareSocket(mockSocket);

        // Vérifier que setEnabledProtocols() est appelé avec les bons arguments
        verify(mockSocket).setEnabledProtocols(new String[]{"TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3"});
    }

}
