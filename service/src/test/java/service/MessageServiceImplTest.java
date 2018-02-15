package service;

import ar.edu.itba.interfaces.MessageDao;
import ar.edu.itba.model.Message;
import ar.edu.itba.service.MessageServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

  @InjectMocks
  private MessageServiceImpl messageService;

  @Mock
  private MessageDao messageDao;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetById() {
      long existingId = 7;
      when(messageDao.getById(existingId)).thenReturn(mock(Message.class));
      assertNotNull(messageService.getById(existingId));
      assertNull(messageService.getById(45L));
  }


}
