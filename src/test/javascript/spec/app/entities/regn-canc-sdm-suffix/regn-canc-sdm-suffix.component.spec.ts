import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { TestProdDatabaseTestModule } from '../../../test.module';
import { RegnCancSdmSuffixComponent } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix.component';
import { RegnCancSdmSuffixService } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix.service';
import { RegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';

describe('Component Tests', () => {
  describe('RegnCancSdmSuffix Management Component', () => {
    let comp: RegnCancSdmSuffixComponent;
    let fixture: ComponentFixture<RegnCancSdmSuffixComponent>;
    let service: RegnCancSdmSuffixService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestProdDatabaseTestModule],
        declarations: [RegnCancSdmSuffixComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(RegnCancSdmSuffixComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegnCancSdmSuffixComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegnCancSdmSuffixService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegnCancSdmSuffix(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.regnCancs && comp.regnCancs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegnCancSdmSuffix(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.regnCancs && comp.regnCancs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RegnCancSdmSuffix(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.regnCancs && comp.regnCancs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
  });
});
