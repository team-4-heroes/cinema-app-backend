package kea.dat3.repositories;

import kea.dat3.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    // check if a room is available for a new screening at the chosen time
    // TODO: improve this to account for the duration of movies in db
    @Query("SELECT CASE WHEN COUNT(s) = 0 THEN true ELSE false END FROM Screening s " +
            "WHERE s.room.id = :id AND s.startTime between :start and :end")
    boolean isRoomAvailableForScreening(@Param("id") long id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
