package ceiba.CeibaEstacionamiento.dominio.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ceiba.CeibaEstacionamiento.dominio.Vehiculo;

@Repository
public interface RepositorioVehiculo extends JpaRepository<Vehiculo, String>{
	List<Vehiculo> findByTipo(String name);
}
