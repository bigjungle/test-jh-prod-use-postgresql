import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';

@Component({
  selector: 'jhi-regn-revo-sdm-suffix-detail',
  templateUrl: './regn-revo-sdm-suffix-detail.component.html'
})
export class RegnRevoSdmSuffixDetailComponent implements OnInit {
  regnRevo: IRegnRevoSdmSuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regnRevo }) => (this.regnRevo = regnRevo));
  }

  previousState(): void {
    window.history.back();
  }
}
