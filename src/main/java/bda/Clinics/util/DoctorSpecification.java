package bda.Clinics.util;

import bda.Clinics.dao.model.entity.Clinic;
import bda.Clinics.dao.model.entity.Doctor;
import bda.Clinics.dao.model.entity.Review;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class DoctorSpecification {

    public static Specification<Doctor> hasFullName(String fullName) {
        return (doctorRoot, query, criteriaBuilder) -> {
            if (fullName == null || fullName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(doctorRoot.get("fullName")),
                    "%" + fullName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Doctor> hasSpeciality(String speciality) {
        return (doctorRoot, query, criteriaBuilder) -> {
            if (speciality == null || speciality.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(doctorRoot.get("speciality")),
                    "%"+speciality.toLowerCase()+"%"
            );
        };
    }

    public static Specification<Doctor> hasClinic(String clinicName) {
        return (doctorRoot, query, criteriaBuilder) -> {
            if (clinicName == null || clinicName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<Doctor, Clinic> doctorClinicJoin = doctorRoot.join("clinics");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(doctorClinicJoin.get("isActive"), true),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(doctorClinicJoin.get("clinicName")),
                            "%" + clinicName.toLowerCase() + "%"
                    )
            );
        };
    }

    public static Specification<Doctor> hasMoreThanXReviews(Double reviewCount) {
        return (doctorRoot, query, criteriaBuilder) -> {
            if (reviewCount == null || reviewCount <= 0) {
                return criteriaBuilder.conjunction();
            }
            Join<Doctor, Review> doctorReviewJoin = doctorRoot.join("reviews");
            query.groupBy(doctorRoot.get("doctorId"));
            query.having(criteriaBuilder.gt(criteriaBuilder.count(doctorReviewJoin.get("reviewId")), reviewCount));
            return query.getRestriction();
        };
    }

    public static Specification<Doctor> hasMoreThanXRating(Double rating) {
        return (doctorRoot, query, criteriaBuilder) -> {
            if (rating == null || rating <= 0) {
                return criteriaBuilder.conjunction();
            }
            Join<Doctor, Review> doctorReviewJoin = doctorRoot.join("reviews");
            query.groupBy(doctorRoot.get("doctorId"));
            query.having(criteriaBuilder.gt(criteriaBuilder.avg(doctorReviewJoin.get("rating")), rating));
            return query.getRestriction();
        };
    }

    public static Specification<Doctor> isActive() {
        return (doctorRoot, query, criteriaBuilder) -> criteriaBuilder.equal(doctorRoot.get("isActive"), true);
    }
}