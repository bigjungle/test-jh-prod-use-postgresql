import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestProdDatabaseTestModule } from '../../../test.module';
import { RegnCancSdmSuffixDetailComponent } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix-detail.component';
import { RegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';

describe('Component Tests', () => {
  describe('RegnCancSdmSuffix Management Detail Component', () => {
    let comp: RegnCancSdmSuffixDetailComponent;
    let fixture: ComponentFixture<RegnCancSdmSuffixDetailComponent>;
    const route = ({ data: of({ regnCanc: new RegnCancSdmSuffix(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestProdDatabaseTestModule],
        declarations: [RegnCancSdmSuffixDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegnCancSdmSuffixDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegnCancSdmSuffixDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load regnCanc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.regnCanc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
