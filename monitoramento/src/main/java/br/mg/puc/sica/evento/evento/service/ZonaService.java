package br.mg.puc.sica.evento.evento.service;

import br.mg.puc.sica.evento.evento.model.Envolvidos;
import br.mg.puc.sica.evento.evento.model.Sensor;
import br.mg.puc.sica.evento.evento.model.Zona;
import br.mg.puc.sica.evento.evento.model.request.ZonaRequest;
import br.mg.puc.sica.evento.evento.model.response.EnvolvidoResponse;
import br.mg.puc.sica.evento.evento.model.response.SensorResponse;
import br.mg.puc.sica.evento.evento.model.response.ZonaResponse;
import br.mg.puc.sica.evento.evento.repositoy.ZonaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ZonaService {

  @Autowired
  ZonaRepository zonaRepository;

  public List<ZonaResponse> getZonas() {
    List<ZonaResponse> responseList = new ArrayList<>();
    List<Zona> zonaList = zonaRepository.findAll();
    for (Zona obj: zonaList) {
      ZonaResponse zonaResponse = new ZonaResponse();
      zonaResponse.setIdZona(obj.getIdZona());
      zonaResponse.setNomeZona(obj.getNomeZona());
      zonaResponse.setSensores(convertToSensorResponse(obj.getSensores()));
      zonaResponse.setEnvolvidos(convertToEnvolvidoResponse(obj.getEnvolvidos()));
      responseList.add(zonaResponse);
    }
    return responseList;
  }

  private List<SensorResponse> convertToSensorResponse (List<Sensor> sensores) {
    List<SensorResponse> listSensorResponse = new ArrayList<>();
    for (Sensor obj: sensores) {
      SensorResponse sensorResponse = new SensorResponse();
      sensorResponse.setIdSensor(obj.getIdSensor());
      sensorResponse.setLocalizacao(obj.getLocalizacao());
      sensorResponse.setNomeSensor(obj.getNomeSensor());
      listSensorResponse.add(sensorResponse);
    }
    return listSensorResponse;
  }

  private List<EnvolvidoResponse> convertToEnvolvidoResponse (List<Envolvidos> envolvidos) {
    List<EnvolvidoResponse> envolvidosResponseList = new ArrayList<>();
    for (Envolvidos obj: envolvidos) {
      EnvolvidoResponse envolvidoResponse = new EnvolvidoResponse();
      envolvidoResponse.setIdEnvolvido(obj.getIdEnvolvido());
      envolvidoResponse.setEmail(obj.getEmail());
      envolvidoResponse.setCategoria(obj.getCategoria());
      envolvidosResponseList.add(envolvidoResponse);
    }
    return envolvidosResponseList;
  }

  public void criarZona(ZonaRequest zonaRequest) {
    Zona zona = Zona.builder().nomeZona(zonaRequest.getNomeZona()).build();
    zonaRepository.save(zona);
  }

  public Zona recuperarZona(Long idZona) {
    Optional<Zona> zona = zonaRepository.findById(idZona);
    if (!zona.isPresent()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Zona não encontrada");
    }
    return zona.get();
  }
}
