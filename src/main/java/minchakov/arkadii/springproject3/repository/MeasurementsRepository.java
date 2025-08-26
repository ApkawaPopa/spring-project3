package minchakov.arkadii.springproject3.repository;

import minchakov.arkadii.springproject3.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    @Query(
        value = "select count(distinct date(m.created_at)) from measurements m where m.raining = true",
        nativeQuery = true
    )
    int countRainyDays();
}
