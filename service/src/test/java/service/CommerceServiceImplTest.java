package service;

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.model.Resource;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;
import ar.edu.itba.service.CommerceServiceImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CommerceServiceImplTest {

  @InjectMocks
  private CommerceServiceImpl commerceService;

  @Mock
  private CommerceDao commerceDao;

  @Mock
  private EmpireService empireService;

  @Mock
  private User user;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateOffer() {
    when(empireService.getResource(user, 0)).thenReturn(new Resource(0, 1000));
    assertFalse(commerceService.createOffer(user, 0, 50, 0, 20));
    assertFalse(commerceService.createOffer(user, 0, 2000, 1, 20));
    assertTrue(commerceService.createOffer(user, 0, 500, 1, 20));
  }

  @Test
  public void testRemoveOffer() {
    List<TradeOffer> offers = new ArrayList<>();
    int offerAmount = 100;
    int receiveAmount = 1000;
    int offerType = 1;
    int receiveType = 2;
    TradeOffer toRemove = new TradeOffer(user, offerType, offerAmount, receiveType, receiveAmount);
    offers.add(toRemove);
    offers.add(new TradeOffer(user, 1, 100, 2, 100));
    offers.add(new TradeOffer(user, 2, 100, 1, 100));
    when(commerceDao.getAllOffers()).thenReturn(offers);
    doAnswer(inv -> offers.removeIf(o -> o.getId() == (int)(inv.getArguments()[0]))).when(commerceDao).removeOffer(toRemove.getId());
    commerceService.removeOffer(toRemove);
    assertFalse(offers.contains(toRemove));
  }

  @Test
  public void testShowOffers() {
    List<TradeOffer> allOffers = new ArrayList<>();
    List<TradeOffer> owned = new ArrayList<>();
    TradeOffer ownedTo = new TradeOffer(user, 1, 100, 2, 100);
    TradeOffer otherTo = mock(TradeOffer.class);
    allOffers.add(ownedTo);
    allOffers.add(otherTo);
    owned.add(ownedTo);
    when(commerceDao.getAllOffers()).thenReturn(allOffers);
    when(commerceDao.getAllOffers(user)).thenReturn(owned);
    List<TradeOffer> showed = commerceService.showOffers(user);
    assertTrue(showed.contains(otherTo));
    assertFalse(showed.contains(ownedTo));
  }
}
