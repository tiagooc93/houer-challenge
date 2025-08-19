package com.example.houer_challenge.service;

import com.example.houer_challenge.dto.SchoolDeleteDTO;
import com.example.houer_challenge.dto.SchoolUpdateDTO;
import com.example.houer_challenge.model.School;
import com.example.houer_challenge.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class SchoolService {

    @Autowired
    SchoolRepository schoolRepository;

    public void createSchool(School school){
        schoolRepository.save(school);
    }

    private Integer parseInteger(String value) {
        try {
            if (value == null || value.isEmpty()) return null;
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long createSchool(){
        log.info("Create a school entry on database and return its id");
        School school = new School();
        return schoolRepository.save(school).getId();
    }


    public void saveAllCsvSchools(List<Map<String, String>> csvData) {
        log.info("Save All Schools from the CSV file");
        List<School> schools = new ArrayList<>();
        for (Map<String, String> row : csvData) {
            try {
                School school = new School();
                school.setNomeDep(row.get("NOMEDEP"));
                school.setDe(row.get("DE"));
                school.setMun(row.get("MUN"));
                school.setDistr(row.get("DISTR"));
                school.setCodEsc(Integer.valueOf(row.get("CODESC")));
                school.setNomEsc(row.get("NOMESC"));
                school.setTipoEsc(Integer.valueOf(row.get("TIPOESC")));
                school.setTipoEscDesc(row.get("TIPOESC_DESC"));
                school.setCodSit(Integer.valueOf(row.get("CODSIT")));
                school.setCodEsc1(Integer.valueOf(row.get("CODESC1")));
                school.setSalasAula(Integer.valueOf(row.get("SALAS_AULA")));
                school.setSalasEdInf(Integer.valueOf(row.get("SALAS_ED_INF")));
                school.setSalasEdEsp(Integer.valueOf(row.get("SALAS_ED_ESP")));
                school.setSalasEdArt(Integer.valueOf(row.get("SALAS_ED_ART")));
                school.setSalaRecurso(Integer.valueOf(row.get("SALA_RECURSO")));
                school.setTotSalasAula(Integer.valueOf(row.get("TOT_SALAS_AULA")));
                school.setAuditorio(Integer.valueOf(row.get("AUDITORIO")));
                school.setAnfiteatro(Integer.valueOf(row.get("ANFITEATRO")));
                school.setTeatro(Integer.valueOf(row.get("TEATRO")));
                school.setCantina(Integer.valueOf(row.get("CANTINA")));
                school.setCopa(Integer.valueOf(row.get("COPA")));
                school.setCozinha(Integer.valueOf(row.get("COZINHA")));
                school.setRefeitorio(Integer.valueOf(row.get("REFEITORIO")));
                school.setDepositoAlimentos(Integer.valueOf(row.get("DEPOSITO_ALIMENTOS")));
                school.setDespensa(Integer.valueOf(row.get("DESPENSA")));
                school.setTotDespensa(Integer.valueOf(row.get("TOT_DESPENSA")));
                school.setSalaLeitura(Integer.valueOf(row.get("SALA_LEITURA")));
                school.setBiblioteca(Integer.valueOf(row.get("BIBLIOTECA")));
                school.setTotSalaLeitura(Integer.valueOf(row.get("TOT_SALA_LEITURA")));
                school.setQuadraCoberta(Integer.valueOf(row.get("QUADRA_COBERTA")));
                school.setQuadraDescoberta(Integer.valueOf(row.get("QUADRA_DESCOBERTA")));
                school.setGinasio(Integer.valueOf(row.get("GINASIO")));
                school.setTotQuadra(Integer.valueOf(row.get("TOT_QUADRA")));
                school.setQuadraAreia(Integer.valueOf(row.get("QUADRA_AREIA")));
                school.setQuadraGrama(Integer.valueOf(row.get("QUADRA_GRAMA")));
                school.setCampoFutebol(Integer.valueOf(row.get("CAMPO_FUTEBOL")));
                school.setGabineteDentario(Integer.valueOf(row.get("GABINETE_DENTARIO")));
                school.setConsultorioMedico(Integer.valueOf(row.get("CONSULTORIO_MEDICO")));
                school.setEnfermaria(Integer.valueOf(row.get("ENFERMARIA")));
                school.setAmbulatorio(Integer.valueOf(row.get("AMBULATORIO")));
                school.setAlmoxarifado(Integer.valueOf(row.get("ALMOXARIFADO")));
                school.setArquivo(Integer.valueOf(row.get("ARQUIVO")));
                school.setReprografia(Integer.valueOf(row.get("REPROGRAFIA")));
                school.setSalaGremio(Integer.valueOf(row.get("SALA_GREMIO")));
                school.setDiretoria(Integer.valueOf(row.get("DIRETORIA")));
                school.setVicediretoria(Integer.valueOf(row.get("VICEDIRETORIA")));
                school.setSalaProf(Integer.valueOf(row.get("SALA_PROF")));
                school.setSecretaria(Integer.valueOf(row.get("SECRETARIA")));
                school.setSalaOrientEd(Integer.valueOf(row.get("SALA_ORIENT_ED")));
                school.setSalaCoordPedag(Integer.valueOf(row.get("SALA_COORD_PEDAG")));
                school.setPatioCoberto(Integer.valueOf(row.get("PATIO_COBERTO")));
                school.setPatioDescoberto(Integer.valueOf(row.get("PATIO_DESCOBERTO")));
                school.setZeladoria(Integer.valueOf(row.get("ZELADORIA")));
                school.setVestiarioFem(Integer.valueOf(row.get("VESTIARIO_FEM")));
                school.setVestiarioMasc(Integer.valueOf(row.get("VESTIARIO_MASC")));
                school.setTotVestiario(Integer.valueOf(row.get("TOT_VESTIARIO")));
                school.setVideoteca(Integer.valueOf(row.get("VIDEOTECA")));
                school.setSalaTv(Integer.valueOf(row.get("SALA_TV")));
                school.setLabInfo(Integer.valueOf(row.get("LAB_INFO")));
                school.setLabCiencias(Integer.valueOf(row.get("LAB_CIENCIAS")));
                school.setLabFisica(Integer.valueOf(row.get("LAB_FISICA")));
                school.setLabQuimica(Integer.valueOf(row.get("LAB_QUIMICA")));
                school.setLabBiologia(Integer.valueOf(row.get("LAB_BIOLOGIA")));
                school.setLabCienciaFisicaBiologica(Integer.valueOf(row.get("LAB_CIENCIA_FISICA_BIOLOGICA")));
                school.setTotLabCiencia(Integer.valueOf(row.get("TOT_LAB_CIENCIA")));
                school.setLabLinguas(Integer.valueOf(row.get("LAB_LINGUAS")));
                school.setLabMultiuso(Integer.valueOf(row.get("LAB_MULTIUSO")));
                school.setOficina(Integer.valueOf(row.get("OFICINA")));
                school.setPlayground(Integer.valueOf(row.get("PLAYGROUND")));
                school.setDormitorio(Integer.valueOf(row.get("DORMITORIO")));
                school.setBercario(Integer.valueOf(row.get("BERCARIO")));
                school.setSanitarioAdeqPre(Integer.valueOf(row.get("SANITARIO_ADEQ_PRE")));
                school.setSanitarioAdeqPreFem(Integer.valueOf(row.get("SANITARIO_ADEQ_PRE_FEM")));
                school.setSanitarioAdeqPreMasc(Integer.valueOf(row.get("SANITARIO_ADEQ_PRE_MASC")));
                school.setSanitarioAdeqDef(Integer.valueOf(row.get("SANITARIO_ADEQ_DEF")));
                school.setSanitarioAdeqDefFem(Integer.valueOf(row.get("SANITARIO_ADEQ_DEF_FEM")));
                school.setSanitarioAdeqDefMasc(Integer.valueOf(row.get("SANITARIO_ADEQ_DEF_MASC")));
                school.setSanitarioAlMasc(Integer.valueOf(row.get("SANITARIO_AL_MASC")));
                school.setSanitarioAlFem(Integer.valueOf(row.get("SANITARIO_AL_FEM")));
                school.setTotSanitarioAl(Integer.valueOf(row.get("TOT_SANITARIO_AL")));
                school.setSanitarioFuncFem(Integer.valueOf(row.get("SANITARIO_FUNC_FEM")));
                school.setSanitarioFuncMasc(Integer.valueOf(row.get("SANITARIO_FUNC_MASC")));
                school.setTotSanitarioFunc(Integer.valueOf(row.get("TOT_SANITARIO_FUNC")));
                school.setDependAdeqDef(Integer.valueOf(row.get("DEPEND_ADEQ_DEF")));
                school.setSalaEdFisica(Integer.valueOf(row.get("SALA_ED_FISICA")));
                school.setPiscina(Integer.valueOf(row.get("PISCINA")));
                school.setPortaria(Integer.valueOf(row.get("PORTARIA")));
                school.setSalaProgEscFamilia(Integer.valueOf(row.get("SALA_PROG_ESC_FAMILIA")));
                school.setBrinquedoteca(Integer.valueOf(row.get("BRINQUEDOTECA")));
                school.setFraldario(Integer.valueOf(row.get("FRALDARIO")));
                school.setLactario(Integer.valueOf(row.get("LACTARIO")));
                school.setLavanderia(Integer.valueOf(row.get("LAVANDERIA")));
                school.setSolario(Integer.valueOf(row.get("SOLARIO")));
                school.setSalaEspera(Integer.valueOf(row.get("SALA_ESPERA")));
                school.setSalaInspetor(Integer.valueOf(row.get("SALA_INSPETOR")));
                school.setSalaReuniao(Integer.valueOf(row.get("SALA_REUNIAO")));
                school.setTesouraria(Integer.valueOf(row.get("TESOURARIA")));
                school.setSalaReforco(Integer.valueOf(row.get("SALA_REFORCO")));
                school.setSalaDiretorTecnico(Integer.valueOf(row.get("SALA_DIRETOR_TECNICO")));
                school.setGaragemOnibus(Integer.valueOf(row.get("GARAGEM_ONIBUS")));
                school.setSalaFisioterapia(Integer.valueOf(row.get("SALA_FISIOTERAPIA")));
                school.setSalaPsicologia(Integer.valueOf(row.get("SALA_PSICOLOGIA")));
                school.setSalaFonoaudiologia(Integer.valueOf(row.get("SALA_FONOAUDIOLOGIA")));
                school.setSalaEventos(Integer.valueOf(row.get("SALA_EVENTOS")));
                school.setSalaAssistSocial(Integer.valueOf(row.get("SALA_ASSIST_SOCIAL")));
                school.setSalaTerapiaEduc(Integer.valueOf(row.get("SALA_TERAPIA_EDUC")));
                school.setAbatedouro(Integer.valueOf(row.get("ABATEDOURO")));
                school.setAlojamentoFem(Integer.valueOf(row.get("ALOJAMENTO_FEM")));
                school.setAlojamentoMasc(Integer.valueOf(row.get("ALOJAMENTO_MASC")));
                school.setTotAlojamento(Integer.valueOf(row.get("TOT_ALOJAMENTO")));
                school.setAreaServico(Integer.valueOf(row.get("AREA_SERVICO")));
                school.setBazar(Integer.valueOf(row.get("BAZAR")));
                school.setCasaMaquina(Integer.valueOf(row.get("CASA_MAQUINA")));
                school.setCasaFunc(Integer.valueOf(row.get("CASA_FUNC")));
                school.setChurrasqueira(Integer.valueOf(row.get("CHURRASQUEIRA")));
                school.setDepositosCereais(Integer.valueOf(row.get("DEPOSITOS_CEREAIS")));
                school.setElevador(Integer.valueOf(row.get("ELEVADOR")));
                school.setEstacionamento(Integer.valueOf(row.get("ESTACIONAMENTO")));
                school.setEstufa(Integer.valueOf(row.get("ESTUFA")));
                school.setGalpaoAvesCorte(Integer.valueOf(row.get("GALPAO_AVES_CORTE")));
                school.setGalpaoAvesPostura(Integer.valueOf(row.get("GALPAO_AVES_POSTURA")));
                school.setGalpaoBovinosLeite(Integer.valueOf(row.get("GALPAO_BOVINOS_LEITE")));
                school.setGalpaoCunicultura(Integer.valueOf(row.get("GALPAO_CUNICULTURA")));
                school.setGalpaoMaqAgricola(Integer.valueOf(row.get("GALPAO_MAQ_AGRICOLA")));
                school.setGalpaoOvinosCaprinos(Integer.valueOf(row.get("GALPAO_OVINOS_CAPRINOS")));
                school.setGalpaoSuino(Integer.valueOf(row.get("GALPAO_SUINO")));
                school.setGrafica(Integer.valueOf(row.get("GRAFICA")));
                school.setHorta(Integer.valueOf(row.get("HORTA")));
                school.setLabDidatica(Integer.valueOf(row.get("LAB_DIDATICA")));
                school.setLabJunior(Integer.valueOf(row.get("LAB_JUNIOR")));
                school.setLabEnfermagem(Integer.valueOf(row.get("LAB_ENFERMAGEM")));
                school.setLabEstetica(Integer.valueOf(row.get("LAB_ESTETICA")));
                school.setLabPsicopedagogia(Integer.valueOf(row.get("LAB_PSICOPEDAGOGIA")));
                school.setLabTurismo(Integer.valueOf(row.get("LAB_TURISMO")));
                school.setLavatorio(Integer.valueOf(row.get("LAVATORIO")));
                school.setMangueira(Integer.valueOf(row.get("MANGUEIRA")));
                school.setMinhocario(Integer.valueOf(row.get("MINHOCARIO")));
                school.setPackingHouse(Integer.valueOf(row.get("PACKING_HOUSE")));
                school.setPomar(Integer.valueOf(row.get("POMAR")));
                school.setPiscicultura(Integer.valueOf(row.get("PSICULTURA")));
                school.setRecepcao(Integer.valueOf(row.get("RECEPCAO")));
                school.setSalaAtendimento(Integer.valueOf(row.get("SALA_ATENDIMENTO")));
                school.setSalaAtendPsicologico(Integer.valueOf(row.get("SALA_ATEND_PSICOLOGICO")));
                school.setSalaAuxCoordenacao(Integer.valueOf(row.get("SALA_AUX_COORDENACAO")));
                school.setSalaDados(Integer.valueOf(row.get("SALA_DADOS")));
                school.setSalaDepPessoal(Integer.valueOf(row.get("SALA_DEP_PESSOAL")));
                school.setSalaEdReligiosa(Integer.valueOf(row.get("SALA_ED_RELIGIOSA")));
                school.setSalaEnergiaEletrica(Integer.valueOf(row.get("SALA_ENERGIA_ELETRICA")));
                school.setSalaEntretenimento(Integer.valueOf(row.get("SALA_ENTRETENIMENTO")));
                school.setSalaEstagio(Integer.valueOf(row.get("SALA_ESTAGIO")));
                school.setSalaGinastica(Integer.valueOf(row.get("SALA_GINASTICA")));
                school.setSalaInsumoAgricola(Integer.valueOf(row.get("SALA_INSUMO_AGRICOLA")));
                school.setSalaInsumoVeterinario(Integer.valueOf(row.get("SALA_INSUMO_VETERINARIO")));
                school.setSalaMarketing(Integer.valueOf(row.get("SALA_MARKETING")));
                school.setSalaMatricula(Integer.valueOf(row.get("SALA_MATRICULA")));
                school.setSalaMusica(Integer.valueOf(row.get("SALA_MUSICA")));
                school.setSalaPosGraduacao(Integer.valueOf(row.get("SALA_POS_GRADUACAO")));
                school.setSalaOrdenha(Integer.valueOf(row.get("SALA_ORDENHA")));
                school.setSalaProcProdAgropecuarios(Integer.valueOf(row.get("SALA_PROC_PROD_AGROPECUARIOS")));
                school.setSalaSeguranca(Integer.valueOf(row.get("SALA_SEGURANCA")));
                school.setSalaTelefonia(Integer.valueOf(row.get("SALA_TELEFONIA")));
                school.setSalaFinanceiro(Integer.valueOf(row.get("SALA_FINANCEIRO")));
                school.setSalaPastoral(Integer.valueOf(row.get("SALA_PASTORAL")));
                school.setSalaReservAgua(Integer.valueOf(row.get("SALA_RESERV_AGUA")));
                school.setServidor(Integer.valueOf(row.get("SERVIDOR")));
                school.setSilo(Integer.valueOf(row.get("SILO")));
                school.setVaranda(Integer.valueOf(row.get("VARANDA")));
                school.setViveiro(Integer.valueOf(row.get("VIVEIRO")));
                school.setSalaReorgNeuro(Integer.valueOf(row.get("SALA_REORG_NEURO")));
                school.setSalaTerapiaOcup(Integer.valueOf(row.get("SALA_TERAPIA_OCUP")));
                school.setSalaSerigrafia(Integer.valueOf(row.get("SALA_SERIGRAFIA")));
                school.setSalaMarcenaria(Integer.valueOf(row.get("SALA_MARCENARIA")));
                school.setQuiosque(Integer.valueOf(row.get("QUIOSQUE")));

                schools.add(school);
            }
            catch(Exception e){
                log.warn("Mistake on Row {}", row);
            }
        }
        try {
            schoolRepository.saveAll(schools);
        } catch (DataIntegrityViolationException ex) {
            log.error("Integrity error while saving schools: {}", ex.getMessage());
        } catch (JpaSystemException | TransactionSystemException ex) {
            log.error("JPA/Hibernate error while saving schools: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error while saving schools: {}", ex.getMessage(), ex);
        }
    }
    public List<School> getAllSchools() {
        log.info("List all schools");
        return schoolRepository.findAllByOrderByCsvIndexAsc();
    }

    public void deleteSchool(SchoolDeleteDTO schoolDeleteDTO) {
        log.info("Deleting schools data: {}", schoolDeleteDTO.toString());
        if (schoolDeleteDTO.schoolIds() == null || schoolDeleteDTO.schoolIds().isEmpty()) {
            throw new IllegalArgumentException("No ID sent for delete !");
        }

        schoolRepository.deleteByIdIn(schoolDeleteDTO.schoolIds());
    }

    public void updateSchool(SchoolUpdateDTO schoolUpdateDTO) {
        log.info("Updating school data: {}", schoolUpdateDTO.toString());

        if (schoolUpdateDTO.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Optional<School> optionalSchool = schoolRepository.findById(schoolUpdateDTO.getId());
        if (!optionalSchool.isPresent()) {
            throw new EntityNotFoundException("School not found with id: " + schoolUpdateDTO.getId());
        }

        School school = optionalSchool.get();
        if (schoolUpdateDTO.getNomeDep() != null) school.setNomeDep(schoolUpdateDTO.getNomeDep());
        if (schoolUpdateDTO.getDe() != null) school.setDe(schoolUpdateDTO.getDe());
        if (schoolUpdateDTO.getMun() != null) school.setMun(schoolUpdateDTO.getMun());
        if (schoolUpdateDTO.getDistr() != null) school.setDistr(schoolUpdateDTO.getDistr());
        if (schoolUpdateDTO.getCodEsc() != null) school.setCodEsc(schoolUpdateDTO.getCodEsc());
        if (schoolUpdateDTO.getNomEsc() != null) school.setNomEsc(schoolUpdateDTO.getNomEsc());
        if (schoolUpdateDTO.getTipoEsc() != null) school.setTipoEsc(schoolUpdateDTO.getTipoEsc());
        if (schoolUpdateDTO.getTipoEscDesc() != null) school.setTipoEscDesc(schoolUpdateDTO.getTipoEscDesc());
        if (schoolUpdateDTO.getCodSit() != null) school.setCodSit(schoolUpdateDTO.getCodSit());
        if (schoolUpdateDTO.getCodEsc1() != null) school.setCodEsc1(schoolUpdateDTO.getCodEsc1());

        if (schoolUpdateDTO.getSalasAula() != null) school.setSalasAula(schoolUpdateDTO.getSalasAula());
        if (schoolUpdateDTO.getSalasEdInf() != null) school.setSalasEdInf(schoolUpdateDTO.getSalasEdInf());
        if (schoolUpdateDTO.getSalasEdEsp() != null) school.setSalasEdEsp(schoolUpdateDTO.getSalasEdEsp());
        if (schoolUpdateDTO.getSalasEdArt() != null) school.setSalasEdArt(schoolUpdateDTO.getSalasEdArt());
        if (schoolUpdateDTO.getSalaRecurso() != null) school.setSalaRecurso(schoolUpdateDTO.getSalaRecurso());
        if (schoolUpdateDTO.getTotSalasAula() != null) school.setTotSalasAula(schoolUpdateDTO.getTotSalasAula());
        if (schoolUpdateDTO.getAuditorio() != null) school.setAuditorio(schoolUpdateDTO.getAuditorio());
        if (schoolUpdateDTO.getAnfiteatro() != null) school.setAnfiteatro(schoolUpdateDTO.getAnfiteatro());
        if (schoolUpdateDTO.getTeatro() != null) school.setTeatro(schoolUpdateDTO.getTeatro());
        if (schoolUpdateDTO.getCantina() != null) school.setCantina(schoolUpdateDTO.getCantina());

        if (schoolUpdateDTO.getCopa() != null) school.setCopa(schoolUpdateDTO.getCopa());
        if (schoolUpdateDTO.getCozinha() != null) school.setCozinha(schoolUpdateDTO.getCozinha());
        if (schoolUpdateDTO.getRefeitorio() != null) school.setRefeitorio(schoolUpdateDTO.getRefeitorio());
        if (schoolUpdateDTO.getDepositoAlimentos() != null)
            school.setDepositoAlimentos(schoolUpdateDTO.getDepositoAlimentos());
        if (schoolUpdateDTO.getDespensa() != null) school.setDespensa(schoolUpdateDTO.getDespensa());
        if (schoolUpdateDTO.getTotDespensa() != null) school.setTotDespensa(schoolUpdateDTO.getTotDespensa());
        if (schoolUpdateDTO.getSalaLeitura() != null) school.setSalaLeitura(schoolUpdateDTO.getSalaLeitura());
        if (schoolUpdateDTO.getBiblioteca() != null) school.setBiblioteca(schoolUpdateDTO.getBiblioteca());
        if (schoolUpdateDTO.getTotSalaLeitura() != null) school.setTotSalaLeitura(schoolUpdateDTO.getTotSalaLeitura());
        if (schoolUpdateDTO.getQuadraCoberta() != null) school.setQuadraCoberta(schoolUpdateDTO.getQuadraCoberta());

        if (schoolUpdateDTO.getQuadraDescoberta() != null)
            school.setQuadraDescoberta(schoolUpdateDTO.getQuadraDescoberta());
        if (schoolUpdateDTO.getGinasio() != null) school.setGinasio(schoolUpdateDTO.getGinasio());
        if (schoolUpdateDTO.getTotQuadra() != null) school.setTotQuadra(schoolUpdateDTO.getTotQuadra());
        if (schoolUpdateDTO.getQuadraAreia() != null) school.setQuadraAreia(schoolUpdateDTO.getQuadraAreia());
        if (schoolUpdateDTO.getQuadraGrama() != null) school.setQuadraGrama(schoolUpdateDTO.getQuadraGrama());
        if (schoolUpdateDTO.getCampoFutebol() != null) school.setCampoFutebol(schoolUpdateDTO.getCampoFutebol());
        if (schoolUpdateDTO.getGabineteDentario() != null)
            school.setGabineteDentario(schoolUpdateDTO.getGabineteDentario());
        if (schoolUpdateDTO.getConsultorioMedico() != null)
            school.setConsultorioMedico(schoolUpdateDTO.getConsultorioMedico());
        if (schoolUpdateDTO.getEnfermaria() != null) school.setEnfermaria(schoolUpdateDTO.getEnfermaria());

        if (schoolUpdateDTO.getAmbulatorio() != null) school.setAmbulatorio(schoolUpdateDTO.getAmbulatorio());
        if (schoolUpdateDTO.getAlmoxarifado() != null) school.setAlmoxarifado(schoolUpdateDTO.getAlmoxarifado());
        if (schoolUpdateDTO.getArquivo() != null) school.setArquivo(schoolUpdateDTO.getArquivo());
        if (schoolUpdateDTO.getReprografia() != null) school.setReprografia(schoolUpdateDTO.getReprografia());
        if (schoolUpdateDTO.getSalaGremio() != null) school.setSalaGremio(schoolUpdateDTO.getSalaGremio());
        if (schoolUpdateDTO.getDiretoria() != null) school.setDiretoria(schoolUpdateDTO.getDiretoria());
        if (schoolUpdateDTO.getVicediretoria() != null) school.setVicediretoria(schoolUpdateDTO.getVicediretoria());
        if (schoolUpdateDTO.getSalaProf() != null) school.setSalaProf(schoolUpdateDTO.getSalaProf());
        if (schoolUpdateDTO.getSecretaria() != null) school.setSecretaria(schoolUpdateDTO.getSecretaria());
        if (schoolUpdateDTO.getSalaOrientEd() != null) school.setSalaOrientEd(schoolUpdateDTO.getSalaOrientEd());

        if (schoolUpdateDTO.getSalaCoordPedag() != null) school.setSalaCoordPedag(schoolUpdateDTO.getSalaCoordPedag());
        if (schoolUpdateDTO.getPatioCoberto() != null) school.setPatioCoberto(schoolUpdateDTO.getPatioCoberto());
        if (schoolUpdateDTO.getPatioDescoberto() != null)
            school.setPatioDescoberto(schoolUpdateDTO.getPatioDescoberto());
        if (schoolUpdateDTO.getZeladoria() != null) school.setZeladoria(schoolUpdateDTO.getZeladoria());
        if (schoolUpdateDTO.getVestiarioFem() != null) school.setVestiarioFem(schoolUpdateDTO.getVestiarioFem());
        if (schoolUpdateDTO.getVestiarioMasc() != null) school.setVestiarioMasc(schoolUpdateDTO.getVestiarioMasc());
        if (schoolUpdateDTO.getTotVestiario() != null) school.setTotVestiario(schoolUpdateDTO.getTotVestiario());
        if (schoolUpdateDTO.getVideoteca() != null) school.setVideoteca(schoolUpdateDTO.getVideoteca());
        if (schoolUpdateDTO.getSalaTv() != null) school.setSalaTv(schoolUpdateDTO.getSalaTv());
        if (schoolUpdateDTO.getLabInfo() != null) school.setLabInfo(schoolUpdateDTO.getLabInfo());

        if (schoolUpdateDTO.getLabCiencias() != null) school.setLabCiencias(schoolUpdateDTO.getLabCiencias());
        if (schoolUpdateDTO.getLabFisica() != null) school.setLabFisica(schoolUpdateDTO.getLabFisica());
        if (schoolUpdateDTO.getLabQuimica() != null) school.setLabQuimica(schoolUpdateDTO.getLabQuimica());
        if (schoolUpdateDTO.getLabBiologia() != null) school.setLabBiologia(schoolUpdateDTO.getLabBiologia());
        if (schoolUpdateDTO.getLabCienciaFisicaBiologica() != null)
            school.setLabCienciaFisicaBiologica(schoolUpdateDTO.getLabCienciaFisicaBiologica());
        if (schoolUpdateDTO.getTotLabCiencia() != null) school.setTotLabCiencia(schoolUpdateDTO.getTotLabCiencia());
        if (schoolUpdateDTO.getLabLinguas() != null) school.setLabLinguas(schoolUpdateDTO.getLabLinguas());
        if (schoolUpdateDTO.getLabMultiuso() != null) school.setLabMultiuso(schoolUpdateDTO.getLabMultiuso());
        if (schoolUpdateDTO.getOficina() != null) school.setOficina(schoolUpdateDTO.getOficina());
        if (schoolUpdateDTO.getPlayground() != null) school.setPlayground(schoolUpdateDTO.getPlayground());

        if (schoolUpdateDTO.getDormitorio() != null) school.setDormitorio(schoolUpdateDTO.getDormitorio());

        schoolRepository.save(school);
    }

    public static String clean(String s) {
        if (s == null) return "";
        if (s.startsWith("\uFEFF")) s = s.substring(1);
        return s.trim().replaceAll("\\p{C}", "");
    }

    public static List<Map<String, String>> parseCsv(MultipartFile file) throws IOException {
        List<Map<String, String>> result = new ArrayList<>();

        CSVFormat FORMAT = CSVFormat.Builder.create()
                .setDelimiter(';')
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String headerLine = reader.readLine();
            if (headerLine == null) return result;

            String[] rawHeaders = headerLine.split(";", -1);

            Map<String, Integer> headerCount = new HashMap<>();
            List<String> headers = new ArrayList<>();
            for (String rawHeader : rawHeaders) {
                String h = clean(rawHeader);
                int count = headerCount.getOrDefault(h, 0);
                headerCount.put(h, count + 1);
                if (count > 0) {
                    h = h + count;
                }
                headers.add(h);
            }
            Iterable<CSVRecord> records = FORMAT.parse(reader);

            for (CSVRecord record : records) {
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    String value = i < record.size() ? clean(record.get(i)) : "";
                    row.put(headers.get(i), value);
                }
                result.add(row);
            }
        }
        return result;
    }

    public static List<Map<String, String>> convertSchoolsToMap(List<School> schools) {
        List<Map<String, String>> result = new ArrayList<>();

        List<String> columnOrder = Arrays.asList(
                "id", "csvIndex", "nomeDep", "de", "mun", "distr", "codEsc", "nomEsc",
                "tipoEsc", "tipoEscDesc", "codSit", "codEsc1", "salasAula", "salasEdInf", "salasEdEsp", "salasEdArt",
                "salaRecurso", "totSalasAula", "auditorio", "anfiteatro", "teatro", "cantina", "copa", "cozinha",
                "refeitorio", "depositoAlimentos", "despensa", "totDespensa", "salaLeitura", "biblioteca", "totSalaLeitura", "quadraCoberta",
                "quadraDescoberta", "ginasio", "totQuadra", "quadraAreia", "quadraGrama", "campoFutebol", "gabineteDentario", "consultorioMedico",
                "enfermaria", "ambulatorio", "almoxarifado", "arquivo", "reprografia", "salaGremio", "diretoria", "vicediretoria",
                "salaProf", "secretaria", "salaOrientEd", "salaCoordPedag", "patioCoberto", "patioDescoberto", "zeladoria", "vestiarioFem",
                "vestiarioMasc", "totVestiario", "videoteca", "salaTv", "labInfo", "labCiencias", "labFisica", "labQuimica",
                "labBiologia", "labCienciaFisicaBiologica", "totLabCiencia", "labLinguas", "labMultiuso", "oficina", "playground", "dormitorio",
                "bercario", "sanitarioAdeqPre", "sanitarioAdeqPreFem", "sanitarioAdeqPreMasc", "sanitarioAdeqDef", "sanitarioAdeqDefFem", "sanitarioAdeqDefMasc", "sanitarioAlMasc",
                "sanitarioAlFem", "totSanitarioAl", "sanitarioFuncFem", "sanitarioFuncMasc", "totSanitarioFunc", "dependAdeqDef", "salaEdFisica", "piscina",
                "portaria", "salaProgEscFamilia", "brinquedoteca", "fraldario", "lactario", "lavanderia", "solario", "salaEspera",
                "salaInspetor", "salaReuniao", "tesouraria", "salaReforco", "salaDiretorTecnico", "garagemOnibus", "salaFisioterapia", "salaPsicologia",
                "salaFonoaudiologia", "salaEventos", "salaAssistSocial", "salaTerapiaEduc", "abatedouro", "alojamentoFem", "alojamentoMasc", "totAlojamento",
                "areaServico", "bazar", "casaMaquina", "casaFunc", "churrasqueira", "depositosCereais", "elevador", "estacionamento",
                "estufa", "galpaoAvesCorte", "galpaoAvesPostura", "galpaoBovinosLeite", "galpaoCunicultura", "galpaoMaqAgricola", "galpaoOvinosCaprinos", "galpaoSuino",
                "grafica", "horta", "labDidatica", "labJunior", "labEnfermagem", "labEstetica", "labPsicopedagogia", "labTurismo",
                "lavatorio", "mangueira", "minhocario", "packingHouse", "pomar", "piscicultura", "recepcao", "salaAtendimento",
                "salaAtendPsicologico", "salaAuxCoordenacao", "salaDados", "salaDepPessoal", "salaEdReligiosa", "salaEnergiaEletrica", "salaEntretenimento", "salaEstagio",
                "salaGinastica", "salaInsumoAgricola", "salaInsumoVeterinario", "salaMarketing", "salaMatricula", "salaMusica", "salaPosGraduacao", "salaOrdenha",
                "salaProcProdAgropecuarios", "salaSeguranca", "salaTelefonia", "salaFinanceiro", "salaPastoral", "salaReservAgua", "servidor", "silo",
                "varanda", "viveiro", "salaReorgNeuro", "salaTerapiaOcup", "salaSerigrafia", "salaMarcenaria", "quiosque"
        );
        for (School school : schools) {
            Map<String, String> map = new LinkedHashMap<>();

            for (String column : columnOrder) {
                try {
                    Field field = School.class.getDeclaredField(column);
                    field.setAccessible(true);
                    Object value = field.get(school);
                    map.put(column, value != null ? value.toString() : "");
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    map.put(column, "");
                }
            }

            result.add(map);
        }

        return result;
    }
}
