package bda.Clinics.dao.repository;

import bda.Clinics.dao.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
