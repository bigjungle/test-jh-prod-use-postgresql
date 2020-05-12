import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRegnCancSdmSuffix, RegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';
import { RegnCancSdmSuffixService } from './regn-canc-sdm-suffix.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-regn-canc-sdm-suffix-update',
  templateUrl: './regn-canc-sdm-suffix-update.component.html'
})
export class RegnCancSdmSuffixUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  cancellationTimeDp: any;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(256)]],
    descString: [null, [Validators.maxLength(256)]],
    orgInfo: [null, [Validators.maxLength(4000)]],
    cancellationWay: [null, [Validators.maxLength(256)]],
    cancellationReason: [null, [Validators.maxLength(256)]],
    cancellationTime: [],
    cancellationProof: [null, [Validators.maxLength(256)]],
    remarks: [null, [Validators.maxLength(256)]],
    ownerById: []
  });

  constructor(
    protected regnCancService: RegnCancSdmSuffixService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regnCanc }) => {
      this.updateForm(regnCanc);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(regnCanc: IRegnCancSdmSuffix): void {
    this.editForm.patchValue({
      id: regnCanc.id,
      name: regnCanc.name,
      descString: regnCanc.descString,
      orgInfo: regnCanc.orgInfo,
      cancellationWay: regnCanc.cancellationWay,
      cancellationReason: regnCanc.cancellationReason,
      cancellationTime: regnCanc.cancellationTime,
      cancellationProof: regnCanc.cancellationProof,
      remarks: regnCanc.remarks,
      ownerById: regnCanc.ownerById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const regnCanc = this.createFromForm();
    if (regnCanc.id !== undefined) {
      this.subscribeToSaveResponse(this.regnCancService.update(regnCanc));
    } else {
      this.subscribeToSaveResponse(this.regnCancService.create(regnCanc));
    }
  }

  private createFromForm(): IRegnCancSdmSuffix {
    return {
      ...new RegnCancSdmSuffix(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      descString: this.editForm.get(['descString'])!.value,
      orgInfo: this.editForm.get(['orgInfo'])!.value,
      cancellationWay: this.editForm.get(['cancellationWay'])!.value,
      cancellationReason: this.editForm.get(['cancellationReason'])!.value,
      cancellationTime: this.editForm.get(['cancellationTime'])!.value,
      cancellationProof: this.editForm.get(['cancellationProof'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      ownerById: this.editForm.get(['ownerById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRegnCancSdmSuffix>>): void {
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
