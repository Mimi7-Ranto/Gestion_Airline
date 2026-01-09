package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_billet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeBillet {
    @Id
    @Column(name = "id_type_billet")
    private Integer idTypeBillet;

    @Column(name = "nom_type", nullable = false)
    private String nomType;
}
