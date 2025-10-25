package com.example.cardatabase_v2.DataAccessLayer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import java.util.ArrayList;

@Entity
@Table(
        name="owners",
        uniqueConstraints = {@UniqueConstraint(name="uk_owner_email",columnNames = "email")}
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Owner {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String firstname;

    @Column(nullable = false, length = 80)
    private String lastname;

    @Column(length = 255)
    private String email;

    @Column(length = 40)
    private String phone;

    @CreatedDate
    @Column
    private Instant createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Car>cars = new ArrayList<>();
}
