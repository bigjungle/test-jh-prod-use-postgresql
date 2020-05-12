import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';
import { RegnRevoSdmSuffixService } from './regn-revo-sdm-suffix.service';

@Component({
  templateUrl: './regn-revo-sdm-suffix-delete-dialog.component.html'
})
export class RegnRevoSdmSuffixDeleteDialogComponent {
  regnRevo?: IRegnRevoSdmSuffix;

  constructor(
    protected regnRevoService: RegnRevoSdmSuffixService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.regnRevoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('regnRevoListModification');
      this.activeModal.close();
    });
  }
}
