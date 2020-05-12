import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RegnCancSdmSuffixService } from './regn-canc-sdm-suffix.service';
import { RegnCancSdmSuffixDeleteDialogComponent } from './regn-canc-sdm-suffix-delete-dialog.component';

@Component({
  selector: 'jhi-regn-canc-sdm-suffix',
  templateUrl: './regn-canc-sdm-suffix.component.html'
})
export class RegnCancSdmSuffixComponent implements OnInit, OnDestroy {
  regnCancs: IRegnCancSdmSuffix[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected regnCancService: RegnCancSdmSuffixService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.regnCancs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.regnCancService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRegnCancSdmSuffix[]>) => this.paginateRegnCancs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.regnCancs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRegnCancs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRegnCancSdmSuffix): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRegnCancs(): void {
    this.eventSubscriber = this.eventManager.subscribe('regnCancListModification', () => this.reset());
  }

  delete(regnCanc: IRegnCancSdmSuffix): void {
    const modalRef = this.modalService.open(RegnCancSdmSuffixDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.regnCanc = regnCanc;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRegnCancs(data: IRegnCancSdmSuffix[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.regnCancs.push(data[i]);
      }
    }
  }
}
