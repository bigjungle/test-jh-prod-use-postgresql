import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestProdDatabaseTestModule } from '../../../test.module';
import { RegnRevoSdmSuffixUpdateComponent } from 'app/entities/regn-revo-sdm-suffix/regn-revo-sdm-suffix-update.component';
import { RegnRevoSdmSuffixService } from 'app/entities/regn-revo-sdm-suffix/regn-revo-sdm-suffix.service';
import { RegnRevoSdmSuffix } from 'app/shared/model/regn-revo-sdm-suffix.model';

describe('Component Tests', () => {
  describe('RegnRevoSdmSuffix Management Update Component', () => {
    let comp: RegnRevoSdmSuffixUpdateComponent;
    let fixture: ComponentFixture<RegnRevoSdmSuffixUpdateComponent>;
    let service: RegnRevoSdmSuffixService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestProdDatabaseTestModule],
        declarations: [RegnRevoSdmSuffixUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegnRevoSdmSuffixUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegnRevoSdmSuffixUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegnRevoSdmSuffixService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegnRevoSdmSuffix(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegnRevoSdmSuffix();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
