package com.example.JwtApi.Entity;

import com.example.JwtApi.Enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleName name;
}



























