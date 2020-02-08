package tim63.sistemKlinickogCentar.service.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import tim63.sistemKlinickogCentar.model.Lekar;
import tim63.sistemKlinickogCentar.model.Pacijent;
import tim63.sistemKlinickogCentar.model.Pregled;
import tim63.sistemKlinickogCentar.repository.PacijentRepositoryInterface;
import tim63.sistemKlinickogCentar.repository.PregledRepositoryInterface;
import tim63.sistemKlinickogCentar.service.PregledService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PregledServiceUnitTest {

    @Autowired
    PregledService pregledService;

    @MockBean
    PregledRepositoryInterface pregledRepositoryInterface;

    @MockBean
    PacijentRepositoryInterface pacijentRepositoryInterface;

    @BeforeEach
    void setUp() {

        when(
                pregledRepositoryInterface.findById(2L)
        ).thenReturn(
                Optional.of(new Pregled(LocalDateTime.parse("2020-03-28T13:00:00"), 90, 1L, 1L, 1L, 700,1L))
        );
        when(
                pacijentRepositoryInterface.findById(2L)
        ).thenReturn(
                Optional.of(new Pacijent("Milan", "Milanović","mglukic@gmail.com","22222222","Grad1","Adresa1","Drzava1","111111111","344"))
        );

        when(
                pregledRepositoryInterface.findById(2L)
        ).thenReturn(
                Optional.empty()
        );
        Pregled p=new Pregled(2L,LocalDateTime.parse("2020-03-28T13:00:00"), 90, 1L, 1L, 1L, 700,1L,0L);
        when(
               pregledRepositoryInterface.save(any(Pregled.class))
       ).thenReturn(p);



        List<Pregled> allCountries = new ArrayList<>(5);
        when(
                pregledRepositoryInterface.findAll()
        ).thenReturn(
                allCountries
        );


    }

    @Test
    void update() throws Exception {
        Pregled pregled1 = pregledService.findById(2L);
        // Pregled pregled2 = pregledService.findById(1L);

        System.out.println(pregled1.getIdPacijenta());
        //modifikovanje istog objekta
        pregled1.setIdPacijenta(2L);
        System.out.println(pregled1.getIdPacijenta().intValue());
        pregled1.setRezervisan(true);
        pregledService.update(pregled1);


        //verzija oba objekta je 0
        assertEquals(2, pregled1.getIdPacijenta().intValue());
         assertEquals(true, pregled1.isRezervisan());


    }
}