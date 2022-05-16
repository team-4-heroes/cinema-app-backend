package kea.dat3.repositories;

import kea.dat3.entities.ReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ReservedSeatRepository extends JpaRepository<ReservedSeat, Long> {

    @Query("select rs from ReservedSeat rs where rs.id = :desiredSeat")
    ReservedSeat getById(@Param("desiredSeat") int desiredSeat);

    @Query("select rs from ReservedSeat rs where rs.screening = :screeningId and rs.reservation is not null")
    Set<ReservedSeat> getReservedSeats(@Param("screeningId") Long screeningId);

    @Query("select rs from ReservedSeat rs where rs.screening.id = :screeningId and rs.reservation is null")
    Set<ReservedSeat> getSeatsWithNoReservations(@Param("screeningId") Long screeningId);
}
