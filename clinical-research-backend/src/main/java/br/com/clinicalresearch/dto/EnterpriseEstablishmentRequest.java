package br.com.clinicalresearch.dto;

public class EnterpriseEstablishmentRequest {

    private Long idEnterprise;
    private Long idEstablishment;

    public Long getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Long idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public Long getIdEstablishment() {
        return idEstablishment;
    }

    public void setIdEstablishment(Long idEstablishment) {
        this.idEstablishment = idEstablishment;
    }
}
