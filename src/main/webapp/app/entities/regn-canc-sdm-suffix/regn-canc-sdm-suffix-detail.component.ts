import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';

@Component({
  selector: 'jhi-regn-canc-sdm-suffix-detail',
  templateUrl: './regn-canc-sdm-suffix-detail.component.html'
})
export class RegnCancSdmSuffixDetailComponent implements OnInit {
  regnCanc: IRegnCancSdmSuffix | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ regnCanc }) => (this.regnCanc = regnCanc));
  }

  previousState(): void {
    window.history.back();
  }
}
