import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestProdDatabaseTestModule } from '../../../test.module';
import { RegnCancSdmSuffixUpdateComponent } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix-update.component';
import { RegnCancSdmSuffixService } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix.service';
import { RegnCancSdmSuffix } from 'app/shared/model/regn-canc-sdm-suffix.model';

describe('Component Tests', () => {
  describe('RegnCancSdmSuffix Management Update Component', () => {
    let comp: RegnCancSdmSuffixUpdateComponent;
    let fixture: ComponentFixture<RegnCancSdmSuffixUpdateComponent>;
    let service: RegnCancSdmSuffixService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestProdDatabaseTestModule],
        declarations: [RegnCancSdmSuffixUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegnCancSdmSuffixUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegnCancSdmSuffixUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegnCancSdmSuffixService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegnCancSdmSuffix(123);
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
        const entity = new RegnCancSdmSuffix();
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
