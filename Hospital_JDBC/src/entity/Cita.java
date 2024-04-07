package entity;

import java.sql.Time;
import java.util.Date;

public class Cita {
    private int id;
    private int id_paciente;

    private int id_medico;
    private String fecha_cita;
    private String hora_cita;
    private String motivo;

    private Medico objMedico;
    private Paciente objPaciente;

    public Cita() {
    }

    public Cita(int id_paciente, int id_medico, String fecha_cita, String hora_cita, String motivo, Medico objMedico, Paciente objPaciente) {
        this.id_paciente = id_paciente;
        this.id_medico = id_medico;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        this.motivo = motivo;
        this.objMedico = objMedico;
        this.objPaciente = objPaciente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public String getFecha_cita() {
        return fecha_cita;
    }

    public void setFecha_cita(String fecha_cita) {
        this.fecha_cita = fecha_cita;
    }

    public String getHora_cita() {
        return hora_cita;
    }

    public void setHora_cita(String hora_cita) {
        this.hora_cita = hora_cita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Medico getObjMedico() {
        return objMedico;
    }

    public void setObjMedico(Medico objMedico) {
        this.objMedico = objMedico;
    }

    public Paciente getObjPaciente() {
        return objPaciente;
    }

    public void setObjPaciente(Paciente objPaciente) {
        this.objPaciente = objPaciente;
    }

    @Override
    public String toString() {
        return "Cita \n" +
                "fecha_cita='" + fecha_cita + '\'' +
                "hora_cita='" + hora_cita + '\'' +
                "motivo='" + motivo + '\n' +
                "Medico= " + objMedico.getNombre() + " " + objMedico.getApellidos() +'\n' +
                "Paciente= " + objPaciente.getNombre()+ " " + objPaciente.getApellidos();
    }
}
