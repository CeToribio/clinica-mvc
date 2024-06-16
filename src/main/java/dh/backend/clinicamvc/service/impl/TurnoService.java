package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.Dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.Dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.Dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.Dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.repository.IturnoRepository;
import dh.backend.clinicamvc.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private IOdontologoRepository odontologoRepository;
    private IPacienteRepository pacienteRepository;
    private IturnoRepository turnoRepository;
    private ModelMapper modelMapper;

    public TurnoService(IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository, IturnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto registrar(TurnoRequestDto turnoRequestDto) {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        Turno turnoARegistrar = new Turno();
        Turno turnoGuardado = null;
        TurnoResponseDto turnoADevolver = null;
        if(paciente.isPresent() && odontologo.isPresent()){

            turnoARegistrar.setOdontologo(odontologo.get());
            turnoARegistrar.setPaciente(paciente.get());
            turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoGuardado = turnoRepository.save(turnoARegistrar);
            LOGGER.info("Turno registrado: " + turnoGuardado);

            turnoADevolver = mapToResponseDto(turnoGuardado);
        }

        return turnoADevolver;
    }

    @Override
    public TurnoResponseDto buscarPorId(Integer id) {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        if(turnoOptional.isPresent()){
            Turno turnoEncontrado = turnoOptional.get();
            LOGGER.info("Turno encontrado: " + id);
            TurnoResponseDto turnoADevolver = mapToResponseDto(turnoEncontrado);
            return turnoADevolver;
        }
        return null;
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        LOGGER.info("Listado de turnos");
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for(Turno turno: turnos){
            turnoAuxiliar = mapToResponseDto(turno);
            turnosADevolver.add(turnoAuxiliar);
        }
        return turnosADevolver;
    }

    @Override
    public void actualizarTurno(Integer id, TurnoRequestDto turnoRequestDto) {
        Optional<Paciente> paciente = pacienteRepository.findById(turnoRequestDto.getPaciente_id());
        LOGGER.info("Paciente actualizado en turno: " + id);
        Optional<Odontologo> odontologo = odontologoRepository.findById(turnoRequestDto.getOdontologo_id());
        LOGGER.info("Odontologo actualizado en turno: " + id);
        Optional<Turno> turno = turnoRepository.findById(id);
        LOGGER.info("Turno actualizado: " + id);
        Turno turnoAModificar = new Turno();
        if(paciente.isPresent() && odontologo.isPresent() && turno.isPresent()){
            turnoAModificar.setId(id);
            turnoAModificar.setOdontologo(odontologo.get());
            turnoAModificar.setPaciente(paciente.get());
            turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoRepository.save(turnoAModificar);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        LOGGER.info("Turno eliminado: " + id);
        turnoRepository.deleteById(id);
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<Turno> listadoTurnos = turnoRepository.buscarTurnoEntreFechas(startDate, endDate);
        LOGGER.info("Listado de turnos entre fechas startDate: "+ startDate + " endDate: " + endDate );
        List<TurnoResponseDto> listadoARetornar = new ArrayList<>();
        TurnoResponseDto turnoAuxiliar = null;
        for (Turno turno: listadoTurnos){
            turnoAuxiliar = mapToResponseDto(turno);
            listadoARetornar.add(turnoAuxiliar);
        }
        return listadoARetornar;
    }


    // metodo que mapea turno en turnoResponseDto
    private TurnoResponseDto mapToResponseDto(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return  turnoResponseDto;
    }
}
