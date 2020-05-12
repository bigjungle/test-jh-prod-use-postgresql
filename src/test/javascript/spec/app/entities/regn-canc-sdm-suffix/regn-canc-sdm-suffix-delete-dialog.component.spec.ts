import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TestProdDatabaseTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { RegnCancSdmSuffixDeleteDialogComponent } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix-delete-dialog.component';
import { RegnCancSdmSuffixService } from 'app/entities/regn-canc-sdm-suffix/regn-canc-sdm-suffix.service';

describe('Component Tests', () => {
  describe('RegnCancSdmSuffix Management Delete Component', () => {
    let comp: RegnCancSdmSuffixDeleteDialogComponent;
    let fixture: ComponentFixture<RegnCancSdmSuffixDeleteDialogComponent>;
    let service: RegnCancSdmSuffixService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestProdDatabaseTestModule],
        declarations: [RegnCancSdmSuffixDeleteDialogComponent]
      })
        .overrideTemplate(RegnCancSdmSuffixDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegnCancSdmSuffixDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegnCancSdmSuffixService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
