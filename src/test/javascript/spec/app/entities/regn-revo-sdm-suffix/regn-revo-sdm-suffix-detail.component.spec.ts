import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestProdDatabaseTestModule } from '../../../test.module';
import { RegnRevoSdmSuffixDetailComponent } from 'app/entities/regn-revo-sdm-suffix/regn-revo-sdm-suffix-detail.component';
import { RegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';

describe('Component Tests', () => {
  describe('RegnRevoSdmSuffix Management Detail Component', () => {
    let comp: RegnRevoSdmSuffixDetailComponent;
    let fixture: ComponentFixture<RegnRevoSdmSuffixDetailComponent>;
    const route = ({ data: of({ regnRevo: new RegnRevoSdmSuffix(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestProdDatabaseTestModule],
        declarations: [RegnRevoSdmSuffixDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegnRevoSdmSuffixDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegnRevoSdmSuffixDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load regnRevo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.regnRevo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
