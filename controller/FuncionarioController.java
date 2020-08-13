package br.com.springjpa.controller;

import br.com.springjpa.entity.Funcionario;
import br.com.springjpa.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @PostMapping(value = "/random")
    private void criaFuncionariosRandom() {
        String[] cargos = new String[6];
        cargos[0] = "engenheiro";
        cargos[1] = "analista de suporte";
        cargos[2] = "analista de sistemas";
        cargos[3] = "tech lead";
        cargos[4] = "desenvolvedor backend";
        cargos[5] = "desenvolvedor frontend";

        String[] departamentos = new String[3];
        departamentos[0] = "engenharia";
        departamentos[1] = "tecnologia";
        departamentos[2] = "suporte";

        BigDecimal[] salarios = new BigDecimal[4];
        salarios[0] = BigDecimal.valueOf(4500);
        salarios[1] = BigDecimal.valueOf(4500);
        salarios[2] = BigDecimal.valueOf(5500);
        salarios[3] = BigDecimal.valueOf(6500);

        for (int i = 0; i < 90; i++) {
            Funcionario funcionario = new Funcionario();
            int rnd = new Random().nextInt(cargos.length);
            int rndSalarios = new Random().nextInt(salarios.length);
            funcionario.setNome("F" + i);
            funcionario.setCargo(cargos[rnd]);
            String cargo = funcionario.getCargo();
            if (cargo.equals(
                    "desenvolvedor backend") || cargo.equals(
                    "desenvolvedor frontend") || cargo.equals(
                    "engenheiro")) {
                funcionario.setDepartamento(departamentos[0]);


            } else if (cargo.equals("tech lead") || cargo.equals("analista de sistemas")) {
                funcionario.setDepartamento(departamentos[1]);
            } else if (cargo.equals("analista de suporte")) {
                funcionario.setDepartamento(departamentos[2]);
            }
            funcionario.setSobrenome("S" + i);
            funcionario.setSalario(salarios[rndSalarios]);
            funcionario.setCreatedAt(LocalDateTime.now());
            repository.save(funcionario);

        }
    }

    @PostMapping
    private Funcionario create(@RequestBody Funcionario funcionario) {
        funcionario.setCreatedAt(LocalDateTime.now());
        repository.save(funcionario);
        return funcionario;
    }

    @GetMapping
    private List<Funcionario> findAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/departamento/{departamento}")
    private List<Funcionario> findAllByDepartamento(@PathVariable String departamento) {
        return repository.findAllByDepartamento(departamento);
    }

    @GetMapping(value = "/cargo/{cargo}")
    private List<Funcionario> findAllByCargo(@PathVariable String cargo) {
        return repository.findAllByCargo(cargo);
    }

    @GetMapping(value = "/{id}")
    private Optional<Funcionario> read(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping(value = "/{cargo}/{departamento}")
    private List<Funcionario> findAllByCargoAndDepartamento(@PathVariable String cargo, @PathVariable String departamento) {
        return repository.findAllByCargoAndDepartamento(cargo, departamento);
    }

    @GetMapping(value = "/detail/{sobrenome}")
    private Funcionario findBySobrenome(@PathVariable String sobrenome) {
        return repository.findBySobrenome(sobrenome);
    }

    @PutMapping(value = "/{id}")
    private Funcionario update(@PathVariable Long id, @RequestBody Funcionario funcionario) throws Exception {
        Funcionario funcionarioToUpdate = repository.findById(id).orElseThrow(Exception::new);
        funcionarioToUpdate.setDepartamento(funcionario.getDepartamento());
        funcionarioToUpdate.setCargo(funcionario.getCargo());
        funcionarioToUpdate.setSalario(funcionario.getSalario());
        return repository.save(funcionarioToUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) throws Exception {
        Funcionario funcionarioToDelete = repository.findById(id).orElseThrow(Exception::new);
        repository.delete(funcionarioToDelete);
    }

    @GetMapping(value = "/salarios")
    public List<BigDecimal> findAllBySalarioOrder() {
        List<Funcionario> funcionarios = repository.findAll();
        return funcionarios.stream().sorted(Comparator.comparing(Funcionario::getSalario)).map(Funcionario::getSalario).collect(Collectors.toList());
    }

    @GetMapping(value = "/ordenados")
    public List<Funcionario> findFuncionariosOrderByCreatedAt() {
        return repository.findFuncionariosOrderByCreatedAt();
    }
}
