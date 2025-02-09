package com.daw.services.mappers;

import java.util.ArrayList;
import java.util.List;

import com.daw.persistence.entities.Desayuno;
import com.daw.persistence.entities.Establecimiento;
import com.daw.services.dto.DesayunoDTO;
import com.daw.services.dto.EstablecimientoDTO;

public class EstablecimientoMapper {
    public static EstablecimientoDTO toDTO(Establecimiento establecimiento) {
        EstablecimientoDTO dto = new EstablecimientoDTO();
        dto.setId(establecimiento.getId());
        dto.setNombre(establecimiento.getNombre());
        dto.setUbicacion(establecimiento.getUbicacion());
        dto.setPuntuacion(establecimiento.getPuntuacion());
        
        List<DesayunoDTO> desayunoDTOs = new ArrayList<>();
        for (Desayuno d : establecimiento.getDesayunos()) {
            desayunoDTOs.add(DesayunoMapper.toDTO(d));
        }
        dto.setDesayunos(desayunoDTOs);
        return dto;
    }
}

