package service;

import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.service.MessageServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    private MessageServiceImpl messageService;

    @Mock
    private MessageDao messageDao;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        messageService = new MessageServiceImpl();
        messageService.setMessageDao(messageDao);
    }

    @Test
    public void testDeleteMessages(){

    }



}
