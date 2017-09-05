package service;

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.CommerceService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.service.CommerceServiceImpl;
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
public class CommerceServiceImplTest {

    private CommerceServiceImpl commerceService;

    @Mock
    private CommerceDao commerceDao;

    @Mock
    private EmpireService empireService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        commerceService = new CommerceServiceImpl();
        commerceService.setCommerceDao(commerceDao);
        commerceService.setEmpireService(empireService);
    }

    @Test
    public void testAcceptOffer(){

    }

}
