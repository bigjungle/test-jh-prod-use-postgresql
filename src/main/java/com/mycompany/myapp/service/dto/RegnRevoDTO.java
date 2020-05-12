package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.RegnRevo} entity.
 */
@ApiModel(description = "Training institution registration revocation 培训机构资质吊销表\n@author JasonYang")
public class RegnRevoDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 256)
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 描述
     */
    @Size(max = 256)
    @ApiModelProperty(value = "描述")
    private String descString;

    /**
     * 资质证书编号
     */
    @NotNull
    @Size(max = 4000)
    @ApiModelProperty(value = "资质证书编号", required = true)
    private String orgInfo;

    /**
     * 吊销时长
     */
    @NotNull
    @ApiModelProperty(value = "吊销时长", required = true)
    private Integer revokeTimeSpan;

    /**
     * 吊销开始
     */
    @NotNull
    @ApiModelProperty(value = "吊销开始", required = true)
    private LocalDate revokeStart;

    /**
     * 吊销结束
     */
    @NotNull
    @ApiModelProperty(value = "吊销结束", required = true)
    private LocalDate revokeOver;

    /**
     * 处罚机关
     */
    @Size(max = 256)
    @ApiModelProperty(value = "处罚机关")
    private String punishOrg;

    /**
     * 处罚时间
     */
    @ApiModelProperty(value = "处罚时间")
    private Instant punishTime;

    /**
     * 事实依据
     */
    @Size(max = 256)
    @ApiModelProperty(value = "事实依据")
    private String facts;

    /**
     * 期满自动处理(是否自动恢复)
     */
    @ApiModelProperty(value = "期满自动处理(是否自动恢复)")
    private Boolean autoProcess;

    /**
     * 凭证上传(存储路径)
     */
    @Size(max = 256)
    @ApiModelProperty(value = "凭证上传(存储路径)")
    private String revokeProof;

    /**
     * 备注
     */
    @Size(max = 256)
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 处罚人
     */
    @ApiModelProperty(value = "处罚人")

    private Long punishPersonId;

    private String punishPersonLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescString() {
        return descString;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
    }

    public Integer getRevokeTimeSpan() {
        return revokeTimeSpan;
    }

    public void setRevokeTimeSpan(Integer revokeTimeSpan) {
        this.revokeTimeSpan = revokeTimeSpan;
    }

    public LocalDate getRevokeStart() {
        return revokeStart;
    }

    public void setRevokeStart(LocalDate revokeStart) {
        this.revokeStart = revokeStart;
    }

    public LocalDate getRevokeOver() {
        return revokeOver;
    }

    public void setRevokeOver(LocalDate revokeOver) {
        this.revokeOver = revokeOver;
    }

    public String getPunishOrg() {
        return punishOrg;
    }

    public void setPunishOrg(String punishOrg) {
        this.punishOrg = punishOrg;
    }

    public Instant getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(Instant punishTime) {
        this.punishTime = punishTime;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public Boolean isAutoProcess() {
        return autoProcess;
    }

    public void setAutoProcess(Boolean autoProcess) {
        this.autoProcess = autoProcess;
    }

    public String getRevokeProof() {
        return revokeProof;
    }

    public void setRevokeProof(String revokeProof) {
        this.revokeProof = revokeProof;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getPunishPersonId() {
        return punishPersonId;
    }

    public void setPunishPersonId(Long userId) {
        this.punishPersonId = userId;
    }

    public String getPunishPersonLastName() {
        return punishPersonLastName;
    }

    public void setPunishPersonLastName(String userLastName) {
        this.punishPersonLastName = userLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegnRevoDTO regnRevoDTO = (RegnRevoDTO) o;
        if (regnRevoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regnRevoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegnRevoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descString='" + getDescString() + "'" +
            ", orgInfo='" + getOrgInfo() + "'" +
            ", revokeTimeSpan=" + getRevokeTimeSpan() +
            ", revokeStart='" + getRevokeStart() + "'" +
            ", revokeOver='" + getRevokeOver() + "'" +
            ", punishOrg='" + getPunishOrg() + "'" +
            ", punishTime='" + getPunishTime() + "'" +
            ", facts='" + getFacts() + "'" +
            ", autoProcess='" + isAutoProcess() + "'" +
            ", revokeProof='" + getRevokeProof() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", punishPersonId=" + getPunishPersonId() +
            ", punishPersonLastName='" + getPunishPersonLastName() + "'" +
            "}";
    }
}
