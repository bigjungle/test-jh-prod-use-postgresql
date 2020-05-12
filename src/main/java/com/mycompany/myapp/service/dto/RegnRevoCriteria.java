package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.RegnRevo} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RegnRevoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /regn-revos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegnRevoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descString;

    private StringFilter orgInfo;

    private IntegerFilter revokeTimeSpan;

    private LocalDateFilter revokeStart;

    private LocalDateFilter revokeOver;

    private StringFilter punishOrg;

    private InstantFilter punishTime;

    private StringFilter facts;

    private BooleanFilter autoProcess;

    private StringFilter revokeProof;

    private StringFilter remarks;

    private LongFilter punishPersonId;

    public RegnRevoCriteria() {
    }

    public RegnRevoCriteria(RegnRevoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descString = other.descString == null ? null : other.descString.copy();
        this.orgInfo = other.orgInfo == null ? null : other.orgInfo.copy();
        this.revokeTimeSpan = other.revokeTimeSpan == null ? null : other.revokeTimeSpan.copy();
        this.revokeStart = other.revokeStart == null ? null : other.revokeStart.copy();
        this.revokeOver = other.revokeOver == null ? null : other.revokeOver.copy();
        this.punishOrg = other.punishOrg == null ? null : other.punishOrg.copy();
        this.punishTime = other.punishTime == null ? null : other.punishTime.copy();
        this.facts = other.facts == null ? null : other.facts.copy();
        this.autoProcess = other.autoProcess == null ? null : other.autoProcess.copy();
        this.revokeProof = other.revokeProof == null ? null : other.revokeProof.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.punishPersonId = other.punishPersonId == null ? null : other.punishPersonId.copy();
    }

    @Override
    public RegnRevoCriteria copy() {
        return new RegnRevoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescString() {
        return descString;
    }

    public void setDescString(StringFilter descString) {
        this.descString = descString;
    }

    public StringFilter getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(StringFilter orgInfo) {
        this.orgInfo = orgInfo;
    }

    public IntegerFilter getRevokeTimeSpan() {
        return revokeTimeSpan;
    }

    public void setRevokeTimeSpan(IntegerFilter revokeTimeSpan) {
        this.revokeTimeSpan = revokeTimeSpan;
    }

    public LocalDateFilter getRevokeStart() {
        return revokeStart;
    }

    public void setRevokeStart(LocalDateFilter revokeStart) {
        this.revokeStart = revokeStart;
    }

    public LocalDateFilter getRevokeOver() {
        return revokeOver;
    }

    public void setRevokeOver(LocalDateFilter revokeOver) {
        this.revokeOver = revokeOver;
    }

    public StringFilter getPunishOrg() {
        return punishOrg;
    }

    public void setPunishOrg(StringFilter punishOrg) {
        this.punishOrg = punishOrg;
    }

    public InstantFilter getPunishTime() {
        return punishTime;
    }

    public void setPunishTime(InstantFilter punishTime) {
        this.punishTime = punishTime;
    }

    public StringFilter getFacts() {
        return facts;
    }

    public void setFacts(StringFilter facts) {
        this.facts = facts;
    }

    public BooleanFilter getAutoProcess() {
        return autoProcess;
    }

    public void setAutoProcess(BooleanFilter autoProcess) {
        this.autoProcess = autoProcess;
    }

    public StringFilter getRevokeProof() {
        return revokeProof;
    }

    public void setRevokeProof(StringFilter revokeProof) {
        this.revokeProof = revokeProof;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getPunishPersonId() {
        return punishPersonId;
    }

    public void setPunishPersonId(LongFilter punishPersonId) {
        this.punishPersonId = punishPersonId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegnRevoCriteria that = (RegnRevoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(orgInfo, that.orgInfo) &&
            Objects.equals(revokeTimeSpan, that.revokeTimeSpan) &&
            Objects.equals(revokeStart, that.revokeStart) &&
            Objects.equals(revokeOver, that.revokeOver) &&
            Objects.equals(punishOrg, that.punishOrg) &&
            Objects.equals(punishTime, that.punishTime) &&
            Objects.equals(facts, that.facts) &&
            Objects.equals(autoProcess, that.autoProcess) &&
            Objects.equals(revokeProof, that.revokeProof) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(punishPersonId, that.punishPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descString,
        orgInfo,
        revokeTimeSpan,
        revokeStart,
        revokeOver,
        punishOrg,
        punishTime,
        facts,
        autoProcess,
        revokeProof,
        remarks,
        punishPersonId
        );
    }

    @Override
    public String toString() {
        return "RegnRevoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (orgInfo != null ? "orgInfo=" + orgInfo + ", " : "") +
                (revokeTimeSpan != null ? "revokeTimeSpan=" + revokeTimeSpan + ", " : "") +
                (revokeStart != null ? "revokeStart=" + revokeStart + ", " : "") +
                (revokeOver != null ? "revokeOver=" + revokeOver + ", " : "") +
                (punishOrg != null ? "punishOrg=" + punishOrg + ", " : "") +
                (punishTime != null ? "punishTime=" + punishTime + ", " : "") +
                (facts != null ? "facts=" + facts + ", " : "") +
                (autoProcess != null ? "autoProcess=" + autoProcess + ", " : "") +
                (revokeProof != null ? "revokeProof=" + revokeProof + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (punishPersonId != null ? "punishPersonId=" + punishPersonId + ", " : "") +
            "}";
    }

}
