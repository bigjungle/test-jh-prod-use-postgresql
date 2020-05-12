package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Training institution registration revocation 培训机构资质吊销表\n@author JasonYang
 */
@Entity
@Table(name = "regn_revo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegnRevo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 256)
    @Column(name = "name", length = 256, nullable = false)
    private String name;

    /**
     * 描述
     */
    @Size(max = 256)
    @Column(name = "desc_string", length = 256)
    private String descString;

    /**
     * 资质证书编号
     */
    @NotNull
    @Size(max = 4000)
    @Column(name = "org_info", length = 4000, nullable = false)
    private String orgInfo;

    /**
     * 吊销时长
     */
    @NotNull
    @Column(name = "revoke_time_span", nullable = false)
    private Integer revokeTimeSpan;

    /**
     * 吊销开始
     */
    @NotNull
    @Column(name = "revoke_start", nullable = false)
    private LocalDate revokeStart;

    /**
     * 吊销结束
     */
    @NotNull
    @Column(name = "revoke_over", nullable = false)
    private LocalDate revokeOver;

    /**
     * 处罚机关
     */
    @Size(max = 256)
    @Column(name = "punish_org", length = 256)
    private String punishOrg;

    /**
     * 处罚时间
     */
    @Column(name = "punish_time")
    private Instant punishTime;

    /**
     * 事实依据
     */
    @Size(max = 256)
    @Column(name = "facts", length = 256)
    private String facts;

    /**
     * 期满自动处理(是否自动恢复)
     */
    @Column(name = "auto_process")
    private Boolean autoProcess;

    /**
     * 凭证上传(存储路径)
     */
    @Size(max = 256)
    @Column(name = "revoke_proof", length = 256)
    private String revokeProof;

    /**
     * 备注
     */
    @Size(max = 256)
    @Column(name = "remarks", length = 256)
    private String remarks;

    /**
     * 处罚人
     */
    @ManyToOne
    @JsonIgnoreProperties("regnRevos")
    private User punishPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public RegnRevo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescString() {
        return descString;
    }

    public RegnRevo descString(String descString) {
        this.descString = descString;
        return this;
    }

    public void setDescString(String descString) {
        this.descString = descString;
    }

    public String getOrgInfo() {
        return orgInfo;
    }

    public RegnRevo orgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
        return this;
    }

    public void setOrgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
    }

    public Integer getRevokeTimeSpan() {
        return revokeTimeSpan;
    }

    public RegnRevo revokeTimeSpan(Integer revokeTimeSpan) {
        this.revokeTimeSpan = revokeTimeSpan;
        return this;
    }

    public void setRevokeTimeSpan(Integer revokeTimeSpan) {
        this.revokeTimeSpan = revokeTimeSpan;
    }

    public LocalDate getRevokeStart() {
        return revokeStart;
    }

    public RegnRevo revokeStart(LocalDate revokeStart) {
        this.revokeStart = revokeStart;
        return this;
    }

    public void setRevokeStart(LocalDate revokeStart) {
        this.revokeStart = revokeStart;
    }

    public LocalDate getRevokeOver() {
        return revokeOver;
    }

    public RegnRevo revokeOver(LocalDate revokeOver) {
        this.revokeOver = revokeOver;
        return this;
    }

    public void setRevokeOver(LocalDate revokeOver) {
        this.revokeOver = revokeOver;
    }

    public String getPunishOrg() {
        return punishOrg;
    }

    public RegnRevo punishOrg(String punishOrg) {
        this.punishOrg = punishOrg;
        return this;
    }

    public void setPunishOrg(String punishOrg) {
        this.punishOrg = punishOrg;
    }

    public Instant getPunishTime() {
        return punishTime;
    }

    public RegnRevo punishTime(Instant punishTime) {
        this.punishTime = punishTime;
        return this;
    }

    public void setPunishTime(Instant punishTime) {
        this.punishTime = punishTime;
    }

    public String getFacts() {
        return facts;
    }

    public RegnRevo facts(String facts) {
        this.facts = facts;
        return this;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public Boolean isAutoProcess() {
        return autoProcess;
    }

    public RegnRevo autoProcess(Boolean autoProcess) {
        this.autoProcess = autoProcess;
        return this;
    }

    public void setAutoProcess(Boolean autoProcess) {
        this.autoProcess = autoProcess;
    }

    public String getRevokeProof() {
        return revokeProof;
    }

    public RegnRevo revokeProof(String revokeProof) {
        this.revokeProof = revokeProof;
        return this;
    }

    public void setRevokeProof(String revokeProof) {
        this.revokeProof = revokeProof;
    }

    public String getRemarks() {
        return remarks;
    }

    public RegnRevo remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public User getPunishPerson() {
        return punishPerson;
    }

    public RegnRevo punishPerson(User user) {
        this.punishPerson = user;
        return this;
    }

    public void setPunishPerson(User user) {
        this.punishPerson = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegnRevo)) {
            return false;
        }
        return id != null && id.equals(((RegnRevo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegnRevo{" +
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
            "}";
    }
}
