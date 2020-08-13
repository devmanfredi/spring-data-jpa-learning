package br.com.springjpa.repository;

import br.com.springjpa.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findAllByDepartamento(String departamento);

    List<Funcionario> findAllByCargo(String cargo);

    List<Funcionario> findAllByCargoAndDepartamento(String cargo, String departamento);

    Funcionario findBySobrenome(String sobrenome);

    @Query("select fun from Funcionario fun order by createdAt asc")
    List<Funcionario> findFuncionariosOrderByCreatedAt();
}
