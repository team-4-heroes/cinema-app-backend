package kea.dat3.repositories;

import kea.dat3.entities.ReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Long> {

    @Query("select rs from ReservedSeat rs where rs.id = :desiredSeat")
    Set<ReservedSeat> getById(@Param("desiredSeat") int desiredSeat);

    @Query("select rs from ReservedSeat rs where rs.screening = :screeningId")
    Set<ReservedSeat> getReservedSeats(@Param("screeningId") Long screeningId);

    @Query("select rs from ReservedSeat rs where rs.screening.id <> :screeningId")
    Set<ReservedSeat> getSeatsWithNoReservations(@Param("screeningId") Long screeningId);
}
