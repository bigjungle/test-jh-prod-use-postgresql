import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';
import { RegnCancSdmSuffixService } from './regn-canc-sdm-suffix.service';

@Component({
  templateUrl: './regn-canc-sdm-suffix-delete-dialog.component.html'
})
export class RegnCancSdmSuffixDeleteDialogComponent {
  regnCanc?: IRegnCancSdmSuffix;

  constructor(
    protected regnCancService: RegnCancSdmSuffixService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.regnCancService.delete(id).subscribe(() => {
      this.eventManager.broadcast('regnCancListModification');
      this.activeModal.close();
    });
  }
}
