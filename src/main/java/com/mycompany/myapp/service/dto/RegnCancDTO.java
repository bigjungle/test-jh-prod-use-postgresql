package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.RegnCanc} entity.
 */
@ApiModel(description = "Training institution registration cancellation 培训机构登记注销表\n@author JasonYang")
public class RegnCancDTO implements Serializable {

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
     * 培训机构信息
     */
    @Size(max = 4000)
    @ApiModelProperty(value = "培训机构信息")
    private String orgInfo;

    /**
     * 注销方式(主动,被动)
     */
    @Size(max = 256)
    @ApiModelProperty(value = "注销方式(主动,被动)")
    private String cancellationWay;

    /**
     * 注销原因
     */
    @Size(max = 256)
    @ApiModelProperty(value = "注销原因")
    private String cancellationReason;

    /**
     * 注销时间
     */
    @ApiModelProperty(value = "注销时间")
    private LocalDate cancellationTime;

    /**
     * 注销凭证(存储路径)
     */
    @Size(max = 256)
    @ApiModelProperty(value = "注销凭证(存储路径)")
    private String cancellationProof;

    /**
     * 备注
     */
    @Size(max = 256)
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")

    private Long ownerById;

    private String ownerByLastName;

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

    public String getCancellationWay() {
        return cancellationWay;
    }

    public void setCancellationWay(String cancellationWay) {
        this.cancellationWay = cancellationWay;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public LocalDate getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(LocalDate cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    public String getCancellationProof() {
        return cancellationProof;
    }

    public void setCancellationProof(String cancellationProof) {
        this.cancellationProof = cancellationProof;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getOwnerById() {
        return ownerById;
    }

    public void setOwnerById(Long userId) {
        this.ownerById = userId;
    }

    public String getOwnerByLastName() {
        return ownerByLastName;
    }

    public void setOwnerByLastName(String userLastName) {
        this.ownerByLastName = userLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegnCancDTO regnCancDTO = (RegnCancDTO) o;
        if (regnCancDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), regnCancDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegnCancDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", descString='" + getDescString() + "'" +
            ", orgInfo='" + getOrgInfo() + "'" +
            ", cancellationWay='" + getCancellationWay() + "'" +
            ", cancellationReason='" + getCancellationReason() + "'" +
            ", cancellationTime='" + getCancellationTime() + "'" +
            ", cancellationProof='" + getCancellationProof() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", ownerById=" + getOwnerById() +
            ", ownerByLastName='" + getOwnerByLastName() + "'" +
            "}";
    }
}
