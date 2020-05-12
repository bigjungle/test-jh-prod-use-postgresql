import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRegnRevoSdmSuffix, RegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';
import { RegnRevoSdmSuffixService } from './regn-revo-sdm-suffix.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-regn-revo-sdm-suffix-update',
  templateUrl: './regn-revo-sdm-suffix-update.component.html'
})
export class RegnRevoSdmSuffixUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  revokeStartDp: any;
  revokeOverDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(256)]],
    descString: [null, [Validators.maxLength(256)]],
    orgInfo: [null, [Validators.required, Validators.maxLength(4000)]],
    revokeTimeSpan: [null, [Validators.required]],
    revokeStart: [null, [Validators.required]],
    revokeOver: [null, [Validators.required]],
    punishOrg: [null, [Validators.maxLength(256)]],
    punishTime: [],
    facts: [null, [Validators.maxLength(256)]],
    autoProcess: [],
    revokeProof: [null, [Validators.maxLength(256)]],
    remarks: [null, [Validators.maxLength(256)]],
    punishPersonId: []
  });

  constructor(
    protected regnRevoService: RegnRevoSdmSuffixService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regnRevo }) => {
      if (!regnRevo.id) {
        const today = moment().startOf('day');
        regnRevo.punishTime = today;
      }

      this.updateForm(regnRevo);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(regnRevo: IRegnRevoSdmSuffix): void {
    this.editForm.patchValue({
      id: regnRevo.id,
      name: regnRevo.name,
      descString: regnRevo.descString,
      orgInfo: regnRevo.orgInfo,
      revokeTimeSpan: regnRevo.revokeTimeSpan,
      revokeStart: regnRevo.revokeStart,
      revokeOver: regnRevo.revokeOver,
      punishOrg: regnRevo.punishOrg,
      punishTime: regnRevo.punishTime ? regnRevo.punishTime.format(DATE_TIME_FORMAT) : null,
      facts: regnRevo.facts,
      autoProcess: regnRevo.autoProcess,
      revokeProof: regnRevo.revokeProof,
      remarks: regnRevo.remarks,
      punishPersonId: regnRevo.punishPersonId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const regnRevo = this.createFromForm();
    if (regnRevo.id !== undefined) {
      this.subscribeToSaveResponse(this.regnRevoService.update(regnRevo));
    } else {
      this.subscribeToSaveResponse(this.regnRevoService.create(regnRevo));
    }
  }

  private createFromForm(): IRegnRevoSdmSuffix {
    return {
      ...new RegnRevoSdmSuffix(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descString: this.editForm.get(['descString'])!.value,
      orgInfo: this.editForm.get(['orgInfo'])!.value,
      revokeTimeSpan: this.editForm.get(['revokeTimeSpan'])!.value,
      revokeStart: this.editForm.get(['revokeStart'])!.value,
      revokeOver: this.editForm.get(['revokeOver'])!.value,
      punishOrg: this.editForm.get(['punishOrg'])!.value,
      punishTime: this.editForm.get(['punishTime'])!.value ? moment(this.editForm.get(['punishTime'])!.value, DATE_TIME_FORMAT) : undefined,
      facts: this.editForm.get(['facts'])!.value,
      autoProcess: this.editForm.get(['autoProcess'])!.value,
      revokeProof: this.editForm.get(['revokeProof'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      punishPersonId: this.editForm.get(['punishPersonId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegnRevoSdmSuffix>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
