package bda.Clinics.dao.model.dto.response;

import bda.Clinics.dao.model.Doctor;
import bda.Clinics.dao.model.enums.ReviewStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseReviewDto {
    int rating;
    String comment;
    Date reviewDate;
    String fullName;
    ReviewStatus status;
}
