package kea.dat3.repositories;

import kea.dat3.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    // Check if a room is available for a new screening at the chosen time
    // calculate with 30 minutes between the movies for cleaning etc.
    @Query(value = "SELECT CASE WHEN COUNT(*) = 0 THEN true ELSE false END FROM screening s JOIN movie m ON s.movie_id=m.id " +
            "WHERE s.room_id = :id AND NOT (TIMESTAMPDIFF(MINUTE, TIMESTAMPADD(MINUTE, length_in_minutes, start_time), :start) >= 30 " +
            "OR TIMESTAMPDIFF(MINUTE, :end, start_time) >= 30)", nativeQuery = true)
    boolean isRoomAvailableForScreening(@Param("id") long id, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
