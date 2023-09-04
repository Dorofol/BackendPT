package com.BackEnd.BackEnd.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Usuarios")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    private String password;
    private String nombreCompleto;
    private String fechaNacimiento;

}
