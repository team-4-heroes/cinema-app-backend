package kea.dat3.services;

import kea.dat3.dto.ReservationRequest;
import kea.dat3.entities.Person;
import kea.dat3.entities.Screening;
import kea.dat3.repositories.ReservationRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    ReservationService reservationService;
    ReservationRepository reservationRepository;

    Person cs = new Person();
    Screening sc = new Screening();

}
