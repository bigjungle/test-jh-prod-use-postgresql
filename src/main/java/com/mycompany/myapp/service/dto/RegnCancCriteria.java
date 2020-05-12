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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.RegnCanc} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RegnCancResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /regn-cancs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegnCancCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter descString;

    private StringFilter orgInfo;

    private StringFilter cancellationWay;

    private StringFilter cancellationReason;

    private LocalDateFilter cancellationTime;

    private StringFilter cancellationProof;

    private StringFilter remarks;

    private LongFilter ownerById;

    public RegnCancCriteria() {
    }

    public RegnCancCriteria(RegnCancCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.descString = other.descString == null ? null : other.descString.copy();
        this.orgInfo = other.orgInfo == null ? null : other.orgInfo.copy();
        this.cancellationWay = other.cancellationWay == null ? null : other.cancellationWay.copy();
        this.cancellationReason = other.cancellationReason == null ? null : other.cancellationReason.copy();
        this.cancellationTime = other.cancellationTime == null ? null : other.cancellationTime.copy();
        this.cancellationProof = other.cancellationProof == null ? null : other.cancellationProof.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.ownerById = other.ownerById == null ? null : other.ownerById.copy();
    }

    @Override
    public RegnCancCriteria copy() {
        return new RegnCancCriteria(this);
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

    public StringFilter getCancellationWay() {
        return cancellationWay;
    }

    public void setCancellationWay(StringFilter cancellationWay) {
        this.cancellationWay = cancellationWay;
    }

    public StringFilter getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(StringFilter cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public LocalDateFilter getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(LocalDateFilter cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    public StringFilter getCancellationProof() {
        return cancellationProof;
    }

    public void setCancellationProof(StringFilter cancellationProof) {
        this.cancellationProof = cancellationProof;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getOwnerById() {
        return ownerById;
    }

    public void setOwnerById(LongFilter ownerById) {
        this.ownerById = ownerById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegnCancCriteria that = (RegnCancCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(descString, that.descString) &&
            Objects.equals(orgInfo, that.orgInfo) &&
            Objects.equals(cancellationWay, that.cancellationWay) &&
            Objects.equals(cancellationReason, that.cancellationReason) &&
            Objects.equals(cancellationTime, that.cancellationTime) &&
            Objects.equals(cancellationProof, that.cancellationProof) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(ownerById, that.ownerById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        descString,
        orgInfo,
        cancellationWay,
        cancellationReason,
        cancellationTime,
        cancellationProof,
        remarks,
        ownerById
        );
    }

    @Override
    public String toString() {
        return "RegnCancCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (descString != null ? "descString=" + descString + ", " : "") +
                (orgInfo != null ? "orgInfo=" + orgInfo + ", " : "") +
                (cancellationWay != null ? "cancellationWay=" + cancellationWay + ", " : "") +
                (cancellationReason != null ? "cancellationReason=" + cancellationReason + ", " : "") +
                (cancellationTime != null ? "cancellationTime=" + cancellationTime + ", " : "") +
                (cancellationProof != null ? "cancellationProof=" + cancellationProof + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (ownerById != null ? "ownerById=" + ownerById + ", " : "") +
            "}";
    }

}
